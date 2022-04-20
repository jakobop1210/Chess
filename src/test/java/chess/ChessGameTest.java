package chess;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ChessGameTest {
    private ChessGame game;
    

    @BeforeEach
	public void setUp() {
        game = new ChessGame();
    }
    
    @Test
    @DisplayName("Test that board get updated correct when moving pieces")
    public void testUpdateBoard() {
        game.setBoard("0h00000000000000000000000000000000000000000000000000P000000Q0000");
        game.tryMove(new int[]{6, 4}, new Pawn('w'), new int[]{4, 4});
        assertEquals(game.getBoardString(), "0h0000000000000000000000000000000000P0000000000000000000000Q0000");
    
        game.tryMove(new int[]{0, 1}, new Horse('b'), new int[]{2, 2});
        assertEquals(game.getBoardString(), "000000000000000000h00000000000000000P0000000000000000000000Q0000");
    
        game.tryMove(new int[]{7, 3}, new Queen('w'), new int[]{3, 7});
        assertEquals(game.getBoardString(), "000000000000000000h000000000000Q0000P000000000000000000000000000");
    }
    
    @Test
    @DisplayName("Test that the corret king piece is found")
    public void testFindCorrectKing() {
        assertEquals(game.getKingPiece('w').getClass(), new King('w').getClass());
        assertEquals(game.getKingPiece('b').getClass(), new King('b').getClass());
    
        assertThrows(IllegalArgumentException.class, () -> {
            game.getKingPiece('0');
        }, "Cannot create a piece with color 0");
        assertThrows(IllegalArgumentException.class, () -> {
            game.getKingPiece('s');
        }, "Cannot create a piece with color s");
    }
    
    @Test
    @DisplayName("Test for check with black rook and white king")
    public void testCheckWithRook() {
        game.setBoard("r00000000000000000000000000000000000000000000000000000000000K000");
        game.setTurn('b');
        game.tryMove(new int[]{0, 0}, new Rook('b'), new int[]{0, 4});
        assertEquals(game.isCheck(), true, "The king is in check by the black rook");
        game.tryMove(new int[]{7, 4}, new King('w'), new int[]{7, 5});
        assertEquals(game.isCheck(), false, "The king moved away so isCheck() should be false");
    }

    @Test
    @DisplayName("Test for check with white horse and black king")
    public void testCheckWithHorse() {
        game.setBoard("0000k00000000000000H00000000000000000000000000000000000000000000");
        game.tryMove(new int[]{3, 3}, new Horse('w'), new int[]{1, 2});
        assertEquals(game.isCheck(), true, "The king is in check by the white horse");
        game.tryMove(new int[]{0, 4}, new King('b'), new int[]{1, 3});
        assertEquals(game.isCheck(), false, "The king moved away so isCheck() should be false");
    }

    @Test
    @DisplayName("Test for check with realistic posisiiton")
    public void testCheckWithRealisticPosistion() {
        assertEquals(game.isCheck(), false, "Should not be check from the start posiion");
        game.setBoard("r0bqk00rpppp0ppp00h00h000000p0H00bB0P00000000000PPPP0PPPRHBQK00R");
        assertEquals(game.isCheck(), false);
        game.tryMove(new int[]{4, 2}, new Bishop('w'), new int[]{1, 5});
        assertEquals(game.isCheck(), true, "The black king is in check by the white bishop");
    }

    @Test
    @DisplayName("Test for schoolmate")
    public void testSchoolMate() {
        assertEquals(game.isGameOver() && game.isCheck(), false);
        game.setBoard("r0bqkbhrppp000pp00hp00000000p00000B0P00000000Q00PPPP0PPPRHB0K0HR"); 
        game.tryMove(new int[]{5, 5}, new Queen('w'), new int[]{1, 5});
        assertEquals(game.isGameOver() && game.isCheck(), true, "Schoolmate");
    }

    @Test
    @DisplayName("Test for Black rank mate")
    public void testBlackRankMate() {
        game.setBoard("00k00000000000000000000000000000000000000000000000000rPP0000000K");
        game.setTurn('b');
        game.getKingPiece('w').setHasMoved(true);
        game.getKingPiece('b').setHasMoved(true);
        game.tryMove(new int[]{6, 5}, new Rook('b'), new int[]{7, 5});
        assertEquals(game.isCheck(), true, "Black rank mate");
    }

    @Test
    @DisplayName("Test for stalemate")
    public void testStaleMate() {
        game.setBoard("0k0000000P00000000K000000000000000000000000000000000000000000000"); 
        game.getKingPiece('w').setHasMoved(true);
        game.getKingPiece('b').setHasMoved(true);
        game.tryMove(new int[]{2, 2}, game.getBoard()[2][2], new int[]{2, 1});
        assertEquals(game.isCheck() && game.isGameOver(), false, "Not checkmate but stalemate");
        assertEquals(game.isGameOver(), true, "The posistion is a draw and the game should be over");
    }
}
