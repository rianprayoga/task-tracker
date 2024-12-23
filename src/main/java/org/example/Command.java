package org.example;

public enum Command {
    ADD("add", "add\\s*"),
    UPDATE("update", "update\\s*"),
    DELETE("delete", "delete\\s*"),
    LIST("list", "list\\s*"),
    MARK_IN_PROGRESS("mark-in-progress", "mark-in-progress\\s*"),
    MARK_DONE("mark-done", "mark-done\\s*"),
    HELP("help","help\\s*");

    private final String value;
    private final String regex;

    Command(String value, String regex){
        this.value = value;
        this.regex = regex;
    }

    public String getValue() {
        return this.value;
    }

    public String getRegex(){
        return this.regex;
    }
}
