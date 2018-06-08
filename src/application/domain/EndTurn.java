package application.domain;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


public interface EndTurn {

	void endTurn() throws RemoteException;

}
