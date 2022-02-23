package chess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChessGame {
    private Piece[][] boardGame;
    private char turn = 'w';
    
    public ChessGame() {
        Board newBoard = new Board();
        this.boardGame = newBoard.getBoard();
    }

    public List<List<Integer>> findLegalMoves(Piece piece) {
        List<List<Integer>> squares = new ArrayList<>();
        if (piece.getColor() != turn) {
            throw new IllegalArgumentException("Det er dne fargen sin tur");
        } 
        return squares;
    }

    public List<List<Integer>> pawnPossMoves(int[] currentSquare, Piece piece) {
        List<List<Integer>> legalMoves = new ArrayList<>();
        if (piece.getColor() == 'w') {
            if (currentSquare[0] != 0) {                  
                Piece pieceInfront = boardGame[currentSquare[0]-1][currentSquare[1]];                             
                if (currentSquare[0] != 1) {
                    Piece piece2Infront = boardGame[currentSquare[0]-2][currentSquare[1]];
                    if (pieceInfront.getPiece() == '0' && piece2Infront.getPiece() == '0' && currentSquare[0] == 7) legalMoves.add(Arrays.asList(5, currentSquare[1]));  
                }
                if (pieceInfront.getPiece() == '0') legalMoves.add(Arrays.asList(currentSquare[0]-1, currentSquare[1]));
                if (currentSquare[1] != 7) {
                    Piece rightDiagonal = boardGame[currentSquare[0]-1][currentSquare[1]+1]; 
                    if (rightDiagonal.getColor() == 'b') legalMoves.add(Arrays.asList(currentSquare[0]-1, currentSquare[1]+1)); 
                }
                if (currentSquare[1] != 0) {
                    Piece leftDiagonal = boardGame[currentSquare[0]-1][currentSquare[1]-1]; 
                    if (leftDiagonal.getColor() == 'b') legalMoves.add(Arrays.asList(currentSquare[0]-1, currentSquare[1]-1)); 
                }
            }                  
        } else { 
            if (currentSquare[0] != 7) {
                Piece pieceInfront = boardGame[currentSquare[0]+1][currentSquare[1]];                             
                if (currentSquare[0] != 6) {
                    Piece piece2Infront = boardGame[currentSquare[0]+2][currentSquare[1]];
                    if (pieceInfront.getPiece() == '0' && piece2Infront.getPiece() == '0' && currentSquare[0] == 1) legalMoves.add(Arrays.asList(3, currentSquare[1]));  
                }                           
                if (pieceInfront.getPiece() == '0') legalMoves.add(Arrays.asList(currentSquare[0]+1, currentSquare[1]));
                if (currentSquare[1] != 7) {
                    Piece rightDiagonal = boardGame[currentSquare[0]+1][currentSquare[1]+1]; 
                    if (rightDiagonal.getColor() == 'w') legalMoves.add(Arrays.asList(currentSquare[0]+1, currentSquare[1]+1)); 
                }
                if (currentSquare[1] != 0) {
                    Piece leftDiagonal = boardGame[currentSquare[0]+1][currentSquare[1]-1]; 
                    if (leftDiagonal.getColor() == 'w') legalMoves.add(Arrays.asList(currentSquare[0]+1, currentSquare[1]-1)); 
                }
            }            
        }
        return legalMoves;               
    }
    
    public List<List<Integer>> knightPossMoves(int[] currentSquare, Piece piece) {
        List<List<Integer>> legalMoves = new ArrayList<>();;
        int kMoves[][] = {{2,1}, {2,-1}, {1,2}, {1,-2}, {-2,1}, {-2,-1}, {-1,2}, {-1,-2}};

        for (int[] square : kMoves) {
            if (currentSquare[0]+square[0] >= 0 && currentSquare[0]+square[0] < 8 && currentSquare[1]+square[1] >= 0 && currentSquare[1]+square[1] < 8) {
                Piece newSquare = boardGame[currentSquare[0]+square[0]][currentSquare[1]+square[1]]; 
                if (piece.getColor() != newSquare.getColor()) {
                    legalMoves.add(Arrays.asList(currentSquare[0]+square[0], currentSquare[1]+square[1]));
                }
            }
        }
        return legalMoves;     
    }

    public void checkForCheck() {

    }

    public void checkForMate() {

    }

    public void updateBoard() {

    }


    public static void main(String[] args) {
        ChessGame newGame = new ChessGame();
        int[] arr = {6,7};
        Piece whiteP = new Piece('p', 'b');
        System.out.println(newGame.pawnPossMoves(arr, whiteP));
    }

}
