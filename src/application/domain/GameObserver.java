package application.domain;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GameObserver extends Remote {
	public void modelChanged(Game game) throws RemoteException;
	public void setDisabled(boolean disabled) throws RemoteException;
	public void disableEndTurn(boolean disabled) throws RemoteException;
}
