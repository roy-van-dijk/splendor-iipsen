package application.views;

import java.rmi.RemoteException;

import application.controllers.GameController;
import application.domain.Card;
import application.domain.CardRow;
import application.domain.CardRowObserver;
import application.domain.TempHand;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

/**
 * Card row views are the rows of cards that can be seen on the playing field
 * @author Sanchez
 *
 */
public class CardRowView implements UIComponent, CardRowObserver {

	private static final long serialVersionUID = 1L;
	private Pane root;
	private GridPane grid;
	
	private GameController gameController;
	private TempHand tempHand;

	/**
	 * Creates a new card row view
	 * @param cardRow a row of cards on the playing field
	 * @param gameController
	 * @param tempHand used to determine if a card is selected or not
	 */
	public CardRowView(CardRow cardRow, GameController gameController, TempHand tempHand) {
		this.tempHand = tempHand;
		this.gameController = gameController;
		this.buildUI(cardRow);
		
		try {
			cardRow.addObserver(this);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Update the card row view, like when a card is bought, reserved or selected.
	 * @return void
	 */
	@Override
	public void modelChanged(CardRow cardRow) throws RemoteException 
	{
		grid.getChildren().clear();
		
		// Fetch the cards
		Card[] cardSlots = cardRow.getCardSlots();
		
		// Render each card if it exists
        for(int idx = 0; idx < cardSlots.length; idx++)
        {

        	Card card = cardSlots[idx];
        	if(card == null) continue; // Do not display anything for an empty slot.
        	
        	// Create card view
        	FrontCardView cardView = new FrontCardView(card, GameView.cardSizeX, GameView.cardSizeY);
			
        	if(tempHand.getBoughtCard() == card || tempHand.getReservedCard() == card) {
        		cardView.asPane().getStyleClass().remove("selectable");
        		cardView.asPane().getStyleClass().add("selected");
        	} else if(cardRow.getSelectableCards().contains(card)) {
    			cardView.asPane().getStyleClass().remove("selected");
				cardView.asPane().getStyleClass().add("selectable");
	        	cardView.asPane().setOnMouseClicked(e -> { 
	        		try {
	        			gameController.cardClickedFromField(cardRow, card);
						gameController.disableEndTurn(false);
	        		} catch (RemoteException e1) {
	        			// TODO Auto-generated catch block
	        			e1.printStackTrace();
	        		} 
	        	});
			} else {
				cardView.asPane().getStyleClass().remove("selectable");
			}
        	
        	// Display cards by index
        	grid.add(cardView.asPane(), idx + 1, 0);
        }
		
		// TODO: separate functions
		
		// Create the deck view (Looks like the back side of a card)
		CardDeckView cardDeckView = new CardDeckView(cardRow.getCardDeck(), GameView.cardSizeX, GameView.cardSizeY);
		
		if(cardRow.getCardDeck().isSelected()) {
    		cardDeckView.asPane().getStyleClass().remove("selectable");
    		cardDeckView.asPane().getStyleClass().add("selected");
		} else if(cardRow.getCardDeck().isSelectable()) {
			cardDeckView.asPane().getStyleClass().remove("selected");
			cardDeckView.asPane().getStyleClass().add("selectable");
			cardDeckView.asPane().setOnMouseClicked(e -> {
				try {
					gameController.reserveCardFromDeck(cardRow);
					gameController.disableEndTurn(false);
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			});
		} else {
			cardDeckView.asPane().getStyleClass().remove("selectable");
		}
		
		// // Add the deck to the grid, make deck view first in row
		grid.add(cardDeckView.asPane(), 0, 0);
		
		
        
	}

	/**
	 * 
	 * @param cardRow
	 */
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
