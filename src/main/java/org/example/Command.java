package org.example;

public enum Command {
    ADD("add", "\\s*add\\s*"),
    UPDATE("update", "\\s*update\\s*"),
    DELETE("delete", "\\s*delete\\s*"),
    LIST("list", "\\s*list\\s*"),
    MARK_IN_PROGRESS("mark-in-progress", "\\s*mark-in-progress\\s*"),
    MARK_DONE("mark-done", "\\s*mark-done\\s*"),
    HELP("help","\\s*help\\s*");

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
