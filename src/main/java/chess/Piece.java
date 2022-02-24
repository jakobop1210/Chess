package chess;

public class Piece {
    private char piece;
    private char color;

    public Piece(char piece, char color) {
        String validPieces = "rhbqkp0";
        if (!validPieces.contains(Character.toString(piece))) {
            throw new IllegalArgumentException("Ikke gydlig brikke!");
        }
        this.piece = piece;
        if (color != 'w' && color != 'b' && color != '0') {
            throw new IllegalArgumentException("Ikke gyldig farge!");
        }
        this.color = color;
    }

    public char getPiece() {
        return piece;
    }

    public char getColor() {
        return color;
    }

    public void setPiece(char piece) {
        this.piece = piece;
    }

    public void setColor(char color) {
        this.color = color;
    }

    public static void main(String[] args) {
        Piece whitePawn = new Piece('p', 'w');
        whitePawn.setPiece('r');
        System.out.println(whitePawn.getPiece());
    }
}
