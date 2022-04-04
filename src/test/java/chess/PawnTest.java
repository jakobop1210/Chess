package chess;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PawnTest {
    private ChessGame game;
    private Piece whitePawn, blackPawn;
    private int[] whitePawnSquare, blackPawnSquare;

    @BeforeEach
	public void setUp() {
        game = new ChessGame();
        whitePawn = new Pawn('w');
        blackPawn = new Pawn('b');
        whitePawnSquare = new int[]{6,0};
        blackPawnSquare = new int[]{1,0};
    }
    
    @Test
    @DisplayName("Tester lovlige trekk for bonde en fram og ulovlige trekk to fram")
    public void testMovesForward() {
        int[] whitePawnMove = new int[]{5,0};
        assertEquals(true, game.movePiece(whitePawnSquare, whitePawn, whitePawnMove), "Trekket burde være lovlig");

        int[] blackPawnMove = new int[]{2,0};
        assertEquals(true, game.movePiece(blackPawnSquare, blackPawn, blackPawnMove), "Trekket burde være lovlig");

        whitePawnSquare = whitePawnMove;
        whitePawnMove = new int[]{3,0};
        assertEquals(false, game.movePiece(whitePawnSquare, whitePawn, whitePawnMove), "Kan bare gå to fram fra start posisjon");
        whitePawnMove = new int[]{4,0};
        assertEquals(true, game.movePiece(whitePawnSquare, whitePawn, whitePawnMove), "Trekket burde være lovlig");

        blackPawnSquare = blackPawnMove;
        blackPawnMove = new int[]{4,0};
        assertEquals(false, game.movePiece(blackPawnSquare, blackPawn, blackPawnMove), "Kan bare gå to fram fra start posisjon");
        blackPawnMove = new int[]{3,0};
        assertEquals(true, game.movePiece(blackPawnSquare, blackPawn, blackPawnMove), "Trekket burde være lovlig");
    }

    @Test
    @DisplayName("Tester lovlige trekk for bonde to fram og brikke som sperrer")
    public void testMovesForward2() {
        int[] whitePawnMove = new int[]{4,0};
        assertEquals(true, game.movePiece(whitePawnSquare, whitePawn, whitePawnMove), "Trekket burde være lovlig");

        int[] blackPawnMove = new int[]{3,0};
        assertEquals(true, game.movePiece(blackPawnSquare, blackPawn, blackPawnMove), "Trekket burde være lovlig");

        whitePawnSquare = whitePawnMove;
        whitePawnMove = new int[]{3,0};
        assertEquals(false, game.movePiece(whitePawnSquare, whitePawn, whitePawnMove), "Det står en brikke foran");

        blackPawnSquare = blackPawnMove;
        blackPawnMove = new int[]{4,0};
        assertEquals(false, game.movePiece(blackPawnSquare, blackPawn, blackPawnMove), "Det står en brikke foran");
    }

    @Test
    @DisplayName("Tester lovlige og ulovlige diagonale trekk")
    public void testDiagonalMoves() {
        int[] whitePawnMove = new int[]{-1,1};
        assertEquals(false, game.movePiece(whitePawnSquare, whitePawn, whitePawnMove), "Kan ikke gå ut av brettet");

        int[] blackPawnMove = new int[]{2,1};
        assertEquals(false, game.movePiece(blackPawnSquare, blackPawn, blackPawnMove), "Kan bare gå på skrå hvis det er en mostanderbrikke der");

        whitePawnSquare = new int[]{6,1};
        whitePawnMove = new int[]{4,1};
        game.movePiece(whitePawnSquare, whitePawn, whitePawnMove);
        blackPawnMove = new int[]{3,0};
        game.movePiece(blackPawnSquare, blackPawn, blackPawnMove);

        assertEquals(true, game.movePiece(whitePawnMove, whitePawn, blackPawnMove), "Kan gå på skrå hvis det er en mostanderbrikke der");
    }

    @Test
    @DisplayName("Tester når bonden går til enden av brettet")
    public void testPawnBecomeQueen() {
        char[][] boardChar = new char[][] {{'0','0','0','0','r','0','0','0'}, {'p','0','p','0','0','P','k','p'},
        {'0','0','p','0','0','0','0','0'}, {'0','0','0','0','H','0','0','0'}, {'0','0','P','0','0','0','0','q'}, 
        {'0','0','0','0','0','0','0','0'}, {'P','0','P','Q','p','0','P','P'}, {'R','0','0','0','0','0','K','0'}};
        game.setBoard(boardChar);
        game.getBoard()[1][6].setHasMoved(true);
        game.getBoard()[7][6].setHasMoved(true);
        
        whitePawnSquare = new int[]{1,5};
        int[] whitePawnMove = new int[]{0,4};
        game.movePiece(whitePawnSquare, whitePawn, whitePawnMove);
        assertEquals("chess.Queen", game.getBoard()[0][4].getName(), "Blir automatisk ny dronning på andre siden av brettet");

        blackPawnSquare = new int[]{6,4};
        int[] blackPawnMove = new int[]{7,4};
        game.movePiece(blackPawnSquare, blackPawn, blackPawnMove);
        assertEquals("chess.Queen", game.getBoard()[7][4].getName(), "Blir automatisk ny dronning på andre siden av brettet");

    }
}
