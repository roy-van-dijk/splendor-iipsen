package application.views;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;
import java.util.List;

import application.controllers.GameController;
import application.domain.Card;
import application.domain.CardRow;
import application.domain.CardRowObserver;

import application.domain.Gem;
import javafx.application.Platform;

import application.domain.TempHand;
import application.util.Logger;
import application.util.Logger.Verbosity;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

/**
 * Card row views are the rows of cards that can be seen on the playing field
 * @author Sanchez
 *
 */
public class CardRowView extends UnicastRemoteObject implements UIComponent, CardRowObserver {

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
	public CardRowView(CardRow cardRow, GameController gameController, TempHand tempHand) throws RemoteException {
		this.tempHand = tempHand;
		this.gameController = gameController;
		this.buildUI();
		
		cardRow.addObserver(this);
	}

	/**
	 * Update the card row view, like when a card is bought, reserved or selected.
	 * @return void
	 */	
	private void updateCardRow(CardRow cardRow) throws RemoteException
	{
		grid.getChildren().clear();
		
		// Create the deck view (Looks like the back side of a card)
		CardDeckView cardDeckView = new CardDeckView(cardRow.getCardDeck(), GameView.cardSizeX, GameView.cardSizeY);
		if(cardRow.getCardDeck().isSelected()) {
    		cardDeckView.asPane().getStyleClass().add("selected");
		} else if(cardRow.getCardDeck().isSelectable()) {
			cardDeckView.asPane().getStyleClass().add("selectable");
			cardDeckView.asPane().setOnMouseClicked(e -> {
				try {
					gameController.reserveCardFromDeck(cardRow.getIndex());
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			});
		}
		
		
		// // Add the deck to the grid, make deck view first in row
		grid.add(cardDeckView.asPane(), 0, 0);
		
		// Fetch the cards
		Card[] cardSlots = cardRow.getCardSlots();
		//Logger.log("CardRowView::updateCardRow()::Card row has " + Arrays.asList(cardSlots), Verbosity.DEBUG);
		//Logger.log("CardRowView::updateCardRow()::First card in row = " + cardSlots[0].getCosts(), Verbosity.DEBUG);
		//Logger.log("CardRowView::updateCardRow()::Selectable cards = " + cardRow.getSelectableCards(), Verbosity.DEBUG);
		
		Card boughtCard = tempHand.getBoughtCard();
		Card reservedCard = tempHand.getReservedCard();
		List<Card> selectableCards = cardRow.getSelectableCards();
		
		// Render each card if it exists
        for(int idx = 0; idx < cardSlots.length; idx++)
        {
        	int cardIdx = idx;
        	Card card = cardSlots[cardIdx];
        	if(card == null) continue; // Do not display anything for an empty slot.
        	
        	// Create card view
        	FrontCardView cardView = new FrontCardView(card, GameView.cardSizeX, GameView.cardSizeY);
			//System.out.println("[Debug] tempHand.getresrvdcard = " + tempHand.getReservedCard());
			//System.out.println("[Debug] & card = " + card);
        	if((boughtCard != null && boughtCard.equals(card)) || (reservedCard != null && reservedCard.equals(card))) {
        		cardView.asPane().getStyleClass().add("selected");
        	} else if(selectableCards.contains(card)) { 
				cardView.asPane().getStyleClass().add("selectable");
				
	        	cardView.asPane().setOnMouseClicked(e -> { 
	        		try {
	        			gameController.onFieldCardClicked(cardRow.getIndex(), cardIdx);
	        		} catch (RemoteException e1) {
	        			// TODO Auto-generated catch block
	        			e1.printStackTrace();
	        		} 
	        	});
			}
        	
        	// Display cards by index
        	grid.add(cardView.asPane(), idx + 1, 0);
        }
	}
	
	
	@Override
	public void modelChanged(CardRow cardRow) throws RemoteException 
	{
		//System.out.println("[DEBUG] CardRowView::modelChanged()::Updating card row");
		Platform.runLater(() ->
		{
			try {
				this.updateCardRow(cardRow);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}

	private void buildUI()
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
