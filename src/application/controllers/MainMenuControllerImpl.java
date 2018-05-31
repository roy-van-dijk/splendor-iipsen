package application.controllers;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import application.StageManager;
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
public class MainMenuControllerImpl implements MainMenuController {
	

	@Override
	public void joinLobby(String hostIP, String playerName) throws RemoteException, NotBoundException {
		System.out.println("Getting access to RMI registry");
		Registry registry = LocateRegistry.getRegistry(hostIP); // Default port: 1099				
		System.out.println("Getting the Lobby stub from registry");
		Lobby lobby = (Lobby) registry.lookup("Lobby");

		Player player = new PlayerImpl(playerName);
		lobby.connectPlayer(player);
		
		System.out.println("Connected to lobby!");
		
		LobbyController lobbyController = new LobbyControllerImpl();
		LobbyView lobbyView = new LobbyView(lobby, lobbyController);
		
		StageManager.getInstance().switchScene(lobbyView.asPane());
	}

	@Override
	public void hostPreviousGame(String playerName) throws RemoteException {
		LobbyImpl lobby = new LobbyImpl(3);
		Lobby lobbySkeleton = (Lobby) UnicastRemoteObject.exportObject(lobby, 0); // cast to remote object
		System.out.println("Lobby skeleton created");
		Registry registry = LocateRegistry.createRegistry(1099); // default port 1099 // run RMI registry on local host
		System.out.println("RMI Registry starter");
		registry.rebind("Lobby", lobbySkeleton); // bind calculator to RMI registry
        System.out.println("Lobby skeleton bound");
        System.out.println("Server running...");
        
        Player player = new PlayerImpl(playerName);
        lobby.connectPlayer(player);
        
		LobbyController lobbyController = new LobbyControllerImpl();
		LobbyView lobbyView = new LobbyView(lobby, lobbyController);

		StageManager.getInstance().switchScene(lobbyView.asPane());
	}

	@Override
	public void hostNewGame(String playerName) throws RemoteException {
		LobbyImpl lobby = new LobbyImpl();
		Lobby lobbySkeleton  = (Lobby) UnicastRemoteObject.exportObject(lobby, 0); // cast to remote object
		System.out.println("Lobby skeleton created");
		Registry registry = LocateRegistry.createRegistry(1099); // default port 1099 // run RMI registry on local host
		System.out.println("RMI Registry starter");
		registry.rebind("Lobby", lobbySkeleton); // bind calculator to RMI registry
        System.out.println("Lobby skeleton bound");
        System.out.println("Server running...");
        
        Player player = new PlayerImpl(playerName);
        lobby.connectPlayer(player);
        
		LobbyController lobbyController = new LobbyControllerImpl();
		LobbyView lobbyView = new LobbyView(lobby, lobbyController);

		StageManager.getInstance().switchScene(lobbyView.asPane());
	}


}
