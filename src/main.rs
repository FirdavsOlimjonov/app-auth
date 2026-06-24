use std::env;
#[allow(unused_imports)]
use std::io::{self, Write};

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
                println!("{input}: command not found");
            }
        }
    }

    fn find_executable(command: &str) -> Option<String> {
        let path_var = env::var("PATH").ok()?;

        for dir in env::split_paths(&path_var) {
            let full_path = dir.join(command);

            if full_path.is_file() {
                return Some(full_path.to_string_lossy().into_owned());
            }

            #[cfg(windows)]
            {
                let exe_path = dir.join(format!("{}.exe", command));
                if exe_path.is_file() {
                    return Some(exe_path.to_string_lossy().into_owned());
                }
            }
        }

        None
    }
}
