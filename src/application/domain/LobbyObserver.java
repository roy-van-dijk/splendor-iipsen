package application.domain;

import java.rmi.Remote;
import java.rmi.RemoteException;

import application.domain.LobbyImpl.LobbyStates;

public interface LobbyObserver extends Remote {

	public void modelChanged(Lobby lobby) throws RemoteException;
	
	public void disconnect(LobbyStates lobbyState) throws RemoteException;
}
