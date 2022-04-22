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
        game.setBoard("0h00000000000000000000000000000000000000000000000000P00000000000");
        Piece whitePawn = new Pawn('w');
        whitePawn.setSquare(new int[]{6, 4});
        game.tryMove(whitePawn, new int[]{4, 4});
        assertEquals("0h0000000000000000000000000000000000P000000000000000000000000000", game.getBoardString());
    
        Piece blackHorse = new Horse('b');
        blackHorse.setSquare(new int[]{0, 1});
        game.tryMove(blackHorse, new int[]{2, 2});
        assertEquals("000000000000000000h00000000000000000P000000000000000000000000000", game.getBoardString());
    
        game.tryMove(whitePawn, new int[]{3, 4});
        assertEquals("000000000000000000h000000000P00000000000000000000000000000000000", game.getBoardString());
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
        Piece blackRook = new Rook('b');
        blackRook.setSquare(new int[]{0, 0});
        Piece whiteKing = new King('w');
        whiteKing.setSquare(new int[]{7, 4});

        game.tryMove(blackRook, new int[]{0, 4});
        assertEquals(true, game.isCheck(), "The king is in check by the black rook");
        game.tryMove(whiteKing, new int[]{7, 5});
        assertEquals(false, game.isCheck(), "The king moved away so isCheck() should be false");
    }

    @Test
    @DisplayName("Test for check with white horse and black king")
    public void testCheckWithHorse() {
        game.setBoard("0000k00000000000000H00000000000000000000000000000000000000000000");
        Piece whiteHorse = new Horse('w');
        whiteHorse.setSquare(new int[]{3, 3});
        Piece blackKing = new King('b');
        blackKing.setSquare(new int[]{0, 4});

        game.tryMove(whiteHorse, new int[]{1, 2});
        assertEquals(true, game.isCheck(), "The king is in check by the white horse");
        game.tryMove(blackKing, new int[]{1, 3});
        assertEquals(false, game.isCheck(), "The king moved away so isCheck() should be false");
    }

    @Test
    @DisplayName("Test for check with realistic posisiiton")
    public void testCheckWithRealisticPosistion() {
        assertEquals(false, game.isCheck(), "Should not be check from the start posiion");
        game.setBoard("r0bqk00rpppp0ppp00h00h000000p0H00bB0P00000000000PPPP0PPPRHBQK00R");
        assertEquals(false, game.isCheck());

        Piece whiteBishop = new Bishop('w');
        whiteBishop.setSquare(new int[]{4, 2});
        game.tryMove(whiteBishop, new int[]{1, 5});
        assertEquals(true, game.isCheck(), "The black king is in check by the white bishop");
    }

    @Test
    @DisplayName("Test for schoolmate")
    public void testSchoolMate() {
        assertEquals(game.isGameOver() && game.isCheck(), false);
        game.setBoard("r0bqkbhrppp000pp00hp00000000p00000B0P00000000Q00PPPP0PPPRHB0K0HR"); 
        Piece whiteQueen = new Queen('w');
        whiteQueen.setSquare(new int[]{5, 5});
        game.tryMove(whiteQueen, new int[]{1, 5});
        assertEquals(true, game.isGameOver() && game.isCheck(), "Schoolmate");
    }

    @Test
    @DisplayName("Test for Black rank mate")
    public void testBlackRankMate() {
        game.setBoard("00k00000000000000000000000000000000000000000000000000rPP0000000K");
        game.setTurn('b');
        game.getKingPiece('w').setHasMoved(true);
        game.getKingPiece('b').setHasMoved(true);
        Piece blackRook = new Rook('b');
        blackRook.setSquare(new int[]{6, 5});
        game.tryMove(blackRook, new int[]{7, 5});
        assertEquals(true, game.isCheck(), "Black rank mate");
    }

    @Test
    @DisplayName("Test for stalemate")
    public void testStaleMate() {
        game.setBoard("0k0000000P00000000K000000000000000000000000000000000000000000000"); 
        game.getKingPiece('w').setHasMoved(true);
        game.getKingPiece('b').setHasMoved(true);
        game.getBoard()[2][2].setSquare(new int[]{2, 2});
        game.tryMove(game.getBoard()[2][2], new int[]{2, 1});
        assertEquals(game.isCheck() && game.isGameOver(), false, "Not checkmate but stalemate");
        assertEquals(true, game.isGameOver(), "The posistion is a draw and the game should be over");
    }
}
