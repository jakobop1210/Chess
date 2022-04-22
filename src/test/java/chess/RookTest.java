package chess;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class RookTest {
    private ChessGame game;
    private List<List<Integer>> actualMoves;
    private List<List<Integer>> expectedMoves;
    private final Piece whiteRook = new Rook('w');
    private final Piece blackRook = new Rook('b');
    
    @BeforeEach
	public void setUp() {
        game = new ChessGame();
        whiteRook.setSquare(new int[]{7, 7});
        blackRook.setSquare(new int[]{0, 0});
    }
    
    @Test
    @DisplayName("Test that the rook cannot move from the beginning posistion")
    public void testFindLegalMovesBeginningPosistion() {
        expectedMoves = Arrays.asList();
        assertEquals(expectedMoves, whiteRook.findLegalMoves(game.getBoard()));
        assertEquals(expectedMoves, blackRook.findLegalMoves(game.getBoard()));
    }

    @Test
    @DisplayName("Test that the rook can move until hitting a piece. If the piece has the same color, the move will not be legal")
    public void testFindLegalMoves() {
        game.setBoard("0000000000000000000000000000000000000000000000000000000P000q000R");
        expectedMoves = Arrays.asList(Arrays.asList(7, 6), Arrays.asList(7, 5), Arrays.asList(7, 4), Arrays.asList(7, 3));
        actualMoves = whiteRook.findLegalMoves(game.getBoard());
        assertEquals(true, expectedMoves.containsAll(actualMoves),
        "Cannot move forward because of a white pawn, but can move to the left until taking the black queen");

        game.setBoard("rQ0000000000000000000000p000000000000000000000000000000000000000");
        expectedMoves = Arrays.asList(Arrays.asList(0, 1), Arrays.asList(1, 0), Arrays.asList(2, 0));
        actualMoves = blackRook.findLegalMoves(game.getBoard());
        assertEquals(true, expectedMoves.containsAll(actualMoves),
        "Cannot move one left to take white queen, and foward until hitting black pawn");
    }
}
