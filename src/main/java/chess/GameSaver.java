package chess;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class GameSaver implements IGameSaver {

    @Override
    public void saveGame(String filename, ChessGame game) throws FileNotFoundException {
        try (PrintWriter writer = new PrintWriter(getFilePath(filename))) {
			writer.println(game.getBoardString());
			writer.println(game.getTurn());
			writer.println(game.getNextTurn());
			writer.println(game.isGameOver());

            writer.println(game.getKingPiece(game.getTurn()).hasMoved());
            writer.println(game.getKingPiece(game.getNextTurn()).hasMoved());
            writer.close();
        }
    }

    @Override
    public ChessGame loadGame(String filename) throws FileNotFoundException {
        ChessGame savedGame = new ChessGame();
        try (Scanner scanner = new Scanner(getFilePath(filename))) {
            savedGame.setBoard(scanner.nextLine());
            savedGame.setTurn(scanner.next().charAt(0));
            savedGame.setNextTurn(scanner.next().charAt(0));
            savedGame.setGameOver(scanner.nextBoolean());
            savedGame.getKingPiece(savedGame.getTurn()).setHasMoved(scanner.nextBoolean());
            savedGame.getKingPiece(savedGame.getNextTurn()).setHasMoved(scanner.nextBoolean());
        }
        return savedGame;
    }

    public static File getFilePath(String filename) {
	    return new File("src/main/resources/chess/savedGames/" + filename + ".txt");
    }   
}
