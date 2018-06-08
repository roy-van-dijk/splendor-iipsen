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

public class EndTurnImpl implements EndTurn, Serializable {
	
	private Game game;
	private PlayingField playingField;
	private TempHand tempHand;
	private Player player;
	
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
		List<Token> tokenlist = playingField.getTokenList().getAll();
		//TokenList newtokenlist =  game.getPlayingField().getTokenList();
		TempHand temphand = playingField.getTempHand();
		TokenList newtokenlist1 = new TokenList();
			for(Gem gem: temphand.getSelectedGemTypes()) {
				
				Token token = new TokenImpl(gem);

				newtokenlist1.add(token);
				System.out.println();
				player.addToken(token);
				playingField.removeToken(token);
			}
		
	}

	@Override
	public void endTurn() throws RemoteException {

		this.getCurrentPlayer();
		
		TempHand tempHand = playingField.getTempHand();
		MoveType moveType = tempHand.getMoveType();
		
		
		if(moveType == MoveType.PURCHASE_CARD) {
			/**
			 * Add temphand cards to the player
			 */
			Card boughtCard = tempHand.getBoughtCard();
			game.cleanUpSelections();
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
			player.addReserverveCard(tempHand.getReservedCard());
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
