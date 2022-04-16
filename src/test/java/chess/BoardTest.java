package chess;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BoardTest {
    private Board boardClass;

    @BeforeEach
	public void setUp() {
        boardClass = new Board();
    }

    @Test
	public void testConstructor() {
		assertEquals(boardClass.getBoard().length, 8);
		for (Piece[] row : boardClass.getBoard()) {
            assertEquals(row.length, 8);
        }
	}

    @Test
    public void testStringBoard() {
        String startBoard = "rhbqkbhrpppppppp00000000000000000000000000000000PPPPPPPPRHBQKBHR";
        assertEquals(boardClass.boardString(), startBoard, "This is a valid board string");

        String randomBoard = "r0bqr0k0pppp0ppp00000h00000000B000B0P00000Q00000PPP00PPPR00000K0";
        boardClass.setBoard(randomBoard);
        assertEquals(boardClass.boardString(), randomBoard, "This is a valid board string");

        assertThrows(IllegalArgumentException.class, () -> {
            boardClass.setBoard("0bqr0k0pppp0ppp00000h0");
        }, "The string is to short, should be 64 characters");

        assertThrows(IllegalArgumentException.class, () -> {
            boardClass.setBoard("rhbqkbhrppppppps00000000000000000000000000000000PPlPPPPPRHBQKBHR");
        }, "The string contains illegal characters such as l ans s"); 
    }

    @Test
    public void testCreatePiece() {
        String randomBoard = "r0bqr0k0pppp0ppp00000h00000000B000B0P00000Q00000PPP00PPPR00000K0";
        boardClass.setBoard(randomBoard);

        Piece whiteKing = new King('w');
        Piece expectedKing = boardClass.getBoard()[7][6];
        assertEquals(expectedKing.getName(), whiteKing.getName());

        Piece blackRook = new Rook('b');
        Piece expectedRook = boardClass.getBoard()[0][0];
        assertEquals(expectedRook.getName(), blackRook.getName());

        Piece expectedNull = boardClass.getBoard()[3][2];
        assertEquals(expectedNull, null); 
    }
}
