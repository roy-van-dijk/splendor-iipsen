package application.domain;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface LobbyObserver extends Remote {

	public void modelChanged(Lobby lobby) throws RemoteException;
}
