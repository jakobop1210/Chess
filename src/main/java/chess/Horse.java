package chess;

import java.util.List;

public class Horse extends Piece {
    private int moves[][] = {{2,1}, {2,-1}, {1,2}, {1,-2}, {-2,1}, {-2,-1}, {-1,2}, {-1,-2}};

    public Horse(char color) {
        super(color);
        this.setJumpable(true);
    }

    public List<List<Integer>> findLegalMoves(int[] currentSquare, Piece[][] board) {
        return legalMoves(board, moves, currentSquare);
    }
}
