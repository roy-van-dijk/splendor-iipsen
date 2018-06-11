package application.views;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import application.StageManager;
import application.controllers.LobbyController;
import application.domain.Lobby;
import application.domain.LobbyImpl.LobbyStates;
import application.domain.LobbyObserver;
import application.domain.Player;
import application.domain.PlayerSlot;
import application.util.AlertDialog;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

/**
 * The Class LobbyView.
 *
 * @author Roy
 */
public class LobbyView extends UnicastRemoteObject implements UIComponent, LobbyObserver  {

	private static final long serialVersionUID = 1L;
	
	private static final int gridChildWidth = 250;
	private static final int gridGap = 5;
	
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
	
	/**
	 * Instantiates a new lobby view.
	 *
	 * @param lobby
	 * @param lobbyController
	 * @throws RemoteException
	 */
	public LobbyView(Lobby lobby, LobbyController lobbyController) throws RemoteException {
		this.lobbyController = lobbyController;
		
		this.buildUI();
	}

	/**
	 * update the lobby view.
	 *
	 * @param lobby
	 * @throws RemoteException
	 */
	public void modelChanged(Lobby lobby) throws RemoteException {
		// Avoid throwing IllegalStateException by running from a non-JavaFX thread.
		Platform.runLater(
		  () -> {
			  try {
				  updateUI(lobby);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		  });	
	}
	
	/**
	 * Leave lobby and return the player to showMainMenu.
	 */
	private void leaveLobby()
	{
		StageManager.getInstance().showMainMenu();
	}

	
	/* (non-Javadoc)
	 * @see application.domain.LobbyObserver#disconnect(application.domain.LobbyImpl.LobbyStates)
	 */
	public void disconnect(LobbyStates lobbyState) throws RemoteException
	{
		Platform.runLater(() -> {
			StageManager.getInstance().showMainMenu();
			
			AlertDialog dialog;
			if(lobbyState == LobbyStates.CLOSING)
			{
				 dialog = new AlertDialog(AlertType.INFORMATION, "Server has been terminated. You have been disconnected.");
			} else {
				dialog = new AlertDialog(AlertType.INFORMATION, "You have been disconnected from the server.");
			}
			dialog.setHeaderText("");
			dialog.show();
		});
	}
	
	
	/**
	 * Update unassigned players.
	 *
	 * @param maxPlayers
	 * @param unassignedPlayers
	 * @throws RemoteException
	 */
	private void updateUnassignedPlayers(int maxPlayers, List<Player> unassignedPlayers) throws RemoteException
	{
		for(int i = 0; i < maxPlayers; i++) 
		{
			String displayName = "Empty slot...";
			Label label = new Label();
			label.getStyleClass().add("lobby-slot");
			label.setOnMouseClicked(e -> {
				try {
					lobbyController.unassignPlayer(this);
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			});
			
			if(i < unassignedPlayers.size()) 
			{
				displayName = unassignedPlayers.get(i).getName();
				label.getStyleClass().add("active");
			}
			label.setText(displayName);
			label.setPrefWidth(gridChildWidth);
			gpane.add(label, 0, i + 2);
		}
	}
	
	/**
	 * Update assignable slots.
	 *
	 * @param slots
	 * @throws RemoteException
	 */
	private void updateAssignableSlots(List<PlayerSlot> slots) throws RemoteException
	{
		for(int slotIdx = 0; slotIdx < slots.size(); slotIdx++) 
		{
			PlayerSlot slot = slots.get(slotIdx);
			
			String displayName = String.format("Player %d - empty", slotIdx);
			Label label = new Label();
			label.getStyleClass().add("lobby-slot");
			label.setOnMouseClicked(e -> {
				try {
					lobbyController.selectSlot(this, slot);
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			});
			if(slot.getPreviousPlayer() != null)
			{
				displayName = String.format("(Previously %s)", slot.getPreviousPlayer().getName());
			}
			if(slot.getCurrentPlayer() != null) 
			{
				displayName = slot.getCurrentPlayer().getName();
				label.getStyleClass().add("active");
				
				if(slot.isReady())
				{
					label.getStyleClass().add("checked");
				} else {
					label.getStyleClass().add("unchecked");
				}
			}

			label.setText(displayName);
			label.setPrefWidth(gridChildWidth);
			gpane.add(label, 1, slotIdx + 2);
		}
	}
	
	/**
	 * Update UI.
	 *
	 * @param lobby
	 * @throws RemoteException
	 */
	private void updateUI(Lobby lobby) throws RemoteException {
		LobbyStates lobbyState = lobby.getLobbyState();
		if(lobbyState == LobbyStates.CLOSING) {
			System.out.println("[DEBUG] LobbyView::modelChanged()::LobbyState changed to CLOSING");
			this.leaveLobby();
		} 
		else if(lobbyState == LobbyStates.STARTED_GAME) {
			System.out.println("[DEBUG] LobbyView::modelChanged()::LobbyState changed to STARTED_GAME");
			this.lobbyController.showGameScreen(this);			
		} 
		else if(lobbyState == LobbyStates.WAITING) {
			int maxPlayers = lobby.getMaxPlayers();
			String hostIP = lobby.getHostIP();
			
			lblLobbyIP.setText(String.format("Lobby IP: %s", hostIP));
			
			List<Player> unassignedPlayers = lobby.getUnassignedPlayers();
			this.updateUnassignedPlayers(maxPlayers, unassignedPlayers);
			
			List<PlayerSlot> assignableSlots = lobby.getAssignableSlots();
			this.updateAssignableSlots(assignableSlots);
			
			if(lobby.isReady(this)) {
				this.btnReady.setDisable(true);
			}
			else {
				this.btnReady.setDisable(!lobby.isAssigned(this));
			}
			
		}
	}
	
	/**
	 * build up the UI on creating the lobby.
	 */
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
	
	/**
	 * button to open manual.
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
	 * Builds the pane of the Lobby.
	 *
	 * @return Pane
	 */
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
		btnBack.setCancelButton(true);
		btnBack.setPrefWidth(gridChildWidth);
		btnBack.setOnAction(e -> {
			try {
				lobbyController.leaveLobby(this);
			} catch (RemoteException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		});
		
		btnReady = new Button("Ready");
		btnReady.setDefaultButton(true);
		btnReady.setPrefWidth(gridChildWidth);
		btnReady.setOnAction(e -> {
			try {
				lobbyController.readyUp(this);
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}); // TODO: replace with call to LobbyController ready()
		
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

	/* (non-Javadoc)
	 * @see application.views.UIComponent#asPane()
	 */
	public Pane asPane() {
		return root;
	}

}
