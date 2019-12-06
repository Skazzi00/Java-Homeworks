package game;

/**
 * @author Alexandr Eremin (eremin.casha@gmail.com)
 */
public class ProxyPosition implements Position {
    private final Position board;

    public ProxyPosition(MNKBoard board) {
        this.board = board;
    }

    @Override
    public boolean isValid(Move move) {
        return board.isValid(move);
    }

    @Override
    public Cell getCell(int r, int c) {
        return board.getCell(r, c);
    }

    @Override
    public String toString() {
        return board.toString();
    }
}
