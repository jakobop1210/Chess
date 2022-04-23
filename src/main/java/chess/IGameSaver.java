package chess;

import java.io.IOException;
import java.nio.file.Path;

public interface IGameSaver {
    
    void saveGame(String filename, ChessGame game) throws IOException;

    ChessGame loadGame(String filename) throws IOException;

    Path getFilePath(String filename) throws IOException;

    Path getFileFolderPath(); 
}
