package chess;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CastlingTest {
   
    
    @Test
    void testLegalCastling() {
        ChessGame legalCastleGame =  new ChessGame();
        char[][] boardChar = new char[][] {{'r','0','b','q','k','0','0','r'}, {'p','p','p','0','b','p','p','p'}, 
        {'0','0','h','p','0','h','0','0'}, {'0','B','0','0','p','0','0','0'}, {'0','0','0','P','P','0','0','0'}, 
        {'0','0','H','0','0','H','0','0'}, {'P','P','P','0','0','P','P','P'}, {'R','0','B','Q','K','0','0','R'}};
        legalCastleGame.setBoard(boardChar);

        int[] square = {7,4};
        int[] move = {7,6};
        Piece whiteK = new King('w');
        assertEquals(true, legalCastleGame.movePiece(square, whiteK, move));

        int[] square2 = {0,4};
        int[] move2 = {0,6};
        Piece blackK = new King('b');
        assertEquals(true, legalCastleGame.movePiece(square2, blackK, move2));
    } 

    @Test
    void testIllegalCastlingBecauseOfChess() {
        ChessGame legalCastleGame =  new ChessGame();
        char[][] boardChar = new char[][] {{'r','0','0','0','k','b','h','r'}, {'p','p','p','0','0','p','p','p'}, 
        {'0','0','h','p','b','0','0','0'}, {'0','0','0','0','p','0','B','0'}, {'0','0','B','0','P','0','0','0'}, 
        {'0','0','0','P','0','0','P','q'}, {'P','P','P','0','H','P','0','P'}, {'R','H','0','Q','K','0','0','R'}};
        legalCastleGame.setBoard(boardChar);

        int[] square = {7,4};
        int[] castleRight = {7,6};
        int[] castleLeft = {7,2};
        Piece whiteK = new King('w');
        assertEquals(false, legalCastleGame.movePiece(square, whiteK, castleRight));
        assertEquals(false, legalCastleGame.movePiece(square, whiteK, castleLeft));

        int[] square2 = {0,4};
        int[] move2 = {0,6};
        Piece blackK = new King('b');
        assertEquals(false, legalCastleGame.movePiece(square2, blackK, move2));
    }

    @Test
    void testIllegalCastlingBecauseOfMovedPiece() {
        ChessGame legalCastleGame =  new ChessGame();
        char[][] boardChar = new char[][] {{'0','h','b','0','k','0','0','0'}, {'0','p','p','p','0','p','p','r'}, 
        {'r','0','0','0','0','h','0','p'}, {'p','0','b','0','0','0','0','0'}, {'0','0','0','p','0','B','0','0'}, 
        {'0','0','0','B','0','H','0','0'}, {'P','P','P','H','0','P','P','P'}, {'R','0','0','0','K','0','0','R'}};
        legalCastleGame.setBoard(boardChar);

        int[] square = {7,4};
        int[] castleRight = {7,6};
        int[] castleLeft = {7,2};
        Piece whiteK = new King('w');
        assertEquals(false, legalCastleGame.movePiece(square, whiteK, castleRight));
        assertEquals(false, legalCastleGame.movePiece(square, whiteK, castleLeft));

        int[] squareB = {0,4};
        int[] castleRightB = {0,6};
        int[] castleLeftB = {0,2};
        Piece blackK = new King('b');
        assertEquals(false, legalCastleGame.movePiece(squareB, blackK, castleLeftB));
        assertEquals(false, legalCastleGame.movePiece(squareB, blackK, castleRightB));
    }
}
