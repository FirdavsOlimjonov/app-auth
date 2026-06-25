use std::env;
use std::env::{current_dir, set_current_dir, split_paths, var};
#[warn(unused_imports)]
use std::fs::{metadata, write};
#[warn(unused_imports)]
use std::io::{self, Write, stderr, stdin, stdout};
use std::mem::take;
use std::path::{Path, PathBuf};
use std::process::Command;

#[cfg(unix)]
use std::os::unix::fs::MetadataExt;
#[cfg(unix)]
use std::os::unix::process::CommandExt;

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

fn main() {
    loop {
        print!("$ ");
        stdout().flush().unwrap();

        let mut input = String::new();
        stdin().read_line(&mut input).unwrap();

        let args = split(&input);

        if args.is_empty() {
            continue;
        }

        let refs: Vec<&str> = args.iter().map(|s| s.as_str()).collect();

        let ExecOutput { out, err, exit } = exec(&refs);

        stdout().write_all(&out).ok();
        stderr().write_all(&err).ok();

        if exit {
            break;
        }
    }
}

fn exec(args: &[&str]) -> ExecOutput {
    match *args {
        [] => ExecOutput::new(),

        [ref head @ .., ">" | "1>" | ">>"  | "1>>" , path] => {
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
