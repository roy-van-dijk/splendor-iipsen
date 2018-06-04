package application.domain;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface PlayingFieldObserver extends Remote {
	public void modelChanged(PlayingField playingField) throws RemoteException;
}
