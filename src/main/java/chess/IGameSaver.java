package chess;

import java.io.File;
import java.io.IOException;

public interface IGameSaver {
    
    void saveGame(String filename, ChessGame game) throws IOException;

    ChessGame loadGame(String filename) throws IOException;

    File getGameFile(String filename);
}
