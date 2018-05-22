package application.views;

import application.domain.Card;
import application.domain.CardRow;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class CardRowView implements UIComponent {

	
	private Pane root;
	private CardRow cardRow;
	private int spacingBetweenCards;

	public CardRowView(CardRow cardRow, int spacingBetweenCards) {
		this.cardRow = cardRow;
		this.spacingBetweenCards = spacingBetweenCards;
		
		this.buildUI();
	}
	
	// POC
	public void modelChanged(CardRow cardRow)
	{
		
	}
	
	private void buildUI()
	{
		GridPane grid = new GridPane();
		grid.setHgap(spacingBetweenCards);
		
		// Create the deck view (Looks like the back side of a card)
		CardDeckView cardDeckView = new CardDeckView(cardRow.getCardDeck(), GameView.cardSizeX, GameView.cardSizeY);
		
		// Make deck view first in row
		GridPane.setConstraints(cardDeckView.asPane(), 0, 0);
		
		// Add the deck to the grid
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
