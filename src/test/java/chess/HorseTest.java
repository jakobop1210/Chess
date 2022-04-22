package chess;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class HorseTest {
    private ChessGame game;
    private List<List<Integer>> expectedMoves;
    private List<List<Integer>> actualMoves;
    private final Piece whiteHorse = new Horse('w');;
    private final Piece blackHorse = new Horse('b');
    
    @BeforeEach
	public void setUp() {
        game = new ChessGame();
        whiteHorse.setSquare(new int[]{7, 1});
        blackHorse.setSquare(new int[]{0, 6});
    }
    
    @Test
    @DisplayName("Test that the horses can move to correct squares from start posistion")
    public void testFindLegalMoves1() {
        expectedMoves = Arrays.asList(Arrays.asList(5, 2), Arrays.asList(5, 0));
        assertEquals(true, expectedMoves.containsAll(whiteHorse.findLegalMoves(game.getBoard())));
        expectedMoves = Arrays.asList(Arrays.asList(2, 7), Arrays.asList(2, 5));
        assertEquals(true, expectedMoves.containsAll(blackHorse.findLegalMoves(game.getBoard())));
    }

    @Test
    @DisplayName("Test that the horse can move all directions if possible")
    public void testFindLegalMoves2() {
        game.setBoard("000000000000000000h00000000000000000000000000H000000000000000000");
        expectedMoves = Arrays.asList(Arrays.asList(3, 4), Arrays.asList(3, 6), Arrays.asList(4, 7), Arrays.asList(6, 7), 
                        Arrays.asList(7, 6), Arrays.asList(7, 4), Arrays.asList(6, 3), Arrays.asList(4, 3));
        whiteHorse.setSquare(new int[]{5, 5});
        assertEquals(true, expectedMoves.containsAll(whiteHorse.findLegalMoves(game.getBoard())));

        expectedMoves = Arrays.asList(Arrays.asList(0, 1), Arrays.asList(0, 3), Arrays.asList(1, 4), Arrays.asList(3, 4), 
                        Arrays.asList(4, 3), Arrays.asList(4, 1), Arrays.asList(3, 0), Arrays.asList(1, 0));
        blackHorse.setSquare(new int[]{2, 2});
        assertEquals(true, expectedMoves.containsAll(blackHorse.findLegalMoves(game.getBoard())));
    }

    @Test
    @DisplayName("Test that the horse cannot move if there is no legal moves")
    public void testFindLegalMoves3() {
        game.setBoard("rhb0k0hrppppbpp000000q0p0000p000P0000000R0P000000P0PPPPP0HBQKBHR");
        expectedMoves = Arrays.asList();
        assertEquals(expectedMoves, whiteHorse.findLegalMoves(game.getBoard()));
        assertEquals(expectedMoves, blackHorse.findLegalMoves(game.getBoard()));
    }
}
