package application.views;

import java.rmi.Remote;
import java.rmi.RemoteException;
/**
 * interface to make it able to disable something.
 * @author Tom de Jong labtop
 *
 */
public interface Disableable extends Remote {
	public void setDisabled(boolean disabled) throws RemoteException;
}
