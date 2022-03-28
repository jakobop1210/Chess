package chess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Piece {
    private char color;
    private boolean jumpable;
    String className = this.getClass().getName();

    public Piece(char color) {
        if (color != 'w' && color != 'b' && color != '0') {
            throw new IllegalArgumentException("Ikke gyldig farge!");
        }
        this.color = color;
    }

    protected boolean isValidPieces(char piece) {
        String validPieces = "rhbqkp0";
        if (!validPieces.contains(Character.toString(piece))) {
            throw new IllegalArgumentException("Ikke gydlig brikke!");
        }
        return true;
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
    
    public void setJumpable(boolean jumpable) {
        this.jumpable = jumpable;
    }

    public List<List<Integer>> legalMoves(Piece[][] board, int[][] moves, int[] currentSquare) {
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

    public List<List<Integer>> findLegalMoves(int[] currentSquare, Piece[][] board) {
        throw new UnsupportedOperationException("Not implemented");
    }


    public boolean isInsideBoard(int[] currentSquare, int[] moveTo) {
        if (currentSquare[0]+moveTo[0] >= 0 && currentSquare[0]+moveTo[0] < 8 
        && currentSquare[1]+moveTo[1] >= 0 && currentSquare[1]+moveTo[1] < 8) {
            return true;
        }
        return false;
    }



    public static void main(String[] args) {
    
    }
}
