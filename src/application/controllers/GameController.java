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

/**
 * @author Sanchez
 *
 */
public class GameController {
	private Game game;

	public GameController(Game game) {
		this.game = game;
	}

	public void reserveCard() throws RemoteException {
		game.findSelectableCards();
	}
	
	public void purchaseCard() throws RemoteException {
		game.findSelectableCards();
	}
	
	public void debugNextTurn() throws RemoteException {
		game.nextTurn();
	}
	
	public void endTurn() throws RemoteException {
		game.getEndTurn().endTurn();
	}
	
	public void findSelectableTokens() throws RemoteException {
		game.getPlayingField().setTokensSelectable();
	}

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

	public void cardClickedFromField(CardRow cardRow, Card card) throws RemoteException {
		cardRow.addCardToTemp(cardRow, card, game.getPlayingField().getTempHand());
		if(game.getPlayingField().getTempHand().getMoveType() == MoveType.RESERVE_CARD) {
			game.getPlayingField().setDeckDeselected();
		}
		game.updatePlayingFieldAndPlayerView();
	}
	
	public void cardClickedFromReserve(Card card) throws RemoteException {
		game.addCardToTempFromReserve(card);
		game.updatePlayingFieldAndPlayerView();
	}

	public void onFieldTokenClicked(Gem gemType) throws RemoteException {	
		game.getPlayingField().addTokenToTemp(gemType);
		
	}
	
	public TempHand getTempHand() throws RemoteException {
		return game.getPlayingField().getTempHand();
	}

	public void reserveCardFromDeck(CardRow cardRow) throws RemoteException {
		cardRow.addCardToTemp(cardRow, cardRow.getCardDeck().top(), this.getTempHand());
		cardRow.getCardDeck().top().setReservedFromDeck(true);
		game.getPlayingField().setDeckSelected(cardRow);
		game.updatePlayingFieldAndPlayerView();	
	}

	public void clearDeckSelection() throws RemoteException {
		game.getPlayingField().setDeckDeselected();
		game.updatePlayingFieldAndPlayerView();			
	}

	public void resetTurn() throws RemoteException {
		this.getTempHand().emptyHand();
		game.cleanUpSelections();		
	}
	
	public void disableEndTurn(boolean disabled) throws RemoteException {
		game.disableEndTurn(disabled);
	}

}