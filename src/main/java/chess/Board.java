package chess;


public class Board {
    private Piece[][] board = new Piece[8][8];


    public Board() {
        this.board = createEmptyBoard(board);
    }

    private Piece[][] createEmptyBoard(Piece[][] board) {
        String blackFirstRow = "rhbqkbhr";
        String blackSecondRow = "pppppppp";
        String whiteFirstRow = "RHBQKBHR";
        String whiteSecondRow = "PPPPPPPP";

        for (int i = 0; i < 8; i++) {
            if (i == 0) {
                Piece[] row1 = new Piece[8];
                for (int j = 0; j < 8; j++) {
                    row1[j] = new Piece(blackFirstRow.charAt(j));
                }
                board[i] = row1;
            } else if (i == 1) {
                Piece[] row2 = new Piece[8];
                for (int j = 0; j < 8; j++) {
                    row2[j] = new Piece(blackSecondRow.charAt(j));
                }
                board[i] = row2;
            } else if (i == 6) {
                Piece[] row7 = new Piece[8];
                for (int j = 0; j < 8; j++) {
                    row7[j] = new Piece(whiteSecondRow.charAt(j));
                }
                board[i] = row7;
            } else if (i == 7) {
                Piece[] row8 = new Piece[8];
                for (int j = 0; j < 8; j++) {
                    row8[j] = new Piece(whiteFirstRow.charAt(j));
                }
                board[i] = row8;
            } else {
                Piece[] emptyRow = new Piece[8];
                for (int j = 0; j < 8; j++) {
                    emptyRow[j] = new Piece('0');
                }
                board[i] = emptyRow;
            }
        }
        return board;
    }

    public Piece[][] getBoard() {
        return board;
    }

    public void printBoard() {
        for (Piece[] pieces : board) {
            for (Piece piece : pieces) {
                System.out.print(piece.getPiece() + " ");
            }
            System.out.println();
        }
    }
    
    public static void main(String[] args) {
        Board board1 = new Board();
        board1.printBoard();
    }
}
