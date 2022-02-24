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

    public List<List<Integer>> findLegalMoves(int[] currentSquare, Piece piece) {
        List<List<Integer>> squares = new ArrayList<>();
      // if (piece.getColor() != turn) {
      //     throw new IllegalArgumentException("Det er dne fargen sin tur");
      // }
        if (piece.getPiece() == 'p') {
            squares.addAll(pawnLegalMoves(currentSquare, piece));
        } else if (piece.getPiece() == 'h') {
            squares.addAll(horseLegalMoves(currentSquare, piece));
        } else if (piece.getPiece() == 'r') {
            squares.addAll(rookLegalMoves(currentSquare, piece));
        } else if (piece.getPiece() == 'b') {
            squares.addAll(bishopLegalMoves(currentSquare, piece));
        } else if (piece.getPiece() == 'q') {
            squares.addAll(queenLegalMoves(currentSquare, piece));
        } else if (piece.getPiece() == 'k') {
            squares.addAll(kingLegalMoves(currentSquare, piece));
        } 
        return squares;
    }

    // Pawn
    public List<List<Integer>> pawnLegalMoves(int[] currentSquare, Piece piece) {
        List<List<Integer>> legalMoves = new ArrayList<>();
        if (piece.getColor() == 'w') {
            if (currentSquare[0] != 0) {                  
                Piece pieceInfront = boardGame[currentSquare[0]-1][currentSquare[1]];                             
                if (currentSquare[0] != 1) {
                    Piece piece2Infront = boardGame[currentSquare[0]-2][currentSquare[1]];
                    if (pieceInfront.getPiece() == '0' && piece2Infront.getPiece() == '0' && currentSquare[0] == 6) legalMoves.add(Arrays.asList(4, currentSquare[1]));  
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
    
    // Horse
    public List<List<Integer>> horseLegalMoves(int[] currentSquare, Piece piece) {
        List<List<Integer>> legalMoves = new ArrayList<>();
        int hMoves[][] = {{2,1}, {2,-1}, {1,2}, {1,-2}, {-2,1}, {-2,-1}, {-1,2}, {-1,-2}};

        for (int[] square : hMoves) {
            if (currentSquare[0]+square[0] >= 0 && currentSquare[0]+square[0] < 8 && currentSquare[1]+square[1] >= 0 && currentSquare[1]+square[1] < 8) {
                Piece newSquare = boardGame[currentSquare[0]+square[0]][currentSquare[1]+square[1]]; 
                if (piece.getColor() != newSquare.getColor()) {
                    legalMoves.add(Arrays.asList(currentSquare[0]+square[0], currentSquare[1]+square[1]));
                }
            }
        }
        return legalMoves;     
    }

    // Rook
    public List<List<Integer>> rookLegalMoves(int[] currentSquare, Piece piece) {
        List<List<Integer>> legalMoves = new ArrayList<>();
        int i = 1;
        // Sjekker nedover 
        if (currentSquare[0] != 7) {
            i = 1;
            while (true) {
                if (currentSquare[0] + i > 7) {
                    break;
                }
                Piece squareInfront = boardGame[currentSquare[0]+i][currentSquare[1]]; 
                if (squareInfront.getPiece() == '0') {
                    legalMoves.add(Arrays.asList(currentSquare[0]+i, currentSquare[1]));
                    i++;
                } else if (squareInfront.getColor() != piece.getColor()) {
                    legalMoves.add(Arrays.asList(currentSquare[0]+i, currentSquare[1]));
                    break;
                } else {
                    break;
                }
            }
        // Sjekker oppover
        } if (currentSquare[0] != 0) {
            i = 1;
            while (true) {
                if (currentSquare[0] - i < 0) {
                    break;
                }
                Piece squareInfront = boardGame[currentSquare[0]-i][currentSquare[1]]; 
                if (squareInfront.getPiece() == '0') {
                    legalMoves.add(Arrays.asList(currentSquare[0]-i, currentSquare[1]));
                    i++;
                } else if (squareInfront.getColor() != piece.getColor()) {
                    legalMoves.add(Arrays.asList(currentSquare[0]-i, currentSquare[1]));
                    break;
                } else {
                    break;
                }
            }
        // Sjekker til høyre
        } if (currentSquare[1] != 7) {
            i = 1;
            while (true) {
                if (currentSquare[1] + i > 7) {
                    break;
                }
                Piece squareInfront = boardGame[currentSquare[0]][currentSquare[1]+i]; 
                if (squareInfront.getPiece() == '0') {
                    legalMoves.add(Arrays.asList(currentSquare[0], currentSquare[1]+i));
                    i++;
                } else if (squareInfront.getColor() != piece.getColor()) {
                    legalMoves.add(Arrays.asList(currentSquare[0], currentSquare[1]+i));
                    break;
                } else {
                    break;
                }
            }
        // Sjekker til venstre
        } if (currentSquare[1] != 0) {
            i = 1;
            while (true) {
                if (currentSquare[1] - i < 0) {
                    break;
                }
                Piece squareInfront = boardGame[currentSquare[0]][currentSquare[1]-i]; 
                if (squareInfront.getPiece() == '0') {
                    legalMoves.add(Arrays.asList(currentSquare[0], currentSquare[1]-i));
                    i++;
                } else if (squareInfront.getColor() != piece.getColor()) {
                    legalMoves.add(Arrays.asList(currentSquare[0], currentSquare[1]-i));
                    break;
                } else {
                    break;
                }
            }
        } 
        return legalMoves;
    }

    // Bishop
    public List<List<Integer>> bishopLegalMoves(int[] currentSquare, Piece piece) {
        List<List<Integer>> legalMoves = new ArrayList<>();
        int i = 1;
        // Sjekker skrått høyre nedover 
        if (currentSquare[0] != 7 && currentSquare[1] != 7) {
            i = 1;
            while (true) {
                if (currentSquare[0] + i > 7 || currentSquare[1] + i > 7) {
                    break;
                }
                Piece squareInfront = boardGame[currentSquare[0]+i][currentSquare[1]+i]; 
                if (squareInfront.getPiece() == '0') {
                    legalMoves.add(Arrays.asList(currentSquare[0]+i, currentSquare[1]+i));
                    i++;
                } else if (squareInfront.getColor() != piece.getColor()) {
                    legalMoves.add(Arrays.asList(currentSquare[0]+i, currentSquare[1]+i));
                    break;
                } else {
                    break;
                }
            }
        // Sjekker skrått venstre oppover
        } if (currentSquare[0] != 0 && currentSquare[1] != 0) {
            i = 1;
            while (true) {
                if (currentSquare[0] - i < 0 || currentSquare[0] - i < 0) {
                    break;
                }
                Piece squareInfront = boardGame[currentSquare[0]-i][currentSquare[1]-i]; 
                if (squareInfront.getPiece() == '0') {
                    legalMoves.add(Arrays.asList(currentSquare[0]-i, currentSquare[1]-i));
                    i++;
                } else if (squareInfront.getColor() != piece.getColor()) {
                    legalMoves.add(Arrays.asList(currentSquare[0]-i, currentSquare[1]-i));
                    break;
                } else {
                    break;
                }
            }
        // Sjekker skrått høyre oppover
        } if (currentSquare[0] != 0 && currentSquare[1] != 7) {
            i = 1;
            while (true) {
                if (currentSquare[0] - i < 0 || currentSquare[1] + i > 7) {
                    break;
                }
                Piece squareInfront = boardGame[currentSquare[0]-i][currentSquare[1]+i]; 
                if (squareInfront.getPiece() == '0') {
                    legalMoves.add(Arrays.asList(currentSquare[0]-i, currentSquare[1]+i));
                    i++;
                } else if (squareInfront.getColor() != piece.getColor()) {
                    legalMoves.add(Arrays.asList(currentSquare[0]-i, currentSquare[1]+i));
                    break;
                } else {
                    break;
                }
            }
        // Sjekker skrått venstre nedover
        } if (currentSquare[0] != 7 && currentSquare[1] != 0) {
            i = 1;
            while (true) {
                if (currentSquare[0] + i > 7 || currentSquare[1] - i < 0) {
                    break;
                }
                Piece squareInfront = boardGame[currentSquare[0]+i][currentSquare[1]-i]; 
                if (squareInfront.getPiece() == '0') {
                    legalMoves.add(Arrays.asList(currentSquare[0]+i, currentSquare[1]-i));
                    i++;
                } else if (squareInfront.getColor() != piece.getColor()) {
                    legalMoves.add(Arrays.asList(currentSquare[0]+i, currentSquare[1]-i));
                    break;
                } else {
                    break;
                }
            }
        } 

        return legalMoves;
    }

    //Queen
    public List<List<Integer>> queenLegalMoves(int[] currentSquare, Piece piece) {
        List<List<Integer>> legalMoves = new ArrayList<>();
        legalMoves.addAll(rookLegalMoves(currentSquare, piece));
        legalMoves.addAll(bishopLegalMoves(currentSquare, piece));
        return legalMoves;
    }

    //King 
    public List<List<Integer>> kingLegalMoves(int[] currentSquare, Piece piece) {
        List<List<Integer>> legalMoves = new ArrayList<>();
        int kMoves[][] = {{-1,0}, {-1,1}, {0,1}, {1,1}, {1,0}, {1,-1}, {0,-1}, {-1,-1}};

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

    public static void main(String[] args) {
        ChessGame newGame = new ChessGame();
        int[] arr = {6,3};
        Piece whiteP = new Piece('p', 'b');
        System.out.println(newGame.findLegalMoves(arr, whiteP));
    }
}
