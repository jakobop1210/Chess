package chess;

import java.util.ArrayList;
import java.util.List;

public class ChessGame {
    private Piece[][] boardGame;
    private char turn = 'W';
    
    public ChessGame() {
        Board newBoard = new Board();
        this.boardGame = newBoard.getBoard();
    }

    public List<List<Integer>> findLegalMoves(Piece piece) {
        List<List<Integer>> squares = new ArrayList<>();

        if (Character.isUpperCase(piece.getPiece()) && turn == 'W') {
            throw new IllegalArgumentException("Det er ikke hvit sin tur til å flytte!");
        } else if (!Character.isUpperCase(piece.getPiece()) && turn == 'B') {
            throw new IllegalArgumentException("Det er ikke svart sin tur til å flytte!");
        }

        return squares;
    }

    public void checkForCheck() {

    }

    public void checkForMate() {

    }

    public void updateBoard() {

    }

    public static void main(String[] args) {
        
    }

}
