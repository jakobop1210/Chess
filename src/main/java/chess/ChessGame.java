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
        this.turn = turn;
    }

    public void setNextTurn(char nextTurn) {
        this.nextTurn = nextTurn;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public void setLastMoveSquare(int[] lastMoveSquare) {
        this.lastMoveSquare = lastMoveSquare;
    }

    // Execute move if it's legal, checks for check and checkmate, checks result
    public boolean tryMove(Piece piece, int[] move) {
        if (piece == null || piece.getSquare() == null) throw new NullPointerException("Not a valid piece");
        if (piece.isIllegalSquare(move)) throw new IllegalArgumentException("Not a valid move");
        if (piece.getColor() != turn) return false;
        
        moveWasCastling = false;
        List<List<Integer>> legalMoves = filterOutCheckMoves(piece, piece.findLegalMoves(board));
        List<Integer> moveToList = Arrays.asList(move[0], move[1]);
        
        if (legalMoves.contains(moveToList)) {
            if (checkForIllegalCastling(piece, move)) return false;
            executeMove(piece, move);
            return true;
        }
        System.out.println("Brikken kan ikke flytte ditt, prøv et annet trekk!");
        return false;
    }

    // Executes the move by updating the board and turn. Also checking for check and checkmate
    private void executeMove(Piece piece, int[] move) {
        if (piece.getName() == "chess.Pawn" && (move[0] == 0 || move[0] == 7)) { 
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
            if (checkForMate()) {
                winner = nextTurn;
                gameOver = true;
            }
        } else if (checkForMate()) {
            gameOver = true;
        } else {
            check = false;
        }
        System.out.println(boardClass.boardString());
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
        if (turn != 'w' && turn != 'b') {
            throw new IllegalArgumentException("Not a valid color");
        } 
        return Arrays.stream(board)
            .flatMap(row -> Arrays.stream(row)
            .filter(p -> p != null)
            .filter(p -> p.getName() == "chess.King" && p.getColor() == turn))
            .findFirst().
            orElse(null);
    }
            
    // Filter out illegal moves that leads to check
    private List<List<Integer>> filterOutCheckMoves(Piece piece, List<List<Integer>> moves) {
        List<List<Integer>> notCheckMoves = new ArrayList<>();
        for (List<Integer> square : moves) {
            int[] originalSquare = piece.getSquare();
            int[] moveTo = {square.get(0), square.get(1)};
            Piece pieceMoveTo = board[moveTo[0]][moveTo[1]];
            updateBoard(new Piece[]{null, piece}, piece.getSquare(), moveTo);
            if (!checkForCheck()) notCheckMoves.add(square);
            updateBoard(new Piece[]{pieceMoveTo, piece}, piece.getSquare(), originalSquare);
        }
        return notCheckMoves;
    }

    // Checks for check
    private boolean checkForCheck() {
        if (getKingPiece(turn) != null) {
            List<Integer> kingSquareList = Arrays.asList(getKingPiece(turn).getX(), getKingPiece(turn).getY());
            for (Piece[] row : board) for (Piece piece : row) {
                if (piece != null) {
                    if (piece.getColor() == nextTurn) {
                        if (piece.findLegalMoves(board).stream().anyMatch(p -> p.equals(kingSquareList))) {
                            return true;
                        }  
                    }
                }
            }
        }
        return false;  
    }

    // Checks for checkmate
    private boolean checkForMate() {
        for (Piece[] row : board) for (Piece piece : row) {
            if (piece != null) {
                if (piece.getColor() == turn) {
                    List<List<Integer>> pieceMoves = piece.findLegalMoves(board);
                    if (!filterOutCheckMoves(piece, pieceMoves).isEmpty()) return false;
                }
            }
        }
        return true;
    }

    // Checks if move is castling and if it's legal
    private boolean checkForIllegalCastling(Piece piece, int[] move) {
        if (piece.getName() == "chess.King" && Math.abs(piece.getY()-move[1]) == 2) {
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
                System.out.println("Kongen kan ikke rokere, prøv et annet trekk!");
                return true;
            }
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
        moveWasCastling = true;
        updateBoard(new Piece[]{null, rook}, rook.getSquare(), rookMoveto);
    }
}
