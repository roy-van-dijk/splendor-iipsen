package application.controllers;

import application.StageManager;
import javafx.application.Platform;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MenuControllerImpl implements MenuController {

	@Override
	public void joinLobby(Pane rootNode) {
		StageManager.getInstance().switchScene(rootNode);
	}

	@Override
	public void hostPreviousGame() {
//		StageManager.getInstance().showLobbyScreen(hostIp, nickname);
		
	}

	@Override
	public void hostNewGame() {
		// TODO Auto-generated method stub

	}


}
