package chess;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class QueenTest {
    private ChessGame game;
    private List<List<Integer>> expectedMoves;
    private List<List<Integer>> actualMoves;
    private static final Piece whiteQueen = new Queen('w');
    private static final Piece blackQueen = new Queen('b');
    
    @BeforeEach
	public void setUp() {
        game = new ChessGame();
        whiteQueen.setSquare(new int[]{7, 3});
        blackQueen.setSquare(new int[]{0, 3});
    }
    
    @Test
    @DisplayName("Test that the queen cannot move from the beginning position")
    public void testFindLegalMoves() {
        expectedMoves = Arrays.asList();
        assertEquals(expectedMoves, whiteQueen.findLegalMoves(game.getBoard()));
        assertEquals(expectedMoves, blackQueen.findLegalMoves(game.getBoard()));
    }

    @Test
    @DisplayName("Test that the queen can move until hitting a piece. If the piece has the same color, the move will not be legal")
    public void testFindLegalMoves2() {
        game.setBoard("00Bqk000000pp00000000000r00H000000000000000000000000000000000000");
        expectedMoves = Arrays.asList(Arrays.asList(0, 2), Arrays.asList(1, 2), Arrays.asList(2, 1));
        actualMoves = blackQueen.findLegalMoves(game.getBoard());
        assertEquals(false, actualMoves.contains(Arrays.asList(0, 0)), 
        "Cannot take a friendly piece");
        assertEquals(true, expectedMoves.containsAll(actualMoves));
    }
}

