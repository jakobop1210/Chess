package chess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Pawn extends Piece {
  //private int[][] pawnBMoves = {{1,0}, {2,0}, {1,1}, {1,-1}};
  //private int[][] pawnWMoves = {{-1,0}, {-2,0}, {-1,1}, {-1,-1}};
    
    public Pawn(char color) {
        super(color);
    }

    public List<List<Integer>> findLegalMoves(int[] currentSquare, Piece[][] board) {
        List<List<Integer>> legalMoves = new ArrayList<>();
      //Piece piece = board[currentSquare[0]][currentSquare[1]];
      //int infront, twoInfront;

      //if (piece.getColor() == 'b') {
      //    infront = 1;
      //    twoInfront = 2;
      //} else {
      //    infront = -1;
      //    twoInfront = -2;
      //}
      //Piece pieceInfront = board[currentSquare[0]+infront][currentSquare[1]];
      //Piece pieceTwoInfront = board[currentSquare[0]+twoInfront][currentSquare[1]];
      //if ()
      if (this.getColor() == 'w') {
                if (currentSquare[0] != 0) {                  
                    Piece pieceInfront = board[currentSquare[0]-1][currentSquare[1]];                             
                    if (currentSquare[0] != 1) {
                        Piece piece2Infront = board[currentSquare[0]-2][currentSquare[1]];
                        if (pieceInfront == null && piece2Infront == null && currentSquare[0] == 6) legalMoves.add(Arrays.asList(4, currentSquare[1]));  
                    }
                    if (pieceInfront == null) legalMoves.add(Arrays.asList(currentSquare[0]-1, currentSquare[1]));
                    if (currentSquare[1] != 7) {
                        Piece rightDiagonal = board[currentSquare[0]-1][currentSquare[1]+1]; 
                        if (rightDiagonal != null) {
                            if (rightDiagonal.getColor() == 'b') legalMoves.add(Arrays.asList(currentSquare[0]-1, currentSquare[1]+1)); 
                        }
                    } 
                    if (currentSquare[1] != 0) {
                        Piece leftDiagonal = board[currentSquare[0]-1][currentSquare[1]-1]; 
                        if (leftDiagonal != null) {
                            if (leftDiagonal.getColor() == 'b') legalMoves.add(Arrays.asList(currentSquare[0]-1, currentSquare[1]-1));
                        } 
                    }
                }                  
            } else { 
                if (currentSquare[0] != 7) {
                    Piece pieceInfront = board[currentSquare[0]+1][currentSquare[1]];                             
                    if (currentSquare[0] != 6) {
                        Piece piece2Infront = board[currentSquare[0]+2][currentSquare[1]];
                        if (pieceInfront == null && piece2Infront == null && currentSquare[0] == 1) legalMoves.add(Arrays.asList(3, currentSquare[1]));  
                    }                          
                    if (pieceInfront == null) legalMoves.add(Arrays.asList(currentSquare[0]+1, currentSquare[1]));
                    if (currentSquare[1] != 7) {
                        Piece rightDiagonal = board[currentSquare[0]+1][currentSquare[1]+1]; 
                        if (rightDiagonal != null) {
                            if (rightDiagonal.getColor() == 'w') legalMoves.add(Arrays.asList(currentSquare[0]+1, currentSquare[1]+1)); 
                        }
                    }
                    if (currentSquare[1] != 0) {
                        Piece leftDiagonal = board[currentSquare[0]+1][currentSquare[1]-1]; 
                        if (leftDiagonal != null) {
                            if (leftDiagonal.getColor() == 'w') legalMoves.add(Arrays.asList(currentSquare[0]+1, currentSquare[1]-1)); 
                        }
                    }
                }            
            }
        
        return legalMoves; 
    }
}
