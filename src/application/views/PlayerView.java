package application.views;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import application.controllers.GameController;
import application.domain.Card;
import application.domain.ColorBlindModes;
import application.domain.Gem;
import application.domain.Player;
import application.domain.PlayerObserver;
import application.domain.TokenList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Separator;
import javafx.scene.control.ToggleGroup;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
/**
 * 
 * @author Sanchez
 *
 */
public class PlayerView implements UIComponent, PlayerObserver {

	// Radio button toggle group
	final private ToggleGroup group = new ToggleGroup();
	
	private GameController gamecontroller;
	
	private Pane root;
	
	private GridPane tokensGrid;
	private HBox ownedCards;
	
	private Label lblPrestigeValue;
	
	public PlayerView(Player player) throws RemoteException {		
		
		this.buildUI();
		
		player.addObserver(this);
	}
	
	private void buildUI() throws RemoteException
	{
		root = new HBox(25);
		root.setPadding(new Insets(15, 0, 15, 25));
		root.getStyleClass().add("player");
		
		// Player prestige
		lblPrestigeValue = new Label();
		lblPrestigeValue.setAlignment(Pos.CENTER);
		lblPrestigeValue.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 50));
		lblPrestigeValue.getStyleClass().add("prestige");
		
		StackPane panePrestige = new StackPane(lblPrestigeValue);
		
		tokensGrid = new GridPane();
		tokensGrid.setVgap(2);
		tokensGrid.setHgap(15);
		
		ownedCards = new HBox(10);
		
		VBox accessibility = buildAccessibilityMenu();
		
		root.getChildren().addAll(panePrestige, sep(), tokensGrid, sep(), ownedCards, accessibility);
	}
	
	public void modelChanged(Player player) throws RemoteException
	{
		lblPrestigeValue.setText(String.valueOf(player.getPrestige()));
		
		this.updatePlayerTokens(player.getTokensGemCount());
	}
	
	private VBox buildAccessibilityMenu() 
	{
		VBox accessibility = new VBox(10);
		accessibility.setPadding(new Insets(0, 22, 0, 0));
		accessibility.setAlignment(Pos.BOTTOM_RIGHT);
		
		HBox btnManual 					= buildManualButton();
		RadioButton rbAccNormal 		= new RadioButton();
		RadioButton rbAccDeuteranopia 	= new RadioButton();
		RadioButton rbAccProtanopia 	= new RadioButton();
		RadioButton rbAccTritanopia 	= new RadioButton();
		
		btnManual.setPadding(new Insets(0, -8, 0, 0));
		
		rbAccNormal.setOnAction(e -> {
			System.out.println("Switching to normal accessiblity mode");
			GameView.changeColorBlindMode(ColorBlindModes.NORMAL);
		});
		
		rbAccDeuteranopia.setOnAction(e -> {
			System.out.println("Switching to Deuteranopia accessiblity mode");
			GameView.changeColorBlindMode(ColorBlindModes.DEUTERANOPIA);
		});
		
		rbAccProtanopia.setOnAction(e -> {
			System.out.println("Switching to Protanopia accessiblity mode");
			GameView.changeColorBlindMode(ColorBlindModes.PROTANOPIA);
		});
		
		rbAccTritanopia.setOnAction(e -> {
			System.out.println("Switching to Tritanopia accessiblity mode");
			GameView.changeColorBlindMode(ColorBlindModes.TRITANOPIA);
		});
		
		rbAccNormal.setToggleGroup(group);
		rbAccDeuteranopia.setToggleGroup(group);
		rbAccProtanopia.setToggleGroup(group);
		rbAccTritanopia.setToggleGroup(group);
		
		rbAccNormal.setSelected(true);
		
		accessibility.getChildren().addAll(rbAccNormal, rbAccDeuteranopia, rbAccProtanopia, rbAccTritanopia, btnManual);
		
		HBox.setHgrow(accessibility, Priority.ALWAYS);
		
		return accessibility;
	}
	
	private VBox createCardDisplay(ArrayList<Card> cards, int count, int sizeX, int sizeY) {
		int offset = 0;
		
		StackPane cardStack = new StackPane();
		
		for(Card card : cards) {
			CardView cardView = new FrontCardView(card, sizeX, sizeY);
			cardView.asPane().setTranslateY(offset);
			offset = offset + 20;
			cardStack.getChildren().add(cardView.asPane());
		}
		
		Label cardCountLabel = new Label(String.valueOf(count));
		cardCountLabel.setAlignment(Pos.CENTER);
		cardCountLabel.getStyleClass().add("card-count");
		cardCountLabel.setFont(Font.font(25));
		
		VBox ownedCardDisplay = new VBox(10, cardCountLabel, cardStack);
		
		return ownedCardDisplay;
	}
	
	private void updatePlayerCards(ArrayList<Card> cards) throws RemoteException {
		ownedCards.getChildren().clear();
		
		ArrayList<Card> diamondCards = new ArrayList<Card>();
		ArrayList<Card> sapphireCards = new ArrayList<Card>();
		ArrayList<Card> emeraldCards = new ArrayList<Card>();
		ArrayList<Card> rubyCards = new ArrayList<Card>();
		ArrayList<Card> onyxCards = new ArrayList<Card>();
		
		for(Card c : cards) {
			switch(c.getBonusGem()) {
				case DIAMOND:
					diamondCards.add(c);
					break;
				case SAPPHIRE:
					sapphireCards.add(c);
					break;
				case EMERALD:
					emeraldCards.add(c);
					break;
				case RUBY:
					rubyCards.add(c);
					break;
				case ONYX:
					onyxCards.add(c);
					break;
				default:
					break;
			}
		}
		
		VBox d = createCardDisplay(diamondCards, diamondCards.size(), 100, 80);
		VBox s = createCardDisplay(sapphireCards, sapphireCards.size(), 100, 80);
		VBox e = createCardDisplay(emeraldCards, emeraldCards.size(), 100, 80);
		VBox r = createCardDisplay(rubyCards, rubyCards.size(), 100, 80);
		VBox o = createCardDisplay(onyxCards, onyxCards.size(), 100, 80);
		
		ownedCards.getChildren().addAll(d, s, e, r, o);
	}
	
	private void updatePlayerTokens(Map<Gem, Integer> gemsCount) throws RemoteException
	{
		tokensGrid.getChildren().clear();
		
		int col = 0, row = 0;	
		for(Map.Entry<Gem, Integer> entry : gemsCount.entrySet())
		{	
			HBox tokenGemCountDisplay = createTokenGemCountDisplay(entry.getKey(), entry.getValue(), GameView.tokenSizeRadius / 2);
			tokensGrid.add(tokenGemCountDisplay, col % 2, row % 3);
			col++;
			row++;
		}
	}
	
	
	private HBox createTokenGemCountDisplay(Gem gemType, int count, int radius)
	{
		TokenView tokenView = new TokenView(gemType, radius);
        
        Label tokenCountLabel = new Label(String.valueOf(count));
        tokenCountLabel.setAlignment(Pos.CENTER);
        tokenCountLabel.getStyleClass().add("token-count");	
        tokenCountLabel.setFont(Font.font(radius * 2));
        
		HBox tokenRow = new HBox(10, tokenView.asPane(), tokenCountLabel);
		tokenRow.setAlignment(Pos.CENTER);
		
		return tokenRow;
	}
	
	private HBox buildManualButton() 
	{
		HBox manualContainer = new HBox();
		Button manualButton = new Button("?");
		
		manualButton.getStyleClass().addAll("button", "manual-button-game");
		manualButton.setOnAction(e -> new ManualWindowView());
		
		manualContainer.getChildren().add(manualButton);
		manualContainer.setAlignment(Pos.TOP_RIGHT);
		
		return manualContainer;
	}
	
	private Separator sep() {
		Separator sep = new Separator();
		sep.setOrientation(Orientation.VERTICAL);
		sep.setValignment(VPos.CENTER);
		//sep.setPrefHeight(80);
		
		return sep;
	}

	public Pane asPane() {
		return root;
	}

}