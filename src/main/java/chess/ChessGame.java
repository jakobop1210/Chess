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
    private int[] lastMoveSquare;
    int[][] rookCastleSqaures;
    
    public ChessGame() {
        this.boardClass = new Board();
        this.board = boardClass.getBoard();
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

    public int[][] getRookCastleSquares() {
        return rookCastleSqaures;
    }

    public int[] getLastMoveSquare() {
        return lastMoveSquare;
    }

    public String getBoardString() {
        return boardClass.boardString();
    }

    public void setBoard(String stringBoard) {
        boardClass.setBoard(stringBoard);
    }

    public void setTurn(char turn) {
        if (!Piece.isValidColor(turn)) {
            throw new IllegalArgumentException("Not a valid turn");
        }
        this.turn = turn;
    }

    public void setNextTurn(char nextTurn) {
        if (!Piece.isValidColor(nextTurn)) {
            throw new IllegalArgumentException("Not a valid turn");
        }
        this.nextTurn = nextTurn;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public void setLastMoveSquare(int[] lastMoveSquare) {
        if (Piece.isIllegalSquare(lastMoveSquare)) {
            throw new IllegalArgumentException("Not a valid sqaure");
        }
        this.lastMoveSquare = lastMoveSquare;
    }

    // Checks for invalid input, and if move is legal calls executeMove
    public boolean tryMove(Piece piece, int[] move) {
        if (piece == null || piece.getSquare() == null) {
             throw new NullPointerException("Not a valid piece");
        } else if (Piece.isIllegalSquare(move) || piece.getColor() != turn) {
            throw new IllegalArgumentException("Not a valid move");
        }
        moveWasCastling = false;
        List<List<Integer>> legalMoves = filterOutCheckMoves(piece, piece.findLegalMoves(board));
        List<Integer> moveToList = Arrays.asList(move[0], move[1]);
        
        if (!legalMoves.contains(moveToList)) {
            throw new IllegalArgumentException("Not a valid move for that piece");
        } else if (checkForIllegalCastling(piece, move)) {
                throw new IllegalArgumentException("The king cannot castle");
        }
        executeMove(piece, move);
        return true;
    }

    // Executes the move by updating the board and turn. Also checking for check, checkmate and draw
    private void executeMove(Piece piece, int[] move) {
        if (piece instanceof Pawn && (move[0] == 0 || move[0] == 7)) { 
            int[] square = piece.getSquare();
            piece = new Queen(piece.getColor());
            piece.setSquare(square);
        }
        lastMoveSquare = move;
        piece.setHasMoved(true);
        updateBoard(new Piece[]{null, piece}, piece.getSquare(), move);
        updateTurn();
        if (checkForCheck()) {
            check = true;
            if (!checkForAnyLegalMoves()) {
                winner = nextTurn;
                gameOver = true;
            }
        } else if (!checkForAnyLegalMoves()) {
            gameOver = true;
        } else {
            check = false;
        }
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
    private void updateBoard(Piece[] pieces, int[] moveFrom, int[] moveTo) {    
        board[moveFrom[0]][moveFrom[1]]= pieces[0];
        board[moveTo[0]][moveTo[1]] = pieces[1];
        if (pieces[0] != null) pieces[0].setSquare(moveFrom);
        if (pieces[1] != null) pieces[1].setSquare(moveTo);
    }

    // Finds the king with the color turn
    public Piece getKingPiece(char turn) {
        if (!Piece.isValidColor(turn)) {
            throw new IllegalArgumentException("Not a valid color");
        } 
        return Arrays.stream(board)
            .flatMap(row -> Arrays.stream(row)
            .filter(p -> p != null)
            .filter(p -> p instanceof King && p.getColor() == turn))
            .findFirst()
            .orElse(null);
    }
            
    // Filter out illegal moves that leads to check
    private List<List<Integer>> filterOutCheckMoves(Piece piece, List<List<Integer>> moves) {
        List<List<Integer>> notCheckMoves = new ArrayList<>();
        for (List<Integer> square : moves) {
            int[] originalSquare = piece.getSquare();
            int[] moveTo = {square.get(0), square.get(1)};
            Piece pieceMoveTo = board[moveTo[0]][moveTo[1]];
            updateBoard(new Piece[]{null, piece}, piece.getSquare(), moveTo);
            if (!checkForCheck()) {
                notCheckMoves.add(square);
            }
            updateBoard(new Piece[]{pieceMoveTo, piece}, piece.getSquare(), originalSquare);
        }
        return notCheckMoves;
    }

     // Checks if the color turn is in check
    private boolean checkForCheck() {
        if (getKingPiece(turn) != null) {
            List<Integer> kingSquareList = Arrays.asList(getKingPiece(turn).getX(), getKingPiece(turn).getY());
            for (Piece[] row : board) for (Piece piece : row) {
                if (piece != null) {
                    if (piece.getColor() != turn) {
                        if (piece.findLegalMoves(board).stream().anyMatch(p -> p.equals(kingSquareList))) {
                            return true;
                        }  
                    }
                }
            }
        }
        return false;  
    }

    // Checks if the color turn has any legal moves
    private boolean checkForAnyLegalMoves() {
        for (Piece[] row : board) for (Piece piece : row) {
            if (piece != null) {
                if (piece.getColor() == turn) {
                    List<List<Integer>> pieceMoves = piece.findLegalMoves(board);
                    if (!filterOutCheckMoves(piece, pieceMoves).isEmpty()) return true;
                }
            }
        }
        return false;
    }

    // If move is castling, checks if caslting path is attacked or check is true
    private boolean checkForIllegalCastling(Piece piece, int[] move) {
        if (piece instanceof King && Math.abs(piece.getY()-move[1]) == 2) {
            int pos1 = 1;
            int pos2 = 2;
            if (piece.getY() > move[1]) {
                pos1 = -1;
                pos2 = -2;
            }
            List<List<Integer>> pathMoves = new ArrayList<>();
            pathMoves.add(Arrays.asList(piece.getX(), piece.getY()+pos1));
            pathMoves.add(Arrays.asList(piece.getX(), piece.getY()+pos2));
            if (filterOutCheckMoves(piece, pathMoves).size() < 2 || check){
                return true;
            }
            moveWasCastling = true;
            updateRook(piece.getSquare(), move);
        }
        return false;
    }
   
    // Updates the rook if the castling move is executed 
    private void updateRook(int[] currentSquare, int[] move) {
        int rookPlacementReleativeToKing = 3;
        int rookMoveToRealtiveToKing = 1;
        if (currentSquare[1] > move[1]) {
            rookPlacementReleativeToKing = -4;
            rookMoveToRealtiveToKing = -1;
        }
        Piece rook = board[currentSquare[0]][currentSquare[1]+rookPlacementReleativeToKing];
        int[] rookMoveto = new int[]{currentSquare[0], currentSquare[1]+rookMoveToRealtiveToKing};
        rookCastleSqaures = new int[][]{rook.getSquare(), rookMoveto};
        updateBoard(new Piece[]{null, rook}, rook.getSquare(), rookMoveto);
    }
}
