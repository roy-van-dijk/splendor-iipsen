package application.domain;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EndTurn {
	private Game game;
	
	//check if a noble can visit a player after pressing end turn
	public void checkNobleVisits() throws RemoteException{
		
		List<Noble> allNobles= game.getPlayingField().getNobles();
		Map<Gem, Integer> totalBonusGems = game.getCurrentPlayer().getBonus();
		
		//make a empty list of nobles, and add all the nobles of the playingfield that can visit
		List<Noble> visitingNobles = checkPlayingFieldNobles(allNobles, totalBonusGems);
		
		//add all the visiting nobles to the players noble list
		for(Noble noble : visitingNobles)
		game.getCurrentPlayer().addNoble(noble);
	}
	
	//add the playingFieldNobles to the previously made List<Noble>visitingNobles.
	public List<Noble> checkPlayingFieldNobles(List<Noble> allNobles, Map<Gem, Integer> totalBonusGems) throws RemoteException {
		
		List<Noble> visitingNobles = new ArrayList<Noble>();
		
		//loop through all the playingFieldNobles
		for(Noble noble : allNobles) 
		{
			//get the cost of the current noble and compare cost and player bonuses in another function
			Map<Gem, Integer> nobleCost = noble.getRequirements();
			if(compareNobleAndBonusGems(nobleCost, totalBonusGems) == true) 
			{
				visitingNobles.add(noble);
			}
			
		} return visitingNobles;
		
	}
	
	//check if a player has enough bonus gems for the noble to visit with a boolean.
	//boolean by default on true. if a noble has to high a price for 1 of the gemTypes put boolean on false
	//return true or false
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
}
