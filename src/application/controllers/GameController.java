package application.controllers;

import java.rmi.RemoteException;
import java.util.Optional;

import application.StageManager;
import application.domain.Card;
import application.domain.CardRow;
import application.domain.Game;
import application.domain.Gem;
import application.domain.MoveType;
import application.domain.TempHand;
import application.domain.Token;
import application.domain.TokenList;
import application.util.ConfirmDialog;
import application.views.PopUpWindowView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

// TODO: Auto-generated Javadoc
/**
 * The Class GameController.
 *
 * @author Sanchez
 */
public class GameController {
	private Game game;

	/**
	 * Instantiates a new game controller.
	 *
	 * @param game
	 */
	public GameController(Game game) {
		this.game = game;
	}

	/**
	 * Reserve card.
	 *
	 * @throws RemoteException
	 */
	public void reserveCard() throws RemoteException {
		game.findSelectableCards();
	}
	
	/**
	 * Purchase card.
	 *
	 * @throws RemoteException
	 */
	public void purchaseCard() throws RemoteException {
		game.findSelectableCards();
	}
	
	/**
	 * Debug next turn.
	 *
	 * @throws RemoteException
	 */
	public void debugNextTurn() throws RemoteException {
		game.nextTurn();
	}
	
	/**
	 * End turn.
	 *
	 * @throws RemoteException
	 */
	public void endTurn() throws RemoteException {
		game.getEndTurn().endTurn();
	}
	
	/**
	 * Find selectable tokens.
	 *
	 * @throws RemoteException
	 */
	public void findSelectableTokens() throws RemoteException {
		game.getPlayingField().setTokensSelectable();
	}

	/**
	 * Leave game.
	 */
	public void leaveGame() {
		ConfirmDialog dialog = new ConfirmDialog(AlertType.CONFIRMATION);
		dialog.setTitle("Confirmation Dialog");
		dialog.setHeaderText("You are leaving the game");
		dialog.setContentText("Are you ok with this?");
		
		Optional<ButtonType> results = dialog.showAndWait();
		if (results.get() == ButtonType.OK){
			try {
				game.saveGame();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			StageManager.getInstance().showMainMenu();		
			new PopUpWindowView("Het spel is beëindigd door een van de spelers.", "Het spel is gestopt");
		}
	}

	/**
	 * Card clicked from field.
	 *
	 * @param cardRow
	 * @param card
	 * @throws RemoteException
	 */
	public void cardClickedFromField(CardRow cardRow, Card card) throws RemoteException {
		cardRow.addCardToTemp(cardRow, card, game.getPlayingField().getTempHand());
		if(game.getPlayingField().getTempHand().getMoveType() == MoveType.RESERVE_CARD) {
			game.getPlayingField().setDeckDeselected();
		}
		game.updatePlayingFieldAndPlayerView();
	}
	
	/**
	 * Card clicked from reserve.
	 *
	 * @param card
	 * @throws RemoteException
	 */
	public void cardClickedFromReserve(Card card) throws RemoteException {
		game.addCardToTempFromReserve(card);
		game.updatePlayingFieldAndPlayerView();
	}

	/**
	 * On field token clicked.
	 *
	 * @param gemType
	 * @throws RemoteException
	 */
	public void onFieldTokenClicked(Gem gemType) throws RemoteException {	
		game.getPlayingField().addTokenToTemp(gemType);
		
	}
	
	/**
	 * Gets the temphand.
	 *
	 * @return TempHand
	 * @throws RemoteException
	 */
	public TempHand getTempHand() throws RemoteException {
		return game.getPlayingField().getTempHand();
	}

	/**
	 * Reserve card from deck.
	 *
	 * @param cardRow 
	 * @throws RemoteException
	 */
	public void reserveCardFromDeck(CardRow cardRow) throws RemoteException {
		cardRow.addCardToTemp(cardRow, cardRow.getCardDeck().top(), this.getTempHand());
		cardRow.getCardDeck().top().setReservedFromDeck(true);
		game.getPlayingField().setDeckSelected(cardRow);
		game.updatePlayingFieldAndPlayerView();	
	}

	/**
	 * Clear deck selection.
	 *
	 * @throws RemoteException
	 */
	public void clearDeckSelection() throws RemoteException {
		game.getPlayingField().setDeckDeselected();
		game.updatePlayingFieldAndPlayerView();			
	}

	/**
	 * Reset turn.
	 *
	 * @throws RemoteException
	 */
	public void resetTurn() throws RemoteException {
		this.getTempHand().emptyHand();
		game.cleanUpSelections();		
	}
}