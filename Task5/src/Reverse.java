import java.util.Arrays;

/**
 * @author Alexandr Eremin (eremin.casha@gmail.com)
 */
public class Reverse {
    private static final int MIN_ARRAY_SIZE = 16;

    public static void main(String[] args) {
        int[][] input = new int[MIN_ARRAY_SIZE][];
        int inputSize = 0;
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
            }
        }


        for (int i = inputSize - 1; i >= 0; i--) {
            for (int j = input[i].length - 1; j >= 0; j--) {
                System.out.print(input[i][j] + " ");
            }
            System.out.println();
        }
    }
}