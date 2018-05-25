package application;
	
import application.controllers.GameController;
import application.controllers.GameControllerImpl;
import application.domain.Game;
import application.domain.GameImpl;
import application.services.CardsReader;
import application.views.GameView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


/**
 * @author Sanchez
 *
 *
 */
public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		try {
			StageManager.getInstance().showMainMenu(primaryStage);
//			StageManager.getInstance().showLobbyScreen();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
