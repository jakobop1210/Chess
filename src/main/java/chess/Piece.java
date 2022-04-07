package chess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Piece {
    private char color;
    private boolean jumpable;
    private boolean hasMoved = false;
    String className = this.getClass().getName();

    public Piece(char color) {
        if (color != 'w' && color != 'b') {
            throw new IllegalArgumentException("Ikke gyldig farge!");
        }
        this.color = color;
    }

    public String getName() {
        return className;
    }

    public boolean isJumpable() {
        return jumpable;
    }
    
    public char getColor() {
        return color;
    }

    public boolean hasMoved() {
        return hasMoved;
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    public void setJumpable(boolean jumpable) {
        this.jumpable = jumpable;
    }

    // Filter out moves that is outside the board or if there is a piece of the same color 
    public List<List<Integer>> filterLegalMoves(Piece[][] board, int[][] moves, int[] currentSquare) {
        List<List<Integer>> legalMoves = new ArrayList<>();

        for (int[] square : moves) {
            if (isInsideBoard(currentSquare, square)) {
                    Piece currentSquarePiece = board[currentSquare[0]][currentSquare[1]];
                    Piece moveToPiece = board[currentSquare[0]+square[0]][currentSquare[1]+square[1]];

                    if (moveToPiece == null) {
                        legalMoves.add(Arrays.asList(currentSquare[0]+square[0], currentSquare[1]+square[1]));
                    } else if (currentSquarePiece.getColor() == moveToPiece.getColor()) {
                        if (!this.jumpable) break;
                    } else {
                        legalMoves.add(Arrays.asList(currentSquare[0]+square[0], currentSquare[1]+square[1]));
                        if (moveToPiece.getColor() != '0' && !this.jumpable) {
                            break;
                        }
                    }
            }
        }
        return legalMoves;
    }

    public abstract List<List<Integer>> findLegalMoves(int[] currentSquare, Piece[][] board);
  //public List<List<Integer>> findLegalMoves(int[] currentSquare, Piece[][] board) {
  //    throw new UnsupportedOperationException("Not implemented");
  //}

    // Check if a square is inside the board
    private boolean isInsideBoard(int[] currentSquare, int[] moveTo) {
        if (currentSquare[0]+moveTo[0] >= 0 && currentSquare[0]+moveTo[0] < 8 
        && currentSquare[1]+moveTo[1] >= 0 && currentSquare[1]+moveTo[1] < 8) {
            return true;
        }
        return false;
    }
}
