package application.views;

import application.controllers.GameController;
import application.domain.Card;
import application.domain.CardRow;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
/**
 * 
 * @author Sanchez
 *
 */
public class CardRowView implements UIComponent {

	
	private Pane root;
	private CardRow cardRow;
	private int spacingBetweenCards;
	private GameController gameController;
	
	public CardRowView(CardRow cardRow, GameController gameController ,int spacingBetweenCards) {
		this.cardRow = cardRow;
		this.spacingBetweenCards = spacingBetweenCards;
		

		this.buildUI();
	}
	
	// POC
	public void modelChanged(CardRow cardRow)
	{
		this.buildUI(); // POC
	}
	
	private void buildUI()
	{
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(spacingBetweenCards);
		
		// Create the deck view (Looks like the back side of a card)
		CardDeckView cardDeckView = new CardDeckView(cardRow.getCardDeck(), GameView.cardSizeX, GameView.cardSizeY);
		
		// // Add the deck to the grid, make deck view first in row
		GridPane.setConstraints(cardDeckView.asPane(), 0, 0);		
		grid.getChildren().add(cardDeckView.asPane());
		
		// Fetch the cards
		Card[] cardSlots = cardRow.getCardSlots();
		
		// Render each card if it exists
        for(int idx = 0; idx < cardSlots.length; idx++)
        {
        	Card card = cardSlots[idx];
        	if(card == null) continue; // Do not display anything for an empty slot.
        	
        	// Create card view
        	FrontCardView cardView = new FrontCardView(card, GameView.cardSizeX, GameView.cardSizeY);
        	//cardView.asPane().setOnMouseClicked(e -> { gameController. });
        	
        	// Display cards by index
        	GridPane.setConstraints(cardView.asPane(), idx + 1, 0);
        	grid.getChildren().add(cardView.asPane());
        }
        
        root = grid;
	}


	public Pane asPane() {
		return root;
	}

}
