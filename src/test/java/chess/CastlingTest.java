package chess;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CastlingTest {
    private ChessGame game;
    private int[] whiteKingSquare, whiteCastleRight, whiteCastleLeft;
    private int[] blackKingSquare, blackCastleRight, blackCastleLeft;
    private Piece whiteKing, blackKing;
  
    @BeforeEach
	public void setUp() {
        game = new ChessGame();
        whiteKing = new King('w');
        blackKing = new King('b');

        whiteKingSquare = new int[]{7,4};
        whiteCastleRight = new int[]{7,6};
        whiteCastleLeft = new int[]{7,2};
        blackKingSquare = new int[]{0,4};
        blackCastleRight = new int[]{0,6};
        blackCastleLeft = new int[]{0,2};
    }
    
    @Test
    @DisplayName("Sjekker lovlig castling til høyre")
    public void testLegalCastlingRight() {
        char[][] boardChar = new char[][] {{'r','0','b','q','k','0','0','r'}, {'p','p','p','0','b','p','p','p'}, 
        {'0','0','h','p','0','h','0','0'}, {'0','B','0','0','p','0','0','0'}, {'0','0','0','P','P','0','0','0'}, 
        {'0','0','H','0','0','H','0','0'}, {'P','P','P','0','0','P','P','P'}, {'R','0','B','Q','K','0','0','R'}};
        game.setBoard(boardChar);

        assertEquals(true, game.movePiece(whiteKingSquare, whiteKing, whiteCastleRight), "Trekket burde være lovlig");
        assertEquals(false, game.movePiece(whiteKingSquare, whiteKing, whiteCastleLeft), "Kan ikke castle med brikker i veien");
        
        assertEquals(true, game.movePiece(blackKingSquare, blackKing, blackCastleRight), "Det er lovlig rokade");
        assertEquals(false, game.movePiece(blackKingSquare, blackKing, blackCastleLeft), "Kan ikke castle med brikker i veien");
    } 

    @Test
    @DisplayName("Sjekker ulovlig castling pga feltene kongen passerer er i sjakk")
    public void testPieceAttackingPath() {
        char[][] boardChar = new char[][] {{'r','0','0','0','k','b','h','r'}, {'p','p','p','q','0','p','p','p'},
        {'0','0','h','0','0','0','0','0'}, {'0','0','0','p','p','0','B','0'}, {'0','0','0','P','P','0','b','H'},
        {'0','0','H','0','0','0','0','0'}, {'P','P','P','Q','0','P','P','P'}, {'R','0','0','0','K','B','0','R'}};
        game.setBoard(boardChar);

        assertEquals(false, game.movePiece(blackKingSquare, blackKing, blackCastleLeft), "Kan ikke castle siden hvit løper angriper feltet kongen passerer");

        Piece whiteHorse = new Horse('w');
        int[] square = new int[]{5,2};
        int[] moveTo = new int[]{3,1};
        game.movePiece(square, whiteHorse, moveTo);

        assertEquals(false, game.movePiece(whiteKingSquare, whiteKing, whiteCastleLeft), "Kan ikke castle siden svart løper angriper feltet kongen passerer");
    }

    @Test
    @DisplayName("Sjekker ulovlig castling pga kongen er i sjakk")
    public void testKingInChess() {
        char[][] boardChar = new char[][] {{'r','0','b','q','k','0','0','r'}, {'p','p','p','p','0','p','p','p'}, 
        {'0','0','h','0','0','h','0','0'}, {'0','0','0','0','p','0','0','0'}, {'0','b','0','P','P','0','0','0'}, 
        {'0','0','0','B','0','H','0','0'}, {'P','P','P','0','0','P','P','P'}, {'R','H','B','Q','K','0','0','R'}};
        game.setBoard(boardChar);

        assertEquals(false, game.movePiece(whiteKingSquare, whiteKing, whiteCastleRight), "Kan ikke castle siden kongen er i sjakk");
    }
}
