package chess;

public class Piece {
    private char piece;

    public Piece(char piece) {
        String validPieces = "rhbqkp0RHBQKP";
        if (!validPieces.contains(Character.toString(piece))) {
            throw new IllegalArgumentException("Ikke gydlig brikke!");
        }
        this.piece = piece;
    }

    public char getPiece() {
        return piece;
    }

    public 
    public static void main(String[] args) {
        Piece whitePawn = new Piece('P');
        System.out.println(whitePawn.getPiece());
    }
}
