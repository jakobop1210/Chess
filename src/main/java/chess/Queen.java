package chess;

import java.util.ArrayList;
import java.util.List;

public class Queen extends Rook {
    private final int moves[][][] = {{{0,1}, {0,2}, {0,3}, {0,4}, {0,5}, {0,6}, {0,7}}, {{0,-1}, {0,-2}, {0,-3}, {0,-4}, {0,-5}, {0,-6}, {0,-7}}, 
    {{1,0}, {2,0}, {3,0}, {4,0}, {5,0}, {6,0}, {7,0}}, {{-1,0}, {-2,0}, {-3,0}, {-4,0}, {-5,0}, {-6,0}, {-7,0}},
    {{1,1}, {2,2}, {3,3}, {4,4}, {5,5}, {6,6}, {7,7}}, {{1,-1}, {2,-2}, {3,-3}, {4,-4}, {5,-5}, {6,-6}, {7,-7}}, 
    {{-1,-1}, {-2,-2}, {-3,-3}, {-4,-4}, {-5,-5}, {-6,-6}, {-7,-7}}, {{-1,1}, {-2,2}, {-3,3}, {-4,4}, {-5,5}, {-6,6}, {-7,7}}};

    public Queen(char color) {
        super(color);
        super.setJumpable(false);
    }

    // Returns all the legal moves for this piece
    @Override
    public List<List<Integer>> findLegalMoves(int[] currentSquare, Piece[][] board) {
        List<List<Integer>> legalMoves = new ArrayList<>();
        for (int[][] direction :moves) {
            List<List<Integer>> filteredMoves = filterLegalMoves(board, direction, currentSquare);
            for (List<Integer> move : filteredMoves) {
                legalMoves.add(move);
            }
        }
        return legalMoves;
    }
}
