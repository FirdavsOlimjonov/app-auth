#[allow(unused_imports)]
use std::io::{self, Write};

fn main() {
    // TODO: Uncomment the code below to pass the first stage
    loop {
        print!("$ ");
        io::stdout().flush().unwrap();

        // Wait for user input
        let mut command = String::new();
        io::stdin().read_line(&mut command).unwrap();

        command = command.trim().to_string();
        if command == "exit" {
            break;
        }

        if command.starts_with("echo") {
            println!("{}", &command[4..].trim());
            continue;
        }

        let builtins = vec!["type", "echo", "exit"];
        if command.starts_with("type") {
            if builtins.contains(&command[5..].trim()) {
                println!("{} is a shell builtin", &command[5..].trim());
                continue;
            }
            println!("{}: command not found", &command[5..].trim());
            continue;
        }
        println!("{}: command not found", command.trim());
    }
}
