package chess;

import java.util.Arrays;
import java.util.List;

public class King extends Piece {
    private final int[][] moves = {{-1,0}, {-1,1}, {0,1}, {1,1}, {1,0}, {1,-1}, {0,-1}, {-1,-1}};
    private final int[] castleRight = {2,1};
    private final int[] castleLeft = {-2,-1,-3};

    public King(char color) {
        super(color);
        super.setJumpable(true);
    }

    private boolean isSquareEmpty(int[] square, Piece[][] board) {
        if (board[square[0]][square[1]] == null) {
            return true;
        }
        return false;
    }

    // Checks if the castling move is legal (not checking for check)
    private List<List<Integer>> IfLegalAddCastle(int[] currentSquare, Piece[][] board, List<List<Integer>> legalMoves, 
                                            int[] direction, int rookPos) {
        boolean spacesBetweenIsEmpty = true;
        for (int move : direction) {
            int[] square = new int[]{currentSquare[0], currentSquare[1]+move};
            if (!isSquareEmpty(square, board)) {
                spacesBetweenIsEmpty = false;
            }
        }

        Piece castleRook = board[currentSquare[0]][currentSquare[1]+rookPos];
        if (castleRook != null) {
            if (spacesBetweenIsEmpty && castleRook.getName() == "chess.Rook" && !castleRook.hasMoved()) {
                legalMoves.add(Arrays.asList(currentSquare[0], currentSquare[1]+direction[0]));
            }      
        }
        return legalMoves;
    }

    // Returns all the legal moves for this piece
    @Override
    public List<List<Integer>> findLegalMoves(int[] currentSquare, Piece[][] board) {
        List<List<Integer>> legalMoves = filterLegalMoves(board, moves, currentSquare);
        
        if (!this.hasMoved()) {
            legalMoves = IfLegalAddCastle(currentSquare, board, legalMoves, castleRight, +3);
            legalMoves = IfLegalAddCastle(currentSquare, board, legalMoves, castleLeft, -4);
        }
        return legalMoves;
    }
}
