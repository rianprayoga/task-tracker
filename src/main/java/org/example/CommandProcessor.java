package org.example;

import org.example.model.Data;

import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.example.Command.ADD;
import static org.example.Command.DELETE;
import static org.example.Command.HELP;
import static org.example.Command.LIST;
import static org.example.Command.MARK_DONE;
import static org.example.Command.MARK_IN_PROGRESS;
import static org.example.Command.UPDATE;

public class CommandProcessor {

    private final FileManager fileManager;
    private final Data data;
    private final String TASK_CLI_REGEX = "\\s*task-cli\\s*";
    private final String QUOTED_TEXT_REGEX = "\\s*\"(?<value>.+)\"\\s*";
    private final String LIST_OPTIONS_REGEX = "\\s*(?<option>done|todo|in-progress)?\\s*";

    public CommandProcessor(){
        this.fileManager = new FileManager();
        this.data = fileManager.open();
    }

    private boolean isTaskCli(Scanner scanner){
        try{
            scanner.next("^\\s*task-cli\\s*");
            return true;
        }catch (Exception e){
            return false;
        }
    }

    private boolean isTaskCli(String inputString){
        return Pattern.matches(TASK_CLI_REGEX+".*", inputString);
    }

    public void process(String inputString){
        if (isTaskCli(inputString)){
            switch (getCommand(inputString)){
                case ADD -> add(inputString);
                case UPDATE -> System.out.println(UPDATE.getValue());
                case DELETE -> System.out.println(DELETE.getValue());
                case LIST ->list(inputString);
                case MARK_DONE -> System.out.println(MARK_DONE.getValue());
                case MARK_IN_PROGRESS -> System.out.println(MARK_IN_PROGRESS.getValue());
                default -> System.out.print("""
                usage: task-cli add "description"
                task-cli: error: the following arguments are required: description
                """);
            }
        }
    }

    private Command getCommand(String inputString) {
        return Arrays.stream(Command.values())
                .filter(command -> Pattern.matches(TASK_CLI_REGEX + command.getRegex() + ".+", inputString))
                .findAny()
                .orElse(HELP);
    }

    private void add(String inputString){
        String s = TASK_CLI_REGEX + ADD.getRegex() + QUOTED_TEXT_REGEX;
        Pattern compile = Pattern.compile(s);
        Matcher matcher = compile.matcher(inputString);

        if (matcher.find()) {
            String description = matcher.group("value");
            data.addTask(description);
            fileManager.save(data);
            System.out.printf("Task added successfully (ID: %s)%n", data.getLatestId());
            return;
        }

        System.out.print(
                """
                    usage: task-cli add "description"
                    task-cli: error: the following arguments are required: description
                    """);
    }

    private void list(String inputString){
        String s = TASK_CLI_REGEX + LIST.getRegex() + LIST_OPTIONS_REGEX;
        Pattern compile = Pattern.compile(s);
        Matcher matcher = compile.matcher(inputString);

        if (matcher.matches()){
            String status = matcher.group("option");
            if (null == status) {
                data.findAll().forEach(task -> System.out.println(task.toString()));
            } else {
                data.findTasksByStatus(TaskStatus.fromString(status))
                        .forEach(task -> System.out.println(task.toString()));
            }
            return;
        }

        System.out.print(
                """
                    usage: task-cli list option
                    task-cli: error: Options are optional, available values for option are done, todo or in-progress
                    """);
    }
}
