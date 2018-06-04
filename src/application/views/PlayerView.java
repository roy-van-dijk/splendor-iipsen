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
	
	private Label lblDiamondBonus;
	private Label lblSapphireBonus;
	private Label lblEmeraldBonus;
	private Label lblRubyBonus;
	private Label lblOnyxBonus;
	
	private GridPane tokensGrid;
	
	private Label lblPrestigeValue;
	
	public PlayerView(Player player) {
		//player.addObserver(this);
		
		this.buildUI();
		
		try {
			player.addObserver(this);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void buildUI()
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
		
		
		Separator sep = new Separator();
		sep.setOrientation(Orientation.VERTICAL);
		sep.setValignment(VPos.CENTER);
		//sep.setPrefHeight(80);
		
		VBox accessibility = buildAccessibilityMenu();
		
		try {
			HBox test = buildOwnedCardsDisplay();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		root.getChildren().addAll(panePrestige, sep, tokensGrid, accessibility);
	}
	
	public void modelChanged(Player player) throws RemoteException
	{
		lblPrestigeValue.setText(String.valueOf(player.getPrestige()));
		
		this.updatePlayerTokens(player.getTokenList());
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
		
		ArrayList<RadioButton> radios = new ArrayList<RadioButton>();
		
		radios.add(rbAccNormal);
		radios.add(rbAccDeuteranopia);
		radios.add(rbAccProtanopia);
		radios.add(rbAccTritanopia);
		
		rbAccNormal.setSelected(true);
		
		for(RadioButton radio : radios) {
			radio.setToggleGroup(group);
//			radio.setAlignment(Pos.BASELINE_CENTER);
		}
		
		accessibility.getChildren().addAll(rbAccNormal, rbAccDeuteranopia, rbAccProtanopia, rbAccTritanopia, btnManual);
		
		HBox.setHgrow(accessibility, Priority.ALWAYS);
		
		return accessibility;
	}
	
	private HBox buildOwnedCardsDisplay() throws RemoteException {
		HBox ownedCardsDisplay = new HBox();
		
// TODO
//		List<Card> cardsCount = player.getOwnedCards();
//		
//		for(Card c : cardsCount) {
//			VBox v = new VBox();
//			Label l = new Label();
//			
//			FrontCardView cv = new FrontCardView(c, 200, 200);
//			l.setText(c.getBonusGem().toString());
//			
//			v.getChildren().addAll(l, cv.asPane());
//			
//
//			
//			ownedCardsDisplay.getChildren().add(v);
//		}
		
		return ownedCardsDisplay;
	}
	
	private void updatePlayerTokens(TokenList playerTokens) throws RemoteException
	{
		tokensGrid.getChildren().clear();
		
		LinkedHashMap<Gem, Integer> gemsCount = playerTokens.getTokenGemCount();
		
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

	public Pane asPane() {
		return root;
	}

}