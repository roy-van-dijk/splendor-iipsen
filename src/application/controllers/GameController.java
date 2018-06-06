package application.controllers;

import java.rmi.RemoteException;
import java.util.Optional;

import application.StageManager;
import application.domain.Card;
import application.domain.CardRow;
import application.domain.CardRowImpl;
import application.domain.Game;
import application.domain.Gem;
import application.domain.MoveType;
import application.domain.ReturnTokens;
import application.services.SaveGameDAO;
import application.util.ConfirmDialog;
import application.views.PopUpWindowView;
import application.views.ReturnTokensView;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

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
		// Creating POC variables - basically specifying: Hey controller, I clicked on this >predefined< card
		CardRow row = game.getPlayingField().getCardRows().get(1); // Second row
		Card card = row.getCardSlots()[1]; // Second card
		
		//if(!card.equals(card2wantdezeisspeciaal)) return;
		
		game.getCurrentPlayer().reserveCardFromField(row, card);
		//game.nextTurn();
	}
	
	public void debugNextTurn() throws RemoteException {
		game.nextTurn();
	}
	
	public void endTurn() throws RemoteException {
		
		//return Tokens
		ReturnTokens model = new ReturnTokens(game.getPlayingField(), game.getCurrentPlayer());
		ReturnTokenController controller = new ReturnTokenController(model);
		model.moreThanTenTokens(model, controller);
		
		//game.getPlayers().get(game.getCurrentPlayerIdx()).getOwnedCards().add(game.getTurn().getBoughtCard());
		//TODO: subtract tokens from player.
		//game.getPlayers().get(game.getCurrentPlayerIdx()).getReservedCards().add(game.getTurn().getReservedCard());
		 
		//TODO: check for nobles
		game.cleanUpTurn();
		//TODO: Save Game
		//TODO: Check win condition 
		//TODO: Determine next player
		
		game.saveGame();
		game.nextTurn();
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

	
	public void onFieldCardClicked(Card card) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	public void onFieldTokenClicked(Gem gemType) throws RemoteException {
		
		game.getPlayingField().addTokenToTemp(gemType);
		
	}
}
