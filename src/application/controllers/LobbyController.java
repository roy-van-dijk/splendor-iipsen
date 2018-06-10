package application.controllers;

import java.rmi.RemoteException;

import application.StageManager;
import application.domain.Game;
import application.domain.Lobby;
import application.views.GameView;

/**
 * The Class LobbyController.
 *
 * @author Roy
 */
public class LobbyController {

	private Lobby lobby;
	
	/**
	 * Instantiates a new lobby controller.
	 *
	 * @param lobby
	 */
	public LobbyController(Lobby lobby) {
		this.lobby = lobby;
	}
	
	/**
	 * Shows the GameScreen.
	 *
	 * @throws RemoteException
	 */
	public void showGameScreen() throws RemoteException
	{
		Game game = lobby.getGame();
		GameController gameController = new GameController(game);
		GameView gameView = new GameView(game, gameController);
		
		StageManager.getInstance().switchScene(gameView.asPane());
	}

	/**
	 * Connect player.
	 *
	 * @param playerName
	 * @throws RemoteException
	 */
	public void connectPlayer(String playerName) throws RemoteException {
		lobby.createPlayer(playerName);
	}
}
