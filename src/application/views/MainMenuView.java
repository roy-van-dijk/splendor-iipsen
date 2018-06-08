package application.views;

import java.rmi.ConnectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import application.StageManager;
import application.controllers.GameController;
import application.controllers.MainMenuController;
import application.domain.Game;
import application.domain.PlayerImpl;
import application.util.AlertDialog;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.PopupWindow;
import javafx.stage.Stage;
/**
 * main menu views and the buttons they have
 * @author Tom de Jong labtop
 *
 */
public class MainMenuView implements UIComponent  {
	
//	private Home home;
	private MainMenuController menuController;
	
	private BorderPane root;
	
	private Pane manualButton;
	
	private Button btnJoinLobby;
	private Button btnPreviousGame;
	private Button btnNewGame;
	
	private TextField nickname;
	private TextField hostIp;

	public MainMenuView(MainMenuController menuController) {
		this.menuController = menuController;

		this.buildMainMenuScreen();
	}

	/**
	 * button to open manual
	 * @return
	 */
	private HBox buildManualButton() 
	{
		HBox manualContainer = new HBox();
		Button manualButton = new Button("?");
		
		manualButton.getStyleClass().addAll("button", "manual-button");
		manualButton.setOnAction(e -> new ManualWindowView());
		
		manualContainer.getChildren().add(manualButton);
		manualContainer.setAlignment(Pos.TOP_RIGHT);
		
		return manualContainer;
	}
	
	private BorderPane buildJoinLobbyScreen()
	{
		root = new BorderPane();
		manualButton = buildManualButton();
		
		VBox panel = new VBox(4);

		hostIp = new TextField();
		nickname = new TextField();
		
		Button join = new Button("Join");
		Button back = new Button("Back");
		
		hostIp.setPrefWidth(450);
		nickname.setPrefWidth(450);
		join.setPrefWidth(450);
		back.setPrefWidth(450);
		
		hostIp.setPromptText("Enter Host IP...");
		nickname.setPromptText("Enter Nickname");
		
		hostIp.getStyleClass().add("join-text");
		nickname.getStyleClass().add("join-text");
		
		join.setOnAction(e -> {
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
		back.setOnAction(e -> StageManager.getInstance().switchScene(buildMainMenuScreen()));
		
		panel.setAlignment(Pos.CENTER_LEFT); 
		panel.setSpacing(10);
		panel.setTranslateX(-250);
		panel.setTranslateY(100);
		panel.getChildren().addAll(hostIp, nickname, join, back);
		
		root.setTop(manualButton);
		root.setRight(panel);
		root.getStyleClass().addAll("home-view", "join-panel");
		root.setPadding(new Insets(0));
		
		return root;
	}
	
	private BorderPane buildMainMenuScreen()
	{
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
	

	private BorderPane buildHostNewGameScreen()
	{
		root = new BorderPane();
		manualButton = buildManualButton();
		
		VBox panel = new VBox(3);

		nickname = new TextField();
		
		Button join = new Button("Join");
		Button back = new Button("Back");
		
		nickname.setPrefWidth(450);
		join.setPrefWidth(450);
		back.setPrefWidth(450);
		
		nickname.setPromptText("Enter Nickname");
		nickname.getStyleClass().add("join-text");
		
		join.setOnAction(e -> {
			try {
					menuController.hostNewGame(nickname.getText());
			} catch (RemoteException e1) {
				new AlertDialog(AlertType.ERROR, "Could not start a new server.").show();
				e1.printStackTrace();
			}
		});
		back.setOnAction(e -> StageManager.getInstance().switchScene(buildMainMenuScreen()));
		
		panel.setAlignment(Pos.CENTER_LEFT); 
		panel.setSpacing(10);
		panel.setTranslateX(-250);
		panel.setTranslateY(100);
		panel.getChildren().addAll(nickname, join, back);
		
		root.setTop(manualButton);
		root.setRight(panel);
		root.getStyleClass().addAll("home-view", "join-panel");
		root.setPadding(new Insets(0));
		
		return root;
	}

	private BorderPane buildHostPreviousGameScreen()
	{
		root = new BorderPane();
		manualButton = buildManualButton();
		
		VBox panel = new VBox(3);

		nickname = new TextField();
		
		Button join = new Button("Join");
		Button back = new Button("Back");
		
		nickname.setPrefWidth(450);
		join.setPrefWidth(450);
		back.setPrefWidth(450);
		
		nickname.setPromptText("Enter Nickname");
		nickname.getStyleClass().add("join-text");
		
		join.setOnAction(e -> {
			try {
				menuController.hostPreviousGame(nickname.getText());
			} catch (RemoteException e1) {
				new AlertDialog(AlertType.ERROR, "Could not start a new server.").show();
				e1.printStackTrace();
			}
		});
		back.setOnAction(e -> StageManager.getInstance().switchScene(buildMainMenuScreen()));
		
		panel.setAlignment(Pos.CENTER_LEFT); 
		panel.setSpacing(10);
		panel.setTranslateX(-250);
		panel.setTranslateY(100);
		panel.getChildren().addAll(nickname, join, back);
		
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
