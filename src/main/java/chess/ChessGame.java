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

    public String getBoardString() {
        return boardClass.boardString();
    }

    public void setBoard(String stringBoard) {
        boardClass.setBoard(stringBoard);
    }

    public void setTurn(char turn) {
        this.turn = turn;
    }

    public void setNextTurn(char nextTurn) {
        this.nextTurn = nextTurn;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    // Execute move if it's legal, checks for check and checkmate, checks result
    public boolean tryMove(int[] currentSquare, Piece piece, int[] move) {
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
            if (!doMove(currentSquare, piece, move)) return false;
            if (checkForCheck() && checkForMate()) {
                winner = nextTurn;
                gameOver = true;
            } else if (checkForMate()) {
                gameOver = true;
            }
            return true;
        }
        System.out.println("Brikken kan ikke flytte ditt, prøv et annet trekk!");
        return false;
    }

    // Checks if pawn should become queen, if move is castling and if it's legal, updates board and turn
    private boolean doMove(int[] currentSquare, Piece piece, int[] move) {
        if (piece.getName() == "chess.Pawn" && (move[0] == 0 || move[0] == 7)) { 
            piece = new Queen(piece.getColor());
        } else if (piece.getName() == "chess.King" && Math.abs(currentSquare[1]-move[1]) == 2) {
            if (!isCastlePathAttacked(currentSquare, piece, move) || check) {
                System.out.println("Kongen kan ikke rokere, prøv et annet trekk!");
                return false;
            }
            updateRook(currentSquare, move);
        }
        Piece[] updatePieces = {null, piece};
        piece.setHasMoved(true);
        updateBoard(updatePieces, currentSquare, move);
        updateTurn();
        return true;
    }

    // Updates turn
    private void updateTurn() {
        if (turn == 'w') {
            turn = 'b';
            nextTurn = 'w';
        } else {
            turn = 'w';
            nextTurn = 'b';
        }
    }

    // Updates board
    private void updateBoard(Piece[] pieces, int[] currentSquare, int[] move) {    
        board[currentSquare[0]][currentSquare[1]] = pieces[0];
        board[move[0]][move[1]] = pieces[1];
    }

    // Finds the king square for the color turn
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

    public Piece getKingPiece(char turn) {
        int[] kingSquare = findKingSquare(turn);
        return board[kingSquare[0]][kingSquare[1]];
    }

    // Filter out illegal moves that leads to check
    private List<List<Integer>> filterOutCheckMoves(Piece piece, int[] currentSquare, List<List<Integer>> moves) {
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

    // Checks for check
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

    // Checks for checkmate
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

    // Checks if any pieces is attacking the kings castling path, which will lead to an illegal castling
    private boolean isCastlePathAttacked(int[] currentSquare, Piece piece, int[] move) {
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
   
    // Updates the rook if the castling move is executed 
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
