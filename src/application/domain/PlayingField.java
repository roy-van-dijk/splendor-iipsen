package application.domain;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.LinkedHashMap;
import java.util.List;

public interface PlayingField extends Remote {
	
	public List<CardRow> getCardRows() throws RemoteException;
	
	public List<Noble> getNobles() throws RemoteException;
	
	public LinkedHashMap<Gem, Integer> getTokenGemCount() throws RemoteException;
	
	public List<Gem> getSelectableTokens() throws RemoteException;
	
	public void findSelectableCardsFromField() throws RemoteException;
	
	public List<Card> getSelectableCardsFromField() throws RemoteException; 
	
	public Turn getTurn() throws RemoteException;

	public void addTokenToTemp(Gem gemType) throws RemoteException;
	
	public void addObserver(PlayingFieldObserver observer) throws RemoteException;

	public void addTokens(List<Token> tokens) throws RemoteException;

	public void setTokensSelectable() throws RemoteException;
	
	public void newTurn() throws RemoteException;

}
