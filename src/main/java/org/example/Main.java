package org.example;

import org.example.model.Data;

import java.util.Scanner;


public class Main {

    public static void main(String[] args) {

        FileManager fileManager = new FileManager();
        Data data = fileManager.open();

        while (true) {
            Scanner scanner = new Scanner(System.in);

            if (scanner.findInLine("^task-cli\\s*add\\s*") != null) {
                String description = scanner.findInLine("\".*\"");
                if (description == null){
                    System.out.println("Missing argument.");
                }
                System.out.printf("add %s%n", description);
            } else if (scanner.findInLine("^task-cli\\s*update\\s*") != null) {
                System.out.println("update");
            } else if (scanner.findInLine("^task-cli\\s*delete\\s*") != null) {
                System.out.println("delete");
            } else if (scanner.findInLine("^task-cli\\s*mark-in-progress\\s*") != null) {
                System.out.println("in-progress");
            } else if (scanner.findInLine("^task-cli\\s*mark-done\\s*") != null) {
                System.out.println("done");
            } else if (scanner.findInLine("^task-cli\\s*list\\s*") != null) {
                System.out.println("list");
            }
        }

    }

}