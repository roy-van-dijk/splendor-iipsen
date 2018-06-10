/**
 * 
 */
package application.services;

import java.awt.List;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.util.ArrayList;

import application.domain.Game;
import application.domain.GameImpl;

// TODO: Auto-generated Javadoc
/**
 * The Class SaveGameDAO.
 *
 * @author Alexander
 */
public class SaveGameDAO {

	private static SaveGameDAO instance;
	
	private GameImpl game;
	
	private static String path = "saves";
	private static String basePath;


	/**
	 * Instantiates a new save game DAO.
	 */
	private SaveGameDAO() {
	}

	/**
	 * Gets the single instance of SaveGameDAO.
	 *
	 * @return single instance of SaveGameDAO
	 */
	public static SaveGameDAO getInstance() {
		if (instance == null)
			instance = new SaveGameDAO();

		return instance;
	}

	/**
	 * Save game to file.
	 *
	 * @param game            Saves the game to an file void
	 * @throws FileNotFoundException the file not found exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void saveGameToFile(GameImpl game) throws FileNotFoundException, IOException {
		basePath = this.createSavePath("Bob.splendor");
		
			ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(basePath));
			output.writeObject(game);
			output.close();
			System.out.println("Game saved to "+  this.basePath);
	}

	/**
	 * Load save game.
	 *
	 * @param filename the filename
	 * @return Game
	 * @throws FileNotFoundException the file not found exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws ClassNotFoundException             Game
	 */
	public GameImpl loadSaveGame(String filename) throws FileNotFoundException, IOException, ClassNotFoundException {
		this.createSavePath("Bob.splendor");
		ObjectInputStream read = new ObjectInputStream(new FileInputStream(basePath));
		game = (GameImpl) read.readObject();
		
		read.close();
		
		return game;
	}
	
	/**
	 * Creates the save path.
	 *
	 * @param save the save
	 * @return the string
	 */
	public String createSavePath(String save) {
		
		return FileSystems.getDefault().getPath(path).toAbsolutePath().toString() + "/" +  save;
	}
	
}
