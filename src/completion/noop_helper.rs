use rustyline::completion::{Completer, Pair};
use rustyline::highlight::Highlighter;
use rustyline::hint::Hinter;
use rustyline::validate::Validator;
use rustyline::Helper;

// The Editor's Helper bundles Completer + Hinter + Highlighter +
// Validator. Our completion logic lives entirely in the custom Tab
// keybinding (TabCompleteHandler) instead of the Completer trait, so
// this helper is a no-op marker type.
pub struct NoopHelper;

impl Completer for NoopHelper {
    type Candidate = Pair;
}

impl Hinter for NoopHelper {
    type Hint = String;
}

impl Highlighter for NoopHelper {}

impl Validator for NoopHelper {}

impl Helper for NoopHelper {}