package application;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import application.controllers.GameController;
import application.controllers.GameControllerImpl;
import application.controllers.LobbyController;
import application.controllers.LobbyControllerImpl;
import application.controllers.MenuController;
import application.controllers.MenuControllerImpl;
import application.domain.Game;
import application.domain.GameImpl;
import application.domain.Lobby;
import application.domain.LobbyImpl;
import application.domain.Player;
import application.domain.PlayerImpl;
import application.views.GameView;
import application.views.MainMenuView;
import application.views.LobbyView;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.InputEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * 
 * @author Sanchez
 *
 */
public class StageManager {

	private static StageManager instance;
	private static Stage primaryStage;

	// Prevents further initialization
	private StageManager() {
	}

	public synchronized static StageManager getInstance() {
		if (instance == null)
			instance = new StageManager();

		return instance;
	}

	public void switchScene(Pane rootNode) {
		primaryStage.getScene().setRoot(rootNode);
	}

	public void showGameScreen()
	{
		Game game = new GameImpl(null);

		GameController gameController = new GameControllerImpl(game);
		GameView gameView = new GameView(game, gameController);

		this.switchScene(gameView.asPane());
	}

	/**
	 * 
	 * @param Stage
	 *            stage
	 */
	public void showMainMenu(Stage stage) {
		
		
		MenuController menuController = new MenuControllerImpl();
		MainMenuView homeView = new MainMenuView(menuController);

		Scene scene = new Scene(homeView.asPane(), 1800, 1000);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage = stage;

		primaryStage.getIcons().add(new Image(("file:resources/misc/splendor-icon.png")));
		primaryStage.setScene(scene);
		// primaryStage.initStyle(StageStyle.UNDECORATED); // borderless
		// primaryStage.setMaximized(true);

		primaryStage.setTitle("Splendor");
		primaryStage.setOnCloseRequest(e -> Platform.exit());
	   
		primaryStage.show();
	}
	
	/**
	 * Start the MainMenu
	 */
	public void startMainMenu() {
		MenuController menuController = new MenuControllerImpl();
		MainMenuView homeView = new MainMenuView(menuController);

		Scene scene = new Scene(homeView.asPane(), 1800, 1000);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public void hostNewGameLobby(String nickname) throws RemoteException {
		Lobby lobby = new LobbyImpl();
		Lobby lobbySkeleton  = (Lobby) UnicastRemoteObject.exportObject(lobby, 0); // cast to remote object
		System.out.println("Lobby skeleton created");
		Registry registry = LocateRegistry.createRegistry(1099); // default port 1099 // run RMI registry on local host
		System.out.println("RMI Registry starter");
		registry.rebind("Lobby", lobbySkeleton); // bind calculator to RMI registry
        System.out.println("Lobby skeleton bound");
        System.out.println("Server running...");
        
        Player player = new PlayerImpl(nickname);
        lobby.connectPlayer(player);
        
		LobbyController lobbyController = new LobbyControllerImpl();
		LobbyView lobbyView = new LobbyView(lobby, lobbyController);

		this.switchScene(lobbyView.asPane());
	}
	
	public void hostPreviousGameLobby(String nickname) throws RemoteException {
		LobbyImpl lobby = new LobbyImpl(3);
		Lobby lobbySkeleton = (Lobby) UnicastRemoteObject.exportObject(lobby, 0); // cast to remote object
		System.out.println("Lobby skeleton created");
		Registry registry = LocateRegistry.createRegistry(1099); // default port 1099 // run RMI registry on local host
		System.out.println("RMI Registry starter");
		registry.rebind("Lobby", lobbySkeleton); // bind calculator to RMI registry
        System.out.println("Lobby skeleton bound");
        System.out.println("Server running...");
        
        Player player = new PlayerImpl(nickname);
        lobby.connectPlayer(player);
        
		LobbyController lobbyController = new LobbyControllerImpl();
		LobbyView lobbyView = new LobbyView(lobby, lobbyController);

		this.switchScene(lobbyView.asPane());
	}

	public void showLobbyScreen(String hostIP, String nickname) throws RemoteException, NotBoundException {
		
		System.out.println("Getting access to the registry");
		// get access to the RMI registry on the remote server
		Registry registry = LocateRegistry.getRegistry(hostIP); // if server on another machine: provide that machine's IP address. Default port  1099				
		System.out.println("Getting the Lobby stub from registry");
		Lobby lobby = (Lobby) registry.lookup("Lobby");

		LobbyController lobbyController = new LobbyControllerImpl();
		LobbyView lobbyView = new LobbyView(lobby, lobbyController);
		
		Player player = new PlayerImpl(nickname);
		lobby.connectPlayer(player);
		

		System.out.println("Done!");

		this.switchScene(lobbyView.asPane());
	}

}