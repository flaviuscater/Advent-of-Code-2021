package com.company;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) throws IOException {

        int[] depthMeasurement = new int[0];

        try (Stream<String> stream = Files.lines(Paths.get("./input.txt"))) {

            depthMeasurement = stream.mapToInt(s -> Integer.valueOf(s)).toArray();

        } catch (IOException e) {
            e.printStackTrace();
        }

        //partOne(depthMeasurement);
        partTwo(depthMeasurement);
    }

    public static void partOne(int[] depthMeasurement) {
        int count = 0;
        for (int i = 1; i < depthMeasurement.length; i++) {
            if (depthMeasurement[i] > depthMeasurement[i - 1]) {
                count++;
            }
        }
        System.out.println(count);
    }

    public static void partTwo(int[] depthMeasurement) {
        int count = 0;
        for (int i = 0; i < depthMeasurement.length - 3; i++) {
            int firstGroup = depthMeasurement[i] + depthMeasurement[i+1] + depthMeasurement[i+2];
            int secondGroup = depthMeasurement[i+1] + depthMeasurement[i+2] + depthMeasurement[i+3];
            if (firstGroup < secondGroup) {
                count++;
            }
        }
        System.out.println(count);
    }
}
