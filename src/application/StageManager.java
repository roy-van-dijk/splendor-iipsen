package application;

import java.rmi.RemoteException;

import application.controllers.GameController;
import application.controllers.MainMenuController;
import application.domain.Game;
import application.domain.GameImpl;
import application.views.GameView;
import application.views.MainMenuView;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * The Class StageManager.
 *
 * @author Sanchez
 */
public class StageManager {

	private static StageManager instance;
	private static Stage primaryStage;

	/**
	 * Instantiates a new stage manager.
	 */
	// Prevents further initialization
	private StageManager() {
	}

	/**
	 * Gets the single instance of StageManager.
	 *
	 * @return single instance of StageManager
	 */
	public static StageManager getInstance() {
		if (instance == null)
			instance = new StageManager();

		return instance;
	}

	/**
	 * Switch scene.
	 *
	 * @param rootNode the root node
	 */
	public void switchScene(Pane rootNode) {
		primaryStage.getScene().setRoot(rootNode);
	}

	/**
	 * Show game screen.
	 *
	 * @throws RemoteException the remote exception
	 */
	public void showGameScreen() throws RemoteException
	{
		// TODO: remove this function entirely for production release.
		Game game = new GameImpl(4);

		GameController gameController = new GameController(game);
		GameView gameView = new GameView(game, gameController, game.getPlayers().get(0));

		this.switchScene(gameView.asPane());
	}

	/**
	 * Start splendor.
	 *
	 * @param stage the stage
	 */
	public void startSplendor(Stage stage) {

		Scene scene = new Scene(new Pane(), 1800, 1000);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		
		primaryStage = stage;
		primaryStage.getIcons().add(new Image(("file:resources/misc/splendor-icon.png")));
		//primaryStage.initStyle(StageStyle.UNDECORATED); // borderless
		//primaryStage.setMaximized(true);
		primaryStage.setTitle("Splendor");
		primaryStage.setScene(scene);
	   
		primaryStage.show();
		
		showMainMenu();
	}
	
	/**
	 * Start the MainMenu.
	 */
	public void showMainMenu() {
		MainMenuController menuController = new MainMenuController();
		MainMenuView homeView = new MainMenuView(menuController);
		
		switchScene(homeView.asPane());
	}
	

}