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
        pieceWhite.setSquare(new int[]{7, 0});
        pieceBlack.setSquare(new int[]{0, 6});
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
        assertEquals(expectedMoves, pieceWhite.filterLegalMoves(game.getBoard(), exampleMoves));

        exampleMoves = new int[][]{{2, -1}, {-2, 1}, {-2, -1}, {-1, 2}, {-1, -2}};
        expectedMoves = Arrays.asList(Arrays.asList(2, 5));
        assertEquals(expectedMoves, pieceBlack.filterLegalMoves(game.getBoard(), exampleMoves));
    }

    @Test 
    @DisplayName("Test that moves where there is a piece of same color get filtered out")
    public void testFilteredMoves() {
        String newBoard = "000000h00000p00000000p0q000000000000000000000000PP000000KH000000";
        game.setBoard(newBoard);

        exampleMoves = new int[][]{{-1, 0}, {0, 1}, {-1, 1}};
        expectedMoves = Arrays.asList();
        assertEquals(expectedMoves, pieceWhite.filterLegalMoves(game.getBoard(), exampleMoves));

        exampleMoves = new int[][]{{2, -1}, {2, 1}, {1, -2}};
        assertEquals(expectedMoves, pieceBlack.filterLegalMoves(game.getBoard(), exampleMoves));
    }

    @Test 
    @DisplayName("Test illegal and legal inputs for setSquare")
    public void testSetSquare() {
        assertThrows(IllegalArgumentException.class, () -> {
            pieceWhite.setSquare(new int[]{-5, 3});
        }, "Cannot take in a piece on negative x square");

        assertThrows(IllegalArgumentException.class, () -> {
            pieceWhite.setSquare(new int[]{2, 8});
        }, "Cannot take in a piece on y square higher than 7");

        assertThrows(IllegalArgumentException.class, () -> {
            pieceWhite.setSquare(new int[]{20, 1335});
        }, "Cannot take in a piece x and y square higher than seven");

        pieceWhite.setSquare(new int[]{3, 6});
        assertEquals(3, pieceWhite.getX());
        assertEquals(6, pieceWhite.getY());
    }
}
