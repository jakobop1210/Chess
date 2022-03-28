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
    private boolean check = false;
    
    // Konstruktøren som lager et nytt board og en board klasse
    public ChessGame() {
        Board newBoard = new Board();
        this.boardClass = newBoard;
        this.board = newBoard.getBoard();
    }

    public Piece[][] getBoard() {
        return board;
    }

    public char getWinner() {
        return winner;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public boolean isCheck() {
        return check;
    }

    public char getTurn() {
        return turn;
    }

    // Printer brettet i konsollen
    public void printboard() {
        boardClass.printBoard();
    }

    // FLytter brikke, oppdaterer brett, oppdaterer tur, sjekker for sjakk og sjakkmatt
    public boolean movePiece(int[] currentSquare, Piece piece, int[] move) {
        if (piece.getColor() != turn) {
            return false;
        } 
        System.out.println(piece.getName());
        List<List<Integer>> legalMoves = piece.findLegalMoves(currentSquare, board);
        legalMoves = ifCheck(piece, currentSquare, legalMoves);
        List<Integer> movetoList = Arrays.asList(move[0], move[1]);

        if (legalMoves.contains(movetoList)) {
            if (piece.getName() == "chess.Pawn") {
                if (move[0] == 0 || move[0] == 7) {
                    board[currentSquare[0]][currentSquare[1]] = new Queen(piece.getColor());
                }
            }

            Piece[] update = {null, piece};
            updateBoard(update, currentSquare, move);
            updateTurn();
            if (checkForCheck()) {
                System.out.println("Du står i sjakk!");
                check = true;
                if (checkForMate()) {
                    winner = nextTurn;
                    System.out.println(winner + " vant med sjakkmatt!");
                    gameOver = true;
                }
            } else {
                check = false;
            }
            return true;
        }
            System.out.println("Brikken kan ikke flytte ditt, prøv et annet trekk!");
            return false;
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
        // Setter første brukke i pieces på currentSquare feltet
        board[currentSquare[0]][currentSquare[1]] = pieces[0];
        // Setter andre brikke i pieces på move feltet
        board[move[0]][move[1]] = pieces[1];
    }

    // Finner kongen sin rute til fargen turn
    private int[] findKingSquare(char turn) {
        int[] kingSquare = new int[2];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] != null) {
                    if (board[i][j].getName() == "chess.King" && board[i][j].getColor() == turn) {
                        kingSquare[0] = i;
                        kingSquare[1] = j;
                    }
                }
            }
        }
        return kingSquare;
    }

 //private Piece clone(Piece piece) {
 //    Piece newPiece;
 //    if (piece == null) return null;
 //    if (piece.getName() == "chess.Pawn") newPiece = new Pawn(piece.getColor());
 //    else if (piece.getName() == "chess.Rook") newPiece = new Rook(piece.getColor());
 //    else if (piece.getName() == "chess.Horse") newPiece = new Horse(piece.getColor());
 //    else if (piece.getName() == "chess.Bishop") newPiece = new Bishop(piece.getColor());
 //    else if (piece.getName() == "chess.King") newPiece = new King(piece.getColor());
 //    else newPiece = new Queen(piece.getColor());

 //    return newPiece;
 //}

    // Sjekker hvilke trekk som opphever sjakken i listen moves
    private List<List<Integer>> ifCheck(Piece piece, int[] currentSquare, List<List<Integer>> moves) {
        //Piece pieceCopy = new Piece(piece.getPiece(), piece.getColor());
        List<List<Integer>> ifCheckMoves = new ArrayList<>();
        Piece pieceCopy = piece;

        for (List<Integer> square : moves) {
            int[] moveTo = {square.get(0), square.get(1)};
            Piece pieceMoveToCopy = board[moveTo[0]][moveTo[1]];
            Piece[] movePieces = {null, pieceCopy};
            Piece[] movePiecesBack = {pieceMoveToCopy, pieceCopy};

            updateBoard(movePieces, currentSquare, moveTo);
            if (checkForCheck() == false) {
                ifCheckMoves.add(square);
            }
            updateBoard(movePiecesBack, moveTo, currentSquare);
        }
        return ifCheckMoves;
    }

    // Sjekker for sjakk
    private boolean checkForCheck() {
        int[] kingSquare = findKingSquare(turn);
        List<Integer> kingSquareList = Arrays.asList(new Integer[]{kingSquare[0], kingSquare[1]});

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] != null) {
                    if (board[i][j].getColor() != turn) {
                        int [] currentSquare = new int[]{i,j};
                        List<List<Integer>> pieceMoves = board[i][j].findLegalMoves(currentSquare, board);
                        for (List<Integer> square : pieceMoves) {
                            if (square.equals(kingSquareList)) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;  
    }

    // Sjekker for sjakkmatt
    private boolean checkForMate() {
        boolean mate = false;
        List<List<List<Integer>>> allMoves = new ArrayList<>();

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] != null) {
                    if (board[i][j].getColor() == turn) {
                        int[] currentSquare = {i,j};
                        List<List<Integer>> pieceMoves = board[i][j].findLegalMoves(currentSquare, board);
                        pieceMoves = ifCheck(board[i][j], currentSquare, pieceMoves);
                        if (!pieceMoves.isEmpty()) {
                            allMoves.add(pieceMoves);
                        }
                    }
                }
            }
        }
        if (allMoves.isEmpty()) {
            mate = true;
        }
        return mate;
    }

    
    public static void main(String[] args) {
        // Sjekker skolematten manuelt
        ChessGame newGame = new ChessGame();
        int[] square = {6,4};
        int[] move = {4,4};
        Piece whiteP = new Pawn('w');
        newGame.movePiece(square, whiteP, move);

        int[] square1 = {1,4};
        int[] move1 = {3,4};
        Piece blackP = new Pawn('b');
        newGame.movePiece(square1, blackP, move1);

        int[] square2 = {7,5};
        int[] move2 = {4,2};
        Piece whiteB = new Bishop('w');
        newGame.movePiece(square2, whiteB, move2);

        int[] square3 = {0,6};
        int[] move3 = {2,5};
        Piece blackH = new Horse('b');
        newGame.movePiece(square3, blackH, move3);

        int[] square4 = {7,3};
        int[] move4 = {3,7};
        Piece whiteQ = new Queen('w');
        newGame.movePiece(square4, whiteQ, move4);

        int[] square5 = {1,0};
        int[] move5 = {2,0};
        newGame.movePiece(square5, blackP, move5);
        
        int[] square6 = {3,7};
        int[] move6 = {1,5};
        newGame.movePiece(square6, whiteQ, move6);

      //int[] square7 = {2,7};
      //int[] move7 = {0,6};
      //newGame.movePiece(square7, blackH, move7);

        newGame.printboard();
    }
}
