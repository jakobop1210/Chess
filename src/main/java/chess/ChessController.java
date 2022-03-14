package chess;

import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;


public class ChessController {
    private ChessGame game;
    private boolean gameOver;
    private Piece[][] board; 
    private ImageView[][] imageViewArr = new ImageView[8][8];
    private Button lastButtonClicked;
    private int[] lastButtonClickedSquare;
    private int gameCount;


    @FXML
    GridPane squareGrid;

    @FXML
    TextField winnerTextField, winningMethod;

    @FXML
    Pane resultPane;
    
    @FXML
    public void initialize() {
        game = new ChessGame();
        board = game.getBoard();
        gameOver = false;
        resultPane.visibleProperty().set(false);
        // Setter alle brikkene til start posisjon
        if (gameCount > 0) {
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[i].length; j++) {
                    setImageUrl(board[i][j], i, j);
                }
            }
        // Hvis det er første runde så må knapper og bilder lages
        } else {
            createImageViewsAndButtons();
        }
        gameCount++;
    }

    // Legger til bildene av brikkene og usynlige knapper til brettet
    private void createImageViewsAndButtons() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                ImageView image = new ImageView();
                imageViewArr[i][j] = image;
                setImageUrl(board[i][j], i, j);
                Button button = createSquareButton(i, j);
                squareGrid.add(image, j, i);
                squareGrid.add(button, j, i);
            }
        }
    }

    // Lager usynlinge knapper som utfører clickedButton når de tyrkkes på
    private Button createSquareButton(int i, int j) {
        Button button = new Button();
        button.setOpacity(0);
        button.setMaxHeight(Double.MAX_VALUE);
        button.setMaxWidth(Double.MAX_VALUE);
        button.setCursor(Cursor.HAND);
        button.setOnAction((event) -> { clickedButton(button, i, j);});
        return button;
    }

    // Oppdaterer hvilken rute som er trykket på og kaller doMove()
    public void clickedButton(Button button, int i, int j) {
        if (gameOver == false) {
            button.setOpacity(0.5);
            if (lastButtonClicked != null) {
                lastButtonClicked.setOpacity(0);
                doMove(lastButtonClickedSquare, i, j);
            } 
            lastButtonClicked = button;
            lastButtonClickedSquare = new int[]{i,j};
        }
    }

    // Gjennomfører trekket hvis det er lovlig og kaller updateImage() og checkResult()
    public void doMove(int[] lastButtonClickedSquare, int i, int j) {
        int[] moveTo = new int[]{i,j};
        Piece lastPieceClickedOn = new Piece(board[lastButtonClickedSquare[0]][lastButtonClickedSquare[1]].getPiece(), board[lastButtonClickedSquare[0]][lastButtonClickedSquare[1]].getColor());

        if (game.movePiece(lastButtonClickedSquare, lastPieceClickedOn, moveTo)) {
            game.printboard();
            setImageUrl(lastPieceClickedOn, i, j);
            setImageUrl(new Piece('0', '0'), lastButtonClickedSquare[0], lastButtonClickedSquare[1]);
            checkResult();
        } 
    }

    private void setImageUrl(Piece piece, int i, int j) {
        if (piece.getColor() == 'w') {
            String pieceUrl = "Pieces/" + Character.toString(piece.getPiece()) + ".png";
            imageViewArr[i][j].setImage(new Image(this.getClass().getResource(pieceUrl).toString()));
        } else if (piece.getColor() == 'b') {
            String pieceUrl = "Pieces/" + Character.toString(piece.getPiece()) + Character.toString(piece.getPiece()) + ".png";
            imageViewArr[i][j].setImage(new Image(this.getClass().getResource(pieceUrl).toString()));
        } else {
            imageViewArr[i][j].setImage(null);
        }
    }

    // Sjekker resultatene etter et trekk
    private void checkResult() {
        if (game.isGameOver()) {
            gameOver = true;
            resultPane.visibleProperty().set(true);
            winningMethod.setText("by checkmate");
            if (game.getWinner() == 'w') {
                winnerTextField.setText("White won!");
                System.out.println("Vinneren er hvit!");
            }   else {
                winnerTextField.setText("Black won!");
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

    public void whiteResign() {
        gameOver = true;
        resultPane.visibleProperty().set(true);
        winningMethod.setText("by resignation");
        winnerTextField.setText("Black won!");
        System.out.println("Vinnereren er svart!");
    }

    public void blackResign() {
        gameOver = true;
        resultPane.visibleProperty().set(true);
        winningMethod.setText("by resignation");
        winnerTextField.setText("White won!");
        System.out.println("Vinnereren er hvit!");
    }
}
