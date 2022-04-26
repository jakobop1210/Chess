package chess;

import java.util.List;

public class Queen extends Piece {
   
    public Queen(char color) {
        super(color);
    }

    // Returns all the legal moves for this piece
    @Override
    public List<List<Integer>> findLegalMoves(Piece[][] board) {
        Piece bishop = new Bishop(this.getColor());
        Piece rook = new Rook(this.getColor());
        bishop.setSquare(this.getSquare());
        rook.setSquare(this.getSquare());

        List<List<Integer>> legalMoves = bishop.findLegalMoves(board);
        legalMoves.addAll(rook.findLegalMoves(board));
        return legalMoves;
    }
}
