package chess;

public class Board {
    private Piece[][] board = new Piece[8][8];

    public Board() {
        this.board = createStartBoard(this.board);
    }

    public Piece[][] getBoard() {
        return board;
    }

    public void setBoard(String stringBoard) {
        validateBoardString(stringBoard);
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                this.board[i][j] = createPiece(stringBoard.charAt(i*8+j));
            }
        }
    }

    private void validateBoardString(String str) {
        if (!str.matches("[RBHQKPrbhqkp0]{64}")) {
            throw new IllegalArgumentException("Not a valid board string");
        }
    }

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
        
    // Creating the startboard
    private Piece[][] createStartBoard(Piece[][] board) {
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

    // Prints the board to the console
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
