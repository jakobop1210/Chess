package chess;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class GameSaver implements IGameSaver {

    @Override
    public void saveGame(String filename, ChessGame game) throws IOException {
        Files.createDirectories(getFileFolderPath());
        try (PrintWriter writer = new PrintWriter(getGameFile(filename))) {
            writer.println(game.getBoardString());
			writer.println(game.getTurn());
			writer.println(game.getNextTurn());
			writer.println(game.isGameOver());
            writer.println(game.getKingPiece(game.getTurn()).hasMoved());
            writer.println(game.getKingPiece(game.getNextTurn()).hasMoved());
            if (game.getLastMoveSquare() != null) {
                writer.println(game.getLastMoveSquare()[0]);
                writer.println(game.getLastMoveSquare()[1]);
            }
        }
    }

    @Override
    public ChessGame loadGame(String filename) throws IOException {
        ChessGame savedGame = new ChessGame();
        try (Scanner scanner = new Scanner(getGameFile(filename))) {
            savedGame.setBoard(scanner.nextLine());
            savedGame.setTurn(scanner.next().charAt(0));
            savedGame.setNextTurn(scanner.next().charAt(0));
            savedGame.setGameOver(scanner.nextBoolean());
            savedGame.getKingPiece(savedGame.getTurn()).setHasMoved(scanner.nextBoolean());
            savedGame.getKingPiece(savedGame.getNextTurn()).setHasMoved(scanner.nextBoolean());
            if (scanner.hasNextInt()) {
                savedGame.setLastMoveSquare(new int[]{scanner.nextInt(), scanner.nextInt()});
            }
        }
        return savedGame;
    }

    @Override
    public File getGameFile(String filename) {
        return getFileFolderPath().resolve(filename + ".txt").toFile();
    }   

    public static Path getFileFolderPath() {
        return Path.of(System.getProperty("user.home"), "tdt4100files", "Saved games");
    }   
}
