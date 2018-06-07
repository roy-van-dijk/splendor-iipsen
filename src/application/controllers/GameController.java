package application.controllers;

import java.rmi.RemoteException;
import java.util.List;


import java.util.Map;


import java.util.Optional;



import application.StageManager;
import application.domain.Card;
import application.domain.CardRow;
import application.domain.CardRowImpl;
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
		
	}
	
	public void purchaseCard() throws RemoteException {
		game.findSelectableCards();
	}
	
	public void debugNextTurn() throws RemoteException {
		game.nextTurn();
	}
	
	public void endTurn() throws RemoteException {

		PlayingField playingfield = game.getPlayingField();
		TempHand temphand = game.getPlayingField().getTempHand();
		Player player = game.getCurrentPlayer();
		List<Noble> allNobles= game.getPlayingField().getNobles();
		Map<Gem, Integer> totalBonusGems = game.getCurrentPlayer().getBonus();
		/**
		 * Create the returntokens if the an player has moren then 10 tokens
		 */

		ReturnTokens model = new ReturnTokens(game.getPlayingField(), game.getCurrentPlayer());
		ReturnTokenController controller = new ReturnTokenController(model);
		List<Token> tokens = game.getCurrentPlayer().getTokens();
		
		
		
		if(tokens.size() > 10) {
			System.out.println("I'v got " + tokens.size() + " Tokens");
			model.moreThanTenTokens(model, controller);

		}
		/**
		 * Adds the reservecard to the player
		 */
		if(temphand.getReservedCard() != null) {
			CardRow row = game.getPlayingField().getCardRows().get(1); // Second row
			Card card = row.getCardSlots()[1];
			playingfield.removeCard(temphand.getReservedCard());
			player.addReserverveCard(temphand.getReservedCard());
			game.getCurrentPlayer().reserveCardFromField(row, card);
			
		}
		/**
		 * Add temphand cards to the player
		 */
		if(temphand.getBoughtCard() != null) {
			
			//player.returnTokensToField(removedTokens, playingfield);
			
			player.addCard(temphand.getBoughtCard());
		}


		//begin voor toevoegen nobles

		
		//game.getPlayers().get(game.getCurrentPlayerIdx()).getOwnedCards().add(game.getTurn().getBoughtCard());
		//TODO: subtract tokens from player.
		//game.getPlayers().get(game.getCurrentPlayerIdx()).getReservedCards().add(game.getTurn().getReservedCard());
		 
		//TODO: check for nobles
		game.cleanUpTurn();
		game.saveGame();
		//TODO: Check win condition 
		//TODO: Determine next player
		
		
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

	public void cardClicked(CardRow cardRow, Card card) throws RemoteException {
		cardRow.addCardToTemp(cardRow, card, game.getPlayingField().getTempHand());
		game.cardSelected();
	}

	public void onFieldTokenClicked(Gem gemType) throws RemoteException {	
		game.getPlayingField().addTokenToTemp(gemType);
		
	}
}
