package application.views;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import application.controllers.GameController;
import application.domain.CardRow;
import application.domain.CardRowImpl;
import application.domain.Game;
import application.domain.Gem;
import application.domain.MoveType;
import application.domain.Noble;
import application.domain.PlayingField;
import application.domain.PlayingFieldObserver;
import application.util.ConfirmDialog;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
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
public class PlayingFieldView implements UIComponent, PlayingFieldObserver {

	public final static int CARDSPACING = 15, 
							TOKENSPACING = 10,
							TOKENSLABELSPACING = 25,
							TOKENSCARDSPADDING = 20,
							FIELDPADDING = 0;
	
	private HBox root;
	
	private VBox cardsAndNoblesPane;
	private HBox noblesPane;
	private VBox tokensPane;
	
	private VBox exitGamePane;
	
	private List<CardRowView> cardRowViews;
	private List<NobleView> nobleViews;
	private List<TokenView> tokenViews;
	
	private GameController gameController;
	/**
	 * 
	 * @param PlayingField playingField
	 * @param GameController gameController
	 */
	public PlayingFieldView(PlayingField playingField, GameController gameController) {
		this.gameController = gameController;
		
		this.cardRowViews = new ArrayList<>();
		this.nobleViews = new ArrayList<>();
		this.tokenViews = new ArrayList<>();
		this.buildUI();
		
		try {
			playingField.addObserver(this);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void modelChanged(PlayingField playingField) throws RemoteException
	{
		this.updateCardRows(playingField.getCardRows());
		this.updateFieldTokens(playingField.getTokenGemCount());
		this.updateNobles(playingField.getNobles());
	}
	
	private void buildUI()
	{
		cardsAndNoblesPane = buildCardsAndNoblesDisplay(); 
		exitGamePane = buildExitGameDisplay();

		tokensPane = new VBox(TOKENSPACING);
		tokensPane.setAlignment(Pos.CENTER);
		HBox.setHgrow(tokensPane, Priority.ALWAYS);
		
		root = new HBox(TOKENSCARDSPADDING, cardsAndNoblesPane, tokensPane, exitGamePane);
		root.setAlignment(Pos.CENTER);
		root.setPadding(new Insets(FIELDPADDING));
	}
	
	private VBox buildCardsAndNoblesDisplay()
	{
		VBox cardsAndNobles = new VBox(CARDSPACING);
		HBox.setHgrow(cardsAndNobles, Priority.ALWAYS);
		cardsAndNobles.setAlignment(Pos.CENTER);
		
		noblesPane = new HBox(CARDSPACING);
		noblesPane.setAlignment(Pos.CENTER);
		cardsAndNobles.getChildren().add(noblesPane);
		
		return cardsAndNobles;
	}
	
	private void updateCardRows(List<CardRow> cardRows)
	{
		for(CardRow cardRow : cardRows)
		{
			CardRowView cardRowView = new CardRowView(cardRow, gameController);
			cardRowViews.add(cardRowView);
			cardsAndNoblesPane.getChildren().add(cardRowView.asPane());
		}
	}
	
	private void updateNobles(List<Noble> nobles)
	{
		for(Noble noble : nobles)
		{
			NobleView nobleView = new NobleView(noble, GameView.cardSizeX, GameView.cardSizeY / 1.5);
			nobleViews.add(nobleView);
			noblesPane.getChildren().add(nobleView.asPane());
		}
	}
	
	private void updateFieldTokens(Map<Gem, Integer> gemsCount)
	{	
		for(Map.Entry<Gem, Integer> entry : gemsCount.entrySet())
		{	
			HBox tokenGemCountDisplay = createTokenGemCountDisplay(entry.getKey(), entry.getValue(), GameView.tokenSizeRadius);
			if(entry.getValue() >= 4) tokenGemCountDisplay.getStyleClass().add("selected");
			tokensPane.getChildren().add(tokenGemCountDisplay);	
		}
	}
	
	private HBox createTokenGemCountDisplay(Gem gemType, int count, int radius)
	{
		TokenView tokenView = new TokenView(gemType, radius);
		
		tokenView.asPane().setOnMouseClicked(e -> { 
			try {
				gameController.onFieldTokenClicked(gemType);
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} 
		});
		
		// TODO: find a way to incorporate this into modelChanged();
		/*if(playingField.getSelectableTokens().contains(gemType)) {
			tokenView.asPane().getStyleClass().add("selectable");
		}
		if(playingField.getTurn().getTokenList().getAll().contains(gemType))
		{
			tokenView.asPane().getStyleClass().add("selected");
		}*/
		
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
        exit.getStyleClass().addAll("button", "manual-button");	
		vbox.getChildren().add(exit);
		
		exit.setOnAction(e -> {	
			gameController.leaveGame();
		});
		return vbox;
	}

	public Pane asPane() {
		return root;
	}
}
