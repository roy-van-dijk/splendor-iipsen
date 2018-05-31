package application;

import application.controllers.GameController;
import application.controllers.GameControllerImpl;
import application.controllers.MainMenuController;
import application.controllers.MainMenuControllerImpl;
import application.domain.Game;
import application.domain.GameImpl;
import application.views.GameView;
import application.views.MainMenuView;
import javafx.scene.Scene;
import javafx.scene.image.Image;
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
	 * @param stage
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
	 * Start the MainMenu
	 */
	public void showMainMenu() {
		MainMenuController menuController = new MainMenuControllerImpl();
		MainMenuView homeView = new MainMenuView(menuController);
		
		switchScene(homeView.asPane());
	}
	

}