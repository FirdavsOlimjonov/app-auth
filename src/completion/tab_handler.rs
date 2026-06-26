use std::io::{Write, stdout};
use std::sync::Mutex;

use rustyline::{Cmd, ConditionalEventHandler, Event, EventContext, RepeatCount};

use crate::completion::candidates::{
    collect_candidates, collect_filename_candidates, longest_common_prefix,
};

/// Custom handler for the Tab key, bound directly (bypassing
/// rustyline's default Completer-driven Tab behavior) so we can
/// match bash's exact behavior:
///
/// - No matches: ring the bell, leave the line untouched.
/// - One match: complete it fully, with a trailing space.
/// - Multiple matches sharing a longer common prefix than what's
///   typed: complete up to that common prefix (no bell).
/// - Multiple matches with no further common prefix: ring the bell
///   on the first press; on an immediate second press with no
///   progress made, print all matches (sorted, double-space-joined)
///   on their own line, then redraw the prompt.
pub struct TabCompleteHandler {
    // Remembers the prefix that produced an "ambiguous, no progress"
    // bell on the previous Tab press, so we can detect a repeated
    // press at the same position.
    last_ambiguous_prefix: Mutex<Option<String>>,
}

impl TabCompleteHandler {
    pub fn new() -> Self {
        Self {
            last_ambiguous_prefix: Mutex::new(None),
        }
    }
}

impl ConditionalEventHandler for TabCompleteHandler {
    fn handle(
        &self,
        _evt: &Event,
        _n: RepeatCount,
        _positive: bool,
        ctx: &EventContext<'_>,
    ) -> Option<Cmd> {
        let line = ctx.line();
        let pos = ctx.pos();
        let before_cursor = &line[..pos];

        let word_start = before_cursor
            .rfind(|c: char| c.is_whitespace())
            .map(|i| i + 1)
            .unwrap_or(0);

        let prefix = &before_cursor[word_start..];
        let completing_command = word_start == 0;

        let candidates = if completing_command {
            collect_candidates(prefix) // first word -> commands
        } else {
            collect_filename_candidates(prefix) // later word -> filenames
        };

        if candidates.is_empty() {
            *self.last_ambiguous_prefix.lock().unwrap() = None;
            ring_bell();
            return Some(Cmd::Noop);
        }

        if candidates.len() == 1 {
            *self.last_ambiguous_prefix.lock().unwrap() = None;
            let suffix = format!("{} ", &candidates[0][prefix.len()..]);
            return Some(insert_suffix(&suffix));
        }

        // Multiple candidates: see if they share a longer common
        // prefix than what the user has already typed.
        let common = longest_common_prefix(&candidates);

        if common.len() > prefix.len() {
            *self.last_ambiguous_prefix.lock().unwrap() = None;
            let suffix = &common[prefix.len()..];
            return Some(insert_suffix(suffix));
        }

        // No further progress possible. First press: bell.
        // Second consecutive press at the same prefix: list matches.
        let mut last = self.last_ambiguous_prefix.lock().unwrap();

        if last.as_deref() == Some(prefix) {
            *last = None;
            drop(last);
            print_candidates(&candidates);
            // Cmd::Repaint unconditionally redraws the current line
            // without touching the buffer or cursor, restoring
            // "$ <prefix>" below the list we just printed.
            return Some(Cmd::Repaint);
        }

        *last = Some(prefix.to_string());
        drop(last);
        ring_bell();
        Some(Cmd::Noop)
    }
}

fn ring_bell() {
    print!("\x07");
    stdout().flush().ok();
}

/// Builds a Cmd that inserts `suffix` at the current cursor position,
/// correctly advancing the cursor past the inserted text (unlike
/// `Cmd::Replace`, whose underlying `insert_str` does not move the
/// cursor). Since every completion here only ever appends to what's
/// already typed, an append-only insert is always sufficient — no
/// existing text ever needs to be deleted first.
fn insert_suffix(suffix: &str) -> Cmd {
    Cmd::Insert(1, suffix.to_string())
}

/// Prints the sorted candidate list, double-space separated, on its
/// own line below the current prompt line (matching bash's format).
fn print_candidates(candidates: &[String]) {
    println!();
    println!("{}", candidates.join("  "));
}
