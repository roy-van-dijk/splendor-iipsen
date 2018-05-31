package application.views;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import application.controllers.GameController;
import application.domain.CardRowImpl;
import application.domain.Game;
import application.domain.Gem;
import application.domain.Noble;
import application.domain.PlayingField;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
/**
 * 
 * @author Sanchez
 *
 */
public class PlayingFieldView implements UIComponent {

	public final static int CARDSPACING = 15, 
							TOKENSPACING = 10,
							TOKENSLABELSPACING = 25,
							TOKENSCARDSPADDING = 20,
							FIELDPADDING = 0;
	
	private PlayingField playingField;
	private HBox root;
	
	private List<CardRowView> cardRowViews;
	private List<NobleView> nobleViews;
	
	private GameController gameController;
	/**
	 * 
	 * @param PlayingField playingField
	 * @param GameController gameController
	 */
	public PlayingFieldView(PlayingField playingField, GameController gameController) {
		this.playingField = playingField;
		this.gameController = gameController;
		
		this.cardRowViews = new ArrayList<>();
		this.nobleViews = new ArrayList<>();
		this.buildUI();
	}
	
	private void buildUI()
	{
		VBox cardsAndNobles = buildCardsAndNoblesDisplay(); 
		VBox tokens = buildTokensDisplay();
		VBox exitgame =  buildExitGameDisplay();
		
		
		this.root = new HBox(TOKENSCARDSPADDING, cardsAndNobles, tokens, exitgame);
		this.root.setAlignment(Pos.CENTER);
		this.root.setPadding(new Insets(FIELDPADDING));
	}
	
	private VBox buildCardsAndNoblesDisplay()
	{
		VBox cardsAndNobles = new VBox(CARDSPACING);
		HBox.setHgrow(cardsAndNobles, Priority.ALWAYS);
		cardsAndNobles.setAlignment(Pos.CENTER);
		
		HBox nobles = this.createNobles();
		cardsAndNobles.getChildren().add(nobles);
		
		for(CardRowImpl cardRowImpl : playingField.getCardRows())
		{
			CardRowView cardRowView = new CardRowView(cardRowImpl, gameController);
			cardRowViews.add(cardRowView);
			cardsAndNobles.getChildren().add(cardRowView.asPane());
		}
		
		return cardsAndNobles;
	}
	
	private HBox createNobles()
	{
		HBox nobles = new HBox(CARDSPACING);
		nobles.setAlignment(Pos.CENTER);
		
		for(Noble noble : playingField.getNobles())
		{
			NobleView nobleView = new NobleView(noble, GameView.cardSizeX, GameView.cardSizeY / 1.5);
			nobleViews.add(nobleView);
			nobles.getChildren().add(nobleView.asPane());
		}
		return nobles;
	}
	
	private VBox buildTokensDisplay()
	{
		VBox tokens = new VBox(TOKENSPACING);
		tokens.setAlignment(Pos.CENTER);
		HBox.setHgrow(tokens, Priority.ALWAYS);
		
		LinkedHashMap<Gem, Integer> gemsCount = playingField.getTokenGemCount();
		
		for(Map.Entry<Gem, Integer> entry : gemsCount.entrySet())
		{	
			HBox tokenGemCountDisplay = createTokenGemCountDisplay(entry.getKey(), entry.getValue(), GameView.tokenSizeRadius);
			tokens.getChildren().add(tokenGemCountDisplay);	
		}
		
		return tokens;
	}
	
	
	
	private HBox createTokenGemCountDisplay(Gem gemType, int count, int radius)
	{
		TokenView tokenView = new TokenView(gemType, radius);
		
        Label tokenCountLabel = new Label(String.valueOf(count));
        tokenCountLabel.getStyleClass().add("token-count");	
        tokenCountLabel.setFont(Font.font(radius * 2));
        
		HBox tokenRow = new HBox(15, tokenView.asPane(), tokenCountLabel);
        tokenRow.setAlignment(Pos.CENTER);
        
		return tokenRow;
	}
	
	private VBox buildExitGameDisplay()
	{	
		Button exit = new Button("X");

		VBox vbox = new VBox();
		vbox.getChildren().add(exit);
		
		exit.setOnAction(e -> {
			
			System.out.println("exit the game");
			gameController.leaveGame();
		});
		return vbox;
	}

	public Pane asPane() {
		return root;
	}
}
