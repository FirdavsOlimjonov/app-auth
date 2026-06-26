#[derive(Debug, PartialEq)]
pub struct ExecOutput {
    pub out: Vec<u8>,
    pub err: Vec<u8>,
    pub exit: bool,
}

impl ExecOutput {
    pub fn new() -> Self {
        Self {
            out: vec![],
            err: vec![],
            exit: false,
        }
    }

    pub fn exit() -> Self {
        Self {
            out: vec![],
            err: vec![],
            exit: true,
        }
    }

    pub fn err(msg: impl Into<String>) -> Self {
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