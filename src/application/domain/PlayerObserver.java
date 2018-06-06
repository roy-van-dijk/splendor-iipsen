package application.domain;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface PlayerObserver extends Remote {
	public void modelChanged(Player player) throws RemoteException;
}
