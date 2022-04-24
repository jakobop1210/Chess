package chess;

public class Board {
    private Piece[][] board = new Piece[8][8];

    public Board() {
        this.board = createStartBoard(this.board);
    }

    public Piece[][] getBoard() {
        return board;
    }

    // Creating the startboard
    private Piece[][] createStartBoard(Piece[][] board) {
        setBoard("rhbqkbhrpppppppp00000000000000000000000000000000PPPPPPPPRHBQKBHR");
        return board;
    }

    // Creating a nested array board that matches the stringBoard input
    public void setBoard(String stringBoard) {
        if (!legalBoardString(stringBoard)) {
            throw new IllegalArgumentException("Not a valid board string");
        }
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                this.board[i][j] = createPiece(stringBoard.charAt(i*8+j));
                if (board[i][j] != null) this.board[i][j].setSquare(new int[]{i, j});
            }
        }
    }

    // Checks if the string input is a legal board
    private boolean legalBoardString(String str) {
        if (!str.matches("[RBHQKPrbhqkp0]{64}")) {
            return false;
        }
        return true;
    }

    // Returns the right piece connected to the char input
    private Piece createPiece(char piece) {
        if (piece == 'P') return new Pawn('w');
        if (piece == 'R') return new Rook('w');
        if (piece == 'H') return new Horse('w');
        if (piece == 'B') return new Bishop('w');
        if (piece == 'Q') return new Queen('w');
        if (piece == 'K') return new King('w');
        if (piece == 'p') return new Pawn('b');
        if (piece == 'r') return new Rook('b');
        if (piece == 'h') return new Horse('b');
        if (piece == 'b') return new Bishop('b');
        if (piece == 'q') return new Queen('b');
        if (piece == 'k') return new King('b');
        return null;
    }

    // Returns the board in a string version
    public String boardString() {
        String boardString = "";
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == null) {
                    boardString += "0";
                } else if (board[i][j].getColor() == 'w') {
                        boardString += Character.toString(board[i][j].getName().charAt(6));
                } else {
                        boardString += Character.toString(board[i][j].getName().charAt(6)).toLowerCase();
                }
            }
        }
        return boardString;
    }
}
