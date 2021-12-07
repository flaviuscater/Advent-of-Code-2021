package com.company;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    static int gammaRate = 0;
    static int epsilonRate = 0;

    public static void main(String[] args) {
        List<String> diagnosticReport;

        try (Stream<String> stream = Files.lines(Paths.get("./input.txt"))) {

            diagnosticReport = stream.collect(Collectors.toList());
//            calculatePowerConsumption(diagnosticReport);
            calculateLifeSupportRating(diagnosticReport);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void calculatePowerConsumption(List<String> diagnosticReport) {
        int binaryNumberLength = diagnosticReport.get(0).length();
        StringBuilder gammaRateBinary = new StringBuilder();
        StringBuilder epsilonRateBinary = new StringBuilder();
        for (int i = 0; i < binaryNumberLength; i++) {
            int zerosCount = 0;
            int onesCount = 0;
            for (String binaryNumber : diagnosticReport) {
                if (binaryNumber.charAt(i) == '0') {
                    zerosCount++;
                } else if (binaryNumber.charAt(i) == '1') {
                    onesCount++;
                }
            }
            if (zerosCount > onesCount) {
                gammaRateBinary.append("0");
                epsilonRateBinary.append("1");
            } else {
                gammaRateBinary.append("1");
                epsilonRateBinary.append("0");
            }
        }
        int gammaRate = Integer.parseInt(gammaRateBinary.toString(), 2);
        int epsilonRate = Integer.parseInt(epsilonRateBinary.toString(), 2);
        System.out.println(gammaRate * epsilonRate);
    }

    public static void calculateLifeSupportRating(List<String> diagnosticReport) {
        int oxygenGeneratorRating = calculateOxygenGeneratorRating(diagnosticReport, 0);
        int c02GeneratorRating = calculateC02GeneratorRating(diagnosticReport, 0);
        System.out.println(oxygenGeneratorRating + " " + c02GeneratorRating);
        System.out.println(oxygenGeneratorRating * c02GeneratorRating);
    }

    public static int calculateOxygenGeneratorRating(List<String> diagnosticReport, int index) {
        List<String> zeroMostCommonNumbers = new ArrayList<>();
        List<String> oneMostCommonNumbers = new ArrayList<>();

        if (diagnosticReport.size() == 1) {
            return Integer.parseInt(diagnosticReport.get(0), 2);
        }

        for (String binaryNumber : diagnosticReport) {
            if (binaryNumber.charAt(index) == '0') {
                zeroMostCommonNumbers.add(binaryNumber);
            } else if (binaryNumber.charAt(index) == '1') {
                oneMostCommonNumbers.add(binaryNumber);
            }
        }
        if (zeroMostCommonNumbers.size() > oneMostCommonNumbers.size()) {
            return calculateOxygenGeneratorRating(zeroMostCommonNumbers, index + 1);
        } else if (zeroMostCommonNumbers.size() < oneMostCommonNumbers.size()) {
            return calculateOxygenGeneratorRating(oneMostCommonNumbers, index + 1);
        } else { // equals
            return calculateOxygenGeneratorRating(oneMostCommonNumbers, index + 1);
        }
    }

    public static int calculateC02GeneratorRating(List<String> diagnosticReport, int index) {
        List<String> zeroMostCommonNumbers = new ArrayList<>();
        List<String> oneMostCommonNumbers = new ArrayList<>();

        if (diagnosticReport.size() == 1) {
            return Integer.parseInt(diagnosticReport.get(0), 2);
        }

        for (String binaryNumber : diagnosticReport) {
            if (binaryNumber.charAt(index) == '0') {
                zeroMostCommonNumbers.add(binaryNumber);
            } else if (binaryNumber.charAt(index) == '1') {
                oneMostCommonNumbers.add(binaryNumber);
            }
        }
        if (zeroMostCommonNumbers.size() < oneMostCommonNumbers.size()) {
            return calculateC02GeneratorRating(zeroMostCommonNumbers, index + 1);
        } else if (zeroMostCommonNumbers.size() > oneMostCommonNumbers.size()) {
            return calculateC02GeneratorRating(oneMostCommonNumbers, index + 1);
        } else { // equals
            return calculateOxygenGeneratorRating(zeroMostCommonNumbers, index + 1);
        }
    }
}
