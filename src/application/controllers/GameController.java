package application.controllers;

import java.rmi.RemoteException;

import application.StageManager;
import application.domain.Card;
import application.domain.CardRow;
import application.domain.CardRowImpl;
import application.domain.Game;
import application.domain.Gem;
import application.domain.MoveType;
import application.views.PopUpWindowView;
import javafx.scene.Scene;
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
		/*if (game.getCurrentPlayer().getTokenList().getAll().size() + game.getTurn().getTokenList().getAll().size() > 10) {
			//TODO: ReturnTokens
		}
		//game.getPlayers().get(game.getCurrentPlayerIdx()).getOwnedCards().add(game.getTurn().getBoughtCard());
		//TODO: subtract tokens from player.
		//game.getPlayers().get(game.getCurrentPlayerIdx()).getReservedCards().add(game.getTurn().getReservedCard());
		//TODO: check for nobles
		game.getTurn().emptyHand();
		//TODO: Save Game
		//TODO: Check win condition 
		//TODO: Determine next player
		
		game.nextTurn();*/
	}

	public void leaveGame() {
		StageManager.getInstance().showMainMenu();
		PopUpWindowView exitgame = new PopUpWindowView("Het spel is beëindigd door een van de spelers.", "Het spel is gestopt");
	}

	
	public void onFieldCardClicked(Card card) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	public void onFieldTokenClicked(Gem gemType) throws RemoteException {
		// TODO Auto-generated method stub
		
		if(game.getPlayingField().getTurn().getMoveType() == MoveType.TAKE_TWO_TOKENS)
		{
			game.getPlayingField().addTwoTokensToTemp(gemType);
		}
		
	}
}
