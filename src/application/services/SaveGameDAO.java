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

/**
 * @author Alexander
 * 
 * 
 */
public class SaveGameDAO {

	static ArrayList<Game> saveGames = new ArrayList<Game>();
	private Game game;

	private static String basePath = "saves";

	private static String extension = ".splendor";

	private File name;

	public SaveGameDAO(){

	}
/**
 * 
 * @param game Saves the game to an file
 * void
 */
	public void saveAnGame(Game game) {
		try {
			ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(name));
			output.writeObject(game);
			output.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
/**
 * 
 * @param filename
 * @return Game
 * @throws FileNotFoundException
 * @throws IOException
 * @throws ClassNotFoundException
 * Game
 */
	public Game loadSaveGame(String filename) throws FileNotFoundException, IOException, ClassNotFoundException {
	
		ObjectInputStream read = new ObjectInputStream(new FileInputStream(filename));
		game = (Game) read.readObject();
		read.close();
		return game;
	}

	/**
	 * 
	 * @return
	 * ArrayList
	 */
	public ArrayList listSaveGame() {
		ArrayList list = new ArrayList(); ;
		Path path = FileSystems.getDefault().getPath(basePath).toAbsolutePath();
		File folder = new File(path.toString());
		File[] listOfFiles = folder.listFiles();
		for (File file : listOfFiles) {
			if (file.isFile() ) {
				list.add(file);
				System.out.println(file);
				
			}
		}
		return list;
	}

}
