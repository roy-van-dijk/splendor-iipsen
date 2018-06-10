package application.controllers;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import application.StageManager;
import application.domain.GameImpl;
import application.domain.Lobby;
import application.domain.LobbyImpl;
import application.views.LobbyView;

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
	 */
	public void joinLobby(String hostIP, String playerName) throws RemoteException, NotBoundException {
		Lobby lobby = connectToLobby(hostIP);

		/*
		 * System.out.println(lobby.getGame().toString()); lobby.getGame().nextTurn();
		 */

		System.out.println("Connected to lobby!");

		LobbyController lobbyController = new LobbyController(lobby);
		lobbyController.connectPlayer(playerName);
		LobbyView lobbyView = new LobbyView(lobby, lobbyController);

		StageManager.getInstance().switchScene(lobbyView.asPane());
	}

	/**
	 * Sends user input (playerName) to lobbyController to create the lobby, loads
	 * the previous save file, then switches the view.
	 *
	 * @param playerName
	 * @throws RemoteException
	 */
	public void hostPreviousGame(String playerName) throws RemoteException {
		GameImpl game = new GameImpl(3); // TODO: load from binary savefile
		LobbyImpl lobby = createLobby(game);

		lobby.createPlayer(playerName);

		LobbyController lobbyController = new LobbyController(lobby);
		LobbyView lobbyView = new LobbyView(lobby, lobbyController);

		StageManager.getInstance().switchScene(lobbyView.asPane());
	}

	/**
	 * Sends user input (playerName) to lobbyController to create the lobby, loads a
	 * new save file, then switches the view.
	 *
	 * @param playerName
	 * @throws RemoteException
	 */
	public void hostNewGame(String playerName) throws RemoteException {
		GameImpl game = new GameImpl(4);
		/*
		 * try { new ObjectOutputStream(new ByteArrayOutputStream()).writeObject(game);
		 * // Test serialization } catch (IOException e) { // TODO Auto-generated catch
		 * block e.printStackTrace(); }
		 */
		LobbyImpl lobby = createLobby(game);

		lobby.createPlayer(playerName);

		LobbyController lobbyController = new LobbyController(lobby);
		LobbyView lobbyView = new LobbyView(lobby, lobbyController);

		StageManager.getInstance().switchScene(lobbyView.asPane());
	}

	/**
	 * Creates a new lobby.
	 *
	 * @param game
	 * @return LobbyImpl
	 * @throws RemoteException LobbyImpl
	 */
	private LobbyImpl createLobby(GameImpl game) throws RemoteException {
		LobbyImpl lobby = new LobbyImpl(game);
		// Lobby lobbySkeleton = (Lobby) UnicastRemoteObject.exportObject(lobby, 0); //
		// cast to remote object
		System.out.println("Lobby skeleton created");
		Registry registry = LocateRegistry.createRegistry(1099); // default port 1099 // run RMI registry on local host
		System.out.println("RMI Registry starter");
		registry.rebind("Lobby", lobby); // bind calculator to RMI registry
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
	public Lobby connectToLobby(String hostIP) throws RemoteException, NotBoundException {
		System.out.println("Getting access to RMI registry");
		Registry registry = LocateRegistry.getRegistry(hostIP); // Default port: 1099
		System.out.println("Getting the Lobby stub from registry");
		Lobby lobby = (Lobby) registry.lookup("Lobby");
		System.out.println("Connected to RMI server.");

		return lobby;
	}

}
