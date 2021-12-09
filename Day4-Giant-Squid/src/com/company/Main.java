package com.company;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        List<Integer> bingoNumbers = new ArrayList<>();
        List<int[][]> bingoMatrixes = new ArrayList<>();

        try {

            Scanner scanner = new Scanner(new BufferedReader(new FileReader("input.txt")));

            String firstLine = scanner.nextLine();

            bingoNumbers = Arrays.stream(firstLine.split(",")).map(Integer::parseInt).collect(Collectors.toList());

            while (scanner.hasNextInt()) {
                int[][] bingoMatrix = new int[5][5];
                for (int i = 0; i < bingoMatrix.length; i++) {
                    for (int j = 0; j < bingoMatrix.length; j++) {
                        bingoMatrix[i][j] = scanner.nextInt();
                    }
                }
                bingoMatrixes.add(bingoMatrix);
            }

            //System.out.println(bingo(bingoMatrixes, bingoNumbers));
            System.out.println(solveBingoLoser(bingoMatrixes, bingoNumbers));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int bingo(List<int[][]> bingoMatrixes, List<Integer> bingoNumbers) {
        List<int[][]> solutionMatrixes = new ArrayList<>();

        // Initialize solution matrixes with 0
        for (int i = 0; i < bingoMatrixes.size(); i++) {
            solutionMatrixes.add(new int[5][5]);
        }
        for (int bingoNumber : bingoNumbers) {
            for (int idx = 0; idx < bingoMatrixes.size(); idx++) {
                int[][] bingoMatrix = bingoMatrixes.get(idx);
                for (int i = 0; i < bingoMatrix.length; i++) {
                    for (int j = 0; j < bingoMatrix.length; j++) {
                        if (bingoMatrix[i][j] == bingoNumber) {
                            int[][] solutionMatrix = solutionMatrixes.get(idx);
                            solutionMatrix[i][j] = 1;

                            if (checkWinningRow(solutionMatrix, i) || checkWinningColumn(solutionMatrix, j)) {
                                return sumUnmarked(bingoMatrix, solutionMatrix) * bingoNumber;
                            }
                        }
                    }
                }
            }
        }
        return 0;
    }

    public static int solveBingoLoser(List<int[][]> bingoMatrixes, List<Integer> bingoNumbers) {
        List<int[][]> solutionMatrixes = new ArrayList<>();
        Map<Integer, Boolean> winMatrixes = new HashMap<>();
        int winCount = 0;

        // Initialize solution matrixes with 0
        for (int i = 0; i < bingoMatrixes.size(); i++) {
            solutionMatrixes.add(new int[5][5]);
        }
        for (int bingoNumber : bingoNumbers) {
            for (int idx = 0; idx < bingoMatrixes.size(); idx++) {
                if (Boolean.TRUE.equals(winMatrixes.get(idx))) {
                    continue;
                }

                int[][] bingoMatrix = bingoMatrixes.get(idx);
                for (int i = 0; i < bingoMatrix.length; i++) {
                    for (int j = 0; j < bingoMatrix.length; j++) {
                        if (bingoMatrix[i][j] == bingoNumber) {
                            int[][] solutionMatrix = solutionMatrixes.get(idx);
                            solutionMatrix[i][j] = 1;

                            if (checkWinningRow(solutionMatrix, i) || checkWinningColumn(solutionMatrix, j)) {
                                winMatrixes.put(idx, true);
                                winCount ++;
                                if (winCount == bingoMatrixes.size()) {
                                   return sumUnmarked(bingoMatrix, solutionMatrix) * bingoNumber;
                                }
                            }
                        }
                    }
                }
            }
        }

        return 0;
    }

    public static boolean checkWinningRow(int[][] solutionMatrix, int rowIndex) {
        for (int j = 0; j < solutionMatrix.length; j++) {
            if (solutionMatrix[rowIndex][j] == 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean checkWinningColumn(int[][] solutionMatrix, int columnIndex) {
        for (int i = 0; i < solutionMatrix.length; i++) {
            if (solutionMatrix[i][columnIndex] == 0) {
                return false;
            }
        }
        return true;
    }

    public static int sumUnmarked(int[][] bingoMatrix, int[][] solutionMatrix) {
        int sum = 0;
        for (int i = 0; i < bingoMatrix.length; i++) {
            for (int j = 0; j < bingoMatrix.length; j++) {
                if (solutionMatrix[i][j] == 0) {
                    sum+= bingoMatrix[i][j];
                }
            }
        }
        return sum;
    }
}
