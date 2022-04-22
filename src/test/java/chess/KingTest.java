package chess;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class KingTest {
    private ChessGame game;
    List<List<Integer>> expectedMoves;
    List<List<Integer>> actualMoves;
    private final Piece whiteKing = new King('w');
    private final Piece blackKing = new King('b');
    
    @BeforeEach
	public void setUp() {
        game = new ChessGame();
        whiteKing.setSquare(new int[]{7, 4});
        blackKing.setSquare(new int[]{0, 4});
    }
    
    @Test
    @DisplayName("Test that the king cannot move from start posistion")
    public void testFindLegalMoves1() {
        expectedMoves = Arrays.asList();
        assertEquals(expectedMoves, whiteKing.findLegalMoves(game.getBoard()));
        assertEquals(expectedMoves, blackKing.findLegalMoves(game.getBoard()));
    }

    @Test
    @DisplayName("Test that the king can move one square in all directions including diagonal")
    public void testFindLegalMoves2() {
        game.setBoard("000P0000000k0000000h00000000000000000000000000000000000000000000");
        blackKing.setHasMoved(true);
        expectedMoves = Arrays.asList(Arrays.asList(1, 4), Arrays.asList(1, 2), Arrays.asList(2, 4), 
                        Arrays.asList(2, 2), Arrays.asList(0, 4), Arrays.asList(0, 2), Arrays.asList(0, 3));
        blackKing.setSquare(new int[]{1, 3});
        actualMoves = blackKing.findLegalMoves(game.getBoard());

        assertEquals(false, actualMoves.contains(Arrays.asList(2, 3)),
        "Cannot take a friendly horse");
        assertEquals(true, expectedMoves.containsAll(actualMoves),
        "Can move all directions except one forawrd because of a horse with same color");
    }

    @Test
    @DisplayName("Test that castling moves will be added if they are legal")
    public void testLegalCastling() {
        game.setBoard("r000k0000000000000000000000000000000000000000000000000000000K00R");
        actualMoves = whiteKing.findLegalMoves(game.getBoard());
        assertEquals(true, actualMoves.contains(Arrays.asList(7, 6)),
        "Castling to the right should be legal for white");

        actualMoves = blackKing.findLegalMoves(game.getBoard());
        assertEquals(true, actualMoves.contains(Arrays.asList(0, 2)),
        "Castling to the left should be legal for black");
    }

    @Test
    @DisplayName("Test that caslting will not be legal if the king or rook has moved")
    public void testPiecesHasMoved() {
        game.setBoard("r000k0000000000000000000000000000000000000000000000000000000K00R");
        whiteKing.setHasMoved(true);
        actualMoves = whiteKing.findLegalMoves(game.getBoard());
        assertEquals(false, actualMoves.contains(Arrays.asList(7, 6)),
        "Cannot castle if the king has moved");

        game.getBoard()[0][0].setHasMoved(true);
        actualMoves = blackKing.findLegalMoves(game.getBoard());
        assertEquals(false, actualMoves.contains(Arrays.asList(0, 2)),
        "Cannot castle if the rook has moved");
    }

    @Test
    @DisplayName("Test that caslting will not be legal if the king or rook has moved")
    public void testIsSquareEmpty() {
        game.setBoard("r00Bk0000000000000000000000000000000000000000000000000000000K0HR");
        whiteKing.setHasMoved(true);
        actualMoves = whiteKing.findLegalMoves(game.getBoard());
        assertEquals(false, actualMoves.contains(Arrays.asList(7, 6)),
        "Cannot castle because of the friendly horse between the king and rook");

        game.getBoard()[0][0].setHasMoved(true);
        actualMoves = blackKing.findLegalMoves(game.getBoard());
        assertEquals(false, actualMoves.contains(Arrays.asList(0, 2)),
        "Cannot castle because of the enemy bishop between the king and rook");
    }

}

