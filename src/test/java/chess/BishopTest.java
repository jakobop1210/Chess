package chess;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BishopTest {
    private ChessGame game;
    private List<List<Integer>> expectedMoves;
    private static final Piece whiteBishop = new Bishop('w');
    private static final Piece blackBishop = new Bishop('b');
    
    @BeforeEach
	public void setUp() {
        game = new ChessGame();
    }
    
    @Test
    @DisplayName("Test that the bishop cannot move from the beginning posistion")
    public void testFindLegalMoves() {
        expectedMoves = Arrays.asList();
        assertEquals(expectedMoves, whiteBishop.findLegalMoves(new int[]{7, 2}, game.getBoard()));
        assertEquals(expectedMoves, blackBishop.findLegalMoves(new int[]{0, 5}, game.getBoard()));
    }

    @Test
    @DisplayName("Test that the bishop can move until hitting a piece. If the piece has the same color, the move will not be legal")
    public void testFindLegalMoves2() {
        game.setBoard("0000000000000000000000000000000000000000000P00000000000000000B00");
        expectedMoves = Arrays.asList(Arrays.asList(6, 6), Arrays.asList(5, 7), Arrays.asList(6, 4));
        List<List<Integer>> whiteMoves = whiteBishop.findLegalMoves(new int[]{7, 5}, game.getBoard());
        assertEquals(false, whiteMoves.contains(Arrays.asList(5, 3)), "Cannot take a friendly piece");
        assertEquals(true, game.compareLists(expectedMoves, whiteMoves));
    }

    @Test
    @DisplayName("If the hitting piece has a different color, the move will be legal")
    public void testFindLegalMoves3() {
        game.setBoard("00000b000000P000000000000000000000000000000000000000000000000000");
        List<List<Integer>> blackMoves = blackBishop.findLegalMoves(new int[]{0, 5}, game.getBoard());
        expectedMoves = Arrays.asList(Arrays.asList(1, 6), Arrays.asList(2, 7), Arrays.asList(1, 4));
        assertEquals(true, blackMoves.contains(Arrays.asList(1, 4)), "Should be able to take enemy piece");
        assertEquals(true, game.compareLists(expectedMoves, blackMoves));
    }
}
