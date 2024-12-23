package org.example.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import org.example.TaskStatus;
import org.example.exception.TaskNotFoundException;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.example.TaskStatus.TODO;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Data {
    private int latestId;
    private List<Task> tasks;

    private Data(int latestId) {
        this.latestId = latestId;
        this.tasks = new ArrayList<>();
    }

    //for deserialization (json -> POJO)
    public Data(){}

    public static Data empty(){
        return new Data(0);
    }

    public int getLatestId(){
        return this.latestId;
    }

    public void addTask(String description){
        latestId += 1;
        Date now = Date.from(Instant.now());
        this.tasks.add(new Task(latestId, description, TODO, now, now));
    }

    public List<Task> findTasksByStatus(TaskStatus status){
        return this.tasks.stream().filter(task -> status.equals(task.getStatus())).toList();
    }

    public List<Task> findAll(){
        return this.tasks;
    }

    public void updateDescription(int id, String description) throws TaskNotFoundException{
        Task task = this.findTask(id);
        task.setDescription(description);
        task.setUpdatedAt(Date.from(Instant.now()));
    }

    public void deleteTask(int id) throws TaskNotFoundException{
        Task task = this.findTask(id);
        this.tasks.remove(task);
    }

    private Task findTask(int id) throws TaskNotFoundException {
        return this.tasks.stream().filter(value -> value.getId() == id).findAny()
                .orElseThrow(() -> new TaskNotFoundException("Task with id %s not found.".formatted(id)));
    }

    public void changeTaskStatus(int id, TaskStatus status) throws TaskNotFoundException {
        Task task = this.findTask(id);
        task.setStatus(status);
        task.setUpdatedAt(Date.from(Instant.now()));
    }
}
