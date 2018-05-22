package application.views;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import application.domain.CardRow;
import application.domain.Gem;
import application.domain.Noble;
import application.domain.PlayingField;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class PlayingFieldPanel implements UIComponent {

	private PlayingField playingField;
	private Pane root;
	
	private List<CardRowView> cardRowViews;
	private List<NobleView> nobleViews;
	
	private int cardsSpacing;
	private int tokensSpacing;

	public PlayingFieldPanel(PlayingField playingField, int cardsSpacing, int tokensSpacing) {
		this.playingField = playingField;
		this.cardsSpacing = cardsSpacing;
		this.tokensSpacing = tokensSpacing;
		
		
		this.cardRowViews = new ArrayList<>();
		this.nobleViews = new ArrayList<>();
		this.buildUI();
	}
	
	private void buildUI()
	{
		VBox cardsAndNobles = buildCardsAndNoblesDisplay(); 
		VBox tokens = buildTokensDisplay();
		
		this.root = new HBox(50, cardsAndNobles, tokens);
		root.setPadding(new Insets(25));
	}
	
	private VBox buildCardsAndNoblesDisplay()
	{
		VBox cardsAndNobles = new VBox(cardsSpacing);
		
		HBox nobles = this.createNobles();
		cardsAndNobles.getChildren().add(nobles);
		
		for(CardRow cardRow : playingField.getCardRows())
		{
			CardRowView cardRowView = new CardRowView(cardRow, cardsSpacing);
			cardRowViews.add(cardRowView);
			cardsAndNobles.getChildren().add(cardRowView.asPane());
		}
		
		return cardsAndNobles;
	}
	
	private HBox createNobles()
	{
		HBox nobles = new HBox(cardsSpacing);
		
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
		VBox tokens = new VBox(tokensSpacing);
		tokens.setPadding(new Insets(GameView.cardSizeY / 2, 0, 0, 0));
		
		
		LinkedHashMap<Gem, Integer> gemsCount = playingField.getTokenList().getTokenGemCount();
		
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
        tokenCountLabel.setAlignment(Pos.CENTER);
        tokenCountLabel.getStyleClass().add("token-count");	
        tokenCountLabel.setFont(Font.font(radius * 2));
        
		HBox tokenRow = new HBox(10, tokenView.asPane(), tokenCountLabel);
		tokenRow.setAlignment(Pos.CENTER);
		
		return tokenRow;
	}
	

	public Pane asPane() {
		return root;
	}
	

}
