package application.views;

import java.rmi.RemoteException;

import application.controllers.GameController;
import application.domain.Card;
import application.domain.CardRow;
import application.domain.CardRowImpl;
import application.domain.CardRowObserver;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

/**
 * 
 * @author Sanchez
 *
 */
public class CardRowView implements UIComponent, CardRowObserver {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Pane root;
	private GridPane grid;
	
	private GameController gameController;
	
	public CardRowView(CardRow cardRow, GameController gameController) {
		this.gameController = gameController;
		this.buildUI(cardRow);
		
		try {
			cardRow.addObserver(this);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Override
	public void modelChanged(CardRow cardRow) throws RemoteException 
	{
		grid.getChildren().clear();
		
		// TODO: separate functions
		
		// Create the deck view (Looks like the back side of a card)
		CardDeckView cardDeckView = new CardDeckView(cardRow.getCardDeck(), GameView.cardSizeX, GameView.cardSizeY);
		
		// // Add the deck to the grid, make deck view first in row
		grid.add(cardDeckView.asPane(), 0, 0);
		
		// Fetch the cards
		Card[] cardSlots = cardRow.getCardSlots();
		
		// Render each card if it exists
        for(int idx = 0; idx < cardSlots.length; idx++)
        {
        	Card card = cardSlots[idx];
        	if(card == null) continue; // Do not display anything for an empty slot.
        	
        	// Create card view
        	FrontCardView cardView = new FrontCardView(card, GameView.cardSizeX, GameView.cardSizeY);
        	cardView.asPane().setOnMouseClicked(e -> { 
        		try {
        			gameController.onFieldCardClicked(card);
        		} catch (RemoteException e1) {
        			// TODO Auto-generated catch block
        			e1.printStackTrace();
        		} 
        	});
        	
        	// Display cards by index
        	grid.add(cardView.asPane(), idx + 1, 0);
        }
        
	}

	
	private void buildUI(CardRow cardRow)
	{
		grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(PlayingFieldView.CARDSPACING);

        root = grid;
	}


	public Pane asPane() {
		return root;
	}

}
