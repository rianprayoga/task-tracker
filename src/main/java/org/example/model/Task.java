package org.example.model;

import org.example.TaskStatus;

import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Task {
    private int id;
    private String description;
    private TaskStatus status;
    private Date createdAt;
    private Date updatedAt;

    //for deserialization (json -> POJO)
    public Task(){}

    public Task(int id, String description, TaskStatus status, Date createdAt, Date updatedAt) {
        this.id = id;
        this.description = description;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

        return "Task{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", status=" + status.getValue() +
                ", createdAt=" + createdAt.toInstant().atOffset(ZoneOffset.UTC).format(formatter) +
                ", updatedAt=" + updatedAt.toInstant().atOffset(ZoneOffset.UTC).format(formatter) +
                '}';
    }
}
