package application.domain;

import java.rmi.RemoteException;

public interface PlayerObserver {
	public void modelChanged(Player player) throws RemoteException;
}
