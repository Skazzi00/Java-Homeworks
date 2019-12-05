package game;

import java.util.Arrays;
import java.util.Map;

/**
 * @author Alexandr Eremin (eremin.casha@gmail.com)
 */
public class MNKBoard implements Board, Position {
    private static final Map<Cell, Character> SYMBOLS = Map.of(
            Cell.X, 'X',
            Cell.O, 'O',
            Cell.E, '.'
    );

    private final Cell[][] cells;
    private final int m;
    private final int n;
    private final int k;
    private int cellsLeft;
    private Cell turn;

    public MNKBoard(int m, int n, int k) {
        this.cells = new Cell[m][n];
        this.m = m;
        this.n = n;
        this.k = k;
        cellsLeft = m * n;
        for (Cell[] row : cells) {
            Arrays.fill(row, Cell.E);
        }
        turn = Cell.X;
    }

    @Override
    public Position getPosition() {
        return new ProxyPosition(this);
    }

    @Override
    public Cell getCell() {
        return turn;
    }

    @Override
    public Result makeMove(Move move) {
        if (!isValid(move)) {
            return Result.LOSE;
        }

        int x = move.getRow();
        int y = move.getColumn();
        Cell value = move.getValue();
        cells[x][y] = value;
        cellsLeft--;
        int leftSum = 0;
        boolean leftEnd = false;
        int rightSum = 0;
        boolean rightEnd = false;
        int upSum = 0;
        boolean upEnd = false;
        int downSum = 0;
        boolean downEnd = false;
        int mainDiagUpSum = 0;
        boolean mainDiagUpEnd = false;
        int mainDiagDownSum = 0;
        boolean mainDiagDownEnd = false;
        int sideDiagUpSum = 0;
        boolean sideDiagUpEnd = false;
        int sideDiagDownSum = 0;
        boolean sideDiagDownEnd = false;
        for (int d = 1; d < k; d++) {
            if (y - d < 0) sideDiagDownEnd
                    = mainDiagUpEnd
                    = leftEnd
                    = true;
            if (y + d >= n) sideDiagUpEnd
                    = mainDiagDownEnd
                    = rightEnd
                    = true;
            if (x - d < 0) sideDiagUpEnd
                    = mainDiagUpEnd
                    = upEnd
                    = true;
            if (x + d >= m) sideDiagDownEnd
                    = mainDiagDownEnd
                    = downEnd
                    = true;

            if (!leftEnd && getCell(x, y - d) == value) leftSum++;
            else leftEnd = true;
            if (!rightEnd && getCell(x, y + d) == value) rightSum++;
            else rightEnd = true;
            if (!upEnd && getCell(x - d, y) == value) upSum++;
            else upEnd = true;
            if (!downEnd && getCell(x + d, y) == value) downSum++;
            else downEnd = true;
            if (!mainDiagUpEnd && getCell(x - d, y - d) == value) mainDiagUpSum++;
            else mainDiagUpEnd = true;
            if (!mainDiagDownEnd && getCell(x + d, y + d) == value) mainDiagDownSum++;
            else mainDiagDownEnd = true;
            if (!sideDiagUpEnd && getCell(x - d, y + d) == value) sideDiagUpSum++;
            else sideDiagUpEnd = true;
            if (!sideDiagDownEnd && getCell(x + d, y - d) == value) sideDiagDownSum++;
            else sideDiagDownEnd = true;
        }
        if (leftSum + rightSum + 1 == k
                || upSum + downSum + 1 == k
                || mainDiagDownSum + mainDiagUpSum + 1 == k
                || sideDiagDownSum + sideDiagUpSum + 1 == k)
            return Result.WIN;
        if (cellsLeft == 0)
            return Result.DRAW;
        turn = turn == Cell.X ? Cell.O : Cell.X;
        return Result.UNKNOWN;
    }

    @Override
    public boolean isValid(Move move) {
        return 0 <= move.getRow() && move.getRow() < m
                && 0 <= move.getColumn() && move.getColumn() < n
                && cells[move.getRow()][move.getColumn()] == Cell.E
                && turn == getCell();
    }

    @Override
    public Cell getCell(int r, int c) {
        return cells[r][c];
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(" ");
        for (int i = 0; i < n; i++) {
            sb.append(i);
        }
        for (int r = 0; r < m; r++) {
            sb.append("\n");
            sb.append(r);
            for (int c = 0; c < 3; c++) {
                sb.append(SYMBOLS.get(cells[r][c]));
            }
        }
        return sb.toString();
    }
}
