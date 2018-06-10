package application.views;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import application.controllers.GameController;
import application.domain.ColorBlindModes;
import application.domain.Game;
import application.domain.GameObserver;
import application.domain.MoveType;
import application.domain.Player;
import application.util.AlertDialog;
import javafx.geometry.Pos;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * Game view contains the entire visualisation of the game after the lobby. It contains multiple sub-views as well 
 * @author Sanchez
 *
 */
public class GameView implements UIComponent, Disableable, GameObserver  {
	
	public final static int cardSizeX = 130, cardSizeY = 180; 
	public final static int tokenSizeRadius = 45;
	
	public final static int opponentsSpacing = 0;
	
	public static List<ColorChangeable> colorBlindViews;
	
	private Game game;
	private GameController gameController;	
	
	private BorderPane root;
	
	private Button btnReserveCard;
	private Button btnPurchaseCard;
	private Button btnTakeTwoTokens;
	private Button btnTakeThreeTokens;
	private Button btnResetTurn;
	private Button btnEndTurn;
	
	private List<Button> moveButtons;
	

	private Pane buttons;
	private Pane opponents;
	
	private PlayingFieldView playingField;
	private PlayerView player;
	
	/**
	 * Creates a new game view
	 * @param game
	 * @param gameController
	 */
	public GameView(Game game, GameController gameController) {
		this.game = game;
		this.gameController = gameController;
		
		colorBlindViews = new ArrayList<>();
		
		this.buildUI();
		
		try {
			game.addObserver(this);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//TODO	MAKE THIs??
	public void modelChanged(Game game)
	{
		
	}
	/**
	 * 
	 * @param mode, change the colour pallet based on the type of colourblindness
	 */
	public static void changeColorBlindMode(ColorBlindModes mode) {
		Iterator<ColorChangeable> i = colorBlindViews.iterator();
		while (i.hasNext()) {
			ColorChangeable view = i.next(); 
			if(view == null) {
				i.remove();
			}
			else {
				view.updateView(mode);
			}
		}
	}
	
	/**
	 * Build the actual game view
	 * @return void
	 */
	private void buildUI()
	{
		root = new BorderPane();
		root.getStyleClass().add("game-view");
		
		//HBox topLayout = new HBox(10);
		try {
			playingField = buildPlayingField();
			buttons = buildButtons();
			opponents = buildOpponents();
			player = buildPlayer();
		} catch (RemoteException e) {
			new AlertDialog(AlertType.ERROR, "A network error has occured while setting up one of the views.").show();
			e.printStackTrace();
		}
		
		VBox center = new VBox(playingField.asPane(), buttons);
		VBox.setVgrow(playingField.asPane(), Priority.ALWAYS);
		
		//root.setTop(topLayout);
		root.setCenter(center);
		root.setLeft(opponents);
		root.setBottom(player.asPane());
	}
	
	/**
	 * Builds the playing field view (section with all the cards, nobles, tokens, etc.)
	 * @throws RemoteException
	 * @return PlayingFieldView
	 */
	private PlayingFieldView buildPlayingField() throws RemoteException
	{
		return new PlayingFieldView(game.getPlayingField(), gameController);
	}
	
	/**
	 * Builds the buttons the players uses to select their action for a turn
	 * @return HBox
	 */
	private HBox buildButtons()
	{
		HBox buttons = new HBox(20);
		buttons.getStyleClass().add("buttons-view");
		buttons.setAlignment(Pos.CENTER);
		
		this.moveButtons = new ArrayList<>();
		
		// Make separate button class
		btnReserveCard = new Button("Reserve Card");
		btnReserveCard.getStyleClass().add("move-button");
		btnReserveCard.setOnAction(e -> {
			try {
				game.getPlayingField().getTempHand().setMoveType(MoveType.RESERVE_CARD);
				gameController.reserveCard();
				this.disableMoves(true);
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		btnPurchaseCard = new Button("Purchase Card");
		btnPurchaseCard.getStyleClass().add("move-button");
		btnPurchaseCard.setOnAction(e -> {
			// POC -> belongs in GameController
			try {
				game.getPlayingField().getTempHand().setMoveType(MoveType.PURCHASE_CARD);
				gameController.purchaseCard();
				this.disableMoves(true);
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		btnTakeTwoTokens = new Button("Take Two Tokens");
		btnTakeTwoTokens.getStyleClass().add("move-button");
		btnTakeTwoTokens.setOnAction(e ->{
			try {
				
				game.getPlayingField().getTempHand().setMoveType(MoveType.TAKE_TWO_TOKENS);
				gameController.findSelectableTokens();
				this.disableMoves(true);
			} catch (RemoteException e1) {
				e1.printStackTrace();
				new AlertDialog(AlertType.ERROR, "Dit hoor je niet te zien").show();
			}
		});
		
		btnTakeThreeTokens = new Button("Take Three Tokens");
		btnTakeThreeTokens.getStyleClass().add("move-button");		
		btnTakeThreeTokens.setOnAction(e ->{
			try {
				game.getPlayingField().getTempHand().setMoveType(MoveType.TAKE_THREE_TOKENS);
				gameController.findSelectableTokens();
				this.disableMoves(true);
			} catch (RemoteException e1) {
				e1.printStackTrace();
				new AlertDialog(AlertType.ERROR, "Dit hoor je niet te zien").show();
			}
		});
		
		btnResetTurn = new Button("Reset Turn");
		btnResetTurn.getStyleClass().add("move-button");
		btnResetTurn.setOnAction(e ->{
			try {
				gameController.resetTurn();
				this.disableMoves(false);
				this.disableEndTurn(true);
				//gameController.debugNextTurn();
			} catch (RemoteException e1) {
				e1.printStackTrace();
				new AlertDialog(AlertType.ERROR, "Er is iets goed fout gegaan jonge").show();
			}
		});
		
		btnEndTurn = new Button("End Turn");
		btnEndTurn.getStyleClass().add("move-button");
		btnEndTurn.setDisable(true);
		btnEndTurn.setOnAction(e -> {
				try {
					gameController.endTurn();
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		});

		this.moveButtons.addAll(Arrays.asList(btnReserveCard, btnPurchaseCard, btnTakeTwoTokens, btnTakeThreeTokens, btnResetTurn, btnEndTurn));
		buttons.getChildren().addAll(this.moveButtons);
		
		return buttons;
	}
	
	/**
	 * Build the box with the player information of the opponents.
	 * @throws RemoteException
	 * @return Pane
	 */
	private Pane buildOpponents() throws RemoteException
	{
		VBox opponentsRows = new VBox(opponentsSpacing);

		opponentsRows.setAlignment(Pos.CENTER_LEFT);
		opponentsRows.getStyleClass().add("opponents");
		opponentsRows.setPrefWidth(400);
		
		List<Player> players = game.getPlayers();
		for(Player player : players)
		{
			if(player.equals(players.get(0))) continue; // For now we'll assume that the first player in the list is 'our' player
			
			Pane opponentView = new OpponentView(player).asPane();
			opponentsRows.getChildren().add(opponentView);
		}
		
		return opponentsRows;
	}
	
	/**
	 * Builds the player view. This is the view with the player's own information
	 * @throws RemoteException
	 * @return PlayerView
	 */
	private PlayerView buildPlayer() throws RemoteException
	{
		return new PlayerView(game.getPlayers().get(0), gameController);
	}
	

	public Pane asPane() {
		return root;
	}

	public void setDisabled(boolean disabled) throws RemoteException {

		for(Button btn : this.moveButtons)
		{
			btn.setDisable(disabled);
		}
		//this.btnResetTurn.setDisable(false);
		this.btnEndTurn.setDisable(true);
		
		this.playingField.setDisabled(disabled);
		this.player.setDisabled(disabled);
	}
	
	private void disableMoves(boolean disabled) {
		for(Button btn : this.moveButtons)
		{
			btn.setDisable(disabled);
		}
		this.btnResetTurn.setDisable(false);
	}
	@Override
	public void disableEndTurn(boolean disabled) throws RemoteException {
		this.btnEndTurn.setDisable(disabled);
	}
}
