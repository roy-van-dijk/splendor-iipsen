package application.controllers;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javafx.scene.layout.Pane;

/**
 * @author Roy
 *
 */
public interface MainMenuController {
	
	void joinLobby(String hostIP, String playerName) throws RemoteException, NotBoundException;

	void hostPreviousGame(String playerName) throws RemoteException;

	void hostNewGame(String playerName) throws RemoteException;
}
