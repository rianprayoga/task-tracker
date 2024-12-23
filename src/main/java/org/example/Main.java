package org.example;

import java.util.Scanner;


public class Main {

    public static void main(String[] args) {

        DataRepository dataRepository = new DataRepository();
        CommandProcessor processor = new CommandProcessor(dataRepository);

        //\s*task-cli\s*add\s*\".+\"
        //\s*task-cli\s*list\s*(done|todo|progress)?
        //^task-cli\s*update\s*\d*\s*\".*\"$
        //^task-cli\s*delete\s*\d*$
        //^task-cli\s*(mark-in-progress|mark-done)\s*\d*$
        Scanner scanner = new Scanner(System.in);
        while (true){
            String input = scanner.nextLine();
            processor.process(input);
        }


    }

}