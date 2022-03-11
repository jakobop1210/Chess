package chess;

import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;



public class ChessController {
    private ChessGame game = new ChessGame();
    private Piece[][] board = game.getBoard(); 
    private GridPane lastClickedSquare;

    @FXML
    public GridPane squareGrid;

    @FXML
    public void initialize() {
        createBoard();
    }

    // Legger til knapper med riktig bakgrunnsfarge og brikke
    private void createBoard() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
              //int color;
              //if (i%2==0 && j%2==0 || i%2!=0 && j%2!=0) {
              //    color = 1;
              //} else {
              //    color = 2;
              //}
                Button button = createSquareButton(i, j);
                squareGrid.add(button, i, j);
                button.setOnAction((event) -> {
                    for (Node node : squareGrid.getChildren()) {
                        if (GridPane.getColumnIndex(node) == i && GridPane.getRowIndex(node) == j) {
                            node.setStyle("-fx-background-color: #FFFBC8;");
                        }
                    }
                });
            }
        }
    }

    // Lager rutene som er en knapp med riktig farge og brikke
    private Button createSquareButton(int i, int j) {
       // String [][] boardImages = {{"rr.png","hh.png","bb.png","qq.png","kk.png","bb.png","hh.png","rr.png"},{"pp.png","pp.png","pp.png","pp.png","pp.png","pp.png","pp.png","pp.png"},{"","","","","","","",""},{"","","","","","","",""},{"","","","","","","",""},{"","","","","","","",""},{"p.png","p.png","p.png","p.png","p.png","p.png","p.png","p.png"},{"r.png","h.png","b.png","q.png","k.png","b.png","h.png","r.png"}};
        Button button = new Button();
        button.setOpacity(0);
        button.setMaxHeight(Double.MAX_VALUE);
        button.setMaxWidth(Double.MAX_VALUE);
        button.setCursor(Cursor.HAND);

        return button;
    }
    
}
