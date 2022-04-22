package chess;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece {
  private final int[][] pawnBMoves = {{1,0}, {2,0}, {1,1}, {1,-1}};
  private final int[][] pawnWMoves = {{-1,0}, {-2,0}, {-1,1}, {-1,-1}};
  private int[][] moves;
    
    public Pawn(char color) {
        super(color);
        super.setJumpable(true);
    }

    // Returns all the legal moves for this piece
    @Override
    public List<List<Integer>> findLegalMoves(Piece[][] board) { 
        List<List<Integer>> legalMoves = new ArrayList<>();
        int infront, twoInfront;

        if (this.getColor() == 'b') {
            moves = pawnBMoves;
            infront = 1;
            twoInfront = 2;
        } else {
            moves = pawnWMoves;
            infront = -1;
            twoInfront = -2;
        }
        List<List<Integer>> filteredMoves = filterLegalMoves(board, moves);

        for (int i = 0; i < filteredMoves.size(); i++) { 
             if (filteredMoves.get(i).get(1) == 1 + this.getSquare()[1]) {
                if (board[this.getSquare()[0]+infront][this.getSquare()[1]+1] != null) legalMoves.add(filteredMoves.get(i));
            } else if (filteredMoves.get(i).get(1) == -1 + this.getSquare()[1]) {
                if (board[this.getSquare()[0]+infront][this.getSquare()[1]-1] != null) legalMoves.add(filteredMoves.get(i));
            } else if (filteredMoves.get(i).get(0) == moves[0][0] + this.getSquare()[0]) {
                if (board[this.getSquare()[0]+infront][this.getSquare()[1]] == null) legalMoves.add(filteredMoves.get(i));
            } else if (filteredMoves.get(i).get(0) == moves[1][0] + this.getSquare()[0]) {
                if (board[this.getSquare()[0]+twoInfront][this.getSquare()[1]] == null && (this.getSquare()[0] == 1 || this.getSquare()[0] == 6)) legalMoves.add(filteredMoves.get(i));
           }
        }
        return legalMoves; 
    }
}
