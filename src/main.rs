use std::env;
#[allow(unused_imports)]
use std::io::{self, Write};
use std::path::Path;
use std::process::Command;

#[cfg(unix)]
use std::os::unix::process::CommandExt;

fn main() {
    const BUILTINS: [&str; 5] = ["type", "echo", "exit", "pwd", "cd"];

    loop {
        print!("$ ");
        io::stdout().flush().unwrap();

        let mut input = String::new();
        io::stdin().read_line(&mut input).unwrap();

        let input = input.trim();

        let parts = parse_command(input);

        if parts.is_empty() {
            continue;
        }

        let command = parts[0].as_str();
        let args = &parts[1..];

        match command {
            "exit" => break,

            "echo" => {
                println!("{}", args.join(" "));
            }

            "type" => {
                let arg = args.first().map(|s| s.as_str()).unwrap_or("");

                if BUILTINS.contains(&arg) {
                    println!("{arg} is a shell builtin");
                } else if let Some(path) = find_executable(arg) {
                    println!("{arg} is {path}");
                } else {
                    println!("{arg}: not found");
                }
            }

            "pwd" => {
                // Correctly fetches the current working directory variable dynamically
                let current_working_directory = env::current_dir().unwrap();

                println!("{}", current_working_directory.display());
            }

            "cd" => {
                let new_path = args.first().map(|s| s.as_str()).unwrap_or("");
                change_directory(new_path);
            }

            "" => {}

            _ => {
                if let Some(path) = find_executable(command) {
                    let mut child = Command::new(&path);

                    #[cfg(unix)]
                    child.arg0(command);

                    child
                        .args(args)
                        .spawn()
                        .expect("failed to execute command")
                        .wait()
                        .unwrap();
                } else {
                    println!("{command}: command not found");
                }
            }
        }
    }

    #[cfg(unix)]
    fn is_executable(path: &Path) -> bool {
        use std::os::unix::fs::PermissionsExt;

        std::fs::metadata(path)
            .map(|m| m.permissions().mode() & 0o111 != 0)
            .unwrap_or(false)
    }

    #[cfg(not(unix))]
    fn is_executable(path: &Path) -> bool {
        path.is_file()
    }

    fn find_executable(cmd: &str) -> Option<String> {
        let path_var = env::var("PATH").ok()?;

        for dir in env::split_paths(&path_var) {
            let candidate = dir.join(cmd);

            if candidate.is_file() && is_executable(&candidate) {
                return Some(candidate.to_string_lossy().into_owned());
            }
        }

        None
    }

    fn change_directory(target_path: &str) {
        if target_path == "~" {
            let home_var = env::var("HOME").or_else(|_| env::var("USERPROFILE"));

            match home_var {
                Ok(path) => set_target_path(&path),
                Err(_) => eprintln!("Could not find the home directory environment variable."),
            }

            return;
        }

        set_target_path(target_path);
    }
    fn set_target_path(target_path: &str) {
        // 1. Convert the string into a Path slice
        let path = Path::new(target_path);

        // 2. Attempt to change the process's working directory
        match env::set_current_dir(&path) {
            Ok(_) => {
                // Success! Next time you call env::current_dir(), it will reflect this change.
                // println!("Directory successfully changed to: {}", target_path);
            }
            Err(_err) => {
                // Handles missing folders or permission issues (e.g., "cd: /invalid: No such file or directory")
                // eprintln!("cd: {}", target_path);
                println!("cd: {}: No such file or directory", target_path);
            }
        }
    }

    fn parse_command(input: &str) -> Vec<String> {
        let mut args = Vec::new();
        let mut current = String::new();

        #[derive(PartialEq)]
        enum QuoteState {
            None,
            Single,
            Double,
        }

        let mut state = QuoteState::None;

        for ch in input.chars() {
            match ch {
                '\'' if state == QuoteState::None => {
                    state = QuoteState::Single;
                }
                '\'' if state == QuoteState::Single => {
                    state = QuoteState::None;
                }

                '"' if state == QuoteState::None => {
                    state = QuoteState::Double;
                }
                '"' if state == QuoteState::Double => {
                    state = QuoteState::None;
                }

                ' ' | '\t' if state == QuoteState::None => {
                    if !current.is_empty() {
                        args.push(std::mem::take(&mut current));
                    }
                }

                _ => current.push(ch),
            }
        }

        if !current.is_empty() {
            args.push(current);
        }

        args
    }
}
