package chess;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Bishop extends Piece {
    private final Map<String, int[]> moves = new HashMap<>()
                {{
                    put("Right_Down_Diagonal", new int[]{1, 1});
                    put("Left_Down_Diagonal", new int[]{1, -1});
                    put("Right_Up_Diagonal", new int[]{-1, -1});
                    put("Left_Up_Diagonal", new int[]{-1, 1});
                }};

    public Bishop(char color) {
        super(color);
        super.setBreakLoopWhenHttingPiece(true);
    }

    // Generates the 7 moves a bishop can do in one of the 4 diagonls
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

