package application.views;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import application.StageManager;
import application.controllers.LobbyController;
import application.controllers.MainMenuController;
import application.domain.Lobby;
import application.domain.LobbyObserver;
import application.domain.Player;
import javafx.application.Platform;
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
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
/**
 * 
 * @author Roy
 *
 */
public class LobbyView extends UnicastRemoteObject implements UIComponent, LobbyObserver  {

	private static final long serialVersionUID = 1L;
	
	private LobbyController lobbyController;
	
	private BorderPane root;
	
	private Pane pane;
	private GridPane gpane;
	
	private HBox hbox;
	private HBox manualButton;
	
	private Button btnBack;
	private Button btnReady;
	
	private Label lblUnassPlayers;
	private Label lblAssPlayers;
	private Label lblLobbyIP;
	
	private final static int gridChildWidth = 250;
	private final static int gridGap = 5;
	

	public LobbyView(Lobby lobby, LobbyController lobbyController) throws RemoteException {
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
		// Avoid throwing IllegalStateException by running from a non-JavaFX thread.
		
		Platform.runLater(
		  () -> {
			  try {
				  updateUI(lobby);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  });
	

	}
		  
	private void updateUI(Lobby lobby) throws RemoteException {
		int maxPlayers = lobby.getMaxPlayers();
		String hostIP = lobby.getHostIP();
		
		lblLobbyIP.setText(String.format("Lobby IP: %s", hostIP));
		
		// TODO: update ready status of each player;
		
		List<Player> unassignedPlayers = lobby.getUnassignedPlayers();
		for(int i = 0; i < maxPlayers; i++) 
		{
			String displayName = "Empty slot...";
			Label label = new Label();
			
			if(i < unassignedPlayers.size()) 
			{
				displayName = unassignedPlayers.get(i).getName();
				label.getStyleClass().add("active");
			}
			label.setText(displayName);
			label.setPrefWidth(gridChildWidth);
			gpane.add(label, 0, i + 2);
		}
		
		List<Player> assignedPlayers = lobby.getAssignedPlayers();
		for(int i = 0; i < maxPlayers; i++) 
		{
			String displayName = String.format("Player %d - empty", i);
			Label label = new Label();
			
			if(i < assignedPlayers.size()) 
			{
				displayName = assignedPlayers.get(i).getName();
				label.getStyleClass().add("active");
				label.getStyleClass().add("unchecked");
				//label.getStyleClass().add("checked");
			}
			label.setText(displayName);
			label.setPrefWidth(gridChildWidth);
			gpane.add(label, 1, i + 2);
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
	
	private HBox buildManualButton() {
		HBox manualContainer = new HBox();
		Button manualButton = new Button("?");
		
		manualButton.getStyleClass().addAll("button", "manual-button");
		manualButton.setOnAction(e -> new ManualWindowView());
		
		manualContainer.getChildren().add(manualButton);
		manualContainer.setAlignment(Pos.TOP_RIGHT);
		
		return manualContainer;
	}
	
	private Pane buildPane() {
		hbox = new HBox();
		gpane = new GridPane();
		
		lblLobbyIP = new Label();
		lblLobbyIP.setAlignment(Pos.CENTER);
		lblLobbyIP.setPrefWidth(gridChildWidth * 2 + gridGap);
		lblLobbyIP.getStyleClass().add("active");
		
		lblUnassPlayers = new Label("Unassigned Players");
		lblUnassPlayers.getStyleClass().add("active");
		lblUnassPlayers.setPrefWidth(gridChildWidth);
		
		lblAssPlayers = new Label("Assigned Players");
		lblAssPlayers.getStyleClass().add("active");
		lblAssPlayers.setPrefWidth(gridChildWidth);
		
		btnBack = new Button("Back");
		btnBack.setPrefWidth(gridChildWidth);
		btnBack.setOnAction(e -> StageManager.getInstance().showMainMenu()); // TODO: replace with call to LobbyController leaveLobby()
		
		btnReady = new Button("Ready");
		btnReady.setPrefWidth(gridChildWidth);
		btnReady.setOnAction(e -> StageManager.getInstance().showGameScreen()); // TODO: replace with call to LobbyController ready()
		
		gpane.getStyleClass().add("lobby-grid");
		gpane.setVgap(gridGap); 
		gpane.setHgap(gridGap);
		
		gpane.add(lblLobbyIP, 0, 0, 2, 1);
		gpane.add(lblUnassPlayers, 0, 1);
		gpane.add(lblAssPlayers, 1, 1);
		gpane.add(btnReady, 1, 6);
		gpane.add(btnBack, 0, 6);
		
		hbox.setAlignment(Pos.CENTER_LEFT);
		hbox.setTranslateX(-250);
		hbox.setTranslateY(400);
		hbox.getChildren().add(gpane);
				
		return hbox;
	}

	public Pane asPane() {
		return root;
	}

}
