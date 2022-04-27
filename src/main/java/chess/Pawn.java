package chess;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece {
    private int[][] moves = {{1,0}, {2,0}, {1,1}, {1,-1}};
    private final int[][] ifWhitePawn = {{-1,0}, {-2,0}, {-1,1}, {-1,-1}};

    public Pawn(char color) {
        super(color);
        super.setBreakLoopWhenHttingPiece(false);
    }

    @Override
    public List<List<Integer>> findLegalMoves(Piece[][] board) { 
        List<List<Integer>> legalMoves = new ArrayList<>();
        int infront = 1;
        int twoInfront = 2;
        if (this.getColor() == 'w') {
            infront = -1;
            twoInfront = -2;
            moves = ifWhitePawn;
        }
        List<List<Integer>> filteredMoves = filterLegalMoves(board, moves);

        for (int i = 0; i < filteredMoves.size(); i++) { 
            if (filteredMoves.get(i).get(1) == this.getY()+1) {
                if (board[this.getX()+infront][this.getY()+1] != null) legalMoves.add(filteredMoves.get(i));
            } else if (filteredMoves.get(i).get(1) == this.getY()-1) {
                if (board[this.getX()+infront][this.getY()-1] != null) legalMoves.add(filteredMoves.get(i));
            } else if (filteredMoves.get(i).get(0) == this.getX()+infront) {
                if (board[this.getX()+infront][this.getY()] == null) legalMoves.add(filteredMoves.get(i));
            } else if (filteredMoves.get(i).get(0) == this.getX()+twoInfront) {
                if (board[this.getX()+infront][this.getY()] == null 
                && board[this.getX()+twoInfront][this.getY()] == null 
                && (this.getX() == 1 || this.getX() == 6)) {
                    legalMoves.add(filteredMoves.get(i));
                }
           }
        }
        return legalMoves; 
    }

}
