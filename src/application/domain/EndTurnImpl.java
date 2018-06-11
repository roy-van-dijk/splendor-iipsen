package application.domain;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import application.controllers.ReturnTokenController;
import application.services.SaveGameDAO;
import application.util.Logger;
import application.util.Logger.Verbosity;

// TODO: Kees Need to fill the method Endturn()
/**
 * The Class EndTurnImpl.
 */
public class EndTurnImpl extends UnicastRemoteObject implements EndTurn, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5685833325676205128L;
	private Game game;
	private PlayingField playingField;
	private TempHand tempHand;
	private Player player;
	private boolean returningTokens;
	/**
	 * 
	 * @param game
	 * @throws RemoteException
	 */
	public EndTurnImpl(Game game) throws RemoteException {
		this.game = game;
		
		this.playingField = game.getPlayingField();
		this.tempHand = playingField.getTempHand();
		
		this.returningTokens = false;
	}
	
	public boolean returningTokens() throws RemoteException {
		return returningTokens;
	}
	
	/* (non-Javadoc)
	 * @see application.domain.EndTurn#endTurn()
	 */
	@Override
	public void endTurn() throws RemoteException {

		this.player = game.getCurrentPlayer();
		MoveType moveType = tempHand.getMoveType();
		
		if(!returningTokens) {
			if(moveType == MoveType.PURCHASE_CARD) {
				/**
				 * Add temphand cards to the player
				 */
				Card boughtCard = tempHand.getBoughtCard(); 
				if(player.getReservedCards().contains(boughtCard)) {
					player.getReservedCards().remove(boughtCard);
				} else {
					playingField.removeCard(boughtCard);
				}
				player.addCard(boughtCard);
				this.removeTokenCost();	
			} else if(moveType == MoveType.RESERVE_CARD) {
				/**
				 * Adds the reservecard to the player
				 */
				Logger.log("EndTurnImpl::endTurn::Reserved card = " + tempHand.getReservedCard(), Verbosity.DEBUG);
				playingField.removeCard(tempHand.getReservedCard());
				for(CardRow cardRow : playingField.getCardRows()) {
					if(cardRow.getCardDeck().isSelected()) {
						cardRow.getCardDeck().pull();
					}
				}
				player.addReservedCard(tempHand.getReservedCard());
				// Geef speler een joker
				if(playingField.getTokenGemCount().get(Gem.JOKER) > 0){
					Token token = new TokenImpl(Gem.JOKER);
					player.addToken(token);
					playingField.removeToken(token);
				}
			} else if(moveType == MoveType.TAKE_THREE_TOKENS || moveType == MoveType.TAKE_TWO_TOKENS) {
				this.getTokens();
			}
		}
		/**
		 * Create the returntokens if the an player has moren then 10 tokens
		 */
		List<Token> tokens = player.getTokens();
		
		if(tokens.size() > 10) {
			System.out.println("I've got " + tokens.size() + " Tokens");
			this.returningTokens = true;
			this.game.notifyObservers();
		} else {
			this.returningTokens = false;
			this.checkNobleVisits();		
			this.cleanUpTurn();
			game.saveGame();	
			game.nextTurn();
		}
	}

	/**
	 * Check noble visits.
	 *
	 * @throws RemoteException
	 */
	private void checkNobleVisits() throws RemoteException{
		
		List<Noble> allNobles= playingField.getNobles();
		Map<Gem, Integer> totalBonusGems = player.getBonus();

		List<Noble> visitingNobles = checkPlayingFieldNobles(allNobles, totalBonusGems);
		
		for(Noble noble : visitingNobles) {
			player.addNoble(noble);
			playingField.getNobles().remove(noble);
		}
		
	}
	
	/**
	 * Check playing field nobles.
	 *
	 * @param allNobles
	 * @param totalBonusGems
	 * @return List<Noble>
	 * @throws RemoteException
	 */
	private List<Noble> checkPlayingFieldNobles(List<Noble> allNobles, Map<Gem, Integer> totalBonusGems) throws RemoteException {
		
		List<Noble> visitingNobles = new ArrayList<Noble>();
		for(Noble noble : allNobles) 
		{
			Map<Gem, Integer> nobleCost = noble.getRequirements();
			if(compareNobleAndBonusGems(nobleCost, totalBonusGems)) 
			{
				visitingNobles.add(noble);
			}
			
		}
		return visitingNobles;
	}

	/**
	 * Compare noble and bonus gems.
	 *
	 * @param nobleCost
	 * @param totalBonusGems
	 * @return true, if successful
	 */
	private boolean compareNobleAndBonusGems(Map<Gem, Integer> nobleCost, Map<Gem, Integer> totalBonusGems) {
		for(Map.Entry<Gem, Integer> nobleCostEntry : nobleCost.entrySet()) {
		//for(Gem gemType : Gem.values()) 
			if(nobleCostEntry.getValue() > totalBonusGems.get(nobleCostEntry.getKey())) {
				return false;
			}
		}
		return true;	
	}

	/**
	 * Gets the tokens.
	 *
	 * @throws RemoteException
	 */
	private void getTokens() throws RemoteException {
		for(Gem gem: tempHand.getSelectedGemTypes()) {			
			Token token = new TokenImpl(gem);
			player.addToken(token);
			playingField.removeToken(token);
		}
	}


	
	
	/**
	 * Removes the token cost.
	 *
	 * @throws RemoteException
	 */
	private void removeTokenCost() throws RemoteException {
		Map<Gem, Integer> playerGems = player.getTokensGemCount();
		Map<Gem, Integer> costs = tempHand.getBoughtCard().getCosts();
		
		for(Map.Entry<Gem, Integer> cost : costs.entrySet()) {
			int[] totalTokensNeeded = this.neededTokens(cost, playerGems);
			int tokensNeeded = totalTokensNeeded[0];
			int jokersNeeded =  totalTokensNeeded[1];
			List<Token> tokensToPlayingField= new ArrayList<>();
			
			for(int i = 0; i < tokensNeeded; i++) {
				for(Token token : player.getTokens()) {
					if(token.getGemType() == cost.getKey()) {
						tokensToPlayingField.add(token);
						player.getTokens().remove(token);
						break;
					}
				}
			}
			
			for(int i = 0; i < jokersNeeded; i++) {
				for(Token token : player.getTokens()) {
					if(token.getGemType() == Gem.JOKER) {
						tokensToPlayingField.add(token);
						player.getTokens().remove(token);
						break;
					}
				}
			}	
			
			playingField.addTokens(tokensToPlayingField);
		}
	}
	
	/**
	 * Needed tokens.
	 *
	 * @param cost
	 * @param playerGems
	 * @return totalTokensNeeded
	 * @throws RemoteException
	 */
	private int[] neededTokens(Map.Entry<Gem, Integer> cost, Map<Gem, Integer> playerGems) throws RemoteException {
		int[] totalTokensNeeded = new int[3];
		int jokersNeeded = 0;
		int tokensNeeded = 0;
		int bonus = player.getBonus().get(cost.getKey());
		int costAmount = cost.getValue();
		int playerTokens = playerGems.get(cost.getKey());
		
		if(playerTokens < (costAmount - bonus)) {
			tokensNeeded = playerTokens;
			jokersNeeded = costAmount - bonus - tokensNeeded;
		} else {
			tokensNeeded = costAmount - bonus;
		}
		
		totalTokensNeeded[0] = tokensNeeded;
		totalTokensNeeded[1] = jokersNeeded;
		
		return totalTokensNeeded;
	}
	
	/**
	 * Clean up turn.
	 * Emptys's the temphand
	 *
	 * @throws RemoteException
	 */
	private void cleanUpTurn() throws RemoteException {
		tempHand.emptyHand();
		game.cleanUpSelections();
		
	}
}
