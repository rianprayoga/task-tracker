package org.example;

public enum TaskStatus {
    TODO("todo"), IN_PROGRESS("in-progress"), DONE("done");

    private String value;
    TaskStatus(String value){
        this.value = value;
    }


}
