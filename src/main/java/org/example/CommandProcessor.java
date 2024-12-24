package org.example;

import org.example.exception.TaskNotFoundException;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.example.Command.ADD;
import static org.example.Command.DELETE;
import static org.example.Command.HELP;
import static org.example.Command.LIST;
import static org.example.Command.MARK_DONE;
import static org.example.Command.MARK_IN_PROGRESS;
import static org.example.Command.UPDATE;
import static org.example.TaskStatus.DONE;
import static org.example.TaskStatus.IN_PROGRESS;

public class CommandProcessor {

    private final DataRepository dataRepository;
    private final String TASK_CLI_REGEX = "\\s*task-cli\\s*";
    private final String QUOTED_TEXT_REGEX = "\\s*\"(?<value>.+)\"\\s*$";
    private final String NUMBER_REGEX = "\\s*(?<id>[1-9]+\\d*)\\s*$";
    private final String TASK_STATUS_REGEX = "\\s*(?<option>done|todo|in-progress)?\\s*$";

    public CommandProcessor(DataRepository dataRepository){
        this.dataRepository = dataRepository;
    }

    private boolean isTaskCli(String inputString){
        return Pattern.matches(TASK_CLI_REGEX+".*", inputString);
    }

    public void process(String inputString) {
        if (isTaskCli(inputString)) {
            switch (getCommand(inputString)) {
                case ADD -> add(inputString);
                case UPDATE -> System.out.println(UPDATE.getValue());
                case DELETE -> delete(inputString);
                case LIST -> list(inputString);
                case MARK_DONE -> markDone(inputString);
                case MARK_IN_PROGRESS -> markProgress(inputString);
                default -> System.out.print(
                        """
                usage: task-cli add "description"
                task-cli: error: the following arguments are required: description
                """);
            }
        }
    }

    private Command getCommand(String input) {
        return Arrays.stream(Command.values())
                .filter(command -> Pattern.matches(TASK_CLI_REGEX + command.getRegex() + ".*", input))
                .findAny()
                .orElse(HELP);
    }

    private void add(String input){
        Pattern compile = Pattern.compile(TASK_CLI_REGEX + ADD.getRegex() + QUOTED_TEXT_REGEX);
        Matcher matcher = compile.matcher(input);

        if (matcher.find()) {
            String description = matcher.group("value");
            dataRepository.add(description);
            return;
        }

        System.out.print(
                """
                    usage: task-cli add "description"
                    task-cli: error: the following arguments are required: description
                    """);
    }

    private void list(String input){
        String s = TASK_CLI_REGEX + LIST.getRegex() + TASK_STATUS_REGEX;
        Pattern compile = Pattern.compile(s);
        Matcher matcher = compile.matcher(input);

        if (matcher.matches()){
            String status = matcher.group("option");
            if (null == status) {
                dataRepository.findAll();
            } else {
                dataRepository.findBy(TaskStatus.fromString(status));
            }
            return;
        }

        System.out.print(
                """
                    usage: task-cli list option
                    task-cli: error: Option is optional, available values for option are done, todo or in-progress
                    """);
    }

    private void markProgress(String input){
        Pattern compile = Pattern.compile(TASK_CLI_REGEX + MARK_IN_PROGRESS.getRegex() + NUMBER_REGEX);
        Matcher matcher = compile.matcher(input);

        if (matcher.matches()){
            String id = matcher.group("id");
            try {
                dataRepository.mark(Integer.parseInt(id),IN_PROGRESS);
            } catch (TaskNotFoundException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void markDone(String input){
        Pattern compile = Pattern.compile(TASK_CLI_REGEX + MARK_DONE.getRegex() + NUMBER_REGEX);
        Matcher matcher = compile.matcher(input);

        if (matcher.matches()){
            String id = matcher.group("id");
            try {
                dataRepository.mark(Integer.parseInt(id),DONE);
            } catch (TaskNotFoundException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void delete(String input){
        Pattern compile = Pattern.compile(TASK_CLI_REGEX + DELETE.getRegex() + NUMBER_REGEX);
        Matcher matcher = compile.matcher(input);

        if (matcher.matches()){
            String id = matcher.group("id");
            try {
                dataRepository.delete(Integer.parseInt(id));
            } catch (TaskNotFoundException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
