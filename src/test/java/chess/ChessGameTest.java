package chess;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;

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
    @DisplayName("Test setting illegal values")
    public void testSetValues() {
        assertThrows(IllegalArgumentException.class, () -> {
            game.setTurn('s');
        }, "Not a valid color");
        assertThrows(IllegalArgumentException.class, () -> {
            game.setNextTurn('0');
        }, "Not a valid color");
        assertThrows(IllegalArgumentException.class, () -> {
            game.setLastMoveSquare(new int[]{1, 2, 3});
        }, "A sqaure can only have 2 numbers");
        assertThrows(IllegalArgumentException.class, () -> {
            game.setLastMoveSquare(new int[]{-1, 100});
        }, "The numbers need to be between 0 and 7");
    }

    @Test
    @DisplayName("Test for illegal inputs, and legal/illegal moves")
    public void testTryMove() {
        assertThrows(NullPointerException.class, () -> {
            game.tryMove(null, new int[]{4, 4});
        }, "Need a valid piece to execute a move");
        assertThrows(NullPointerException.class, () -> {
            game.tryMove(new Pawn('w'), new int[]{5, 4});
        }, "Need a piece with a posistion");

        Piece whitePawn = new Pawn('w');
        whitePawn.setSquare(new int[]{6, 4});
        assertEquals(true, game.tryMove(whitePawn, new int[]{5, 4}), "Should be legal");
        assertThrows(IllegalArgumentException.class, () -> {
            game.tryMove(whitePawn, new int[]{4, 4});
        }, "Not whites turn");
        game.setTurn('w');
        assertThrows(IllegalArgumentException.class, () -> {
            game.tryMove(whitePawn, new int[]{-2, 8});
        }, "Not a valid move, the numbers needs to be between 0 and 7");
        
        Piece blackHorse = new Horse('b');
        blackHorse.setSquare(new int[]{0, 1});
        game.setTurn('b');
        assertEquals(true, game.tryMove(blackHorse, new int[]{2, 0}), "Should be legal");
        assertThrows(IllegalArgumentException.class, () -> {
            game.tryMove(whitePawn, new int[]{4, 5});
        }, "Not a legal move for that piece");

    }

    @Test
    @DisplayName("Test that the move is executed")
    public void testExecuteMove() {
        Piece whitePawn = new Pawn('w');
        whitePawn.setSquare(new int[]{1, 0});
        game.tryMove(whitePawn, new int[]{0, 1});
        assertEquals(new Queen('w').getName(), game.getBoard()[0][1].getName(), 
        "The white pawn should become a queen on row 0");
        assertEquals(true, Arrays.equals(new int[]{0, 1}, game.getLastMoveSquare()));
        assertEquals('b', game.getTurn());
        assertEquals('w', game.getNextTurn());
        assertEquals("rQbqkbhr0ppppppp00000000000000000000000000000000PPPPPPPPRHBQKBHR", game.getBoardString());
    }

    @Test
    @DisplayName("Test that the move is executed")
    public void testFilterOutCheckMoves() {
        game.setBoard("000000r00000000000000000000000000000000000000000000000000000qB0K");
        Piece whiteKing = new King('w');
        Piece whiteBishop = new Bishop('w');
        whiteKing.setSquare(new int[]{7, 7});
        whiteBishop.setSquare(new int[]{7, 5});
        whiteKing.setHasMoved(true);
        assertThrows(IllegalArgumentException.class, () -> {
            game.tryMove(whiteKing, new int[]{6, 6});
        }, "Cannot move into check from the black rook");
        assertThrows(IllegalArgumentException.class, () -> {
            game.tryMove(whiteBishop, new int[]{6, 6});
        }, "Moving the bishop will lead to check from the black queen");
        assertEquals(true, game.tryMove(whiteKing, new int[]{6, 7}), 
        "The only legal move that will not lead to check");
    }
 
    @Test
    @DisplayName("Test that board get updated correct when moving a piece")
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
        assertThrows(IllegalArgumentException.class, () -> {
            game.getKingPiece('s');
        }, "Cannot find a king with color s");
        assertEquals(game.getKingPiece('w').getName(), new King('w').getName());
        assertEquals(game.getKingPiece('b').getName(), new King('b').getName());
        game.setBoard("0000000000000000000000000000000000000000000000000000000000000000");
        assertEquals(game.getKingPiece('w'), null);
        assertEquals(game.getKingPiece('b'), null);
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

    
    @Test
    @DisplayName("Test that the move is executed")
    public void testCheckForIllegalCastling() {
        game.setBoard("000000000000000000000000000000000000000000000000000000000000K00R");
        Piece whiteKing = new King('w');
        whiteKing.setSquare(new int[]{7, 4});
        assertEquals(true, game.tryMove(whiteKing, new int[]{7, 6}));

        game.setBoard("0000q00000000000000000000000000000000000000000000000000R0000K00R");
        game.setTurn('w');
        assertThrows(IllegalArgumentException.class, () -> {
            game.tryMove(whiteKing, new int[]{7, 2});
        }, "Cannot castle because the king is in check by the black queen");

        game.setBoard("000r000000000000000000000000000000000000000000000000000R0000K00R");
        assertThrows(IllegalArgumentException.class, () -> {
            game.tryMove(whiteKing, new int[]{7, 2});
        }, "black rook is attacking the castling path");
    }

    @Test
    @DisplayName("Test that the move is executed")
    public void testUpdateRook() {
        game.setBoard("r000k00000000000000000000000000000000000000000000000000000000000");
        Piece blackKing = new King('b');
        blackKing.setSquare(new int[]{0, 4});
        game.setTurn('b');
        game.tryMove(blackKing, new int[]{0, 2});
        assertEquals("chess.Rook", game.getBoard()[0][3].getName());
        assertEquals(true, game.getMoveWasCastling());
        assertEquals(true, Arrays.equals(new int[]{0, 0}, game.getRookCastleSquares()[0]));
        assertEquals(true, Arrays.equals(new int[]{0, 3}, game.getRookCastleSquares()[1]));
    }
}
      
