package application;
	
import java.net.InetAddress;
import java.net.UnknownHostException;

import application.util.Logger;
import application.util.Logger.Verbosity;
import application.views.ManualWindowView;
import javafx.application.Application;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;


/**
 * The Class Main.
 *
 * @author Sanchez
 */
public class Main extends Application {
	
	/* (non-Javadoc)
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	@Override
	public void start(Stage primaryStage) {	
		try {
			primaryStage.addEventHandler(KeyEvent.ANY, e -> {
		          if(e.getCode() == KeyCode.F1) {
		        	  new ManualWindowView();
		          }
		        });
			primaryStage.setMaximized(true);
			StageManager.getInstance().startSplendor(primaryStage);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		
		Logger.currentVerbosity = Verbosity.DEBUG;
		
		try {
			System.out.println(InetAddress.getLocalHost().getHostAddress());
			System.setProperty("java.rmi.server.hostname", InetAddress.getLocalHost().getHostAddress());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		launch(args);
		System.exit(0); // TODO: shutdown gracefully. Currently force exits all threads (including RMI)
	}
}
