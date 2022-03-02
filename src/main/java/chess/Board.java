package chess;


public class Board {
    private Piece[][] board = new Piece[8][8];

    public Board() {
        this.board = createEmptyBoard(this.board);
    }

    public Board(Piece[][] board) {
        this.board = board;
    }

    // Lager et startbrett
    private Piece[][] createEmptyBoard(Piece[][] board) {
        String firstRow = "rhbqkbhr";
        String secondRow = "pppppppp";
    
        for (int i = 0; i < 8; i++) {
            if (i == 0) {
                Piece[] row1 = new Piece[8];
                for (int j = 0; j < 8; j++) {
                    row1[j] = new Piece(firstRow.charAt(j), 'b');
                }
                board[i] = row1;
            } else if (i == 1) {
                Piece[] row2 = new Piece[8];
                for (int j = 0; j < 8; j++) {
                    row2[j] = new Piece(secondRow.charAt(j), 'b');
                }
                board[i] = row2;
            } else if (i == 6) {
                Piece[] row7 = new Piece[8];
                for (int j = 0; j < 8; j++) {
                    row7[j] = new Piece(secondRow.charAt(j), 'w');
                }
                board[i] = row7;
            } else if (i == 7) {
                Piece[] row8 = new Piece[8];
                for (int j = 0; j < 8; j++) {
                    row8[j] = new Piece(firstRow.charAt(j), 'w');
                }
                board[i] = row8;
            } else {
                Piece[] emptyRow = new Piece[8];
                for (int j = 0; j < 8; j++) {
                    emptyRow[j] = new Piece('0', '0');
                }
                board[i] = emptyRow;
            }
        }
        return board;
    }

 // public Piece[][] copyBoard(Piece[][] board) {
 //     Piece[][] copyBoard = new Piece[8][8];

 //     for (int i = 0; i < board.length; i++) {
 //         for (int j = 0; j < board[i].length; j++) {
 //             Piece row = new Piece(board[i][j].getPiece(), board[i][j].getColor());
 //             copyBoard[i][j] = row;
 //         }
 //     }
 //     return copyBoard;
 // }

    public Piece[][] getBoard() {
        return board;
    }

    public void printBoard() {
        for (Piece[] pieces : board) {
            for (Piece piece : pieces) {
                System.out.print(piece.getPiece() + " ");
            }
            System.out.printf("%n");
        }
    }
    public static void main(String[] args) {
        Board board1 = new Board();
        board1.printBoard();
    }
}
