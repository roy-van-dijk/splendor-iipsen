package application;

import application.controllers.GameController;
import application.controllers.GameControllerImpl;
import application.controllers.LobbyController;
import application.controllers.LobbyControllerImpl;
import application.controllers.MenuController;
import application.controllers.MenuControllerImpl;
import application.domain.Game;
import application.domain.GameImpl;
import application.views.GameView;
import application.views.MainMenuView;
import application.views.LobbyView;
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

	public void showGameScreen() {
		Game game = new GameImpl();
		
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
		primaryStage = stage;

		MenuController menuController = new MenuControllerImpl();
		MainMenuView homeView = new MainMenuView(menuController);

		Scene scene = new Scene(homeView.asPane(), 1800, 1000);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

		primaryStage.getIcons().add(new Image(("file:resources/misc/splendor-icon.png")));
		primaryStage.setScene(scene);
		// primaryStage.initStyle(StageStyle.UNDECORATED); // borderless
		// primaryStage.setMaximized(true);

		primaryStage.setTitle("Splendor");
		
	   
		primaryStage.show();
	}

	public void showLobbyScreen(String hostIp, String nickname) {
		LobbyController lobbyController = new LobbyControllerImpl(hostIp, nickname);
		LobbyView lobbyView = new LobbyView(lobbyController);

		this.switchScene(lobbyView.asPane());
	}

}