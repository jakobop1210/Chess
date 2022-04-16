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
    public void testFindLegalMoves1() {
        expectedMoves = Arrays.asList();
        int[] currentSquare = new int[]{7, 2};
        assertEquals(expectedMoves, whiteBishop.findLegalMoves(currentSquare, game.getBoard()));

        currentSquare = new int[]{0, 5};
        assertEquals(expectedMoves, blackBishop.findLegalMoves(currentSquare, game.getBoard()));
    }

    @Test
    @DisplayName("Test that the bishop can move until hitting a piece. If the piece has the same color, the move will not be legal")
    public void testFindLegalMoves2() {
        String newBoard = "r0bq0rk0ppp00ppp00hp0h0000b0p000000PPB0000H00000PPPQ0PPP00KR0BHR";
        game.setBoard(newBoard);

        expectedMoves = Arrays.asList(Arrays.asList(5, 4), Arrays.asList(5, 6), Arrays.asList(3, 4), Arrays.asList(3, 6), Arrays.asList(2, 7));
        int[] currentSquare = new int[]{4, 5};
        assertEquals(true, game.compareLists(expectedMoves, whiteBishop.findLegalMoves(currentSquare, game.getBoard())));

        expectedMoves = Arrays.asList(Arrays.asList(4, 3), Arrays.asList(4, 1), Arrays.asList(5, 0), Arrays.asList(2, 1));
        currentSquare = new int[]{3, 2};
        assertEquals(true, game.compareLists(expectedMoves, blackBishop.findLegalMoves(currentSquare, game.getBoard())));
    }
}
