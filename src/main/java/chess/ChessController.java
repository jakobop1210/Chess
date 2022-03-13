package chess;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class ChessController {
    private ChessGame game = new ChessGame();
    private Piece[][] board = game.getBoard(); 
    private ImageView[][] imageViewArr = new ImageView[8][8];
    private Button lastButtonClicked;
    private int[] lastButtonClickedSquare;


    @FXML
    GridPane squareGrid;
    
    @FXML
    public void initialize() {
        createButtons();
    }

    // Legger til knapper til brettet
    private void createButtons() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                ImageView image = createImageView(i, j);
                imageViewArr[i][j] = image;
                Button button = createSquareButton(i, j);
                squareGrid.add(image, j, i);
                squareGrid.add(button, j, i);
            }
        }
    }

    // Lager ImageView med riktig bilde til brikkenes plassering
    private ImageView createImageView(int i, int j) {
        String pieceUrl;
        ImageView image = new ImageView();

        if (board[i][j].getPiece() != '0') {
            if (board[i][j].getColor() == 'w') {
                pieceUrl = "Pieces/" + Character.toString(board[i][j].getPiece()) + ".png";
            } else {
                pieceUrl = "Pieces/" + Character.toString(board[i][j].getPiece()) + Character.toString(board[i][j].getPiece()) + ".png";
            }
            image.setImage(new Image(this.getClass().getResource(pieceUrl).toString()));  
        } 
        image.setStyle("-fx-text-alignment: center;");
        return image;
    }

    // Lager rutene som er en knapp med riktig farge og brikke
    private Button createSquareButton(int i, int j) {
        Button button = new Button();
        button.setOpacity(0);
        button.setMaxHeight(Double.MAX_VALUE);
        button.setMaxWidth(Double.MAX_VALUE);
        button.setCursor(Cursor.HAND);
        button.setOnAction((event) -> { clickedButton(button, i, j);});
        return button;
    }

    // Oppdaterer hvilken rute som er trykket på og gjennomfører evt trekk
    public void clickedButton(Button button, int i, int j) {
        button.setOpacity(0.5);
        if (lastButtonClicked != null) {
            lastButtonClicked.setOpacity(0);
            doMove(lastButtonClickedSquare, i, j);
        } 
        lastButtonClicked = button;
        lastButtonClickedSquare = new int[]{i,j};
    }

    // Gjennomfører trekket hvis det er lovlig
    public void doMove(int[] lastButtonClickedSquare, int i, int j) {
        int[] moveTo = new int[]{i,j};
        Piece pieceClickedOn = new Piece(board[lastButtonClickedSquare[0]][lastButtonClickedSquare[1]].getPiece(), board[lastButtonClickedSquare[0]][lastButtonClickedSquare[1]].getColor());

        if (game.movePiece(lastButtonClickedSquare, pieceClickedOn, moveTo)) {
            game.printboard();
            updateImage(pieceClickedOn, lastButtonClickedSquare, moveTo);
            lastButtonClicked = null;
            checkResult();

            if (pieceClickedOn.getPiece() == '0') {
                System.out.println("Du må velge en brikke!");
            } else {
                System.out.println("Kan ikke flytte dit!");
            }
        }
    }

    // Oppdaterer bildet av brikkene etter et trekk
    private void updateImage(Piece piece, int[] currentSquare, int[] moveTo) {
        if (piece.getColor() == 'w') {
            String pieceUrl = "Pieces/" + Character.toString(piece.getPiece()) + ".png";
            imageViewArr[moveTo[0]][moveTo[1]].setImage(new Image(this.getClass().getResource(pieceUrl).toString()));
        } else {
            String pieceUrl = "Pieces/" + Character.toString(piece.getPiece()) + Character.toString(piece.getPiece()) + ".png";
            imageViewArr[moveTo[0]][moveTo[1]].setImage(new Image(this.getClass().getResource(pieceUrl).toString()));
        }
        imageViewArr[currentSquare[0]][currentSquare[1]].setImage(null);
    }

    // Sjekker resultatene etter et trekk
    private void checkResult() {
        if (game.isGameOver()) {
            if (game.getWinner() == 'w') {
                 System.out.println("Vinneren er hvit!");
            } else {
                System.out.println("Vinnereren er svart!");
            }
        } else if (game.isCheck()) {
            if (game.getTurn() == 'w') {
                System.out.println("Hvit står i sjakk!");
            } else {
                System.out.println("Svart står i sjakk!");
            }
        }
    }  
}
