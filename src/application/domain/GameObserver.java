package application.domain;

import java.rmi.Remote;
import java.rmi.RemoteException;

import application.views.Disableable;

public interface GameObserver extends Remote {
	public void modelChanged(Game game) throws RemoteException;
}
