package application.views;

import java.rmi.RemoteException;
import java.util.List;

import application.StageManager;
import application.controllers.LobbyController;
import application.controllers.MenuController;
import application.domain.Lobby;
import application.domain.LobbyObserver;
import application.domain.Player;
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
/**
 * 
 * @author Roy
 *
 */
public class LobbyView implements UIComponent, LobbyObserver  {
	
	private final static int DEFAULT_MAX_PLAYERS = 4;
	
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
	
	private Label[] lblsAssignedPlayerNames;
	private Label[] lblsUnassignedPlayerNames;

	public LobbyView(Lobby lobby, LobbyController lobbyController) {
		this.lobbyController = lobbyController;
		
		this.buildUI();
		
		try {
			lobby.addObserver(this);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void modelChanged(Lobby lobby) throws RemoteException {
		int maxPlayers = lobby.getMaxPlayers();
		
		lblsAssignedPlayerNames = new Label[maxPlayers];
		lblsUnassignedPlayerNames = new Label[maxPlayers];
		
		
		// TODO: update ready status of each player;
		List<Player> assignedPlayers = lobby.getAssignedPlayers();
		for(int i = 0; i < assignedPlayers.size(); i++) 
		{
			Player player = assignedPlayers.get(i);
			lblsAssignedPlayerNames[i].setText(player.getName());
		}
		
		List<Player> unassignedPlayers = lobby.getUnassignedPlayers();
		for(int i = 0; i < unassignedPlayers.size(); i++) 
		{
			Player player = unassignedPlayers.get(i);
			lblsUnassignedPlayerNames[i].setText(player.getName());
		}
		
		
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
			
		lblLobbyIp 	= new Label("Lobby ip: 132.123.123.123");
		
		lblsAssignedPlayerNames = new Label[DEFAULT_MAX_PLAYERS];
		lblsUnassignedPlayerNames = new Label[DEFAULT_MAX_PLAYERS];

		btnReady = new Button("Ready");		
		
		btnReady.setOnAction(e -> StageManager.getInstance().showGameScreen());
		
		gpane.getStyleClass().add("lobby-grid");
		gpane.setVgap(5); 
		gpane.setHgap(5);
		
		gpane.add(lblLobbyIp, 0, 0, 2, 1);
		
		gpane.add(lblUnassPlayers, 0, 1);
		for(int i = 0; i < lblsUnassignedPlayerNames.length; i++) {
			lblsUnassignedPlayerNames[i] = new Label("Empty spot...");
			gpane.add(lblsUnassignedPlayerNames[i], 0, i + 2);
		}
		
		gpane.add(lblAssPlayers, 1, 1);
		for(int i = 0; i < lblsAssignedPlayerNames.length; i++) {
			lblsAssignedPlayerNames[i] = new Label(String.format("Player %d - empty", i));
			gpane.add(lblsAssignedPlayerNames[i], 1, i + 2);
		}
		
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
