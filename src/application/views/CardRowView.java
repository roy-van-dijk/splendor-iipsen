package application.views;

import java.rmi.RemoteException;

import application.controllers.GameController;
import application.domain.Card;
import application.domain.CardRowImpl;
import application.domain.GenericObserver;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

/**
 * 
 * @author Sanchez
 *
 */
public class CardRowView implements UIComponent, GenericObserver<CardRowImpl> {

	
	private Pane root;
	private GridPane grid;
	
	private GameController gameController;
	
	public CardRowView(CardRowImpl cardRowImpl, GameController gameController) {

		this.buildUI(cardRowImpl);
		
		try {
			cardRowImpl.addObserver(this);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Override
	public void modelChanged(CardRowImpl cardRowImpl) 
	{
		// Fetch the cards
		Card[] cardSlots = cardRowImpl.getCardSlots();
		
		// Render each card if it exists
        for(int idx = 0; idx < cardSlots.length; idx++)
        {
        	Card card = cardSlots[idx];
        	if(card == null) continue; // Do not display anything for an empty slot.
        	
        	// Create card view
        	FrontCardView cardView = new FrontCardView(card, GameView.cardSizeX, GameView.cardSizeY);
        	//cardView.asPane().setOnMouseClicked(e -> { gameController. });
        	
        	// Display cards by index
        	grid.add(cardView.asPane(), idx + 1, 0);
        }
        
	}

	
	private void buildUI(CardRowImpl cardRowImpl)
	{
		grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(PlayingFieldPanel.CARDSPACING);
		
		// Create the deck view (Looks like the back side of a card)
		CardDeckView cardDeckView = new CardDeckView(cardRowImpl.getCardDeck(), GameView.cardSizeX, GameView.cardSizeY);
		
		// // Add the deck to the grid, make deck view first in row
		grid.add(cardDeckView.asPane(), 0, 0);
		

        root = grid;
	}


	public Pane asPane() {
		return root;
	}

}
