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

	public void reserveCard(MoveType moveType) throws RemoteException {
		game.findSelectableCards(moveType);
	}
	
	public void purchaseCard(MoveType moveType) throws RemoteException {
		game.findSelectableCards(moveType);
	}
	
	public void takeTokens(MoveType moveType) throws RemoteException {
		game.setTokensSelectable(moveType);
	}

	public void debugNextTurn() throws RemoteException {
		game.nextTurn();
	}
	
	public void endTurn() throws RemoteException {
		game.getEndTurn().endTurn();
	}
	
	
	public void leaveGame() {
		ConfirmDialog dialog = new ConfirmDialog(AlertType.CONFIRMATION);
		dialog.setTitle("Confirmation Dialog");
		dialog.setHeaderText("You are leaving the game");
		dialog.setContentText("Are you sure you wish to continue?");
		
		Optional<ButtonType> results = dialog.showAndWait();
		if (results.get() == ButtonType.OK){
			try {
				game.saveGame();
				// TODO: Make leave game method
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			StageManager.getInstance().showMainMenu();		
			new PopUpWindowView("Het spel is beëindigd door een van de spelers.", "Het spel is gestopt");
		}
	}
	
	public void reserveCardFromDeck(int cardRowIdx) throws RemoteException {
		game.reserveCardFromDeck(cardRowIdx);
	}

	public void onFieldCardClicked(int cardRowIdx, int cardIdx) throws RemoteException {
		game.addCardToTempFromField(cardRowIdx, cardIdx);
	}
	
	public void onReservedCardClicked(int cardIdx) throws RemoteException {
		game.addCardToTempFromReserve(cardIdx);
	}

	public void onFieldTokenClicked(Gem gemType) throws RemoteException {	
		game.addTokenToTemp(gemType);
	}
	
	public TempHand getTempHand() throws RemoteException {
		return game.getPlayingField().getTempHand();
	}
/*	public void clearDeckSelection() throws RemoteException {
		game.getPlayingField().setDeckDeselected();
		game.updatePlayingFieldAndPlayerView();			
	}*/

	public void resetTurn() throws RemoteException {
		game.cleanUpSelections();		
	}

	public boolean reserveCardInventoryFull() throws RemoteException {
		return game.reserveCardInventoryFull();
	}
}