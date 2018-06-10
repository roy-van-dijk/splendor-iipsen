package application.domain;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


public interface EndTurn extends Remote {

	void endTurn() throws RemoteException;

}
