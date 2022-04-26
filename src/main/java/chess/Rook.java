package chess;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Rook extends Piece {
    private final Map<String, int[]> moves = new HashMap<>()
                {{
                    put("Up", new int[]{-1, 0});
                    put("Down", new int[]{1, 0});
                    put("Left", new int[]{0, -1});
                    put("Right", new int[]{0, 1});
                }};

    public Rook(char color) {
        super(color);
        super.setBreakLoopWhenHttingPiece(true);
    }

    // Generates the 7 moves a rook can do in one of the 4 directions 
    private int[][] generateMoves(String key) {
        int[][] direction = new int[7][2];
        for (int j = 1; j < 8; j++) {
            direction[j-1] = new int[]{j*moves.get(key)[0], j*moves.get(key)[1]};
        }
        return direction;
    }

    // Returns all the legal moves for this piece
    @Override
    public List<List<Integer>> findLegalMoves(Piece[][] board) {
        List<List<Integer>> legalMoves = new ArrayList<>();
        for (String key: moves.keySet()) {
            legalMoves.addAll(filterLegalMoves(board, generateMoves(key)));
        }
        return legalMoves;
    }
}

