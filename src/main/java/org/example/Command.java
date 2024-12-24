package org.example;

public enum Command {
    ADD("add"),
    UPDATE("update"),
    DELETE("delete"),
    LIST("list"),
    MARK_IN_PROGRESS("mark-in-progress"),
    MARK_DONE("mark-done"),
    HELP("help");

    private final String regex;

    Command(String regex) {
        this.regex = regex;
    }

    public String getRegex() {
        return this.regex;
    }
}
