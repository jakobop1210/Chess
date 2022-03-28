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

        for (int i = 0; i < 8; i++) {
            if (i == 0) {
                board[i][0] = new Rook('b');
                board[i][1]= new Horse('b');
                board[i][2] = new Bishop('b');
                board[i][3] = new Queen('b');
                board[i][4]= new King('b');
                board[i][5] = new Bishop('b');
                board[i][6] = new Horse('b');
                board[i][7] = new Rook('b');
            } else if (i == 1) {
                for (int j = 0; j < 8; j++) {
                    board[i][j] = new Pawn('b');
                }    
            } else if (i == 6) {
                for (int j = 0; j < 8; j++) {
                    board[i][j] = new Pawn('w');
                }    
            } else if (i == 7) {
                board[i][0] = new Rook('w');
                board[i][1]= new Horse('w');
                board[i][2] = new Bishop('w');
                board[i][3] = new Queen('w');
                board[i][4]= new King('w');
                board[i][5] = new Bishop('w');
                board[i][6] = new Horse('w');
                board[i][7] = new Rook('w');
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
                if (piece == null) {
                    System.out.print('0');
                } else {
                    System.out.print(piece.getName().charAt(6));
                }
            }
            System.out.printf("%n");
        }
    }
    public static void main(String[] args) {
        Board board1 = new Board();
        board1.printBoard();
    }
}
