package application.views;

import java.rmi.RemoteException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import application.domain.Card;
import application.domain.Gem;
import application.domain.Player;
import application.domain.PlayerImpl;
import application.domain.PlayerObserver;
import application.domain.Token;
import application.domain.TokenList;
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
public class OpponentView implements UIComponent, PlayerObserver {
	
	
	private Pane root;
	
	private HBox reservedCardsFrame;
	private HBox tokensFrame;
	
	private Label lblOpponentName;
	private Label lblOpponentPrestige;
	
	public OpponentView(Player opponent) {
		this.buildUI();
		
		try {
			opponent.addObserver(this);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void buildUI()
	{
		HBox topFrame = buildOpponentHeaderDisplay();
		
		reservedCardsFrame = new HBox();
		reservedCardsFrame.setAlignment(Pos.CENTER);
		
		tokensFrame = new HBox();
		tokensFrame.setAlignment(Pos.CENTER);
		
		root = new VBox(topFrame, tokensFrame, reservedCardsFrame);
		root.getStyleClass().add("opponent");
	}
	
	public void modelChanged(Player opponent) throws RemoteException
	{
		lblOpponentName.setText(opponent.getName());
		lblOpponentPrestige.setText(String.valueOf(opponent.getPrestige()));
		
		this.updateOpponentTokens(opponent.getTokenList());
		this.updateOpponentsReservedCards(opponent.getReservedCards());
	}
	
	private void updateOpponentsReservedCards(List<Card> opponentReservedCards)
	{
		reservedCardsFrame.getChildren().clear();
		for(Card reservedCard : opponentReservedCards)
		{
            FrontCardView card = new FrontCardView(reservedCard, GameView.cardSizeX , GameView.cardSizeY);
            
            //StackPane paneCard = new StackPane(card.asPane());
            //paneCard.setAlignment(Pos.CENTER);
            //HBox.setHgrow(paneCard, Priority.ALWAYS);

            reservedCardsFrame.getChildren().add(card.asPane());
		}
	}
	private void updateOpponentTokens(TokenList opponentTokens)
	{
		tokensFrame.getChildren().clear();
		LinkedHashMap<Gem, Integer> gemsCount = opponentTokens.getTokenGemCount();
		
		for(Map.Entry<Gem, Integer> entry : gemsCount.entrySet())
		{	
			VBox tokenGemCountDisplay = createTokenGemCountDisplay(entry.getKey(), entry.getValue(), GameView.tokenSizeRadius);
			tokensFrame.getChildren().add(tokenGemCountDisplay);	
		}
	}
	
	private HBox buildOpponentHeaderDisplay()
	{
		HBox topFrame = new HBox(10);
		topFrame.getStyleClass().add("opponent-header");	
		
		topFrame.setAlignment(Pos.CENTER);
		
		lblOpponentName = new Label();
		lblOpponentName.setAlignment(Pos.CENTER_LEFT);
		lblOpponentName.getStyleClass().add("opponent-name");	
		
		Pane spacer = new Pane();
		HBox.setHgrow(spacer, Priority.ALWAYS);    // Give prestige points any extra space
		
		lblOpponentPrestige = new Label();
		lblOpponentPrestige.setAlignment(Pos.CENTER_RIGHT);
		lblOpponentPrestige.getStyleClass().add("prestige");
		
		topFrame.getChildren().addAll(lblOpponentName, spacer, lblOpponentPrestige);
		
		return topFrame;
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
