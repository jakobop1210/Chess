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
    private final Piece whiteHorse = new Horse('w');;
    private final Piece blackHorse = new Horse('b');
    
    @BeforeEach
	public void setUp() {
        game = new ChessGame();
    }
    
    @Test
    @DisplayName("Test that the horses can move to correct squares from start posistion")
    public void testFindLegalMoves1() {
        expectedMoves = Arrays.asList(Arrays.asList(5, 2), Arrays.asList(5, 0));
        int[] currentSquare = new int[]{7, 1};
        assertEquals(true, game.compareLists(expectedMoves, whiteHorse.findLegalMoves(currentSquare, game.getBoard())));

        expectedMoves = Arrays.asList(Arrays.asList(2, 7), Arrays.asList(2, 5));
        currentSquare = new int[]{0, 6};
        assertEquals(true, game.compareLists(expectedMoves, blackHorse.findLegalMoves(currentSquare, game.getBoard())));
    }

    @Test
    @DisplayName("Test that the horse can move all directions if possible")
    public void testFindLegalMoves2() {
        String newBoard = "r0b0kbhr0ppp0p0pp0h00qp0000000000000P00000p00H0PPP00KPP0RHBQ0B0R";
        game.setBoard(newBoard);

        expectedMoves = Arrays.asList(Arrays.asList(3, 4), Arrays.asList(3, 6), Arrays.asList(4, 7), Arrays.asList(6, 7), 
                        Arrays.asList(7, 6), Arrays.asList(7, 4), Arrays.asList(6, 3), Arrays.asList(4, 3));
        int[] currentSquare = new int[]{5, 5};
        assertEquals(true, game.compareLists(expectedMoves, whiteHorse.findLegalMoves(currentSquare, game.getBoard())));

        expectedMoves = Arrays.asList(Arrays.asList(0, 1), Arrays.asList(0, 3), Arrays.asList(1, 4), Arrays.asList(3, 4), 
                        Arrays.asList(4, 3), Arrays.asList(4, 1), Arrays.asList(3, 0), Arrays.asList(1, 0));
        currentSquare = new int[]{2, 2};
        assertEquals(true, game.compareLists(expectedMoves, blackHorse.findLegalMoves(currentSquare, game.getBoard())));
    }

    @Test
    @DisplayName("Test that the horse cannot move if there is no legal moves")
    public void testFindLegalMoves3() {
        String newBoard = "rhb0k0hrppppbpp000000q0p0000p000P0000000R0P000000P0PPPPP0HBQKBHR";
        game.setBoard(newBoard);

        expectedMoves = Arrays.asList();
        int[] currentSquare = new int[]{7, 1};
        assertEquals(expectedMoves, whiteHorse.findLegalMoves(currentSquare, game.getBoard()));

        currentSquare = new int[]{0, 6};
        assertEquals(expectedMoves, blackHorse.findLegalMoves(currentSquare, game.getBoard()));
    }
}
