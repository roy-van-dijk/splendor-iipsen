package application.views;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import application.StageManager;
import application.controllers.GameController;
import application.controllers.ReturnTokenController;
import application.controllers.ReturnTokenControllerImpl;
import application.domain.CardLevel;
import application.domain.ColorBlindModes;
import application.domain.Game;
import application.domain.Player;
import application.domain.PlayerImpl;
import application.domain.ReturnTokens;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
public class GameView implements UIComponent  {
	
	public final static int cardSizeX = 30, cardSizeY = 80; 
	public final static int tokenSizeRadius = 15;
	
	public final static int opponentsSpacing = 20;
	
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
	
	private Pane playingField;
	private Pane buttons;
	private Pane opponents;
	private Pane player;
	
	// POC var - see opt 2. @ this.modelChanged() 
	//private List<UIComponent> comps = new ArrayList<>();

	public GameView(Game game, GameController gameController) {
		this.game = game;
		this.gameController = gameController;
		
		colorBlindViews = new ArrayList<>();
		
		this.buildUI();
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
	
//		for(ColorChangeable colorBlindView : colorBlindViews) {
//			colorBlindView.updateView(mode);
//		}
	}
	
	// POC
	public void modelChanged(Game game)
	{
		/*
		 * Big question of what to do here.
		 * 1. We can either re-build the ENTIRE UI (easiest, but very inefficient) -> means calling this.buildUI() and switching scene's root node
		 * 2. [No] Or we can update either the ENTIRE UI (harder, but more efficient) -> means looping through all childviews and triggering modelChanged [THIS IS NOT EASILY POSSIBLE]  
		 * 3. Or we should do neither and instead have ALL "subdomain" objects implement the observer pattern and have them ONLY notify THEIR listeners. (shitload of classes, but most efficient)
		 */
		System.out.println("Updating game view");
		this.buildUI(); // Rebuild entire UI for now
		StageManager.getInstance().switchScene(root);
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		VBox center = new VBox(playingField, buttons);
		VBox.setVgrow(playingField, Priority.ALWAYS);
		
		//gameLayout.setTop(topLayout);
		root.setCenter(center);
		root.setLeft(opponents);
		root.setBottom(player);

		//root.setPadding(new Insets(0));
	}
	
	
	
	private Pane buildPlayingField() throws RemoteException
	{
		Pane playingField = new PlayingFieldView(game.getPlayingField(), gameController).asPane();
		return playingField;
	}
	
	private HBox buildButtons()
	{
		HBox buttons = new HBox(20);
		buttons.getStyleClass().add("buttons-view");
		buttons.setAlignment(Pos.CENTER);
		
		
		// Make separate button class
		btnReserveCard = new Button("Reserve Card");
		btnReserveCard.getStyleClass().addAll("button", "move-button");
		btnReserveCard.setOnAction(e -> {
			try {
				gameController.reserveCard();
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		//btnReserveCard.setOnAction(
				
				//);
		btnPurchaseCard = new Button("Purchase Card");
		btnPurchaseCard.getStyleClass().add("move-button");
		btnPurchaseCard.setOnAction(e -> {
			// POC -> probably belongs in EndTurnController when that's done.
			
			try {
				ReturnTokens model = new ReturnTokens(game.getPlayingField(), game.getCurrentPlayer());
				ReturnTokenController controller = new ReturnTokenControllerImpl(model);
				ReturnTokensView view = new ReturnTokensView(model, controller);
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		btnTakeTwoTokens = new Button("Take Two Tokens");
		btnTakeTwoTokens.getStyleClass().add("move-button");
		btnTakeThreeTokens = new Button("Take Three Tokens");
		btnTakeThreeTokens.getStyleClass().add("move-button");
		btnResetTurn = new Button("Reset Turn");
		btnResetTurn.getStyleClass().add("move-button");
		btnEndTurn = new Button("End Turn");
		btnEndTurn.getStyleClass().add("move-button");
		btnEndTurn.setOnAction(e -> {
			try {
				gameController.endTurn();
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		btnEndTurn.getStyleClass().add("disabled");

		buttons.getChildren().addAll(btnReserveCard, btnPurchaseCard, btnTakeTwoTokens, btnTakeThreeTokens, btnResetTurn, btnEndTurn);
		return buttons;
	}
	
	private Pane buildOpponents() throws RemoteException
	{
		VBox opponentsRows = new VBox(opponentsSpacing);

		opponentsRows.setAlignment(Pos.CENTER_LEFT);
		opponentsRows.getStyleClass().add("opponents");
		opponentsRows.setPrefWidth(600);
		
		List<Player> players = game.getPlayers();
		for(Player player : players)
		{
			if(player.equals(players.get(0))) continue; // For now we'll assume that the first player in the list is 'our' player
			
			Pane opponentView = new OpponentView(player).asPane();
			opponentsRows.getChildren().add(opponentView);
		}
		
		return opponentsRows;
	}
	
	private Pane buildPlayer() throws RemoteException
	{
		Pane player = new PlayerView(game.getPlayers().get(0)).asPane();
		return player;
	}
	

	public Pane asPane() {
		return root;
	}
	
}
