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
import javafx.scene.text.Text;


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
    private Text winnerTextField, winningMethod, savedGameFeedback;

    @FXML
    private TextField gameNameInput;

    @FXML
    private Pane resultPane, savedGamesPane, savedGameFeedbackPane, drawPane;

    @FXML
    private Button extraStartButton, whiteResignButton, blackResignButton, offerDrawButton;

    @FXML
    private ChoiceBox<String> savedGamesCBox;
    
    @FXML
    private void initialize() {
        if (gameCount > 0 && game.getLastMoveSquare() != null) {
            setPaneRightColor(game.getLastMoveSquare());
        }
        game = new ChessGame();
        exitResult();
        extraStartButton.visibleProperty().set(false);
        if (gameCount > 0) {
            enableButtons();
            setPiecesToMatchBoard();
        } else {
            createImageViewsAndButtons();
            addSavedFilesToChoiceBox();
        }
        gameCount++;
    }

    @FXML 
    private void handleSave() {
        savedGameFeedbackPane.visibleProperty().set(true);
        try {
            if (savedGamesCBox.getItems().contains(gameNameInput.getText())) {
                savedGameFeedback.setText("There is already a game with that name");
            } else if (gameNameInput.getText().length() == 0) {
                savedGameFeedback.setText("Need to give the game a name");
            } else {
                gameSaver.saveGame(gameNameInput.getText(), game);
                savedGamesCBox.getItems().add(gameNameInput.getText()); 
                savedGameFeedback.setText("Your game has been saved");
                setChoiceBoxValue();
            }
        } catch (IOException e) {
            savedGameFeedback.setText("An error occured");    
        }
    }


    @FXML
    private void handleLoad() {
        try {
            ChessGame newGame = gameSaver.loadGame(savedGamesCBox.getValue());
            if (!savedGamesCBox.getItems().isEmpty()) initialize();
            game = newGame;
            setPiecesToMatchBoard();
            exitSavedGames();
            int[] move = game.getLastMoveSquare();
            lastClickedSquare = move;
            if (move != null) paneArr[move[0]][move[1]].setStyle("-fx-background-color:#FDFD66");
            if (game.isGameOver()) extraStartButton.visibleProperty().set(true);
        } catch (IOException e) {
            savedGameFeedbackPane.visibleProperty().set(true);
            savedGameFeedback.setText("An error occured - File not found");
        }
    }

    @FXML
    private void deleteGame() {
        try {
            File deleteFile = gameSaver.getFilePath(savedGamesCBox.getValue()).toFile();
            deleteFile.delete();
            savedGamesCBox.getItems().remove(savedGamesCBox.getValue());
            setChoiceBoxValue();
        } catch (IOException e) {
            savedGameFeedbackPane.visibleProperty().set(true);
            savedGameFeedback.setText("Error occured - File not found");
        }
    }

    @FXML
    private void whiteResign() {
        winningMethod.setText("by resignation");
        winnerTextField.setText("Black won!");
        whenGameIsOver();
    }

    @FXML
    private void blackResign() {
        winningMethod.setText("by resignation");
        winnerTextField.setText("White won!");
        whenGameIsOver();
    }

    @FXML
    private void drawAccepted() {
        drawPane.visibleProperty().set(false);
        winningMethod.setText("by agreement");
        winnerTextField.setText("Draw!");
        whenGameIsOver();
    }

    @FXML
    private void exitResult() {
        resultPane.visibleProperty().set(false);
        extraStartButton.visibleProperty().set(true);
    }

    @FXML
    private void exitFeedback() {
        savedGameFeedbackPane.visibleProperty().set(false);
    }

    @FXML
    private void exitSavedGames() {
        savedGamesPane.visibleProperty().set(false);
        enableButtons();
    }
    
    @FXML
    private void exitDrawPane() {
        drawPane.visibleProperty().set(false);
        enableButtons();
    }

    @FXML
    private void offerDraw() {
        drawPane.visibleProperty().set(true);
        disableButtons();
    }

    @FXML
    private void showSavedGames() {
        savedGamesPane.visibleProperty().set(true);
        setChoiceBoxValue();
        disableButtons();
    }

    // Is called when game is over
    private void whenGameIsOver() {
        resultPane.visibleProperty().set(true);
        game.setGameOver(true);
        disableButtons();
    }

    private void disableButtons() {
        whiteResignButton.setDisable(true);
        blackResignButton.setDisable(true);
        offerDrawButton.setDisable(true);
    }
   
    private void enableButtons() {
        whiteResignButton.setDisable(false);
        blackResignButton.setDisable(false);
        offerDrawButton.setDisable(false);
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

    // Creating the invivsible buttons which calls clickedButton when clicked on
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
                if (!Arrays.equals(lastClickedSquare, game.getLastMoveSquare())) {
                    setPaneRightColor(lastClickedSquare); 
                    doMove(lastClickedSquare, i, j);
                }
            } 
            lastClickedSquare = new int[]{i,j};
        }
    }

    // Executes move if it's legal, if so then updates the images with setImageUrl and calls checkResult()
    private void doMove(int[] lastClickedSquare, int i, int j) {
        Piece lastPieceClickedOn = game.getBoard()[lastClickedSquare[0]][lastClickedSquare[1]];
        int[] moveTo = new int[] {i, j};
        int[] lastMove = game.getLastMoveSquare();

        if (lastPieceClickedOn != null) {
            try {
                if (game.tryMove(lastPieceClickedOn, moveTo)) {
                    if (lastMove != null && !Arrays.equals(lastMove, moveTo)) setPaneRightColor(lastMove);
                    setImageUrl(game.getBoard()[i][j], i, j);
                    setImageUrl(null, lastClickedSquare[0], lastClickedSquare[1]);
                    if (game.getMoveWasCastling()) {
                        setImageUrl(null, game.getRookCastleSquares()[0][0], game.getRookCastleSquares()[0][1]);
                        setImageUrl(new Rook(game.getNextTurn()), game.getRookCastleSquares()[1][0], game.getRookCastleSquares()[1][1]);
                    }
                    if (game.isGameOver()) checkResult();
                } 
            } catch (IllegalArgumentException e) {
                System.out.println("Not a valid move");
            }
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
        whenGameIsOver();
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

    // Adding all existing saved files to the choiceBox when game is opened
    private void addSavedFilesToChoiceBox() {
        try {
            File path = gameSaver.getFileFolderPath().toFile();
            File[] fileArray = path.listFiles();
            if (fileArray != null) {
                for (File file : fileArray) {  
                    savedGamesCBox.getItems().add(file.getName().substring(0, file.getName().length()-4));
                }
            setChoiceBoxValue();
            }
        } catch (Exception e) {
            savedGameFeedbackPane.visibleProperty().set(true);
            savedGameFeedback.setText("Could not establish a path connection for saving and loading games");
        }
    }

    // Set choiceBox value when "Saved gamed" is clicked, a game is saved or deleted
    private void setChoiceBoxValue() {
        if (savedGamesCBox.getItems().isEmpty()) {
            savedGamesCBox.setValue("No games saved yet");
        } else {
            savedGamesCBox.setValue("Choose a game");
        }
    }
}
