package chess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class King extends Piece {
    private int[][] moves = {{-1,0}, {-1,1}, {0,1}, {1,1}, {1,0}, {1,-1}, {0,-1}, {-1,-1}};
    private int[] castleRight = {2,1};
    private int[] castleLeft = {-2,-1,-3};

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
        if (spacesBetweenIsEmpty && castleRook != null && !castleRook.isHasMoved()) {
                legalMoves.add(Arrays.asList(currentSquare[0], currentSquare[1]+direction[0]));
        }
        return legalMoves;
    }

    public List<List<Integer>> findLegalMoves(int[] currentSquare, Piece[][] board) {
        List<List<Integer>> legalMoves = new ArrayList<>();
        legalMoves = legalMoves(board, moves, currentSquare);
        if (!this.isHasMoved()) {
            legalMoves = IfLegalAddCastle(currentSquare, board, legalMoves, castleRight, +3);
            legalMoves = IfLegalAddCastle(currentSquare, board, legalMoves, castleLeft, -4);
        }
        return legalMoves;
    }
}
