package chess;

import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;


public class ChessController {
    private ChessGame game;
    private Piece[][] board = game.getboard(); 

    @FXML
    public GridPane squareGrid;

    @FXML
    public void initialize() {
        squareGrid.add(createSquareButton(), 0, 0);
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
               //Piece piece = board[i][j];
               //int[] currentSquare = {i, j};
                squareGrid.add(createSquareButton(), i % 8, i / 8);
            }
        }
    }

    private Button createSquareButton() {
        Button button = new Button("Halla");
        button.setMaxHeight(Double.MAX_VALUE);
        button.setMaxWidth(Double.MAX_VALUE);
        button.setCursor(Cursor.HAND);
        button.setOnAction((event) -> button.setStyle("-fx-background-color: #FFFBC8;"));
        
        return button;
    }
}
