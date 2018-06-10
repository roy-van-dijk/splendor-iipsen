package application.controllers;

import java.rmi.RemoteException;

import application.StageManager;
import application.domain.Game;
import application.domain.Lobby;
import application.domain.LobbyObserver;
import application.domain.PlayerSlot;
import application.views.GameView;
import application.views.LobbyView;

/**
 * @author Roy
 *
 */
public class LobbyController {

	private Lobby lobby;
	
	public LobbyController(Lobby lobby) {
		this.lobby = lobby;
	}
	/**
	 * Shows the GameScreen
	 * @throws RemoteException
	 * void
	 */
	public void showGameScreen(LobbyObserver o) throws RemoteException
	{
		Game game = lobby.getGame();
		GameController gameController = new GameController(game);
		GameView gameView = new GameView(game, gameController, lobby.getAssignedPlayer(o));
		
		StageManager.getInstance().switchScene(gameView.asPane());
	}
	
	public void selectSlot(LobbyObserver o, PlayerSlot slot) throws RemoteException
	{
		lobby.selectSlot(o, slot);
	}
	
	public void readyUp(LobbyObserver o) throws RemoteException
	{
		lobby.readyPlayer(o);
	}
	
	public void leaveLobby(LobbyObserver o) throws RemoteException
	{
		lobby.leaveLobby(o);
		StageManager.getInstance().showMainMenu();
	}

	/**
	 * 
	 * @param playerName
	 * @throws RemoteException
	 * void
	 */
	public void connectPlayer(LobbyObserver o, String playerName) throws RemoteException {
		lobby.createPlayer(o, playerName);
	}
	public void unassignPlayer(LobbyObserver o) throws RemoteException {
		lobby.unassignPlayer(o);
	}
}
