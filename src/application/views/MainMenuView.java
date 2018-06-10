package application.views;

import java.rmi.ConnectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.ExportException;

import application.StageManager;
import application.controllers.MainMenuController;
import application.util.AlertDialog;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
	
/**
 * View for the main menu. Also includes views for each button that can be
 * clicked
 * 
 * @author Roy
 *
 */
public class MainMenuView implements UIComponent {

	// TODO: Remove the DRYness of this class
	
	private MainMenuController menuController;

	private BorderPane root;

	private Pane manualButton;

	private Button btnJoinLobby;
	private Button btnPreviousGame;
	private Button btnNewGame;

	private TextField nickname;
	private TextField hostIp;

	/**
	 * Builds a new main menu screen
	 * 
	 * @param menuController
	 */
	public MainMenuView(MainMenuController menuController) {
		this.menuController = menuController;

		this.buildMainMenuScreen();
	}

	/**
	 * Button that opens the manual
	 * 
	 * @return HBox
	 */
	private HBox buildManualButton() {
		HBox manualContainer = new HBox();
		Button manualButton = new Button("?");

		manualButton.getStyleClass().addAll("button", "manual-button");
		manualButton.setOnAction(e -> new ManualWindowView());

		manualContainer.getChildren().add(manualButton);
		manualContainer.setAlignment(Pos.TOP_RIGHT);

		return manualContainer;
	}

	/**
	 * Builds a separate screen for when you click join lobby
	 * 
	 * @return BorderPane
	 */
	private BorderPane buildJoinLobbyScreen() {
		root = new BorderPane();
		manualButton = buildManualButton();

		VBox panel = new VBox(4);

		hostIp = new TextField();
		nickname = new TextField();

		
		Button btnJoin = new Button("Join");
		btnJoin.setDefaultButton(true);
		Button btnBack = new Button("Back");
		btnBack.setCancelButton(true);
		
		hostIp.setPrefWidth(450);
		nickname.setPrefWidth(450);
		btnJoin.setPrefWidth(450);
		btnBack.setPrefWidth(450);
		
		hostIp.setPromptText("Enter Host IP...");
		nickname.setPromptText("Enter Nickname");

		hostIp.getStyleClass().add("join-text");
		nickname.getStyleClass().add("join-text");

		
		btnJoin.setOnAction(e -> {
			try {
				menuController.joinLobby(hostIp.getText(), nickname.getText());
			} catch (ConnectException e1) {
				new AlertDialog(AlertType.ERROR, "Could not connect to the server. Server may be offline.").show();
			} catch (RemoteException e1) {
				new AlertDialog(AlertType.ERROR, "Something went wrong while connecting to the server.").show();
				e1.printStackTrace();
			} catch (NotBoundException e1) {
				new AlertDialog(AlertType.ERROR, "The server is not configured properly.").show();
				e1.printStackTrace();
			}
		});
		btnBack.setOnAction(e -> StageManager.getInstance().switchScene(buildMainMenuScreen()));
		
		panel.setAlignment(Pos.CENTER_LEFT); 
		panel.setSpacing(10);
		panel.setTranslateX(-250);
		panel.setTranslateY(100);
		panel.getChildren().addAll(hostIp, nickname, btnJoin, btnBack);
		

		root.setTop(manualButton);
		root.setRight(panel);
		root.getStyleClass().addAll("home-view", "join-panel");
		root.setPadding(new Insets(0));

		return root;
	}

	/**
	 * Builds a new screen for when you first start the game
	 * 
	 * @return BorderPane
	 */
	private BorderPane buildMainMenuScreen() {
		root = new BorderPane();
		manualButton = buildManualButton();

		VBox panel = new VBox(3);

		btnJoinLobby = new Button("Join Lobby");
		btnPreviousGame = new Button("Host Previous Game");
		btnNewGame = new Button("Host New Game");

		btnJoinLobby.setPrefWidth(450);
		btnPreviousGame.setPrefWidth(450);
		btnNewGame.setPrefWidth(450);

		btnJoinLobby.getStyleClass().add("home-button");
		btnPreviousGame.getStyleClass().add("home-button");
		btnNewGame.getStyleClass().add("home-button");

		btnJoinLobby.setOnAction(e -> StageManager.getInstance().switchScene(buildJoinLobbyScreen()));
		btnPreviousGame.setOnAction(e -> StageManager.getInstance().switchScene(buildHostPreviousGameScreen()));
		btnNewGame.setOnAction(e -> StageManager.getInstance().switchScene(buildHostNewGameScreen()));

		panel.setAlignment(Pos.CENTER_LEFT);
		panel.setSpacing(10);
		panel.setTranslateX(-250);
		panel.setTranslateY(100);
		panel.getChildren().addAll(btnJoinLobby, btnPreviousGame, btnNewGame);

		root.setTop(manualButton);
		root.setRight(panel);
		root.getStyleClass().add("home-view");
		root.setPadding(new Insets(0));

		return root;
	}

	/**
	 * Builds a new screen for when you click host new game
	 * 
	 * @return BorderPane
	 */
	private BorderPane buildHostNewGameScreen() {
		root = new BorderPane();
		manualButton = buildManualButton();

		VBox panel = new VBox(3);

		nickname = new TextField();
		
		Button btnJoin = new Button("Join");
		btnJoin.setDefaultButton(true);
		Button btnBack = new Button("Back");
		btnBack.setCancelButton(true);
		
		nickname.setPrefWidth(450);
		btnJoin.setPrefWidth(450);
		btnBack.setPrefWidth(450);
		
		nickname.setPromptText("Enter Nickname");
		nickname.getStyleClass().add("join-text");
		
		btnJoin.setOnAction(e -> {
			try {
					menuController.hostNewGame(nickname.getText());
			} catch (ExportException e1) {
				new AlertDialog(AlertType.ERROR, "You are already running a server.").show();
				e1.printStackTrace();
			} catch (RemoteException e1) {
				new AlertDialog(AlertType.ERROR, "Could not start a new server.").show();
				e1.printStackTrace();
			}
		});
		btnBack.setOnAction(e -> StageManager.getInstance().switchScene(buildMainMenuScreen()));
		
		panel.setAlignment(Pos.CENTER_LEFT); 
		panel.setSpacing(10);
		panel.setTranslateX(-250);
		panel.setTranslateY(100);
		panel.getChildren().addAll(nickname, btnJoin, btnBack);
		
		root.setTop(manualButton);
		root.setRight(panel);
		root.getStyleClass().addAll("home-view", "join-panel");
		root.setPadding(new Insets(0));

		return root;
	}

	/**
	 * Builds a new screen for when you click host previous game
	 * 
	 * @return BorderPane
	 */
	private BorderPane buildHostPreviousGameScreen() {
		root = new BorderPane();
		manualButton = buildManualButton();

		VBox panel = new VBox(3);

		nickname = new TextField();
		
		Button btnJoin = new Button("Join");
		btnJoin.setDefaultButton(true);
		Button btnBack = new Button("Back");
		btnBack.setCancelButton(true);
		
		nickname.setPrefWidth(450);
		btnJoin.setPrefWidth(450);
		btnBack.setPrefWidth(450);
		
		nickname.setPromptText("Enter Nickname");
		nickname.getStyleClass().add("join-text");
		
		btnJoin.setOnAction(e -> {
			try {
				menuController.hostPreviousGame(nickname.getText());
			} catch (ExportException e1) {
				new AlertDialog(AlertType.ERROR, "You are already running a server.").show();
				e1.printStackTrace();
			} catch (RemoteException e1) {
				new AlertDialog(AlertType.ERROR, "Could not start a new server.").show();
				e1.printStackTrace();
			}
		});
		btnBack.setOnAction(e -> StageManager.getInstance().switchScene(buildMainMenuScreen()));
		
		panel.setAlignment(Pos.CENTER_LEFT); 
		panel.setSpacing(10);
		panel.setTranslateX(-250);
		panel.setTranslateY(100);
		panel.getChildren().addAll(nickname, btnJoin, btnBack);
	
		root.setTop(manualButton);
		root.setRight(panel);
		root.getStyleClass().addAll("home-view", "join-panel");
		root.setPadding(new Insets(0));

		return root;
	}

	public Pane asPane() {
		return root;
	}
}
