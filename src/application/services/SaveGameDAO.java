/**
 * 
 */
package application.services;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.FileSystems;

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
	
	private static String directory = "";
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
		System.out.println("Game saved to " + basePath);
	}

	/**
	 * Load save game.
	 *
	 * @return Game
	 * @throws FileNotFoundException the file not found exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws ClassNotFoundException             Game
	 */
	public GameImpl loadSaveGame() throws FileNotFoundException, IOException, ClassNotFoundException {
		basePath = this.createSavePath("Bob.splendor");
		ObjectInputStream read = new ObjectInputStream(new FileInputStream(basePath));
		game = (GameImpl) read.readObject();
		game.reinitializeObservers();
		
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
		
		return FileSystems.getDefault().getPath(directory).toAbsolutePath().toString() + "/" +  save;
	}
	
}
