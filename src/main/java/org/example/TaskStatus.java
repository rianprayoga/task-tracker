package org.example;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TaskStatus {
    TODO("todo"),
    IN_PROGRESS("in-progress"),
    DONE("done"),
    ALL("all");

    private final String value;

    TaskStatus(String value){
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    public static TaskStatus fromString(String input) {
        for (TaskStatus status : TaskStatus.values()) {
            if (status.getValue().equals(input)) {
                return status;
            }
        }
        return ALL;
    }
}
