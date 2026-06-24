use std::env;
#[allow(unused_imports)]
use std::io::{self, Write};
use std::path::Path;
use std::process::Command;

fn main() {
    const BUILTINS: [&str; 3] = ["type", "echo", "exit"];

    loop {
        print!("$ ");
        io::stdout().flush().unwrap();

        let mut input = String::new();
        io::stdin().read_line(&mut input).unwrap();

        let input = input.trim();

        let mut parts = input.split_whitespace();
        let command = parts.next().unwrap_or("");

        match command {
            "exit" => break,

            "echo" => {
                let output = parts.collect::<Vec<_>>().join(" ");
                println!("{output}");
            }

            "type" => {
                let arg = parts.next().unwrap_or("");

                if BUILTINS.contains(&arg) {
                    println!("{arg} is a shell builtin");
                } else if let Some(path) = find_executable(arg) {
                    println!("{arg} is {path}");
                } else {
                    println!("{arg}: not found");
                }
            }

            "" => {}

            _ => {
                if let Some(path) = find_executable(command) {
                    let mut child = Command::new(command)
                        .args(parts)
                        .current_dir(std::env::current_dir().unwrap())
                        .spawn()
                        .expect("failed to execute command");

                    child.wait().unwrap();
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
}
