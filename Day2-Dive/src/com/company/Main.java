package com.company;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Main {

    static int horizontalPosition = 0;
    static int depth = 0;
    static int aim = 0;

    public static void main(String[] args) {

        try (Stream<String> stream = Files.lines(Paths.get("./input.txt"))) {

            stream.forEach(s -> {
                String[] command = s.split(" ");
                String commandName = command[0];
                int commandValue = Integer.parseInt(command[1]);
                applyCommand(commandName, commandValue);
            });

            System.out.println(horizontalPosition * depth);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void applyCommand(String commandName, int commandValue) {
        if ("forward".equals(commandName)) {
            horizontalPosition += commandValue;
            depth += aim * commandValue;
        } else if ("down".equals(commandName)) {
            aim += commandValue;
        } else if ("up".equals(commandName)) {
            aim -= commandValue;
        }
    }
}
