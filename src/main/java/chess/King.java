package chess;

import java.util.List;

public class King extends Piece {

    private int[][] moves = {{-1,0}, {-1,1}, {0,1}, {1,1}, {1,0}, {1,-1}, {0,-1}, {-1,-1}};

    public King(char color) {
        super(color);
        this.setJumpable(true);
    }

    public List<List<Integer>> findLegalMoves(int[] currentSquare, Piece[][] board) {
        return legalMoves(board, moves, currentSquare);
    }
}
