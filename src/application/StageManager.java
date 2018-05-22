package application;

import application.controllers.GameController;
import application.controllers.GameControllerImpl;
import application.domain.Game;
import application.domain.GameImpl;
import application.views.GameView;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class StageManager {

	private static StageManager instance;
	private static Stage primaryStage;

	// Prevents further initialization
	private StageManager()
	{
	}
	
	public synchronized static StageManager getInstance() 
	{
		if (instance == null) 
			instance = new StageManager();
		
		return instance;
	}
	
	public void switchScene(Pane rootNode)
	{
		primaryStage.getScene().setRoot(rootNode);
	}
	
	public void showGameScreen(Stage stage)
	{
		primaryStage = stage;
		
		Game game = new GameImpl();
		GameController gameController = new GameControllerImpl(game);
		GameView gameView = new GameView(game, gameController);
		
		Scene scene = new Scene(gameView.asPane(), 1800, 1000);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		
		primaryStage.getIcons().add(new Image(("file:splendor.png")));
		primaryStage.setScene(scene);
		//primaryStage.initStyle(StageStyle.UNDECORATED); // borderless
		//primaryStage.setMaximized(true);
		
		game.addObserver(gameView); // Ugly code. 
		primaryStage.show();
	}
	
	
}