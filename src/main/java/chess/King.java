package chess;

import java.util.Arrays;
import java.util.List;

public class King extends Piece {
    private final int[][] moves = {{-1,0}, {-1,1}, {0,1}, {1,1}, {1,0}, {1,-1}, {0,-1}, {-1,-1}};
    private final int[] castleRight = {2,1};
    private final int[] castleLeft = {-2,-1,-3};

    public King(char color) {
        super(color);
        super.setBreakLoopWhenHttingPiece(false);
    }

    private boolean isSquareEmpty(int[] square, Piece[][] board) {
        if (board[square[0]][square[1]] == null) {
            return true;
        }
        return false;
    }

    // Checks if the castling move is legal (not checking for check)
    private List<List<Integer>> IfLegalAddCastling(Piece[][] board, List<List<Integer>> legalMoves, int[] direction, int rookPos) {
        boolean spacesBetweenIsNotEmpty = Arrays.stream(direction)
        .anyMatch(p -> !isSquareEmpty(new int[]{this.getX(), this.getY()+p}, board));

        Piece castleRook = board[this.getX()][this.getY()+rookPos];
        if (castleRook != null) {
            if (!spacesBetweenIsNotEmpty && castleRook.getName() == "chess.Rook" && !castleRook.hasMoved()) {
                legalMoves.add(Arrays.asList(this.getX(), this.getY()+direction[0]));
            }      
        }
        return legalMoves;
    }

    // Returns all the legal moves for this piece
    @Override
    public List<List<Integer>> findLegalMoves( Piece[][] board) {
        List<List<Integer>> legalMoves = filterLegalMoves(board, moves);
        if (!this.hasMoved()) {
            legalMoves = IfLegalAddCastling(board, legalMoves, castleRight, +3);
            legalMoves = IfLegalAddCastling(board, legalMoves, castleLeft, -4);
        }
        return legalMoves;
    }
}
