package application.views;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import application.StageManager;
import application.controllers.GameController;
import application.controllers.MenuController;
import application.domain.Game;
import application.domain.PlayerImpl;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainMenuView implements UIComponent  {
	
//	private Home home;
	private MenuController menuController;
	
	private BorderPane root;
	
	private Pane manualButton;
	
	private Button btnJoinLobby;
	private Button btnPreviousGame;
	private Button btnNewGame;
	
	private TextField nickname;
	private TextField hostIp;
	
	// POC var - see opt 2. @ this.modelChanged() 
	//private List<UIComponent> comps = new ArrayList<>();

	public MainMenuView(MenuController menuController) {
//		this.home = home;
		this.menuController = menuController;
		
		// home.addObserver(this); // Causes modelChanged() to be called upon initialization, before the scene is even added to the primaryStage.
		
		this.buildMainPanel();
	}
	
	// POC
	public void modelChanged()
	{
		/*
		 * Big question of what to do here.
		 * 1. We can either re-build the ENTIRE UI (easiest, but very inefficient) -> means calling this.buildUI() and switching scene's root node
		 * 2. [No] Or we can update either the ENTIRE UI (harder, but more efficient) -> means looping through all childviews and triggering modelChanged [THIS IS NOT EASILY POSSIBLE]  
		 * 3. Or we should do neither and instead have ALL "subdomain" objects implement the observer pattern and have them ONLY notify THEIR listeners. (shitload of classes, but most efficient)
		 */
		System.out.println("Updating home view");
		this.buildMainPanel(); // Rebuild entire UI for now
		StageManager.getInstance().switchScene(root);
	}
	
	private HBox buildManualButton() 
	{
		HBox manualContainer = new HBox();
		Button manualButton = new Button("?");
		
		manualButton.getStyleClass().addAll("button", "manual-button");
		manualButton.setOnAction(e -> new Manual());
		
		manualContainer.getChildren().add(manualButton);
		manualContainer.setAlignment(Pos.TOP_RIGHT);
		
		return manualContainer;
	}
	
	private BorderPane buildJoinPanel()
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
				StageManager.getInstance().showLobbyScreen(hostIp.getText(), nickname.getText());
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (NotBoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		back.setOnAction(e -> StageManager.getInstance().switchScene(buildMainPanel()));
		
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
	
	private BorderPane buildMainPanel()
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
		
		btnJoinLobby.setOnAction(e -> StageManager.getInstance().switchScene(buildJoinPanel()));
		btnPreviousGame.setOnAction(e -> StageManager.getInstance().switchScene(buildHostPreviousGamePanel()));
		btnNewGame.setOnAction(e -> StageManager.getInstance().switchScene(buildHostNewGamePanel()));
		
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
	

	private BorderPane buildHostNewGamePanel()
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
				StageManager.getInstance().hostNewGameLobby(nickname.getText());
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		back.setOnAction(e -> StageManager.getInstance().switchScene(buildMainPanel()));
		
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



	private BorderPane buildHostPreviousGamePanel()
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
				StageManager.getInstance().hostPreviousGameLobby(nickname.getText());
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		back.setOnAction(e -> StageManager.getInstance().switchScene(buildMainPanel()));
		
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
