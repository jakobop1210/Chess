package chess;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PieceTest {
    private ChessGame game;
    private int[][] exampleMoves;
    private List<List<Integer>> expectedMoves;
    private final Piece pieceWhite = new King('w');
    private final Piece pieceBlack = new Horse('b');

    @BeforeEach
	public void setUp() {
        game = new ChessGame();
    }

    @Test
    public void testConstructor() {
        assertThrows(IllegalArgumentException.class, () -> {
            new King('m');
        }, "Cannot create a piece with color m");

        assertThrows(IllegalArgumentException.class, () -> {
            new Rook('0');
        }, "Cannot create a piece with color 0");

        assertEquals(new Pawn('w').getColor(), 'w');
        assertEquals(new Bishop('b').getColor(), 'b');
    }
    
    @Test 
    @DisplayName("Test that the moves outside the board get filtered out")
    public void testMovesOutsideBoard() {
        game.setBoard("000000h000000000000000000000000000000000000000000000000K00000000");

        exampleMoves = new int[][]{{1, 0}, {2, 0}, {0, -1}, {0, -2}, {-1, 0}};
        expectedMoves = Arrays.asList(Arrays.asList(6, 0));
        assertEquals(pieceWhite.filterLegalMoves(game.getBoard(), exampleMoves, new int[]{7, 0}), expectedMoves);

        exampleMoves = new int[][]{{2,-1}, {-2,1}, {-2,-1}, {-1,2}, {-1,-2}};
        expectedMoves = Arrays.asList(Arrays.asList(2, 5));
        assertEquals(pieceBlack.filterLegalMoves(game.getBoard(), exampleMoves, new int[]{0, 6}), expectedMoves);
    }

    @Test 
    @DisplayName("Test that moves where there is a piece of same color get filtered out")
    public void testFilteredMoves() {
        String newBoard = "rhbqkbhrppp0pppp000p0000000000000000000000000P00PPPPP0PPRHBQKBHR";
        game.setBoard(newBoard);

        exampleMoves = new int[][]{{0, -1}, {1, -1}, {1, 0}, {2, 0}, {1, 1}, {0, 1}};
        expectedMoves = Arrays.asList(Arrays.asList(1, 3));
        assertEquals(pieceBlack.filterLegalMoves(game.getBoard(), exampleMoves, new int[]{0, 3}), expectedMoves);

        exampleMoves = new int[][]{{2,-1}, {2, 1}, {-2, 1}};
        expectedMoves = Arrays.asList(Arrays.asList(5, 7));
        assertEquals(pieceWhite.filterLegalMoves(game.getBoard(), exampleMoves, new int[]{7, 6}), expectedMoves);
    }
}
