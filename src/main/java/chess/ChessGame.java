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
    private boolean moveWasCastling;
    int[][] rookCastlePos;
    
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

    public char getNextTurn() {
        return nextTurn;
    }

    public boolean getMoveWasCastling() {
        return moveWasCastling;
    }

    public int[][] getRookCastlePos() {
        return rookCastlePos;
    }

    // Printer brettet i konsollen
    public void printboard() {
        boardClass.printBoard();
    }

    public void setBoard(char[][] charBoard) {
        boardClass.setBoard(charBoard);
    }

    // FLytter brikke hvis lovlig, oppdaterer brett, oppdaterer tur, sjekker for sjakk, sjakkmatt og resultat
    public boolean movePiece(int[] currentSquare, Piece piece, int[] move) {
        if (piece.getColor() != turn) {
            return false;
        } 
        moveWasCastling = false;
        check = false;
        if (checkForCheck()) check = true;

        List<List<Integer>> legalMoves = piece.findLegalMoves(currentSquare, board);
        legalMoves = filterOutCheckMoves(piece, currentSquare, legalMoves);
        List<Integer> movetoList = Arrays.asList(move[0], move[1]);

        if (legalMoves.contains(movetoList)) {
            if (piece.getName() == "chess.Pawn" && (move[0] == 0 || move[0] == 7)) { 
                piece = new Queen(piece.getColor());
            } else if (piece.getName() == "chess.King" && Math.abs(currentSquare[1]-move[1]) == 2) {
                if (!canKingCastle(currentSquare, piece, move) || check) {
                    System.out.println("Kongen kan ikke rokere, prøv et annet trekk!");
                    return false;
                }
                updateRook(currentSquare, move);
            }
            Piece[] updatePieces = {null, piece};
            piece.setHasMoved(true);
            updateBoard(updatePieces, currentSquare, move);
            updateTurn();

            if (checkForCheck() && checkForMate()) {
                winner = nextTurn;
                System.out.println(winner + " vant med sjakkmatt!");
                gameOver = true;
            } else if (checkForMate()) {
                System.out.println("Det ble uavgjort");
                gameOver = true;
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
                    kingSquare = new int[]{i,j};
                  }
              }
          }
      }
      return kingSquare;
    }

    // Sjekker hvilke trekk som ikke opphever eller fører til sjakk
    private List<List<Integer>> filterOutCheckMoves(Piece piece, int[] currentSquare, List<List<Integer>> moves) {
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
                        pieceMoves = filterOutCheckMoves(board[i][j], currentSquare, pieceMoves);
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

    // Sjekker om noen brikker angriper kongens castle path
    private boolean canKingCastle(int[] currentSquare, Piece piece, int[] move) {
        List<List<Integer>> castlePath = new ArrayList<>();
        castlePath.add(Arrays.asList(currentSquare[0], currentSquare[1]+1));
        castlePath.add(Arrays.asList(currentSquare[0], currentSquare[1]+2));
        if (currentSquare[1] > move[1]) {
            castlePath = Arrays.asList(Arrays.asList(currentSquare[0], currentSquare[1]-1), Arrays.asList(currentSquare[0], currentSquare[1]-2));
        }

        castlePath = filterOutCheckMoves(piece, currentSquare, castlePath);
        if (castlePath.size() < 2) return false;
        return true;
    }
   
    // Gjennofører castle trekket 
    private void updateRook(int[] currentSquare, int[] move) {
        int rookPlacementReleativeToKing = 3;
        int rookMoveToRealtiveToKing = 1;
        if (currentSquare[1] > move[1]) {
            rookPlacementReleativeToKing = -4;
            rookMoveToRealtiveToKing = -1;
        }
        int[] rookSquare = new int[]{currentSquare[0], currentSquare[1]+rookPlacementReleativeToKing};
        int[] rookMoveto = new int[]{currentSquare[0], currentSquare[1]+rookMoveToRealtiveToKing};
        Piece[] updateRook = {null, board[rookSquare[0]][rookSquare[1]]};
        updateBoard(updateRook, rookSquare, rookMoveto);

        moveWasCastling = true;
        rookCastlePos = new int[][]{rookSquare, rookMoveto};
    }
}
