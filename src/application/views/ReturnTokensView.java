package application.views;

import java.rmi.RemoteException;
import java.util.LinkedHashMap;
import java.util.Map;

import application.Main;
import application.controllers.ReturnTokenController;
import application.domain.Gem;
import application.domain.ReturnTokens;
import application.domain.ReturnTokens.ReturnTokenState;
import application.util.Util;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * The Class ReturnTokensView.
 *
 * @author Tom
 */
public class ReturnTokensView {

	private BorderPane pane;

	private Stage stage;
	private HBox gemCounterDisplay;
	private Button confirmButton;

	private ReturnTokenController returnTokenController;

/**
 * Instantiates a new return tokens view.
 *
 * @param returnTokens refers to model return tokens
 * @param returnTokenController the return token controller
 * @throws RemoteException the remote exception
 */
	public ReturnTokensView(ReturnTokens returnTokens, ReturnTokenController returnTokenController) {
		this.returnTokenController = returnTokenController;
		this.pane = new BorderPane();

		Label labelText = new Label("You have more than 10 tokens. Please discard tokens until you have 10.");
		labelText.setFont(Font.font(23.0));

		gemCounterDisplay = new HBox(); // big hbox placed in the middle of the pane with in it up to 6 for the gems of the player.
		gemCounterDisplay.setAlignment(Pos.CENTER);

		confirmButton = new Button("Confirm");
		confirmButton.setOnAction(e -> {
			try {
				returnTokenController.confirmButton();
				stage.close();
				stage.hide();//TODO moet weg
			} catch (RemoteException e1) {
				
				stage.hide();//TODO moet weg
				e1.printStackTrace();
			}
			stage.close();
		});

		HBox confirmBox = new HBox(confirmButton);
		confirmBox.setAlignment(Pos.CENTER);

		BorderPane.setAlignment(labelText, Pos.CENTER);

		pane.setTop(labelText);
		pane.setCenter(gemCounterDisplay);
		pane.setBottom(confirmBox);
		pane.setPadding(new Insets(100));
		pane.setMaxWidth(900);
		pane.setMaxHeight(550);
		
		BorderPane bpane = new BorderPane();
		bpane.setCenter(pane);
		bpane.getStyleClass().add("modal");

		Scene scene = new Scene(bpane, 1000, 700);
		scene.getStylesheets().add(Main.class.getResource(Util.getCSSname()).toExternalForm());
		scene.setFill(Color.TRANSPARENT);

		stage = new Stage();
		stage.setScene(scene);
		stage.setTitle("Return tokens");
		stage.initStyle(StageStyle.TRANSPARENT);
		stage.setResizable(false);
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.show();

		returnTokens.registrate(this);
	}
	
/**
 * model changed, update token gem count and update button, or close view
 * @param returnTokens
 */
	public void modelChanged(ReturnTokens returnTokens) {
		Platform.runLater(() -> 
		{
			try {
				if(returnTokens.getReturningState() == ReturnTokenState.DONE) {  
					stage.close();
				} else {
					this.updateTokenGemCounts(returnTokens);
					this.updateConfirmButton(returnTokens);
				}
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}
	
	/**
	 * Update confirm button.
	 *
	 * @param returnTokens
	 */
	private void updateConfirmButton(ReturnTokens returnTokens) {
		confirmButton.setDisable(!returnTokens.isAllowConfirm());
	}
	
	/**
	 * Update token gem counts.
	 *
	 * @param returnTokens
	 * @throws RemoteException
	 */
	private void updateTokenGemCounts(ReturnTokens returnTokens) throws RemoteException {
		gemCounterDisplay.getChildren().clear();

		LinkedHashMap<Gem, Integer> gemsCount = returnTokens.getTokenListNew().getTokenGemCount();

		// Create a vbox for every gem with curent amount of player.
		for (Map.Entry<Gem, Integer> entry : gemsCount.entrySet()) {
			VBox gemBox = createTokenGemCountBox(entry.getKey(), entry.getValue(), GameView.tokenSizeRadius);
			gemBox.setPadding(new Insets(10));
			gemCounterDisplay.getChildren().add(gemBox);

		}
	}
	//TODO what's this comment? 
	// the display of a gem type and amount of tokens of that type owned
	/**
	 * Creates the token gem count box.
	 *
	 * @param gemType
	 * @param count
	 * @param tokenSizeRadius
	 * @return VBox
	 */
	// with buttons to edit the tokens you want to return
	private VBox createTokenGemCountBox(Gem gemType, int count, int tokenSizeRadius) {

		// buttons to edit amount of coins and confirmation of the turn.
		Button minusTokenButton = new Button("-");
		Button plusTokenButton = new Button("+");

		minusTokenButton.setMinWidth(85);
		plusTokenButton.setMinWidth(85);

		TokenView tokenView = new TokenView(gemType, tokenSizeRadius);
		tokenView.asPane().setPadding(new Insets(10));

		Label tokenCountLabel = new Label(String.valueOf(count));

		minusTokenButton.setOnAction(e -> {
			try {
				returnTokenController.minusToken(gemType);
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
		});

		plusTokenButton.setOnAction(e -> {
			try {
				returnTokenController.plusToken(gemType);
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
		});

		tokenCountLabel.getStyleClass().add("token-count");
		tokenCountLabel.setFont(Font.font(tokenSizeRadius * 2));

		VBox tokenColumn = new VBox(tokenView.asPane(), plusTokenButton, tokenCountLabel, minusTokenButton);
		tokenColumn.setAlignment(Pos.CENTER);

		return tokenColumn;
	}
}
