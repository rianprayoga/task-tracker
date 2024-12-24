package org.example;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.exception.TaskNotFoundException;
import org.example.model.Data;

import java.io.File;
import java.io.IOException;

public class DataRepository {
    private final String PATHNAME = "data.json";
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Data data;

    public DataRepository() {
        this.data = this.open();
    }

    public void add(String description){
        data.addTask(description);
        save(data);
        System.out.printf("Task added successfully (ID: %s)%n", data.getLatestId());
    }

    public void findAll(){
        data.findAll().forEach(task -> System.out.println(task.toString()));
    }

    public void findBy(TaskStatus status){
        data.findTasksByStatus(status).forEach(task -> System.out.println(task.toString()));
    }

    public void mark(int id, TaskStatus status) throws TaskNotFoundException {
        data.changeTaskStatus(id, status);
        save(data);
    }

    public void delete(int id) throws TaskNotFoundException {
        data.deleteTask(id);
        save(data);
    }

    public void update(int id, String desc) throws TaskNotFoundException {
        data.updateDescription(id, desc);
        save(data);
    }

    private Data open(){
        File file = new File(PATHNAME);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }

            JsonParser parser = objectMapper.createParser(file);
            if (parser.nextToken() == null){
                return Data.empty();
            }

            return objectMapper.readValue(parser, Data.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void save(Data data){
        try {
            objectMapper.writeValue(new File(PATHNAME), data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
