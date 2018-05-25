package application.views;

import application.StageManager;
import application.controllers.LobbyController;
import application.controllers.MenuController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class LobbyView implements UIComponent  {
	
//	private Home home;
	private LobbyController lobbyController;
	
	private BorderPane root;
	
	private Pane pane;
	private GridPane gpane;
	
	private HBox hbox;
	private HBox manualButton;
	
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
		manualButton = buildManualButton();
		
//		root.setMargin(hbox, new Insets(200, 0, 0, 0));
		root.setRight(pane);
		root.setTop(manualButton);
		root.getStyleClass().add("home-view");
		root.setPadding(new Insets(0));
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
	
	private Pane buildPane()
	{
		hbox = new HBox();
		gpane = new GridPane();
		
		lblUnassPlayers = new Label("Unassigned Players");
		lblAssPlayers 	= new Label("Assigned Players");
			
		lblLobbyIp 	= new Label("Lobby ip: 132.123.123.123");;
		lblSpot1 	= new Label("Empty spot...");
		lblSpot2 	= new Label("Empty spot...");
		lblSpot3 	= new Label("Empty spot...");
		lblSpot4 	= new Label("Empty spot...");
		lblPlayer1 	= new Label("Player 1 - empty");
		lblPlayer2 	= new Label("Player 2 - empty");
		lblPlayer3 	= new Label("Player 3 - empty");
		lblPlayer4 	= new Label("Player 4 - empty");
		
		btnReady = new Button("Ready");		
		
		btnReady.setOnAction(e -> StageManager.getInstance().showGameScreen());
		
		gpane.getStyleClass().add("lobby-grid");
		gpane.setVgap(5); 
		gpane.setHgap(5);
		
		gpane.add(lblLobbyIp, 0, 0, 2, 1);
		
		gpane.add(lblUnassPlayers, 0, 1);
		gpane.add(lblSpot1, 0, 2);
		gpane.add(lblSpot2, 0, 3);
		gpane.add(lblSpot3, 0, 4);
		gpane.add(lblSpot4, 0, 5);
		
		gpane.add(lblAssPlayers, 1, 1);
		gpane.add(lblPlayer1, 1, 2);
		gpane.add(lblPlayer2, 1, 3);
		gpane.add(lblPlayer3, 1, 4);
		gpane.add(lblPlayer4, 1, 5);
		
		gpane.add(btnReady, 1, 6);
		
//		btnReady.setOnAction(e -> menuController.joinLobby() );
	     
		hbox.setAlignment(Pos.CENTER_LEFT); 
		hbox.setSpacing(10);
		hbox.setTranslateX(-250);
		hbox.setTranslateY(300);
		hbox.getChildren().add(gpane);
		
		return hbox;
	}

	public Pane asPane() {
		return root;
	}
}
