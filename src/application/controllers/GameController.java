package application.controllers;

import java.rmi.RemoteException;
import java.util.LinkedHashMap;
import java.util.List;


import java.util.Map;


import java.util.Optional;



import application.StageManager;
import application.domain.Card;
import application.domain.CardRow;
import application.domain.CardRowImpl;
import application.domain.EndTurnImpl;
import application.domain.Game;
import application.domain.Gem;
import application.domain.MoveType;

import application.domain.Noble;
import application.domain.PlayingField;


import application.domain.Player;
import application.domain.PlayingField;
import application.domain.Noble;


import application.domain.ReturnTokens;
import application.domain.TempHand;
import application.domain.Token;
import application.domain.TokenImpl;
import application.domain.TokenList;
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
		CardRow row = game.getPlayingField().getCardRows().get(2); // Second row
		Card card = row.getCardSlots()[1]; // Second card
		
		//if(!card.equals(card2wantdezeisspeciaal)) return;

		//if(game.getPlayingField().getTokenGemCount().containsKey(Gem.JOKER))
		if(game.getPlayingField().getTokenGemCount().containsKey(Gem.JOKER))
		{
			TokenList tokenList = game.getPlayingField().getTokenList();
			for(Token token : tokenList.getAll())
			{

				game.getCurrentPlayer().debugAddToken(token);
			}

		game.getCurrentPlayer().reserveCardFromField(row, card);
		}

		
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
		game.cardSelected();
	}
	
	public void cardClickedFromReserve(Card card) throws RemoteException {
		game.addCardToTempFromReserve(card);
		game.cardSelected();
	}

	public void onFieldTokenClicked(Gem gemType) throws RemoteException {	
		game.getPlayingField().addTokenToTemp(gemType);
		
	}
	
	public TempHand getTempHand() throws RemoteException {
		return game.getPlayingField().getTempHand();
	}
}
