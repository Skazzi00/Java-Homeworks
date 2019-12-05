package game;

import java.util.Scanner;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class Main {
    public static void main(String[] args) {
        final Game game = new Game(false, new HumanPlayer(), new HumanPlayer());
        int result;
        Scanner in = new Scanner(System.in);
        int m = -1, n = -1, k = -1;
        do {
            System.out.println("Enter m,n,k:");
            String input = in.nextLine();
            String[] tokens = input.split(" ");
            if (tokens.length != 3) {
                System.out.println("Input must contain 3 integers");
                continue;
            }
            try {
                m = Integer.parseInt(tokens[0]);
                n = Integer.parseInt(tokens[1]);
                k = Integer.parseInt(tokens[2]);
            } catch (NumberFormatException e) {
                System.out.println("Tokens must be integers");
            }
        } while (m == -1 || n == -1 || k == -1);
        do {
            result = game.play(new MNKBoard(m, n, k));
            System.out.println("Game result: " + result);
        } while (result != 0);
    }
}
