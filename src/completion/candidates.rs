use std::collections::BTreeSet;
use std::env;
use std::env::split_paths;

use crate::path_utils::is_executable;

const COMPLETABLE_BUILTINS: [&str; 2] = ["echo", "exit"];

/// Collects every command name (builtin or executable on $PATH) that
/// starts with `prefix`, deduplicated and sorted alphabetically.
pub fn collect_candidates(prefix: &str) -> Vec<String> {
    let mut names: BTreeSet<String> = BTreeSet::new();

    for cmd in COMPLETABLE_BUILTINS {
        if cmd.starts_with(prefix) {
            names.insert(cmd.to_string());
        }
    }

    if let Ok(path_var) = env::var("PATH") {
        for dir in split_paths(&path_var) {
            let Ok(entries) = std::fs::read_dir(&dir) else {
                continue;
            };

            for entry in entries.flatten() {
                let file_name = entry.file_name();
                let Some(name) = file_name.to_str() else {
                    continue;
                };

                if !name.starts_with(prefix) {
                    continue;
                }

                let full_path = entry.path();
                if full_path.is_file() && is_executable(&full_path) {
                    names.insert(name.to_string());
                }
            }
        }
    }

    names.into_iter().collect()
}


/// Collects every filename in the current directory that starts
/// with `prefix`, deduplicated and sorted alphabetically.
pub fn collect_filename_candidates(prefix: &str) -> Vec<String> {
    let mut names: BTreeSet<String> = BTreeSet::new();

    let Ok(entries) = std::fs::read_dir(".") else {
        return Vec::new();
    };

    for entry in entries.flatten() {
        let file_name = entry.file_name();
        let Some(name) = file_name.to_str() else {
            continue;
        };

        if name.starts_with(prefix) {
            names.insert(name.to_string());
        }
    }

    names.into_iter().collect()
}

/// Longest common prefix shared by every string in `names`.
/// Returns "" if `names` is empty.
pub fn longest_common_prefix(names: &[String]) -> String {
    let Some(first) = names.first() else {
        return String::new();
    };

    let mut prefix_len = first.len();

    for name in &names[1..] {
        let common = first
            .bytes()
            .zip(name.bytes())
            .take_while(|(a, b)| a == b)
            .count();
        prefix_len = prefix_len.min(common);
    }

    first[..prefix_len].to_string()
}

