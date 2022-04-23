package chess;

import java.util.ArrayList;
import java.util.List;

public class Rook extends Piece {
    private final int moves[][][] = {{{0,1}, {0,2}, {0,3}, {0,4}, {0,5}, {0,6}, {0,7}}, {{0,-1}, {0,-2}, {0,-3}, {0,-4}, {0,-5}, {0,-6}, {0,-7}}, 
    {{1,0}, {2,0}, {3,0}, {4,0}, {5,0}, {6,0}, {7,0}}, {{-1,0}, {-2,0}, {-3,0}, {-4,0}, {-5,0}, {-6,0}, {-7,0}}};

    public Rook(char color) {
        super(color);
        super.setBreakLoopWhenHttingPiece(true);
    }

    // Returns all the legal moves for this piece
    @Override
    public List<List<Integer>> findLegalMoves(Piece[][] board) {
        List<List<Integer>> legalMoves = new ArrayList<>();
        for (int[][] direction :moves) {
            List<List<Integer>> filteredMoves = filterLegalMoves(board, direction);
            for (List<Integer> move : filteredMoves) {
                legalMoves.add(move);
            }
        }
        return legalMoves;
    }
}
