package chess;

import java.io.FileNotFoundException;

public interface IGameSaver {
    
    void saveGame(String filename, ChessGame game) throws FileNotFoundException;

    ChessGame loadGame(String filename) throws FileNotFoundException;

}
