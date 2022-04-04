package chess;

import java.util.Arrays;

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
    private Pane[][] paneArr = new Pane[8][8];
    private int[] lastClickedSquare;
    private int[] lastMoveSquare;
    private int gameCount;


    @FXML
    GridPane squareGrid;

    @FXML
    TextField winnerTextField, winningMethod;

    @FXML
    Pane resultPane;

    @FXML
    Button extraStartButton;
    
    @FXML
    public void initialize() {
        game = new ChessGame();
        board = game.getBoard();
        gameOver = false;
        resultPane.visibleProperty().set(false);
        extraStartButton.visibleProperty().set(false);

        if (gameCount > 0) {
            setPaneRightColor(lastClickedSquare);
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[i].length; j++) {
                    setImageUrl(board[i][j], i, j);
                }
            }
        } else {
            createImageViewsAndButtons();
        }
        gameCount++;
    }

    // Legger til bildene av brikkene og usynlige knapper til brettet
    private void createImageViewsAndButtons() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                Pane pane = new Pane();
                paneArr[i][j] = pane;
                if (i%2 == 0 && j%2 == 1 || i%2 == 1 && j%2 == 0) {
                    pane.setStyle("-fx-background-color:green");
                } else {
                    pane.setStyle("-fx-background-color:#FFFDE7");
                }
                squareGrid.add(pane, j, i);

                ImageView image = new ImageView();
                imageViewArr[i][j] = image;
                setImageUrl(board[i][j], i, j);
                squareGrid.add(image, j, i);

                Button button = createSquareButton(i, j);
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
    private void clickedButton(Button button, int i, int j) {
        if (!gameOver) {
            paneArr[i][j].setStyle("-fx-background-color:#FDFD66");
            if (lastClickedSquare != null) {
                if (!Arrays.equals(lastClickedSquare, lastMoveSquare)) setPaneRightColor(lastClickedSquare); 
                doMove(lastClickedSquare, i, j);
            } 
            lastClickedSquare = new int[]{i,j};
        }
    }

    // Setter Pane ruten til original farge
    private void setPaneRightColor(int[] square) {
        if (square[0]%2 == 0 && square[1]%2 == 1 || square[0]%2 == 1 && square[1]%2 == 0) {
            paneArr[square[0]][square[1]].setStyle("-fx-background-color:green");
        } else {
            paneArr[square[0]][square[1]].setStyle("-fx-background-color:#FFFDE7");
        }
    }

    // Gjennomfører trekket hvis det er lovlig, oppdaterer bildene med setImageUrl og kaller checkResult()
    private void doMove(int[] lastButtonClickedSquare, int i, int j) {
        Piece lastPieceClickedOn = board[lastButtonClickedSquare[0]][lastButtonClickedSquare[1]];
        int[] moveTo = new int[] {i, j};

        if (lastPieceClickedOn != null) {
            if (game.movePiece(lastButtonClickedSquare, lastPieceClickedOn, moveTo)) {
                if (lastMoveSquare != null && !Arrays.equals(lastMoveSquare, moveTo)) setPaneRightColor(lastMoveSquare);
                lastMoveSquare = moveTo;
                setImageUrl(board[i][j], i, j);
                setImageUrl(null, lastButtonClickedSquare[0], lastButtonClickedSquare[1]);

                if (game.getMoveWasCastling()) {
                    setImageUrl(new Rook(game.getNextTurn()), game.getRookCastlePos()[1][0], game.getRookCastlePos()[1][1]);
                    setImageUrl(null, game.getRookCastlePos()[0][0], game.getRookCastlePos()[0][1]);
                }
                checkResult();
            } 
        }
    }

    private void setImageUrl(Piece piece, int i, int j) {
        if (piece == null) {
            imageViewArr[i][j].setImage(null);
        } else if (piece.getColor() == 'w') {
            String pieceUrl = "Pieces/" + Character.toString(piece.getName().charAt(6)) + ".png";
            imageViewArr[i][j].setImage(new Image(this.getClass().getResource(pieceUrl).toString()));
        } else {
            String pieceUrl = "Pieces/" + Character.toString(piece.getName().charAt(6)) + Character.toString(piece.getName().charAt(6)) + ".png";
            imageViewArr[i][j].setImage(new Image(this.getClass().getResource(pieceUrl).toString()));
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
            } else if (game.getWinner() == 'b') {
                winnerTextField.setText("Black won!");
                System.out.println("Vinnereren er svart!");
            } else {
                winnerTextField.setText("Draw!");
                winningMethod.setText("by stalemate");
                System.out.println("Det ble uavgjort");
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

    public void exitResult() {
        resultPane.visibleProperty().set(false);
        extraStartButton.visibleProperty().set(true);
    }
}
