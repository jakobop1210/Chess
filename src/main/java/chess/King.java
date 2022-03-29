package chess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class King extends Piece {
    private boolean hasMoved = false;
    private int[][] moves = {{-1,0}, {-1,1}, {0,1}, {1,1}, {1,0}, {1,-1}, {0,-1}, {-1,-1}};
    private int[][] castleRight = {{0,2}, {0, 1}};
    private int[][] castleLeft = {{0,-2}, {0,-1}, {0,-3}};

    public King(char color) {
        super(color);
        super.setJumpable(true);
    }

    
    public boolean isHasMoved() {
        return hasMoved;
    }


    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    private boolean isSquareEmpty(int[] square, Piece[][] board) {
        if (board[square[0]][square[1]] == null) {
            return true;
        }
        return false;
    }

    private List<List<Integer>> canKingCastle(int[] currentSquare, Piece[][] board, List<List<Integer>> legalMoves) {
        boolean spacesBetweenIsEmpty = true;
        if (!isHasMoved()) {
            for (int[] move : castleRight) {
                int[] square = new int[]{currentSquare[0]+move[0], currentSquare[1]+move[1]};
                if (!isSquareEmpty(square, board)) {
                    spacesBetweenIsEmpty = false;
                }
            }
            Piece castleRookRight = board[currentSquare[0]][currentSquare[1]+3];
            if (spacesBetweenIsEmpty && castleRookRight != null) {
                if (castleRookRight.getName() == "chess.Rook" && !castleRookRight.isHasMoved()) {
                    legalMoves.add(Arrays.asList(currentSquare[0]+castleRight[0][0], currentSquare[1]+castleRight[0][1]));
                }
            }    

            spacesBetweenIsEmpty = true;
            for (int[] move : castleLeft) {
                int[] square = new int[]{currentSquare[0]+move[0], currentSquare[1]+move[1]};
                if (!isSquareEmpty(square, board)) {
                    spacesBetweenIsEmpty = false;
                }
            }
            Piece castleRookLeft = board[currentSquare[0]][currentSquare[1]-4];
            if (spacesBetweenIsEmpty && castleRookLeft != null) {
                if (castleRookLeft.getName() == "chess.Rook" && !castleRookLeft.isHasMoved()) {
                    legalMoves.add(Arrays.asList(currentSquare[0]+castleLeft[0][0], currentSquare[1]+castleLeft[0][1]));
                }
            }     
        }
        return legalMoves;
    }

    public List<List<Integer>> findLegalMoves(int[] currentSquare, Piece[][] board) {
        List<List<Integer>> legalMoves = new ArrayList<>();
        legalMoves = legalMoves(board, moves, currentSquare);
        legalMoves = canKingCastle(currentSquare, board, legalMoves);
        
        return legalMoves;
    }
}
