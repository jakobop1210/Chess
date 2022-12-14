package chess;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GameSaverTest {

    private static final String testGameContent = """
        r0bqkb0rppp00ppp00h00h000B0pp0000000P00000H00H00PPPP0PPPR0BQ0RK0
        b
        w
        false
        false
        true
        5
        2
        """.replaceAll("\\R", System.getProperty("line.separator"));

    private static final String invalidGameContent = """
        r0bqkb0rppp00ppp00h00h000B0000P00000H00H00PPPP0P
        s
        w
        null
        false
        true
        -1
        10
        """.replaceAll("\\R", System.getProperty("line.separator"));

    private IGameSaver getGameSaver() {
        return new GameSaver();
    };

    private ChessGame getCorrectTestGame() {
        ChessGame testGame = new ChessGame();
        testGame.setBoard("r0bqkb0rppp00ppp00h00h000B0pp0000000P00000H00H00PPPP0PPPR0BQ0RK0");
        testGame.setTurn('b');
        testGame.setNextTurn('w');
        testGame.getKingPiece('w').setHasMoved(true);
        testGame.setLastMoveSquare(new int[]{5, 2});
        return testGame;
    }

    @BeforeAll
    public void setup() throws IOException {
        Files.write(getGameSaver().getGameFile("test_game").toPath(), testGameContent.getBytes());
        Files.write(getGameSaver().getGameFile("invalid_file").toPath(), invalidGameContent.getBytes());
    }

    @Test
    public void testSaveGame() throws IOException {
        getGameSaver().saveGame("new_game", getCorrectTestGame());
        Path expectedFile = getGameSaver().getGameFile("test_game").toPath();
        Path actualFile = getGameSaver().getGameFile("new_game").toPath();
        assertEquals(-1, Files.mismatch(expectedFile, actualFile),
            "Not the same files");
    }

    @Test
    public void testLoadGame() throws IOException {
        ChessGame expectedChessGame = getCorrectTestGame();
        ChessGame actualChessGame = getGameSaver().loadGame("test_game");
        assertEquals(expectedChessGame.getBoardString(), actualChessGame.getBoardString());
        assertEquals(expectedChessGame.getTurn(), actualChessGame.getTurn());
        assertEquals(expectedChessGame.getNextTurn(), actualChessGame.getNextTurn());
        assertEquals(expectedChessGame.getKingPiece('w').hasMoved(), actualChessGame.getKingPiece('w').hasMoved());
        assertEquals(expectedChessGame.getKingPiece('b').hasMoved(), actualChessGame.getKingPiece('b').hasMoved());
        assertEquals(true, Arrays.equals(expectedChessGame.getLastMoveSquare(), actualChessGame.getLastMoveSquare()));
    }

    @Test
    public void testLoadNotExistingFile() {
        assertThrows(IOException.class, () -> {
            getGameSaver().loadGame("not_existing_file");
        }, "Cannot load game from a not existing file");
    }
 
    @Test
    public void testSaveInvalidInput() {
        assertThrows(IllegalArgumentException.class, () -> {
            getGameSaver().loadGame("invalid_file");
        }, "The content of the file is not valid for the game");
    }

    @AfterAll
    public void teardown() throws IOException {
        getGameSaver().getGameFile("test_game").delete();
        getGameSaver().getGameFile("new_game").delete();
        getGameSaver().getGameFile("invalid_file").delete();
    }
}
