mod completion;
mod exec_output;
mod executor;
mod path_utils;

use std::io::{self, stderr, stdout, Write};

use rustyline::error::ReadlineError;
use rustyline::{Editor, EventHandler, KeyCode, KeyEvent, Modifiers};

use completion::{NoopHelper, TabCompleteHandler};
use exec_output::ExecOutput;
use executor::{exec, split};

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