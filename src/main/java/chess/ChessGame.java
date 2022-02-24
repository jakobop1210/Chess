package chess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChessGame {
    private Piece[][] boardGame;
    private char turn = 'w';
    private char winner;
    private boolean gameOver;
    private Board board;
    private boolean check;
    
    public ChessGame() {
        Board newBoard = new Board();
        this.board = newBoard;
        this.boardGame = newBoard.getBoard();
    }

    public Piece[][] getBoardGame() {
        return boardGame;
    }

    // FLytter brikke, oppdaterer brett, tur, sjekker for sjakk og sjakkmatt
    public void movePiece(int[] currentSquare, Piece piece, int[] move) {
        if (gameOver == true) {
            System.out.println("Spillet er over!");
        }
        if (piece.getColor() != turn) {
            throw new IllegalArgumentException("Det er ikke din tur!");
        } 
        List<List<Integer>> legalMoves = findLegalMoves(currentSquare, piece);
        List<Integer> moveList = Arrays.asList(move[0], move[1]);

        if (legalMoves.contains(moveList)) {
            updateBoard(currentSquare, piece, move);
            if (checkForCheck(boardGame, findKingSquare())) {
                check = true;
                if (checkForMate(boardGame)) {
                    System.out.println(winner + "vant med sjakkmatt!");
                    winner = turn;
                    gameOver = true;
                }
            } else {
                check = false;
            }
        }
        if (turn == 'w') {
            turn = 'b';
        } else {
            turn = 'w';
        }
        System.out.printf("%n");
        System.out.printf("%n");
        board.printBoard();
    
    }

    // Oppdaterer brettet
    public void updateBoard(int[] currentSquare, Piece piece, int[] move) {
        boardGame[move[0]][move[1]].setPiece(piece.getPiece());
        boardGame[move[0]][move[1]].setColor(piece.getColor());
        System.out.println(currentSquare[0]);
        System.out.println(currentSquare[1]);
        System.out.println(boardGame[currentSquare[0]][currentSquare[1]]);
        boardGame[currentSquare[0]][currentSquare[1]].setPiece('0');
        //boardGame[currentSquare[0]][currentSquare[1]].setColor('0');         
    }

    // Felles metode som sjekker brikketype og deretter finner lovelige trekk
    public List<List<Integer>> findLegalMoves(int[] currentSquare, Piece piece) {
        List<List<Integer>> squares = new ArrayList<>();
     
        if (check == true && piece.getPiece() != 'k') {
            throw new IllegalArgumentException("Du står i sjakk!");
        }
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

    // Pawn gyldige trekk
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
    
    // Horse gyldige trekk
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

    // Rook gyldige trekk
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

    // Bishop gyldige trekk
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

    // Queen gyldige trekk
    public List<List<Integer>> queenLegalMoves(int[] currentSquare, Piece piece) {
        List<List<Integer>> legalMoves = new ArrayList<>();
        legalMoves.addAll(rookLegalMoves(currentSquare, piece));
        legalMoves.addAll(bishopLegalMoves(currentSquare, piece));
        return legalMoves;
    }

    // King gyldige trekk
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

    // Finner kongen sin rute til fargen som skal flytte
    public int[] findKingSquare() {
        int[] kingSquare = new int[2];
        for (int i = 0; i < boardGame.length; i++) {
            for (int j = 0; j < boardGame[i].length; j++) {
                if (boardGame[i][j].getPiece() == 'k' && boardGame[i][j].getColor() != turn) {
                    kingSquare[0] = i;
                    kingSquare[1] = j;
                }
            }
        }
        return kingSquare;
    }

    // Sjekker for sjakk
    public boolean checkForCheck(Piece[][] boardGame, int[] kingSquare) {
        List<List<List<Integer>>> allMoves = new ArrayList<>();
        List<Integer> kingSquareList = Arrays.asList(new Integer[]{kingSquare[0], kingSquare[1]});

        for (int i = 0; i < boardGame.length; i++) {
            for (int j = 0; j < boardGame[i].length; j++) {
                if (boardGame[i][j].getPiece() != '0' && boardGame[i][j].getColor() == turn) {
                    int [] currentSquare = new int[]{i,j};
                    List<List<Integer>> pieceMoves = findLegalMoves(currentSquare, boardGame[i][j]);
                    allMoves.add(pieceMoves);
                }
            }
            
        }
        for (List<List<Integer>> list : allMoves) {
            for (List<Integer> square : list) {
                if (square.equals(kingSquareList)) {
                    System.out.println("Du er i sjakk!");
                    return true;
                }
            }
        }
        return false;  
    }

    // Sjekker for sjakkmatt
    public boolean checkForMate(Piece[][] boardGame) {
        int[] kingSquare = findKingSquare();
        Piece king = boardGame[kingSquare[0]][kingSquare[1]];
        List<List<Integer>> kingMoves = kingLegalMoves(kingSquare, king);

        for (List<Integer> newMove : kingMoves) {
            int[] newSquare = new int[]{newMove.get(0), newMove.get(1)};
            if (checkForCheck(boardGame, newSquare)) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        ChessGame newGame = new ChessGame();
        int[] square = {6,4};
        int[] move = {4,4};
        Piece whiteP = new Piece('p', 'w');
        newGame.movePiece(square, whiteP, move);
        int[] square1 = {1,4};
        int[] move1 = {3,4};
        Piece blackP = new Piece('p', 'b');
        newGame.movePiece(square1, blackP, move1);

    }
}
