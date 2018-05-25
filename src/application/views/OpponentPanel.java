package application.views;

import java.util.LinkedHashMap;
import java.util.Map;

import application.domain.Card;
import application.domain.Gem;
import application.domain.Player;
import application.domain.Token;
import application.util.Util;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
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
public class OpponentPanel implements UIComponent {
	
	private Player opponent;
	private Pane root;

	public OpponentPanel(Player opponent) {
		this.opponent = opponent;
		
		this.buildUI();
	}
	
	private void buildUI()
	{
		HBox topFrame = buildOpponentHeaderDisplay();
		HBox tokensFrame = buildOpponentTokensDisplay();
		HBox reservedCardsFrame = buildOpponentReservedCardsDisplay();
		
		root = new VBox(topFrame, tokensFrame, reservedCardsFrame);
		root.getStyleClass().add("opponent");
	}
	
	private HBox buildOpponentReservedCardsDisplay()
	{
		HBox reservedCardsFrame = new HBox();
		reservedCardsFrame.setAlignment(Pos.CENTER);
		
		for(Card reservedCard : opponent.getReservedCards())
		{
            StackPane container = new StackPane();
            container.setAlignment(Pos.CENTER);
            HBox.setHgrow(container, Priority.ALWAYS);

            FrontCardView card = new FrontCardView(reservedCard, GameView.cardSizeX / 1.8, GameView.cardSizeY / 1.8);
            container.getChildren().add(card.asPane());

            reservedCardsFrame.getChildren().add(container);
		}
        return reservedCardsFrame;
	}
	
	private HBox buildOpponentHeaderDisplay()
	{
		HBox topFrame = new HBox(10);
		topFrame.getStyleClass().add("opponent-header");	
		
		topFrame.setAlignment(Pos.CENTER);
		
		Label opponentNameLabel = new Label(opponent.getName());
		opponentNameLabel.setAlignment(Pos.CENTER_LEFT);
		opponentNameLabel.getStyleClass().add("opponent-name");	
		
		Pane spacer = new Pane();
		HBox.setHgrow(spacer, Priority.ALWAYS);    // Give prestige points any extra space
		
		Label opponentPrestigePoints = new Label(String.valueOf(opponent.getPrestige()));
		opponentPrestigePoints.setAlignment(Pos.CENTER_RIGHT);
		opponentPrestigePoints.getStyleClass().add("prestige");
		
		topFrame.getChildren().addAll(opponentNameLabel, spacer, opponentPrestigePoints);
		
		return topFrame;
	}
	
	private HBox buildOpponentTokensDisplay()
	{
		HBox tokensFrame = new HBox();
		tokensFrame.setAlignment(Pos.CENTER);
		
		LinkedHashMap<Gem, Integer> gemsCount = opponent.getTokenList().getTokenGemCount();
		
		for(Map.Entry<Gem, Integer> entry : gemsCount.entrySet())
		{	
			VBox tokenGemCountDisplay = createTokenGemCountDisplay(entry.getKey(), entry.getValue(), GameView.tokenSizeRadius);
			tokensFrame.getChildren().add(tokenGemCountDisplay);	
		}
		return tokensFrame;
	}
	
	private VBox createTokenGemCountDisplay(Gem gemType, int count, int radius)
	{
		TokenView tokenView = new TokenView(gemType, radius / 1.8);
        
        Label tokenCountLabel = new Label(String.valueOf(count));
        tokenCountLabel.setAlignment(Pos.CENTER);
        tokenCountLabel.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 20));
        
		VBox tokenFrame = new VBox(10, tokenView.asPane(), tokenCountLabel);
		tokenFrame.setAlignment(Pos.CENTER);
		tokenFrame.setPadding(new Insets(5));
		HBox.setHgrow(tokenFrame, Priority.ALWAYS);
		
		return tokenFrame;
	}

	
	public Pane asPane()
	{
		return root;
	}
	
}
