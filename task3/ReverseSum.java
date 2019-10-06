package task3;

import java.util.Arrays;
import java.util.Scanner;

public class ReverseSum {
    private static final int MIN_ARRAY_SIZE = 16;

    public static void main(String[] args) {
        int[][] input = new int[MIN_ARRAY_SIZE][];
        int inputSize = 0;
        int maxRow = 0;
        try (Scanner inputScanner = new Scanner(System.in)) {
            while (inputScanner.hasNextLine()) {
                int[] buffer = new int[MIN_ARRAY_SIZE];
                int bufferSize = 0;
                try (Scanner lineScanner = new Scanner(inputScanner.nextLine())) {
                    while (lineScanner.hasNextInt()) {
                        if (bufferSize == buffer.length) {
                            buffer = Arrays.copyOf(buffer, buffer.length * 2);
                        }
                        buffer[bufferSize++] = lineScanner.nextInt();
                    }
                }
                if (inputSize == input.length) {
                    input = Arrays.copyOf(input, input.length * 2);
                }
                input[inputSize++] = Arrays.copyOfRange(buffer, 0, bufferSize);
                maxRow = Math.max(maxRow, bufferSize);
            }
        }

        int[] linesSum = new int[inputSize];
        int[] rowsSum = new int[maxRow];
        for (int i = 0; i < inputSize; i++) {
            for (int j = 0; j < input[i].length; j++) {
                linesSum[i] += input[i][j];
                rowsSum[j] += input[i][j];
            }
        }

        for (int i = 0; i < inputSize; i++) {
            for (int j = 0; j < input[i].length; j++) {
                System.out.print(linesSum[i] + rowsSum[j] - input[i][j] + " ");
            }
            System.out.println();
        }
    }
}