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
	
	public EndTurnImpl(Game game) {
		this.game = game;
	}

	@Override
	public void checkNobleVisits() throws RemoteException{
		
		List<Noble> allNobles= game.getPlayingField().getNobles();
		Map<Gem, Integer> totalBonusGems = game.getCurrentPlayer().getBonus();

		List<Noble> visitingNobles = checkPlayingFieldNobles(allNobles, totalBonusGems);
		
		for(Noble noble : visitingNobles) {
			game.getCurrentPlayer().addNoble(noble);
			game.getPlayingField().getNobles().remove(noble);
		}
		
	}
	

	@Override
	public List<Noble> checkPlayingFieldNobles(List<Noble> allNobles, Map<Gem, Integer> totalBonusGems) throws RemoteException {
		
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

	@Override
	public boolean compareNobleAndBonusGems(Map<Gem, Integer> nobleCost, Map<Gem, Integer> totalBonusGems) {
		for(Map.Entry<Gem, Integer> nobleCostEntry : nobleCost.entrySet()) {
		//for(Gem gemType : Gem.values()) 
			if(nobleCostEntry.getValue() > totalBonusGems.get(nobleCostEntry.getKey())) {
				return false;
			}
		}
		return true;	
	}

	@Override
	public void getTokens() throws RemoteException {
		List<Token> tokenlist = game.getPlayingField().getTokenList().getAll();
		PlayingField playingfield = game.getPlayingField();
		//TokenList newtokenlist =  game.getPlayingField().getTokenList();
		TempHand temphand = game.getPlayingField().getTempHand();
		Player player = game.getCurrentPlayer();
		TokenList newtokenlist1 = new TokenList();
			for(Gem gem: temphand.getSelectedGemTypes()) {
				
				Token token = new TokenImpl(gem);

				newtokenlist1.add(token);
				System.out.println();
				player.addToken(token);
				playingfield.removeToken(token);
			}
			//List<Token> listToken = newtokenlist.getAll();
			//	playingfield.removeTokens(newtokenlist1.getAll());
			//player.addTokens(newtokenlist);
		
	}

	@Override
	public void endTurn() throws RemoteException {

		PlayingField playingfield = game.getPlayingField();
		TempHand tempHand = game.getPlayingField().getTempHand();
		Player player = game.getCurrentPlayer();
		List<Noble> allNobles= game.getPlayingField().getNobles();
		Map<Gem, Integer> totalBonusGems = game.getCurrentPlayer().getBonus();
		/**
		 * Create the returntokens if the an player has moren then 10 tokens
		 */

		ReturnTokens model = new ReturnTokens(game.getPlayingField(), game.getCurrentPlayer());
		ReturnTokenController controller = new ReturnTokenController(model);
		
		if(tempHand.getSelectedTokensCount() != 0) {
			this.getTokens();
		}
		
		List<Token> tokens = game.getCurrentPlayer().getTokens();
		
		if(tokens.size() > 10) {
			System.out.println("I'v got " + tokens.size() + " Tokens");
			model.moreThanTenTokens(model, controller);

		}
		
		
		/**
		 * Adds the reservecard to the player
		 */
		if(tempHand.getReservedCard() != null) {
			CardRow row = game.getPlayingField().getCardRows().get(1); // Second row
			Card card = row.getCardSlots()[1];
			playingfield.removeCard(tempHand.getReservedCard());
			player.addReserverveCard(tempHand.getReservedCard());
			game.getCurrentPlayer().reserveCardFromField(row, card);
			
		}
		/**
		 * Add temphand cards to the player
		 */
		if(tempHand.getBoughtCard() != null) {
			game.cleanUpSelections();
			//player.returnTokensToField(removedTokens, playingfield);
			
			player.addCard(tempHand.getBoughtCard());

			Map<Gem, Integer> playerGems = player.getTokensGemCount();
			Map<Gem, Integer> costs = tempHand.getBoughtCard().getCosts();
			
			for(Map.Entry<Gem, Integer> cost : costs.entrySet()) {
				
				int[] totalTokensNeeded = this.neededTokens(cost, playerGems, player;
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
		
		//TODO take three token
		
		//game.getPlayers().get(game.getCurrentPlayerIdx()).getOwnedCards().add(game.getTurn().getBoughtCard());
		//TODO: subtract tokens from player.
		//game.getPlayers().get(game.getCurrentPlayerIdx()).getReservedCards().add(game.getTurn().getReservedCard());
		 
		//TODO: check for nobles
		this.checkNobleVisits();
		
		this.cleanUpTurn();
		game.saveGame();
		//TODO: Check win condition 
		//TODO: Determine next player
		
		game.nextTurn();
	}
	
	@Override 
	public int[] neededTokens(Map.Entry<Gem, Integer> cost, Map<Gem, Integer> playerGems, Player player) throws RemoteException {
		int[] totalTokensNeeded = new int[3];
		int jokersNeeded = 0;
		int tokensNeeded = 0;
		
		if(playerGems.get(cost.getKey()) < (cost.getValue() - player.getBonus().get(cost.getKey()))) {
			tokensNeeded = playerGems.get(cost.getKey());
			jokersNeeded -= playerGems.get(cost.getKey()) - (cost.getValue() - player.getBonus().get(cost.getKey()));
		} else {
			tokensNeeded = (cost.getValue() - player.getBonus().get(cost.getKey()));
		}
		
		totalTokensNeeded[0] = tokensNeeded;
		totalTokensNeeded[1] = jokersNeeded;
		
		System.out.println(cost.getKey() + " tokens: " + tokensNeeded);
		System.out.println(cost.getKey() + " jokers: " + jokersNeeded);
		
		return totalTokensNeeded;
	}
	
	@Override
	public void cleanUpTurn() throws RemoteException {
		game.getPlayingField().getTempHand().emptyHand();
		game.updatePlayingFieldCardsAndPlayerView();
		
	}
}
