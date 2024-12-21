package org.example.model;


import org.example.exception.TaskNotFoundException;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.example.TaskStatus.TODO;
import static org.example.TaskStatus.IN_PROGRESS;
import static org.example.TaskStatus.DONE;

public class DataTest {

    private static final String DO_HOMEWORK = "Do homework";
    private static final String MAKE_BREAKFAST = "Make breakfast";

    @Test
    public void dataEmpty_hasZeroSize(){
        Data actual = Data.empty();

        assertThat(actual).extracting(Data::findAll)
                .returns(0, List::size);
    }

    @Test
    public void addTask() {
        Data actual = Data.empty();
        actual.addTask(DO_HOMEWORK);
        actual.addTask(MAKE_BREAKFAST);

        assertThat(actual.findAll())
                .satisfies(task -> {
                    assertThat(task).hasSize(2);
                    assertThat(task).extracting("id")
                            .containsExactly(1, 2);
                    assertThat(task).extracting(Task::getDescription)
                            .containsExactly("Do homework", MAKE_BREAKFAST);
                });
    }

    @Test
    public void updateTaskStatus_throwNotFoundException()  {
        Data actual = Data.empty();
        actual.addTask(DO_HOMEWORK);

        assertThatThrownBy(() -> actual.changeTaskStatus(3, DONE))
                .isInstanceOf(TaskNotFoundException.class)
                .hasMessage("Task with id %s not found.".formatted(3));
    }

    @Test
    public void updateTaskStatus() throws TaskNotFoundException {
        Data actual = Data.empty();
        actual.addTask(DO_HOMEWORK);

        actual.changeTaskStatus(1, DONE);

        assertThat(actual.findAll())
                .hasSize(1)
                .extracting(Task::getStatus)
                .containsExactly(DONE);
    }

    @Test
    public void filterTaskByStatus() throws TaskNotFoundException {
        Data actual = Data.empty();
        actual.addTask(DO_HOMEWORK);
        actual.addTask(MAKE_BREAKFAST);

        actual.changeTaskStatus(2, IN_PROGRESS);

        assertThat(actual.findTasksByStatus(TODO))
                .hasSize(1)
                .extracting(Task::getId)
                .containsExactly(1);

        assertThat(actual.findTasksByStatus(IN_PROGRESS))
                .hasSize(1)
                .extracting(Task::getId)
                .containsExactly(2);

        assertThat(actual.findTasksByStatus(DONE))
                .hasSize(0);
    }

    @Test
    public void updateDescription() throws TaskNotFoundException {
        Data actual = Data.empty();
        actual.addTask(DO_HOMEWORK);

        actual.updateDescription(1, MAKE_BREAKFAST);

        assertThat(actual.findAll())
                .hasSize(1)
                .extracting(Task::getDescription)
                .containsExactly(MAKE_BREAKFAST);
    }

    @Test
    public void updateDescription_throwNotFoundException() {
        Data actual = Data.empty();
        actual.addTask(DO_HOMEWORK);

        assertThatThrownBy(() -> actual.updateDescription(5, MAKE_BREAKFAST))
                .isInstanceOf(TaskNotFoundException.class)
                        .hasMessage("Task with id %s not found.".formatted(5));
    }

    @Test
    public void deleteTask() throws TaskNotFoundException {
        Data actual = Data.empty();
        actual.addTask(DO_HOMEWORK);
        actual.addTask(MAKE_BREAKFAST);

        actual.deleteTask(1);

        assertThat(actual.findAll())
                .hasSize(1)
                .extracting(Task::getId)
                .containsExactly(2);
    }

    @Test
    public void deleteTask_throwNotFoundException() throws TaskNotFoundException {
        Data actual = Data.empty();
        actual.addTask(DO_HOMEWORK);
        actual.addTask(MAKE_BREAKFAST);

        assertThatThrownBy(() -> actual.deleteTask(100))
                .hasMessage("Task with id %s not found.".formatted(100))
                .isInstanceOf(TaskNotFoundException.class);
    }

}