package org.example;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TaskStatus {
    TODO("todo"), IN_PROGRESS("in-progress"), DONE("done");

    private String value;
    TaskStatus(String value){
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
