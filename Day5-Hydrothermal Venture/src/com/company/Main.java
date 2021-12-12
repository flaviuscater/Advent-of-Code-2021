package com.company;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(new BufferedReader(new FileReader("input.txt")));
            Map<AbstractMap.SimpleEntry<Integer, Integer>, List<AbstractMap.SimpleEntry<Integer, Integer>>> entries = new HashMap<>();

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                int[] lineCoordinates = extractIntArray(line);

                AbstractMap.SimpleEntry<Integer, Integer> firstPoint = new AbstractMap.SimpleEntry<>(lineCoordinates[0], lineCoordinates[1]);
                AbstractMap.SimpleEntry<Integer, Integer> secondPoint = new AbstractMap.SimpleEntry<>(lineCoordinates[2], lineCoordinates[3]);

                List<AbstractMap.SimpleEntry<Integer, Integer>> value = entries.get(firstPoint);
                if (value == null) {
                    value = new ArrayList<>();
                }
                value.add(secondPoint);
                entries.put(firstPoint, value);
            }
            solve(entries);

            //System.out.println(entries);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static void solve(Map<AbstractMap.SimpleEntry<Integer, Integer>, List<AbstractMap.SimpleEntry<Integer, Integer>>> entries) {
        int maxValue = getMaxValue(entries);
        int[][] solutionMatrix = new int[maxValue+1][maxValue+1];

        for (Map.Entry<AbstractMap.SimpleEntry<Integer, Integer>, List<AbstractMap.SimpleEntry<Integer, Integer>>> entry : entries.entrySet()) {
            for (AbstractMap.SimpleEntry<Integer, Integer> line : entry.getValue()) {
                int x1 = entry.getKey().getKey();
                int y1 = entry.getKey().getValue();
                int x2 = line.getKey();
                int y2 = line.getValue();

                //horizontal line
                if (x1 == x2) {
                    if (y1 < y2) {
                        for (int i = y1; i <= y2; i++) {
                            solutionMatrix[i][x1]++;
                        }
                    } else {
                        for (int i = y2; i <= y1; i++) {
                            solutionMatrix[i][x1]++;
                        }
                    }
                    //vertical line
                } else if (y1 == y2) {
                    if (x1 < x2) {
                        for (int i = x1; i <= x2; i++) {
                            solutionMatrix[y1][i]++;
                        }
                    } else {
                        for (int i = x2; i <= x1; i++) {
                            solutionMatrix[y1][i]++;
                        }
                    }
                    // first diagonal
                } else if (x1 == y1 && x2 == y2) {
                    if (x1 < x2) {
                        for (int i =x1; i<=x2; i++ ) {
                            solutionMatrix[i][i]++;
                        }
                    } else {
                        for (int i =x2; i<=x1; i++ ) {
                            solutionMatrix[i][i]++;
                        }
                    }
                    //second diagonal
                } else if (x1 == y2 && x2 == y1) {
                    if (x1 < x2) {
                        for (int i =x1, j=x2; i<=x2; i++, j-- ) {
                            solutionMatrix[j][i]++;
                        }
                    } else {
                        for (int i =x2, j =x1; i<=x1; i++, j-- ) {
                            solutionMatrix[j][i]++;
                        }
                    }
                }
            }
        }
        int overlappedPoints = findOverlappedPoints(solutionMatrix);
        System.out.println(overlappedPoints);

        Arrays.stream(solutionMatrix).map(Arrays::toString).forEach(System.out::println);

    }

    public static int findOverlappedPoints(int[][] matrix) {
        int count = 0;

        for(int i =0; i< matrix.length; i++) {
            for (int j =0; j < matrix.length; j++) {
                if (matrix[i][j] > 1) {
                    count ++;
                }
            }
        }

        return count;
    }

    public static int getMaxValue(Map<AbstractMap.SimpleEntry<Integer, Integer>, List<AbstractMap.SimpleEntry<Integer, Integer>>> entries) {
        int maxValue = 0;
        for (Map.Entry<AbstractMap.SimpleEntry<Integer, Integer>, List<AbstractMap.SimpleEntry<Integer, Integer>>> entry : entries.entrySet()) {
            maxValue = max(maxValue, entry);
        }
        return maxValue;
    }

    public static int max(int value, Map.Entry<AbstractMap.SimpleEntry<Integer, Integer>, List<AbstractMap.SimpleEntry<Integer, Integer>>> entry) {
        int result = value;
        for (AbstractMap.SimpleEntry<Integer, Integer> line : entry.getValue()) {
            if (value < entry.getKey().getKey()) {
                result = entry.getKey().getKey();
            } else if (value < entry.getKey().getValue()) {
                result = entry.getKey().getValue();
            } else if (value < line.getKey()) {
                result = line.getKey();
            } else if (value < line.getValue()) {
                result = line.getValue();
            }
        }
        return result;
    }

    static int[] extractIntArray(String str) {
        // Replacing every non-digit number
        // with a space(" ")
        str = str.replaceAll("[^\\d]", " ");

        // Remove extra spaces from the beginning
        // and the ending of the string
        str = str.trim();

        // Replace all the consecutive white
        // spaces with a single space
        str = str.replaceAll(" +", " ");

//        if (str.equals(""))
//            return new int[];

        return Arrays.stream(str.split(" ")).mapToInt(Integer::parseInt).toArray();
    }

}
