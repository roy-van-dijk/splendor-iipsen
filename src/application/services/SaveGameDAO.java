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

/**
 * @author Alexander
 * 
 * 
 */
public class SaveGameDAO {

	private static SaveGameDAO instance;
	
	private GameImpl game;
	
	private static String path = "saves";
	private static String basePath;


	private SaveGameDAO() {
	}

	public static SaveGameDAO getInstance() {
		if (instance == null)
			instance = new SaveGameDAO();

		return instance;
	}

	/**
	 * 
	 * @param game
	 *            Saves the game to an file void
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 * @throws RemoteException
	 */
	public void saveGameToFile(GameImpl game) throws FileNotFoundException, IOException {
		this.setSavePath("Bob.splendor");
		
			ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(basePath));
			output.writeObject(game);
			output.close();
			System.out.println("Game saved to "+  this.basePath);
	}

	/**
	 * 
	 * @param filename
	 * @return Game
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 *             Game
	 */
	public GameImpl loadSaveGame(String filename) throws FileNotFoundException, IOException, ClassNotFoundException {

		ObjectInputStream read = new ObjectInputStream(new FileInputStream(filename));
		game = (GameImpl) read.readObject();
		
		read.close();
		
		return game;
	}
	
	private void setSavePath(String save) {
		
		basePath = FileSystems.getDefault().getPath(path).toAbsolutePath().toString() + "/" +  save;
	}
	
}
