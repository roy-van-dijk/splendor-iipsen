package application.views;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Disableable extends Remote {
	public void setDisabled(boolean disabled) throws RemoteException;
}
