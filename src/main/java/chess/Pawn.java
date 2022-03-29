package chess;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece {
  private int[][] pawnBMoves = {{1,0}, {2,0}, {1,1}, {1,-1}};
  private int[][] pawnWMoves = {{-1,0}, {-2,0}, {-1,1}, {-1,-1}};
  private int[][] moves;
    
    public Pawn(char color) {
        super(color);
        super.setJumpable(true);
    }

    public List<List<Integer>> findLegalMoves(int[] currentSquare, Piece[][] board) {
        List<List<Integer>> filteredMoves = new ArrayList<>();
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
        filteredMoves = legalMoves(board, moves, currentSquare);

        for (int i = 0; i < filteredMoves.size(); i++) { 
             if (filteredMoves.get(i).get(1) == 1 + currentSquare[1]) {
                if (board[currentSquare[0]+infront][currentSquare[1]+1] != null) legalMoves.add(filteredMoves.get(i));
            } else if (filteredMoves.get(i).get(1) == -1 + currentSquare[1]) {
                if (board[currentSquare[0]+infront][currentSquare[1]-1] != null) legalMoves.add(filteredMoves.get(i));
            } else if (filteredMoves.get(i).get(0) == moves[0][0] + currentSquare[0]) {
                if (board[currentSquare[0]+infront][currentSquare[1]] == null) legalMoves.add(filteredMoves.get(i));
            } else if (filteredMoves.get(i).get(0) == moves[1][0] + currentSquare[0]) {
                if (board[currentSquare[0]+twoInfront][currentSquare[1]] == null && (currentSquare[0] == 1 || currentSquare[0] == 6)) legalMoves.add(filteredMoves.get(i));
           }
        }
        return legalMoves; 
    }
}
