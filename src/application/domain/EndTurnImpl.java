package application.domain;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import application.controllers.ReturnTokenController;
import application.services.SaveGameDAO;

// TODO: Kees Need to fill the method Endturn()
/**
 * The Class EndTurnImpl.
 */
public class EndTurnImpl implements EndTurn, Serializable {
	
	private Game game;
	private PlayingField playingField;
	private TempHand tempHand;
	private Player player;
	
	/**
	 * Instantiates a new end turn impl.
	 *
	 * @param game
	 */
	public EndTurnImpl(Game game) {
		this.game = game;
		try {
			playingField = game.getPlayingField();
			tempHand = playingField.getTempHand();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Gets the current player.
	 *
	 * @throws RemoteException
	 */
	private void getCurrentPlayer() throws RemoteException {
		this.player = game.getCurrentPlayer(); //TODO fix the name of method
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

	/* (non-Javadoc)
	 * @see application.domain.EndTurn#endTurn()
	 */
	@Override
	public void endTurn() throws RemoteException {

		this.getCurrentPlayer();
		MoveType moveType = tempHand.getMoveType();
		
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
			playingField.removeCard(tempHand.getReservedCard());
			for(CardRow cardRow : playingField.getCardRows()) {
				if(cardRow.getCardDeck().isSelected()) {
					cardRow.getCardDeck().pull();
				}
			}
			player.addReserverveCard(tempHand.getReservedCard());
			// TODO: Geef speler een joker
			if(playingField.getTokenGemCount().get(Gem.JOKER) > 0){
				Token token = new TokenImpl(Gem.JOKER);
				player.addToken(token);
				playingField.removeToken(token);
			}
		} else if(moveType == MoveType.TAKE_THREE_TOKENS || moveType == MoveType.TAKE_TWO_TOKENS) {
			this.getTokens();
		}
		/**
		 * Create the returntokens if the an player has moren then 10 tokens
		 */
		ReturnTokens model = new ReturnTokens(playingField, player);
		ReturnTokenController controller = new ReturnTokenController(model);

		List<Token> tokens = player.getTokens();
		
		if(tokens.size() > 10) {
			System.out.println("I'v got " + tokens.size() + " Tokens");
			model.moreThanTenTokens(model, controller);

		}
		
		this.checkNobleVisits();		
		this.cleanUpTurn();
		game.saveGame();	
		game.nextTurn();
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
			
			for(int i = 0; i < tokensNeeded; i++) {
				for(Token token : player.getTokens()) {
					if(token.getGemType() == cost.getKey()) {
						player.getTokens().remove(token);
						break;
					}
				}
			}
			
			for(int i = 0; i < jokersNeeded; i++) {
				for(Token token : player.getTokens()) {
					if(token.getGemType() == Gem.JOKER) {
						player.getTokens().remove(token);
						break;
					}
				}
			}	
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
