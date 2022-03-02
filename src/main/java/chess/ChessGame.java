package chess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChessGame {
    private Piece[][] board;
    private Board boardClass;
    private char turn = 'w';
    private char nextTurn = 'b';
    private char winner;
    private boolean gameOver;
    private boolean check;
    
    // Konstruktøren som lager et nytt board og en board klasse
    public ChessGame() {
        Board newBoard = new Board();
        this.boardClass = newBoard;
        this.board = newBoard.getBoard();
    }

    public Piece[][] getboard() {
        return board;
    }

    // Printer brettet i konsollen
    public void printboard() {
        boardClass.printBoard();
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
            Piece[] update = {piece, new Piece('0', '0')};
            updateBoard(update, currentSquare, move);
            if (checkForCheck()) {
                System.out.println("Du står i sjakk!");
                check = true;
                if (checkForMate()) {
                    winner = turn;
                    System.out.println(winner + " vant med sjakkmatt!");
                    gameOver = true;
                }
            } else {
                check = false;
            }
        } else {
            throw new IllegalArgumentException("Brikken kan ikke flytte dit!");
        }
        updateTurn();
    }

    // Oppdaterer turen
    private void updateTurn() {
        if (turn == 'w') {
            turn = 'b';
            nextTurn = 'w';
        } else {
            turn = 'w';
            nextTurn = 'b';
        }
    }
   
    // Oppdaterer brettet
    private void updateBoard(Piece[] pieces, int[] currentSquare, int[] move) {
        // Setter første brikke i pieces på move feltet
        board[move[0]][move[1]].setPiece(pieces[0].getPiece());
        board[move[0]][move[1]].setColor(pieces[0].getColor());
        // Setter andre brukke i pieces på currentSquare feltet
        board[currentSquare[0]][currentSquare[1]].setPiece(pieces[1].getPiece());
        board[currentSquare[0]][currentSquare[1]].setColor(pieces[1].getColor());   
    }

    // Felles metode som sjekker brikketype og deretter finner lovelige trekk
    private List<List<Integer>> findLegalMoves(int[] currentSquare, Piece piece) {
        List<List<Integer>> squares = new ArrayList<>();
     
     // if (check == true && piece.getPiece() != 'k') {
     //     throw new IllegalArgumentException("Du står i sjakk!");
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

    // Pawn gyldige trekk
    private List<List<Integer>> pawnLegalMoves(int[] currentSquare, Piece piece) {
        List<List<Integer>> legalMoves = new ArrayList<>();
        if (piece.getColor() == 'w') {
            if (currentSquare[0] != 0) {                  
                Piece pieceInfront = board[currentSquare[0]-1][currentSquare[1]];                             
                if (currentSquare[0] != 1) {
                    Piece piece2Infront = board[currentSquare[0]-2][currentSquare[1]];
                    if (pieceInfront.getPiece() == '0' && piece2Infront.getPiece() == '0' && currentSquare[0] == 6) legalMoves.add(Arrays.asList(4, currentSquare[1]));  
                }
                if (pieceInfront.getPiece() == '0') legalMoves.add(Arrays.asList(currentSquare[0]-1, currentSquare[1]));
                if (currentSquare[1] != 7) {
                    Piece rightDiagonal = board[currentSquare[0]-1][currentSquare[1]+1]; 
                    if (rightDiagonal.getColor() == 'b') legalMoves.add(Arrays.asList(currentSquare[0]-1, currentSquare[1]+1)); 
                }
                if (currentSquare[1] != 0) {
                    Piece leftDiagonal = board[currentSquare[0]-1][currentSquare[1]-1]; 
                    if (leftDiagonal.getColor() == 'b') legalMoves.add(Arrays.asList(currentSquare[0]-1, currentSquare[1]-1)); 
                }
            }                  
        } else { 
            if (currentSquare[0] != 7) {
                Piece pieceInfront = board[currentSquare[0]+1][currentSquare[1]];                             
                if (currentSquare[0] != 6) {
                    Piece piece2Infront = board[currentSquare[0]+2][currentSquare[1]];
                    if (pieceInfront.getPiece() == '0' && piece2Infront.getPiece() == '0' && currentSquare[0] == 1) legalMoves.add(Arrays.asList(3, currentSquare[1]));  
                }                          
                if (pieceInfront.getPiece() == '0') legalMoves.add(Arrays.asList(currentSquare[0]+1, currentSquare[1]));
                if (currentSquare[1] != 7) {
                    Piece rightDiagonal = board[currentSquare[0]+1][currentSquare[1]+1]; 
                    if (rightDiagonal.getColor() == 'w') legalMoves.add(Arrays.asList(currentSquare[0]+1, currentSquare[1]+1)); 
                }
                if (currentSquare[1] != 0) {
                    Piece leftDiagonal = board[currentSquare[0]+1][currentSquare[1]-1]; 
                    if (leftDiagonal.getColor() == 'w') legalMoves.add(Arrays.asList(currentSquare[0]+1, currentSquare[1]-1)); 
                }
            }            
        }
        return legalMoves;               
    }
    
    // Horse gyldige trekk
    private List<List<Integer>> horseLegalMoves(int[] currentSquare, Piece piece) {
        List<List<Integer>> legalMoves = new ArrayList<>();
        int hMoves[][] = {{2,1}, {2,-1}, {1,2}, {1,-2}, {-2,1}, {-2,-1}, {-1,2}, {-1,-2}};

        for (int[] square : hMoves) {
            if (currentSquare[0]+square[0] >= 0 && currentSquare[0]+square[0] < 8 && currentSquare[1]+square[1] >= 0 && currentSquare[1]+square[1] < 8) {
                Piece newSquare = board[currentSquare[0]+square[0]][currentSquare[1]+square[1]]; 
                if (piece.getColor() != newSquare.getColor()) {
                    legalMoves.add(Arrays.asList(currentSquare[0]+square[0], currentSquare[1]+square[1]));
                }
            }
        }
        return legalMoves;     
    }

    // Rook gyldige trekk
    private List<List<Integer>> rookLegalMoves(int[] currentSquare, Piece piece) {
        List<List<Integer>> legalMoves = new ArrayList<>();
        int i = 1;
        // Sjekker nedover 
        if (currentSquare[0] != 7) {
            i = 1;
            while (true) {
                if (currentSquare[0] + i > 7) {
                    break;
                }
                Piece squareInfront = board[currentSquare[0]+i][currentSquare[1]]; 
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
                Piece squareInfront = board[currentSquare[0]-i][currentSquare[1]]; 
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
                Piece squareInfront = board[currentSquare[0]][currentSquare[1]+i]; 
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
                Piece squareInfront = board[currentSquare[0]][currentSquare[1]-i]; 
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
    private List<List<Integer>> bishopLegalMoves(int[] currentSquare, Piece piece) {
        List<List<Integer>> legalMoves = new ArrayList<>();
        int i = 1;
        // Sjekker skrått høyre nedover 
        if (currentSquare[0] != 7 && currentSquare[1] != 7) {
            i = 1;
            while (true) {
                if (currentSquare[0] + i > 7 || currentSquare[1] + i > 7) {
                    break;
                }
                Piece squareInfront = board[currentSquare[0]+i][currentSquare[1]+i]; 
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
                if (currentSquare[0] - i < 0 || currentSquare[1] - i < 0) {
                    break;
                }
                Piece squareInfront = board[currentSquare[0]-i][currentSquare[1]-i]; 
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
                Piece squareInfront = board[currentSquare[0]-i][currentSquare[1]+i]; 
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
                Piece squareInfront = board[currentSquare[0]+i][currentSquare[1]-i]; 
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
    private List<List<Integer>> queenLegalMoves(int[] currentSquare, Piece piece) {
        List<List<Integer>> legalMoves = new ArrayList<>();
        legalMoves.addAll(rookLegalMoves(currentSquare, piece));
        legalMoves.addAll(bishopLegalMoves(currentSquare, piece));
        return legalMoves;
    }

    // King gyldige trekk
    private List<List<Integer>> kingLegalMoves(int[] currentSquare, Piece piece) {
        List<List<Integer>> legalMoves = new ArrayList<>();
        int kMoves[][] = {{-1,0}, {-1,1}, {0,1}, {1,1}, {1,0}, {1,-1}, {0,-1}, {-1,-1}};

        for (int[] square : kMoves) {
            if (currentSquare[0]+square[0] >= 0 && currentSquare[0]+square[0] < 8 && currentSquare[1]+square[1] >= 0 && currentSquare[1]+square[1] < 8) {
                Piece newSquare = board[currentSquare[0]+square[0]][currentSquare[1]+square[1]]; 
                if (piece.getColor() != newSquare.getColor()) {
                    legalMoves.add(Arrays.asList(currentSquare[0]+square[0], currentSquare[1]+square[1]));
                }
            }
        }
        return legalMoves;     
    }

    // Finner kongen sin rute til fargen som skal flytte
    private int[] findKingSquare() {
        int[] kingSquare = new int[2];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j].getPiece() == 'k' && board[i][j].getColor() != turn) {
                    kingSquare[0] = i;
                    kingSquare[1] = j;
                }
            }
        }
        return kingSquare;
    }

    // Sjekker for sjakk
    private boolean checkForCheck() {
        List<List<List<Integer>>> allMoves = new ArrayList<>();
        int[] kingSquare = findKingSquare();
        List<Integer> kingSquareList = Arrays.asList(new Integer[]{kingSquare[0], kingSquare[1]});

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j].getPiece() != '0' && board[i][j].getColor() == turn) {
                    int [] currentSquare = new int[]{i,j};
                    List<List<Integer>> pieceMoves = findLegalMoves(currentSquare, board[i][j]);
                    allMoves.add(pieceMoves);
                }
            }
        }
        for (List<List<Integer>> list : allMoves) {
            for (List<Integer> square : list) {
                if (square.equals(kingSquareList)) {
                    return true;
                }
            }
        }
        return false;  
    }

    // Sjekker for sjakkmatt
    private boolean checkForMate() {
        boolean mate = true;
        int[] kingSquare = findKingSquare();
        Piece king = new Piece('k', nextTurn);
        List<List<Integer>> kingMoves = kingLegalMoves(kingSquare, king);
    
        for (List<Integer> newMove : kingMoves) {
            int[] moveTo = {newMove.get(0), newMove.get(1)};
            Piece[] firstUpdate = {king, new Piece('0', '0')};
            Piece[] secondUpdate = {new Piece(board[moveTo[0]][moveTo[1]].getPiece(), board[moveTo[0]][moveTo[1]].getColor()), king};
            // Oppdateter brettet for å sjekke om det fortsatt er sjakk når kongen flytter
            updateBoard(firstUpdate, kingSquare, moveTo);
        
            if (checkForCheck() == false) {
                mate = false;
            }
            // Oppdaterer brettet tilbake til sånn det var
            updateBoard(secondUpdate, kingSquare, moveTo);
        }
        return mate;
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

        int[] square2 = {7,5};
        int[] move2 = {4,2};
        Piece whiteB = new Piece('b', 'w');
        newGame.movePiece(square2, whiteB, move2);

        int[] square3 = {1,0};
        int[] move3 = {2,0};
        newGame.movePiece(square3, blackP, move3);

        int[] square4 = {7,3};
        int[] move4 = {5,5};
        Piece whiteQ = new Piece('q', 'w');
        newGame.movePiece(square4, whiteQ, move4);

        int[] square5 = {2,0};
        int[] move5 = {3,0};
        newGame.movePiece(square5, blackP, move5);
        
        int[] square6 = {5,5};
        int[] move6 = {1,5};
        newGame.movePiece(square6, whiteQ, move6);

        newGame.printboard();
    }
}
