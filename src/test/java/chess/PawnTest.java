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
    private final Piece whitePawn = new Pawn('w');
    private final Piece blackPawn = new Pawn('b');
    
    @BeforeEach
	public void setUp() {
        game = new ChessGame();
    }
    
    @Test
    @DisplayName("Test that a pawn can move one and two forawrd from start posistion")
    public void testFindLegalMoves1() {
        expectedMoves = Arrays.asList(Arrays.asList(5, 4), Arrays.asList(4, 4));
        int[] currentSquare= new int[]{6, 4};
        assertEquals(true, game.compareLists(expectedMoves, whitePawn.findLegalMoves(currentSquare, game.getBoard())));

        expectedMoves = Arrays.asList(Arrays.asList(2, 0), Arrays.asList(3, 0));
        currentSquare = new int[]{1, 0};
        assertEquals(true, game.compareLists(expectedMoves, blackPawn.findLegalMoves(currentSquare, game.getBoard())));
    }

    @Test
    @DisplayName("Test that a pawn can move diagonal when taking a piece")
    public void testFindLegalMoves2() {
        String newBoard = "rhbqkbhrp0pppppp0000P00000000000000000000p000000PPPP0PPPRHBQKBHR";
        game.setBoard(newBoard);

        expectedMoves = Arrays.asList(Arrays.asList(1, 5), Arrays.asList(1, 3));
        int[] currentSquare = new int[]{2, 4};
        assertEquals(true, game.compareLists(expectedMoves, whitePawn.findLegalMoves(currentSquare, game.getBoard())));

        expectedMoves = Arrays.asList(Arrays.asList(6, 2), Arrays.asList(6, 0));
        currentSquare = new int[]{5, 1};
        assertEquals(true, game.compareLists(expectedMoves, blackPawn.findLegalMoves(currentSquare, game.getBoard())));
    }

    @Test
    @DisplayName("Test that a pawn cannot take a piece infront")
    public void testFindLegalMoves3() {
        String newBoard = "rhbqkbhrpppp0ppp000000000000p0000000P00000000000PPPP0PPPRHBQKBHR";
        game.setBoard(newBoard);

        expectedMoves = Arrays.asList();
        int[] currentSquare = new int[]{4, 4};
        assertEquals(expectedMoves, whitePawn.findLegalMoves(currentSquare, game.getBoard()));

        currentSquare = new int[]{3, 4};
        assertEquals(expectedMoves, blackPawn.findLegalMoves(currentSquare, game.getBoard()));
    }
}
