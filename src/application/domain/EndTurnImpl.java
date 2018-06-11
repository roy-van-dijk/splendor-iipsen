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

public class EndTurnImpl extends UnicastRemoteObject implements EndTurn, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5685833325676205128L;
	private Game game;
	private PlayingField playingField;
	private TempHand tempHand;
	private Player player;
	
	public EndTurnImpl(Game game) throws RemoteException {
		this.game = game;
		
		this.playingField = game.getPlayingField();
		this.tempHand = playingField.getTempHand();
	}
	
	private void getCurrentPlayer() throws RemoteException {
		this.player = game.getCurrentPlayer();
	}

	private void checkNobleVisits() throws RemoteException{
		
		List<Noble> allNobles= playingField.getNobles();
		Map<Gem, Integer> totalBonusGems = player.getBonus();

		List<Noble> visitingNobles = checkPlayingFieldNobles(allNobles, totalBonusGems);
		
		for(Noble noble : visitingNobles) {
			player.addNoble(noble);
			playingField.getNobles().remove(noble);
		}
		
	}
	
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

	private boolean compareNobleAndBonusGems(Map<Gem, Integer> nobleCost, Map<Gem, Integer> totalBonusGems) {
		for(Map.Entry<Gem, Integer> nobleCostEntry : nobleCost.entrySet()) {
		//for(Gem gemType : Gem.values()) 
			if(nobleCostEntry.getValue() > totalBonusGems.get(nobleCostEntry.getKey())) {
				return false;
			}
		}
		return true;	
	}

	private void getTokens() throws RemoteException {
			for(Gem gem: tempHand.getSelectedGemTypes()) {			
				Token token = new TokenImpl(gem);
				player.addToken(token);
				playingField.removeToken(token);
			}
		
	}

	@Override
	public void endTurn() throws RemoteException {

		this.getCurrentPlayer();
		MoveType moveType = tempHand.getMoveType();
		
		if(moveType == MoveType.PURCHASE_CARD) {
			/**
			 * Add temphand cards to the player
			 */
			Card boughtCard = tempHand.getBoughtCard(); // TODO: This is a proxy from clients. 
			if(player.getReservedCards().contains(boughtCard)) {
				player.getReservedCards().remove(boughtCard);
			} else {
				playingField.removeCard(boughtCard); // removeCard doesn't work with proxies
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
		// TODO: needs rewrite
		ReturnTokens model = new ReturnTokens(playingField, player);
		ReturnTokenController controller = new ReturnTokenController(model);

		List<Token> tokens = player.getTokens();
		
		if(tokens.size() > 10) {
			System.out.println("I've got " + tokens.size() + " Tokens");
			//model.moreThanTenTokens(model, controller);
			/*
			 * TODO: Figure out a way to create the ReturnTokenView locally (only for the Player/GameObserver that has too many tokens)
			 */

		}
		
		this.checkNobleVisits();		
		this.cleanUpTurn();
		game.saveGame();	
		game.nextTurn();
	}
	
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
	
	private void cleanUpTurn() throws RemoteException {
		tempHand.emptyHand();
		game.cleanUpSelections();
		
	}
}
