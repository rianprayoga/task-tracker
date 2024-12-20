package org.example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public record Data(int latestId, List<Task> tasks) {

    public record Task(int id, String description, TaskStatus status, Date createdAt, Date updatedAt){}

    public static Data empty(){
        return new Data(0, new ArrayList<>());
    }
}
