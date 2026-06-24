#[allow(unused_imports)]
use std::io::{self, Write};

fn main() {
    // TODO: Uncomment the code below to pass the first stage
    loop {
        print!("$ ");
        io::stdout().flush().unwrap();

        // Wait for user input
        let mut command = String::new();
        command = command.trim().to_string();
        if command == "exit" {
            break;
        }
        io::stdin().read_line(&mut command).unwrap();
        println!("{}: command not found", command.trim());
    }
}
