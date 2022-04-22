package chess;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PawnTest {
    private ChessGame game;
    List<List<Integer>> expectedMoves;
    List<List<Integer>> actualMoves;
    private final Piece whitePawn = new Pawn('w');
    private final Piece blackPawn = new Pawn('b');
    
    @BeforeEach
	public void setUp() {
        game = new ChessGame();
    }
    
    @Test
    @DisplayName("Test that a pawn can move one and two forward from start posistion")
    public void testFindLegalMoves1() {
        expectedMoves = Arrays.asList(Arrays.asList(5, 4), Arrays.asList(4, 4));
        whitePawn.setSquare(new int[]{6, 4});
        actualMoves = whitePawn.findLegalMoves(game.getBoard());
        assertEquals(true, expectedMoves.containsAll(actualMoves));

        expectedMoves = Arrays.asList(Arrays.asList(2, 0), Arrays.asList(3, 0));
        blackPawn.setSquare(new int[]{1, 0});
        actualMoves = blackPawn.findLegalMoves(game.getBoard());
        assertEquals(true, expectedMoves.containsAll(actualMoves));
    }

    @Test
    @DisplayName("Test that a pawn can move diagonal when taking a piece")
    public void testFindLegalMoves2() {
        game.setBoard("00000000000hpp000000P00000000000000000000p000000PBR0000000000000");
        expectedMoves = Arrays.asList(Arrays.asList(1, 5), Arrays.asList(1, 3));
        whitePawn.setSquare(new int[]{2, 4});
        actualMoves = whitePawn.findLegalMoves(game.getBoard());

        expectedMoves = Arrays.asList(Arrays.asList(6, 2), Arrays.asList(6, 0));
        blackPawn.setSquare(new int[]{5, 1});
        actualMoves = blackPawn.findLegalMoves(game.getBoard());
        assertEquals(true, expectedMoves.containsAll(actualMoves));
    }

    @Test
    @DisplayName("Test that a pawn cannot take a piece infront")
    public void testFindLegalMoves3() {
        game.setBoard("0000000000000000000000000000p0000000P000000000000000000000000000");
        expectedMoves = Arrays.asList();
        whitePawn.setSquare(new int[]{4, 4});
        blackPawn.setSquare(new int[]{3, 4});
        assertEquals(expectedMoves, whitePawn.findLegalMoves(game.getBoard()));
        assertEquals(expectedMoves, blackPawn.findLegalMoves(game.getBoard()));
    }
}
