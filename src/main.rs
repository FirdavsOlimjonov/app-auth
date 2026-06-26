use std::env;
use std::env::{current_dir, set_current_dir, split_paths, var};
use std::fs::OpenOptions;
#[warn(unused_imports)]
use std::fs::{metadata, write};
#[warn(unused_imports)]
use std::io::{self, Write, stderr, stdout};
use std::mem::take;
use std::path::{Path, PathBuf};
use std::process::Command;

#[cfg(unix)]
use std::os::unix::fs::MetadataExt;
#[cfg(unix)]
use std::os::unix::process::CommandExt;

use std::collections::BTreeSet;
use std::sync::Mutex;

use rustyline::completion::{Completer, Pair};
use rustyline::error::ReadlineError;
use rustyline::highlight::Highlighter;
use rustyline::hint::Hinter;
use rustyline::validate::Validator;
use rustyline::{
    Cmd, ConditionalEventHandler, Editor, Event, EventContext, EventHandler, Helper, KeyCode,
    KeyEvent, Modifiers, RepeatCount,
};

#[derive(Debug, PartialEq)]
struct ExecOutput {
    out: Vec<u8>,
    err: Vec<u8>,
    exit: bool,
}

impl ExecOutput {
    fn new() -> Self {
        Self {
            out: vec![],
            err: vec![],
            exit: false,
        }
    }

    fn exit() -> Self {
        Self {
            out: vec![],
            err: vec![],
            exit: true,
        }
    }

    fn err(msg: impl Into<String>) -> Self {
        Self {
            out: vec![],
            err: msg.into().into_bytes(),
            exit: false,
        }
    }
}

impl From<String> for ExecOutput {
    fn from(value: String) -> Self {
        Self {
            out: value.into_bytes(),
            err: vec![],
            exit: false,
        }
    }
}

impl From<std::process::Output> for ExecOutput {
    fn from(output: std::process::Output) -> Self {
        Self {
            out: output.stdout,
            err: output.stderr,
            exit: false,
        }
    }
}

const BUILTINS: [&str; 5] = ["type", "echo", "exit", "pwd", "cd"];
const COMPLETABLE_BUILTINS: [&str; 2] = ["echo", "exit"];

fn collect_candidates(prefix: &str) -> Vec<String> {
    let mut names: BTreeSet<String> = BTreeSet::new();

    for cmd in COMPLETABLE_BUILTINS {
        if cmd.starts_with(prefix) {
            names.insert(cmd.to_string());
        }
    }

    if let Ok(path_var) = env::var("PATH") {
        for dir in split_paths(&path_var) {
            let Ok(entries) = std::fs::read_dir(&dir) else {
                continue;
            };

            for entry in entries.flatten() {
                let file_name = entry.file_name();
                let Some(name) = file_name.to_str() else {
                    continue;
                };

                if !name.starts_with(prefix) {
                    continue;
                }

                let full_path = entry.path();
                if full_path.is_file() && is_executable(&full_path) {
                    names.insert(name.to_string());
                }
            }
        }
    }

    names.into_iter().collect()
}

fn longest_common_prefix(names: &[String]) -> String {
    let Some(first) = names.first() else {
        return String::new();
    };

    let mut prefix_len = first.len();

    for name in &names[1..] {
        let common = first
            .bytes()
            .zip(name.bytes())
            .take_while(|(a, b)| a == b)
            .count();
        prefix_len = prefix_len.min(common);
    }

    first[..prefix_len].to_string()
}

struct TabCompleteHandler {
    last_ambiguous_prefix: Mutex<Option<String>>,
}

impl TabCompleteHandler {
    fn new() -> Self {
        Self {
            last_ambiguous_prefix: Mutex::new(None),
        }
    }
}

impl ConditionalEventHandler for TabCompleteHandler {
    fn handle(
        &self,
        _evt: &Event,
        _n: RepeatCount,
        _positive: bool,
        ctx: &EventContext<'_>,
    ) -> Option<Cmd> {
        let line = ctx.line();
        let pos = ctx.pos();
        let prefix = &line[..pos];

        if prefix.contains(' ') {
            *self.last_ambiguous_prefix.lock().unwrap() = None;
            return None;
        }

        let candidates = collect_candidates(prefix);

        if candidates.is_empty() {
            *self.last_ambiguous_prefix.lock().unwrap() = None;
            ring_bell();
            return Some(Cmd::Noop);
        }

        if candidates.len() == 1 {
            *self.last_ambiguous_prefix.lock().unwrap() = None;
            let suffix = format!("{} ", &candidates[0][prefix.len()..]);
            return Some(insert_suffix(&suffix));
        }

        let common = longest_common_prefix(&candidates);

        if common.len() > prefix.len() {
            *self.last_ambiguous_prefix.lock().unwrap() = None;
            let suffix = &common[prefix.len()..];
            return Some(insert_suffix(suffix));
        }

        let mut last = self.last_ambiguous_prefix.lock().unwrap();

        if last.as_deref() == Some(prefix) {
            *last = None;
            drop(last);
            print_candidates(&candidates);
            return Some(Cmd::Repaint);
        }

        *last = Some(prefix.to_string());
        drop(last);
        ring_bell();
        Some(Cmd::Noop)
    }
}

fn ring_bell() {
    print!("\x07");
    stdout().flush().ok();
}

fn insert_suffix(suffix: &str) -> Cmd {
    Cmd::Insert(1, suffix.to_string())
}

fn print_candidates(candidates: &[String]) {
    println!();
    println!("{}", candidates.join("  "));
}

struct NoopHelper;

impl Completer for NoopHelper {
    type Candidate = Pair;
}

impl Hinter for NoopHelper {
    type Hint = String;
}

impl Highlighter for NoopHelper {}

impl Validator for NoopHelper {}

impl Helper for NoopHelper {}

fn main() {
    let mut rl = Editor::<NoopHelper, rustyline::history::DefaultHistory>::new()
        .expect("failed to initialize line editor");
    rl.set_helper(Some(NoopHelper));

    rl.bind_sequence(
        KeyEvent(KeyCode::Tab, Modifiers::NONE),
        EventHandler::Conditional(Box::new(TabCompleteHandler::new())),
    );

    loop {
        let readline = rl.readline("$ ");

        let input = match readline {
            Ok(line) => line,
            Err(ReadlineError::Eof) | Err(ReadlineError::Interrupted) => break,
            Err(_) => break,
        };

        let args = split(&input);

        if args.is_empty() {
            continue;
        }

        let refs: Vec<&str> = args.iter().map(|s| s.as_str()).collect();

        let ExecOutput { out, err, exit } = exec(&refs);

        stdout().write_all(&out).ok();
        stderr().write_all(&err).ok();
        stdout().flush().ok();

        if exit {
            break;
        }
    }
}

fn exec(args: &[&str]) -> ExecOutput {
    match *args {
        [] => ExecOutput::new(),

        [ref head @ .., ">>" | "1>>", path] => {
            let mut output = exec(head);
            append_file(path, &take(&mut output.out));
            output
        }

        [ref head @ .., "2>>", path] => {
            let mut output = exec(head);
            append_file(path, &take(&mut output.err));
            output
        }

        [ref head @ .., ">" | "1>", path] => {
            let mut output = exec(head);
            write(path, take(&mut output.out)).ok();
            output
        }

        [ref head @ .., "2>", path] => {
            let mut output = exec(head);
            write(path, take(&mut output.err)).ok();
            output
        }

        ["exit", ..] => ExecOutput::exit(),

        ["echo", ref rest @ ..] => format!("{}\n", rest.join(" ")).into(),

        ["pwd"] => format!("{}\n", current_dir().unwrap().display()).into(),

        ["cd", path] => {
            let result = if path == "~" {
                var("HOME")
                    .or_else(|_| var("USERPROFILE"))
                    .ok()
                    .and_then(|p| set_current_dir(p).ok())
            } else {
                set_current_dir(path).ok()
            };

            match result {
                Some(_) => ExecOutput::new(),
                None => ExecOutput::err(format!("cd: {}: No such file or directory\n", path)),
            }
        }

        ["type", cmd] => {
            if BUILTINS.contains(&cmd) {
                format!("{cmd} is a shell builtin\n").into()
            } else if let Some(path) = find_executable(cmd) {
                format!("{cmd} is {}\n", path.display()).into()
            } else {
                ExecOutput::err(format!("{cmd}: not found\n"))
            }
        }

        [cmd, ref cmd_args @ ..] => {
            if let Some(path) = find_executable(cmd) {
                let mut command = Command::new(&path);

                #[cfg(unix)]
                command.arg0(cmd);

                match command.args(cmd_args).output() {
                    Ok(output) => output.into(),
                    Err(_) => ExecOutput::err(format!("{cmd}: command not found\n")),
                }
            } else {
                ExecOutput::err(format!("{cmd}: command not found\n"))
            }
        }
    }
}

fn split(input: &str) -> Vec<String> {
    let mut args = Vec::new();
    let mut current = String::new();

    #[derive(Clone, Copy, PartialEq)]
    enum State {
        None,
        Single,
        Double,
    }

    let mut state = State::None;
    let chars: Vec<char> = input.trim_end().chars().collect();

    let mut iter = &chars[..];

    while !iter.is_empty() {
        iter = match (iter, state) {
            (['\'', tail @ ..], State::None) => {
                state = State::Single;
                tail
            }

            (['\'', tail @ ..], State::Single) => {
                state = State::None;
                tail
            }

            ([c, tail @ ..], State::Single) => {
                current.push(*c);
                tail
            }

            (['"', tail @ ..], State::None) => {
                state = State::Double;
                tail
            }

            (['"', tail @ ..], State::Double) => {
                state = State::None;
                tail
            }

            (['\\', c @ ('"' | '\\' | '$' | '`' | '\n'), tail @ ..], State::Double) => {
                current.push(*c);
                tail
            }

            ([c, tail @ ..], State::Double) => {
                current.push(*c);
                tail
            }

            (['\\', c, tail @ ..], _) => {
                current.push(*c);
                tail
            }

            ([c, tail @ ..], _) if c.is_whitespace() => {
                if !current.is_empty() {
                    args.push(take(&mut current));
                }
                tail
            }

            ([c, tail @ ..], _) => {
                current.push(*c);
                tail
            }

            _ => unreachable!(),
        };
    }

    if !current.is_empty() {
        args.push(current);
    }

    args
}

#[cfg(unix)]
fn is_executable(path: &Path) -> bool {
    metadata(path)
        .map(|m| m.mode() & 0o111 != 0)
        .unwrap_or(false)
}

#[cfg(not(unix))]
fn is_executable(path: &Path) -> bool {
    path.is_file()
}

fn find_executable(cmd: &str) -> Option<PathBuf> {
    let path_var = env::var("PATH").ok()?;

    for dir in split_paths(&path_var) {
        let candidate = dir.join(cmd);

        if candidate.is_file() && is_executable(&candidate) {
            return Some(candidate);
        }
    }

    None
}

fn append_file(path: &str, data: &[u8]) {
    if let Ok(mut file) = OpenOptions::new().create(true).append(true).open(path) {
        let _ = file.write_all(data);
    }
}
