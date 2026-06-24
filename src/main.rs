#[allow(unused_imports)]
use std::io::{self, Write};

fn main() {
    // TODO: Uncomment the code below to pass the first stage
    io::stdin().read_line(&mut command).unwrap();
    print!("$ ");
    io::stdout().flush().unwrap();
}
