package chess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Piece {
    private final String className = this.getClass().getName();
    private final char color;
    private boolean breakLoopWhenHttingPiece;
    private boolean hasMoved = false;
    private int x = -1;
    private int y = -1;

    public Piece(char color) {
        if (color != 'w' && color != 'b') {
            throw new IllegalArgumentException("Ikke gyldig farge!");
        }
        this.color = color;
    }

    public String getName() {
        return className;
    }

    public boolean getBreakLoopWhenHttingPiece() {
        return breakLoopWhenHttingPiece;
    }
    
    public char getColor() {
        return color;
    }

    public boolean hasMoved() {
        return hasMoved;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int[] getSquare() {
        if (x == -1 || y == -1) return null;
        return new int[]{x, y};
    }

    public void setSquare(int[] square) {
        if (isIllegalSquare(square)) {
            throw new IllegalArgumentException("Not a valid square");
        } else {
            this.x = square[0];
            this.y = square[1];
        }
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    public void setBreakLoopWhenHttingPiece(boolean jumpable) {
        this.breakLoopWhenHttingPiece = jumpable;
    }

    // Filter out moves that is outside the board or if there is a piece of the same color 
    public List<List<Integer>> filterLegalMoves(Piece[][] board, int[][] moves) {
        List<List<Integer>> legalMoves = new ArrayList<>();
        for (int[] square : moves) {
            if (isInsideBoard(square)) {
                Piece moveToPiece = board[this.getX()+square[0]][this.getY()+square[1]];

                if (moveToPiece == null) {
                    legalMoves.add(Arrays.asList(this.getX()+square[0], this.getY()+square[1]));
                } else if (this.getColor() == moveToPiece.getColor()) {
                    if (this.getBreakLoopWhenHttingPiece()) break;
                } else {
                    legalMoves.add(Arrays.asList(this.getX()+square[0], this.getY()+square[1]));
                    if (this.getBreakLoopWhenHttingPiece()) break;    
                }
            }
        }
        return legalMoves;
    }

    protected abstract List<List<Integer>> findLegalMoves(Piece[][] board);
  
    // Check if a square is inside the board
    private boolean isInsideBoard(int[] moveTo) {
        if (this.getSquare()[0]+moveTo[0] >= 0 && this.getSquare()[0]+moveTo[0] < 8 
        && this.getSquare()[1]+moveTo[1] >= 0 && this.getSquare()[1]+moveTo[1] < 8) {
            return true;
        }
        return false;
    }

    public boolean isIllegalSquare(int[] square) {
        return Arrays.stream(square).anyMatch(p -> (p < 0 || p > 7));
    }
}
