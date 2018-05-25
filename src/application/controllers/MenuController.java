package application.controllers;

import javafx.scene.layout.Pane;

/**
 * @author Roy
 *
 */
public interface MenuController {
	
	void joinLobby(Pane rootNode);

	void hostPreviousGame();

	void hostNewGame();
	

}
