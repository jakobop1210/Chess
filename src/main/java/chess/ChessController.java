package chess;

import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXML;


public class ChessController {
    private List<Piece> board; 

    @FXML
    public void initialize() {

    }

    private void boardList() {
        board = new ArrayList<>();
        
    }
}
