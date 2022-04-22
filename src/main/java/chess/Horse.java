package chess;

import java.util.List;

public class Horse extends Piece {
    private final int moves[][] = {{2,1}, {2,-1}, {1,2}, {1,-2}, {-2,1}, {-2,-1}, {-1,2}, {-1,-2}};

    public Horse(char color) {
        super(color);
        super.setJumpable(true);
    }

    // Returns all the legal moves for this piece
    @Override
    public List<List<Integer>> findLegalMoves(Piece[][] board) {
        return filterLegalMoves(board, moves);
    }
}
