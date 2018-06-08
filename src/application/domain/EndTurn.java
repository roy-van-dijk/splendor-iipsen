package application.domain;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


public interface EndTurn {

	void checkNobleVisits() throws RemoteException;

	List<Noble> checkPlayingFieldNobles(List<Noble> allNobles, Map<Gem, Integer> totalBonusGems) throws RemoteException;

	boolean compareNobleAndBonusGems(Map<Gem, Integer> nobleCost, Map<Gem, Integer> totalBonusGems);

	void getTokens() throws RemoteException;

	void endTurn() throws RemoteException;

	void cleanUpTurn() throws RemoteException;

	int[] neededTokens(Entry<Gem, Integer> cost, Map<Gem, Integer> playerGems, Player player) throws RemoteException;

}
