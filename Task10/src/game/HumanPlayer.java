package game;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class HumanPlayer implements Player {
    private final PrintStream out;
    private final Scanner in;

    public HumanPlayer(final PrintStream out, final Scanner in) {
        this.out = out;
        this.in = in;
    }

    public HumanPlayer() {
        this(System.out, new Scanner(System.in));
    }

    @Override
    public Move move(final Position position, final Cell cell) {
        while (true) {
            out.println("Position");
            out.println(position);
            out.println(cell + "'s move");
            out.println("Enter row and column");
            int x = -1;
            int y = -1;
            do {
                String input = in.nextLine();
                String[] tokens = input.split(" ");
                if (tokens.length != 2) {
                    out.println("Input must contain 2 integers");
                    continue;
                }
                try {
                    x = Integer.parseInt(tokens[0]);
                    y = Integer.parseInt(tokens[1]);
                } catch (NumberFormatException e) {
                    out.println("Tokens must be integers");
                }
            } while (x == -1 || y == -1);
            final Move move = new Move(x, y, cell);
            if (position.isValid(move)) {
                return move;
            }
            out.println("Move " + move + " is invalid");
        }
    }
}
