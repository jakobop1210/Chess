package chess;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;


public class ChessController {
    private ChessGame game;
    private IGameSaver gameSaver = new GameSaver();
    private ImageView[][] imageViewArr = new ImageView[8][8];
    private Pane[][] paneArr = new Pane[8][8];
    private int[] lastClickedSquare;
    private int gameCount;

    @FXML
    private GridPane squareGrid;

    @FXML
    private TextField winnerTextField, winningMethod, gameNameInput;

    @FXML
    private Pane resultPane, savedGamesPane;

    @FXML
    private Button extraStartButton;

    @FXML
    private ChoiceBox<String> savedGamesCBox;
    
    @FXML
    private void initialize() {
        game = new ChessGame();
        resultPane.visibleProperty().set(false);
        extraStartButton.visibleProperty().set(false);
        
        if (gameCount > 0) {
            setPiecesToMatchBoard();
        } else {
            createImageViewsAndButtons();
            addSavedFilesToChoiceBox();
        }
        gameCount++;
    }

    // Setting all the images to match the board
    private void setPiecesToMatchBoard() {
        if (lastClickedSquare != null) setPaneRightColor(lastClickedSquare);
        for (int i = 0; i < game.getBoard().length; i++) {
            for (int j = 0; j < game.getBoard()[i].length; j++) {
                setImageUrl(game.getBoard()[i][j], i, j);
            }
        }
    }

    // Adding pictures of the pieces and invisible buttons to the board
    private void createImageViewsAndButtons() {
        for (int i = 0; i < game.getBoard().length; i++) {
            for (int j = 0; j < game.getBoard()[i].length; j++) {
                Pane pane = new Pane();
                int[] square = new int[]{i, j};
                paneArr[i][j] = pane;
                setPaneRightColor(square);
                squareGrid.add(pane, j, i);

                ImageView image = new ImageView();
                imageViewArr[i][j] = image;
                setImageUrl(game.getBoard()[i][j], i, j);
                squareGrid.add(image, j, i);

                Button button = createSquareButton(i, j);
                squareGrid.add(button, j, i);
            }
        }
    }

    // Creating the invivsible buttons which calls on clickedButton when clicked on
    private Button createSquareButton(int i, int j) {
        Button button = new Button();
        button.setOpacity(0);
        button.setMaxHeight(Double.MAX_VALUE);
        button.setMaxWidth(Double.MAX_VALUE);
        button.setCursor(Cursor.HAND);
        button.setOnAction((event) -> { clickedButton(button, i, j);});
        return button;
    }

    // Updating which button is clicked on and calls doMove()
    private void clickedButton(Button button, int i, int j) {
        if (!game.isGameOver()) {
            paneArr[i][j].setStyle("-fx-background-color:#FDFD66");
            if (lastClickedSquare != null) {
                if (!Arrays.equals(lastClickedSquare, game.getLastMoveSquare())) setPaneRightColor(lastClickedSquare); 
                doMove(lastClickedSquare, i, j);
            } 
            lastClickedSquare = new int[]{i,j};
        }
    }

    // Setting the pane "square" back to original color
    private void setPaneRightColor(int[] square) {
        if (square[0]%2 == 0 && square[1]%2 == 1 || square[0]%2 == 1 && square[1]%2 == 0) {
            paneArr[square[0]][square[1]].setStyle("-fx-background-color:green");
        } else {
            paneArr[square[0]][square[1]].setStyle("-fx-background-color:#FFFDE7");
        }
    }

    // Executes move if it's legal, if so then updates the images with setImageUrl and calls checkResult()
    private void doMove(int[] lastButtonClickedSquare, int i, int j) {
        Piece lastPieceClickedOn = game.getBoard()[lastButtonClickedSquare[0]][lastButtonClickedSquare[1]];
        int[] moveTo = new int[] {i, j};
        int[] lastMove = game.getLastMoveSquare();

        if (lastPieceClickedOn != null) {
            if (game.tryMove(lastButtonClickedSquare, lastPieceClickedOn, moveTo)) {
                if (lastMove != null && !Arrays.equals(lastMove, moveTo)) setPaneRightColor(lastMove);
                setImageUrl(game.getBoard()[i][j], i, j);
                setImageUrl(null, lastButtonClickedSquare[0], lastButtonClickedSquare[1]);

                if (game.getMoveWasCastling()) {
                    setImageUrl(new Rook(game.getNextTurn()), game.getRookCastlePos()[1][0], game.getRookCastlePos()[1][1]);
                    setImageUrl(null, game.getRookCastlePos()[0][0], game.getRookCastlePos()[0][1]);
                }
                if (game.isGameOver()) checkResult();
            } 
        }
    }

    // Setting the cordinate i, j image to match the piece
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

    // Check the result after a move
    private void checkResult() {
        resultPane.visibleProperty().set(true);
        winningMethod.setText("by checkmate");
        if (game.getWinner() == 'w') {
            winnerTextField.setText("White won!");
        } else if (game.getWinner() == 'b') {
            winnerTextField.setText("Black won!");
        } else {
            winnerTextField.setText("Draw!");
            winningMethod.setText("by stalemate");
        }
    }  

    // Is called when white click resign
    @FXML
    private void whiteResign() {
        resultPane.visibleProperty().set(true);
        winningMethod.setText("by resignation");
        winnerTextField.setText("Black won!");
        System.out.println("Vinnereren er svart!");
    }

    // Is called when black click resign
    @FXML
    private void blackResign() {
        resultPane.visibleProperty().set(true);
        winningMethod.setText("by resignation");
        winnerTextField.setText("White won!");
        System.out.println("Vinnereren er hvit!");
    }

    private void addSavedFilesToChoiceBox() {
        try {
            File path = gameSaver.getFileFolderPath().toFile();
            File[] fileArray = path.listFiles();
            for (File file : fileArray) {  
                savedGamesCBox.getItems().add(file.getName().substring(0, file.getName().length()-4));
            }
            setChoiceBoxValue();
        } catch (Exception e) {
            //TODO: handle exception
        }
    }

    private void setChoiceBoxValue() {
        if (savedGamesCBox.getItems().isEmpty()) {
            savedGamesCBox.setValue("No games saved yet");
        } else {
            savedGamesCBox.setValue("Choose a game");
        }
    }

    private String getFilename() {
        String filename = this.gameNameInput.getText();
        if (filename.isEmpty()) {
            System.out.println("The game must have a name");
        }
        return filename;
    }

    @FXML
    private void deleteGame() {
        try {
            File deleteFile = gameSaver.getFilePath(savedGamesCBox.getValue()).toFile();
            deleteFile.delete();
            savedGamesCBox.getItems().remove(savedGamesCBox.getValue());
            setChoiceBoxValue();
        } catch (IOException e) {
            
        }
    }

    
    @FXML 
    private void handleSave() {
        try {
            gameSaver.saveGame(getFilename(), game);
            if (!savedGamesCBox.getItems().contains(getFilename())) {
                savedGamesCBox.getItems().add(getFilename()); 
            }
            setChoiceBoxValue();
        } catch (IOException e) {
            
        }
    }

    @FXML
    private void handleLoad() {
        try {
            if (!savedGamesCBox.getItems().isEmpty()) initialize();
            game = gameSaver.loadGame(savedGamesCBox.getValue());
            setPiecesToMatchBoard();
            exitSavedGames();
            int[] move = game.getLastMoveSquare();
            if (move != null) paneArr[move[0]][move[1]].setStyle("-fx-background-color:#FDFD66");
            if (game.isGameOver()) extraStartButton.visibleProperty().set(true);
        } catch (IOException e) {

        }
    }

    // When the exit button on the result is clicked
    @FXML
    private void exitResult() {
        resultPane.visibleProperty().set(false);
        extraStartButton.visibleProperty().set(true);
    }

    @FXML
    private void exitSavedGames() {
        savedGamesPane.visibleProperty().set(false);
    }

    @FXML
    private void showSavedGames() {
        savedGamesPane.visibleProperty().set(true);
    }
}
