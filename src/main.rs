use std::env;
#[allow(unused_imports)]
use std::io::{self, Write};
use std::path::Path;
use std::process::Command;

fn main() {
    const BUILTINS: [&str; 5] = ["type", "echo", "exit", "pwd", "cd"];

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

            "pwd" => {
                // Correctly fetches the current working directory variable dynamically
                let current_working_directory = env::current_dir().unwrap();

                println!("{}", current_working_directory.display());
            }

            "cd" => {
                let new_path = parts.next().unwrap_or("");
                if !change_directory(new_path) {
                    println!("cd: {}: No such file or directory", new_path);
                };
            }

            "" => {}

            _ => {
                if let Some(_path) = find_executable(command) {
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

    fn change_directory(target_path: &str) -> bool {
        // 1. Convert the string into a Path slice
        let path = Path::new(target_path);

        // 2. Attempt to change the process's working directory
        match env::set_current_dir(&path) {
            Ok(_) => {
                // Success! Next time you call env::current_dir(), it will reflect this change.
                // println!("Directory successfully changed to: {}", target_path);
                true
            }
            Err(_err) => {
                // Handles missing folders or permission issues (e.g., "cd: /invalid: No such file or directory")
                // eprintln!("cd: {}", target_path);
                false
            }
        }
    }
}
