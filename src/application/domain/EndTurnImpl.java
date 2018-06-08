package application.domain;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import application.controllers.ReturnTokenController;

public class EndTurnImpl implements EndTurn {
	
	private Game game;
	
	public EndTurnImpl(Game game) {
		this.game = game;
	}

	@Override
	public void checkNobleVisits() throws RemoteException{
		
		List<Noble> allNobles= game.getPlayingField().getNobles();
		Map<Gem, Integer> totalBonusGems = game.getCurrentPlayer().getBonus();

		List<Noble> visitingNobles = checkPlayingFieldNobles(allNobles, totalBonusGems);
		
		for(Noble noble : visitingNobles)
		game.getCurrentPlayer().addNoble(noble);
		
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
		boolean nobleWillVisit = true;
		for(Gem gemType : Gem.values())
		{
			if(nobleCost.get(gemType) > totalBonusGems.get(gemType))
			{
				nobleWillVisit = false;
			}
		}
		return nobleWillVisit;	
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
		
		if(temphand.getSelectedTokensCount() != 0) {
			this.getTokens();
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


		
		
		//TODO take three token
		/*
		if (temphand.getPlayer().getTokens() != null) {
			for(Token token : temphand.getPlayer().getTokens()) {
				player.addToken(token);
			}
		}*/
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
}
