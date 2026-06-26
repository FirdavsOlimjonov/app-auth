use std::env;
use std::env::split_paths;
use std::fs::{metadata, write, OpenOptions};
use std::io::Write;
use std::path::{Path, PathBuf};

#[cfg(unix)]
use std::os::unix::fs::MetadataExt;

#[cfg(unix)]
pub fn is_executable(path: &Path) -> bool {
    metadata(path)
        .map(|m| m.mode() & 0o111 != 0)
        .unwrap_or(false)
}

#[cfg(not(unix))]
pub fn is_executable(path: &Path) -> bool {
    path.is_file()
}

pub fn find_executable(cmd: &str) -> Option<PathBuf> {
    let path_var = env::var("PATH").ok()?;

    for dir in split_paths(&path_var) {
        let candidate = dir.join(cmd);

        if candidate.is_file() && is_executable(&candidate) {
            return Some(candidate);
        }
    }

    None
}

pub fn append_file(path: &str, data: &[u8]) {
    if let Ok(mut file) = OpenOptions::new().create(true).append(true).open(path) {
        let _ = file.write_all(data);
    }
}

pub fn write_file(path: &str, data: Vec<u8>) {
    write(path, data).ok();
}