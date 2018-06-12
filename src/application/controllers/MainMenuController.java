package application.controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.ExportException;

import application.StageManager;
import application.domain.GameImpl;
import application.domain.Lobby;
import application.domain.LobbyImpl;
import application.services.SaveGameDAO;
import application.util.LobbyFullException;
import application.views.LobbyView;

// TODO: Auto-generated Javadoc
/**
 * Controls UI flow within the main menu.
 *
 * @author Roy
 */
public class MainMenuController {

	/**
	 * Sends user input (hostIP & playerName) to lobbyController to join the lobby,
	 * then switches the view.
	 *
	 * @param hostIP
	 * @param playerName 
	 * @throws RemoteException
	 * @throws NotBoundException
	 * @throws LobbyFullException 
	 */
	public void joinLobby(String hostIP, String playerName) throws RemoteException, NotBoundException, LobbyFullException {
		Lobby lobby = connectToLobby(hostIP);
		System.out.println("Connected to lobby!");

		LobbyController lobbyController = new LobbyController(lobby);
		LobbyView lobbyView = new LobbyView(lobby, lobbyController);
		lobbyController.connectPlayer(lobbyView, playerName);
		
		StageManager.getInstance().switchScene(lobbyView.asPane());
	}

	/**
	 * Sends user input (playerName) to lobbyController to create the lobby, loads
	 * the previous save file, then switches the view.
	 *
	 * @param playerName
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * @throws FileNotFoundException 
	 */
	public void hostPreviousGame(String playerName) throws FileNotFoundException, ClassNotFoundException, IOException {
		GameImpl game = SaveGameDAO.getInstance().loadSaveGame();
		
		LobbyImpl lobby = createLobby(game);
		LobbyController lobbyController = new LobbyController(lobby);
		LobbyView lobbyView = new LobbyView(lobby, lobbyController);
		
		lobbyController.connectPlayer(lobbyView, playerName);
		
		StageManager.getInstance().switchScene(lobbyView.asPane());
	}

	/**
	 * Sends user input (playerName) to lobbyController to create the lobby, loads a
	 * new save file, then switches the view.
	 *
	 * @param playerName
	 * @throws RemoteException
	 * @throws ExportException
	 */
	public void hostNewGame(String playerName) throws RemoteException, ExportException {
		GameImpl game = new GameImpl(4);
		/*
		 * try { new ObjectOutputStream(new ByteArrayOutputStream()).writeObject(game);
		 * // Test serialization } catch (IOException e) { // TODO Auto-generated catch
		 * block e.printStackTrace(); }
		 */
		LobbyImpl lobby = createLobby(game);
		LobbyController lobbyController = new LobbyController(lobby);
		LobbyView lobbyView = new LobbyView(lobby, lobbyController);
		
		lobbyController.connectPlayer(lobbyView, playerName);

		StageManager.getInstance().switchScene(lobbyView.asPane());
	}

	/**
	 * Creates a new lobby.
	 *
	 * @param game
	 * @return LobbyImpl
	 * @throws RemoteException
	 * @throws ExportException
	 */

	private LobbyImpl createLobby(GameImpl game) throws RemoteException, ExportException
	{
		System.out.println("Creating server.");
		
		LobbyImpl lobby = new LobbyImpl(game);
		System.out.println("Lobby skeleton created");
		
		Registry registry = LocateRegistry.createRegistry(1099);
		System.out.println("RMI Registry starter");
		
		registry.rebind("Lobby", lobby); // bind lobby (auto exported) to RMI registry
		lobby.setRegistry(registry);
        System.out.println("Lobby skeleton bound");
        
        System.out.println("Server running...");
        return lobby;
	}

	/**
	 * Connects to a lobby.
	 *
	 * @param hostIP
	 * @return Lobby
	 * @throws RemoteException
	 * @throws NotBoundException Lobby
	 */
	public Lobby connectToLobby(String hostIP) throws RemoteException, NotBoundException, LobbyFullException {
		System.out.println("Getting access to RMI registry");
		Registry registry = LocateRegistry.getRegistry(hostIP); // Default port: 1099
		System.out.println("Getting the Lobby stub from registry");
		Lobby lobby = (Lobby) registry.lookup("Lobby");
		System.out.println("Connected to RMI server.");
		if(lobby.isFull())
		{
			throw new LobbyFullException("The lobby is full!");
		}

		return lobby;
	}

}
