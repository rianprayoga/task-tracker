package org.example;


import org.example.exception.TaskNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.example.TaskStatus.DONE;
import static org.example.TaskStatus.IN_PROGRESS;
import static org.example.TaskStatus.TODO;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

@RunWith(MockitoJUnitRunner.class)
public class CommandProcessorTest{

    @InjectMocks
    private CommandProcessor commandProcessor;
    @Mock
    private DataRepository dataRepository;
    @Captor
    private ArgumentCaptor<String> stringCaptor;

    @Test
    public void add(){
        String desc = "run forest run";
        commandProcessor.process("task-cli add \"%s\"".formatted(desc));

        verify(dataRepository, times(1)).add(stringCaptor.capture());
        assertThat(stringCaptor.getValue()).isEqualTo(desc);
    }

    @Test
    public void add_withExtraSpace(){
        String desc = "run forest run";
        commandProcessor.process("task-cli  add \"%s\"     ".formatted(desc));

        verify(dataRepository, times(1)).add(stringCaptor.capture());
        assertThat(stringCaptor.getValue()).isEqualTo(desc);
    }

    @Test
    public void add_descriptionContainsPunctuations(){
        String desc = "run forest run !! {} ?. \"what\" ";
        commandProcessor.process("task-cli add  \"%s\" ".formatted(desc));

        verify(dataRepository, times(1)).add(stringCaptor.capture());
        assertThat(stringCaptor.getValue()).isEqualTo(desc);
    }

    @Test
    public void add_missingDescription(){
        commandProcessor.process("task-cli add  ");
        verifyNoInteractions(dataRepository);
    }

    @Test
    public void add_blankDescription(){
        commandProcessor.process("task-cli add  \"\"");
        verifyNoInteractions(dataRepository);
    }

    @Test
    public void add_wrongOrder(){
        String desc = "run forest run !! {} ?. \"what\" ";
        commandProcessor.process("task-cli \"%s\" add ".formatted(desc));
        verifyNoInteractions(dataRepository);
    }

    @Test
    public void list(){
        commandProcessor.process("task-cli list");
        verify(dataRepository,times(1)).findAll();
    }

    @Test
    public void list_withExtraSpace(){
        commandProcessor.process("task-cli      list     ");
        verify(dataRepository,times(1)).findAll();
    }

    @Test
    public void list_withTaskStatus(){
        commandProcessor.process("task-cli list done");
        verify(dataRepository,times(1)).findBy(DONE);

        commandProcessor.process("task-cli list todo");
        verify(dataRepository,times(1)).findBy(TODO);

        commandProcessor.process("task-cli list in-progress");
        verify(dataRepository,times(1)).findBy(IN_PROGRESS);
    }

    @Test
    public void list_withInvalidTaskStatus(){
        commandProcessor.process("task-cli list invalid-status");
        verifyNoInteractions(dataRepository);
    }

    @Test
    public void list_wrongOrder(){
        commandProcessor.process("task-cli todo list");
        verifyNoInteractions(dataRepository);
    }

    @Test
    public void markInProgress() throws TaskNotFoundException {
        commandProcessor.process("task-cli mark-in-progress 1");
        verify(dataRepository,times(1)).mark(1,IN_PROGRESS);
    }

    @Test
    public void markInProgress_withExtraSpace() throws TaskNotFoundException {
        commandProcessor.process("task-cli   mark-in-progress   1    ");
        verify(dataRepository,times(1)).mark(1,IN_PROGRESS);
    }

    @Test
    public void markInProgress_missingId() {
        commandProcessor.process("task-cli mark-in-progress");
        verifyNoInteractions(dataRepository);
    }

    @Test
    public void markInProgress_wrongOrder() {
        commandProcessor.process("task-cli 1123 mark-in-progress");
        verifyNoInteractions(dataRepository);
    }

    @Test
    public void markInProgress_idStartWithZero() {
        commandProcessor.process("task-cli mark-in-progress 012334123");
        verifyNoInteractions(dataRepository);
    }

    @Test
    public void markDone() throws TaskNotFoundException {
        commandProcessor.process("task-cli mark-done 1");
        verify(dataRepository,times(1)).mark(1,DONE);
    }

    @Test
    public void markDone_withExtraSpace() throws TaskNotFoundException {
        commandProcessor.process("task-cli   mark-done   1    ");
        verify(dataRepository,times(1)).mark(1,DONE);
    }

    @Test
    public void markDone_missingId() {
        commandProcessor.process("task-cli mark-done");
        verifyNoInteractions(dataRepository);
    }

    @Test
    public void markDone_wrongOrder() {
        commandProcessor.process("task-cli 1123 mark-done");
        verifyNoInteractions(dataRepository);
    }

    @Test
    public void markDone_idStartWithZero() {
        commandProcessor.process("task-cli mark-done 012334123");
        verifyNoInteractions(dataRepository);
    }

    @Test
    public void delete() throws TaskNotFoundException {
        commandProcessor.process("task-cli delete 21344123");
        verify(dataRepository, times(1)).delete(21344123);
    }

    @Test
    public void delete_withExtraSpace() throws TaskNotFoundException {
        commandProcessor.process("task-cli   delete  21344123   ");
        verify(dataRepository, times(1)).delete(21344123);
    }

    @Test
    public void delete_missingId()  {
        commandProcessor.process("task-cli delete");
        verifyNoInteractions(dataRepository);
    }

    @Test
    public void delete_wrongOrder(){
        commandProcessor.process("task-cli 1 delete");
        verifyNoInteractions(dataRepository);
    }

    @Test
    public void delete_idStartWithZero(){
        commandProcessor.process("task-cli delete 0123141 ");
        verifyNoInteractions(dataRepository);
    }
}
