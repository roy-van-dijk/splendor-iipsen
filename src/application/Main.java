package application;
	
import java.net.InetAddress;
import java.net.UnknownHostException;

import application.controllers.GameController;
import application.controllers.GameControllerImpl;
import application.domain.Game;
import application.domain.GameImpl;
import application.services.CardsReader;
import application.views.GameView;
import application.views.ManualWindowView;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
			primaryStage.addEventHandler(KeyEvent.ANY, e -> {
		          if(e.getCode() == KeyCode.F1) {
		        	  new ManualWindowView();
		          }
		        });
			StageManager.getInstance().startSplendor(primaryStage);
			StageManager.getInstance().showGameScreen();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		
		try {
			System.setProperty("java.rmi.server.hostname", InetAddress.getLocalHost().getHostAddress());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		launch(args);
		System.exit(0); // TODO: shutdown gracefully. Currently force exits all threads (including RMI)
	}
}
