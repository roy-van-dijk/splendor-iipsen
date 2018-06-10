package application.controllers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.BindException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.ExportException;
import java.rmi.server.UnicastRemoteObject;

import application.StageManager;
import application.domain.Game;
import application.domain.GameImpl;
import application.domain.Lobby;
import application.domain.LobbyImpl;
import application.domain.Player;
import application.domain.PlayerImpl;
import application.views.LobbyView;
import application.views.MainMenuView;
import javafx.application.Platform;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * @author Roy
 *
 */
public class MainMenuController {
	
	/**
	 * 
	 * @param hostIP
	 * @param playerName
	 * @throws RemoteException
	 * @throws NotBoundException
	 * void
	 */
	public void joinLobby(String hostIP, String playerName) throws RemoteException, NotBoundException {
		Lobby lobby = connectToLobby(hostIP);
		
/*		System.out.println(lobby.getGame().toString());
		lobby.getGame().nextTurn();*/
		
		System.out.println("Connected to lobby!");
		
		LobbyController lobbyController = new LobbyController(lobby);
		LobbyView lobbyView = new LobbyView(lobby, lobbyController);

		lobbyController.connectPlayer(lobbyView, playerName);
		
		StageManager.getInstance().switchScene(lobbyView.asPane());
	}

	/**
	 * 
	 * @param playerName
	 * @throws RemoteException
	 * void
	 * @throws BindException 
	 */
	public void hostPreviousGame(String playerName) throws RemoteException, ExportException {
		GameImpl game = new GameImpl(3); // TODO: load from binary savefile
		LobbyImpl lobby = createLobby(game);
        
        
		LobbyController lobbyController = new LobbyController(lobby);
		LobbyView lobbyView = new LobbyView(lobby, lobbyController);
		lobbyController.connectPlayer(lobbyView, playerName);
		
		StageManager.getInstance().switchScene(lobbyView.asPane());
	}
	/**
	 * 
	 * @param playerName
	 * @throws RemoteException
	 * void
	 * @throws BindException 
	 */
	public void hostNewGame(String playerName) throws RemoteException, ExportException {
		GameImpl game = new GameImpl(4);
/*		try {
			new ObjectOutputStream(new ByteArrayOutputStream()).writeObject(game); // Test serialization
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} */
		LobbyImpl lobby = createLobby(game);
		LobbyController lobbyController = new LobbyController(lobby);
		LobbyView lobbyView = new LobbyView(lobby, lobbyController);
		
		lobbyController.connectPlayer(lobbyView, playerName);

		StageManager.getInstance().switchScene(lobbyView.asPane());
	}
	/**
	 * 
	 * @param game
	 * @return
	 * @throws RemoteException
	 * LobbyImpl
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
	 * 
	 * @param hostIP
	 * @return
	 * @throws RemoteException
	 * @throws NotBoundException
	 * Lobby
	 */
	public Lobby connectToLobby(String hostIP) throws RemoteException, NotBoundException
	{
		System.out.println("Getting access to RMI registry");
		Registry registry = LocateRegistry.getRegistry(hostIP); // Default port: 1099				
		System.out.println("Getting the Lobby stub from registry");
		Lobby lobby = (Lobby) registry.lookup("Lobby");
		System.out.println("Connected to RMI server.");
		
		return lobby;
	}

}
