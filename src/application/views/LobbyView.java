package application.views;

import application.StageManager;
import application.controllers.LobbyController;
import application.controllers.MenuController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class LobbyView implements UIComponent  {
	
//	private Home home;
	private LobbyController lobbyController;
	
	
	private BorderPane root;
	
	private Pane pane;
	
	private HBox hbox;
	
	
	private Button btnReady;
	
	private Label lblUnassPlayers;
	private Label lblAssPlayers;
	
	private Label lblLobbyIp;
	private Label lblSpot1;
	private Label lblSpot2;
	private Label lblSpot3;
	private Label lblSpot4;
	private Label lblPlayer1;
	private Label lblPlayer2;
	private Label lblPlayer3;
	private Label lblPlayer4;
	
	
	// POC var - see opt 2. @ this.modelChanged() 
	//private List<UIComponent> comps = new ArrayList<>();

	public LobbyView(LobbyController lobbyController) {
//		this.home = home;
		this.lobbyController = lobbyController;
		
		
		// home.addObserver(this); // Causes modelChanged() to be called upon initialization, before the scene is even added to the primaryStage.
		
		this.buildUI();
		
	}
	
	
	// POC
	public void modelChanged()
	{
//		lblPlayer1.setText(modeldiemeegegevenis.getPlayers().get(0).getName());
	}
	
	
	private void buildUI()
	{	
		root = new BorderPane();
		pane = buildPane();
		
		root.setRight(pane);
		root.getStyleClass().add("home-view");
		root.setPadding(new Insets(0));
	}
	
	private Pane buildPane()
	{
		VBox leftBox = new VBox();
		VBox rightBox = new VBox();
		hbox = new HBox();
		
		lblUnassPlayers = new Label("Unassigned Players");
		lblAssPlayers = new Label("Assigned Players");
			
		lblLobbyIp = new Label("Lobby ip: 132.123.123.123");
		lblSpot1 = new Label("Empty spot...");
		lblSpot2 = new Label("Empty spot...");
		lblSpot3 = new Label("Empty spot...");
		lblSpot4 = new Label("Empty spot...");
		lblPlayer1 = new Label("Player 1 - empty");
		lblPlayer2 = new Label("Player 2 - empty");
		lblPlayer3 = new Label("Player 3 - empty");
		lblPlayer4 = new Label("Player 4 - empty");
		
		btnReady = new Button("Ready");
		
		
		leftBox.getChildren().addAll(lblLobbyIp, lblUnassPlayers, lblSpot1, lblSpot2, lblSpot3, lblSpot4);
		rightBox.getChildren().addAll(lblAssPlayers, lblPlayer1, lblPlayer2, lblPlayer3, lblPlayer4, btnReady);
		
		
		
//		btnJoinLobby.setOnAction(e -> menuController.joinLobby() );
//		
//		btnPreviousGame.setOnAction(e -> menuController.hostPreviousGame() );
//		
//		btnNewGame.setOnAction(e -> menuController.hostNewGame() ); 
		
		hbox.setAlignment(Pos.CENTER_LEFT); 
		hbox.setSpacing(10);
		hbox.setTranslateX(-250);
		hbox.setTranslateY(100);
		
		hbox.getChildren().addAll(leftBox, rightBox);
		return hbox;
	}	

	public Pane asPane() {
		return root;
	}
}
