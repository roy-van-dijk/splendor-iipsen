package application.domain;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EndTurn {
	private Game game;
	
	public void checkNobleVisits() throws RemoteException{
		
		List<Noble> allNobles= game.getPlayingField().getNobles();
		Map<Gem, Integer> totalBonusGems = game.getCurrentPlayer().getBonus();

		List<Noble> visitingNobles = checkPlayingFieldNobles(allNobles, totalBonusGems);
		
		for(Noble noble : visitingNobles)
		game.getCurrentPlayer().addNoble(noble);
		
	}
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
