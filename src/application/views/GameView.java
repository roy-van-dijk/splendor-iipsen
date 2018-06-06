package application.views;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import application.StageManager;
import application.controllers.GameController;
import application.controllers.ReturnTokenController;
import application.domain.CardLevel;
import application.domain.ColorBlindModes;
import application.domain.Game;
import application.domain.GameObserver;
import application.domain.MoveType;
import application.domain.Player;
import application.domain.PlayerImpl;
import application.domain.ReturnTokens;
import application.util.AlertDialog;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
/**
 * 
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
	
	public void modelChanged(Game game)
	{
		
	}
	
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
	
	
	
	private PlayingFieldView buildPlayingField() throws RemoteException
	{
		return new PlayingFieldView(game.getPlayingField(), gameController);
	}
	
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
				gameController.reserveCard();
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
				gameController.purchaseCard();
				// TODO Debug code below
				ReturnTokens model = new ReturnTokens(game.getPlayingField(), game.getCurrentPlayer());
				ReturnTokenController controller = new ReturnTokenController(model);
				ReturnTokensView view = new ReturnTokensView(model, controller);
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		btnTakeTwoTokens = new Button("Take Two Tokens");
		btnTakeTwoTokens.getStyleClass().add("move-button");
		btnTakeTwoTokens.setOnAction(e ->{
			try {
				game.getPlayingField().getTurn().setMoveType(MoveType.TAKE_TWO_TOKENS);
				gameController.findSelectableTokens();
			} catch (RemoteException e1) {
				e1.printStackTrace();
				new AlertDialog(AlertType.ERROR, "Dit hoor je niet te zien").show();
			}
		});
		
		btnTakeThreeTokens = new Button("Take Three Tokens");
		btnTakeThreeTokens.getStyleClass().add("move-button");		
		btnTakeThreeTokens.setOnAction(e ->{
			try {
				game.getPlayingField().getTurn().setMoveType(MoveType.TAKE_THREE_TOKENS);
				gameController.findSelectableTokens();
			} catch (RemoteException e1) {
				e1.printStackTrace();
				new AlertDialog(AlertType.ERROR, "Dit hoor je niet te zien").show();
			}
		});
		
		btnResetTurn = new Button("Reset Turn");
		btnResetTurn.getStyleClass().add("move-button");
		btnResetTurn.setOnAction(e ->{
			try {
				gameController.debugNextTurn();
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
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
	
	private PlayerView buildPlayer() throws RemoteException
	{
		return new PlayerView(game.getPlayers().get(0));
	}
	

	public Pane asPane() {
		return root;
	}

	public void setDisabled(boolean disabled) throws RemoteException {

		for(Button btn : this.moveButtons)
		{
			btn.setDisable(disabled);
		}
		this.btnResetTurn.setDisable(false);
		
		this.playingField.setDisabled(disabled);
		this.player.setDisabled(disabled);
	}
	
}
