import java.util.Arrays;

public class ReverseMin {
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

        int[] linesMin = new int[inputSize];
        int[] rowsMin = new int[maxRow];
        Arrays.fill(linesMin, Integer.MAX_VALUE);
        Arrays.fill(rowsMin, Integer.MAX_VALUE);
        for (int i = 0; i < inputSize; i++) {
            for (int j = 0; j < input[i].length; j++) {
                linesMin[i] = Math.min(input[i][j], linesMin[i]);
                rowsMin[j] = Math.min(input[i][j], rowsMin[j]);
            }
        }

        for (int i = 0; i < inputSize; i++) {
            for (int j = 0; j < input[i].length; j++) {
                System.out.print(Math.min(linesMin[i], rowsMin[j]) + " ");
            }
            System.out.println();
        }
    }
}