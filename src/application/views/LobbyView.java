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
	
	private VBox buttons;
	
	private HBox panes;
	private VBox leftPane;
	private VBox rightPane;
	
	private Button btnReady;
	
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
		Label lblUnassPlayers;
		Label lblAssPlayers;
		
		root = new BorderPane();
		buttons = buildButtons();
		
		root.setRight(buttons);
		root.getStyleClass().add("home-view");
		root.setPadding(new Insets(0));
	}
	
	private VBox buildButtons()
	{
		
		
		VBox buttons = new VBox(3);
		
		btnReady = new Button("Ready");

		
//		btnJoinLobby.setOnAction(e -> menuController.joinLobby() );
//		
//		btnPreviousGame.setOnAction(e -> menuController.hostPreviousGame() );
//		
//		btnNewGame.setOnAction(e -> menuController.hostNewGame() ); 
		
		buttons.setAlignment(Pos.CENTER_LEFT); 
		buttons.setSpacing(10);
		buttons.setTranslateX(-250);
		buttons.setTranslateY(100);
		
		buttons.getChildren().addAll();
		return buttons;
	}	

	public Pane asPane() {
		return root;
	}
}
