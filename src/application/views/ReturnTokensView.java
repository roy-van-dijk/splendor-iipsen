package application.views;

import java.rmi.RemoteException;
import java.util.LinkedHashMap;
import java.util.Map;

import application.domain.Game;
import application.domain.Gem;
import application.domain.Player;
import application.domain.PlayingField;
import application.domain.PlayingFieldImpl;
import application.domain.TokenList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
/**
 * 
 * @author Tom
 *
 */
public class ReturnTokensView {
	
	private BorderPane pane;
	private Player player;
	private PlayingField playingField;

	public ReturnTokensView(Game game) throws RemoteException {
	
	pane  = new BorderPane();
	player = game.getCurrentPlayer();
	playingField = game.getPlayingField();
	
	Scene scene = new Scene(pane, 900, 350);
	Stage stage = new Stage();
	
	Label labelText = new Label("You hvae more than 10 tokens. Please discard tokens until you have 10.");	
	HBox returnTokenDiscription = new HBox(labelText);
	
	HBox gemcounterDisplay = buildGemcounterDisplay();
	HBox confirmBox = new HBox();
	
	Button confirmButton = new Button("Confirm");
	
	scene.getStylesheets().add(getClass().getResource("../application.css").toExternalForm());
	
	pane.setTop(returnTokenDiscription);
	returnTokenDiscription.setAlignment(Pos.CENTER);
	labelText.setFont(Font.font(26.0));
	
	pane.setBottom(confirmBox);
	confirmBox.getChildren().add(confirmButton);
	confirmBox.setAlignment(Pos.CENTER);
	
	pane.setCenter(gemcounterDisplay);
	gemcounterDisplay.setAlignment(Pos.CENTER);
	
	pane.setPrefHeight(300);
	pane.setPrefWidth(400);
			
	stage.setScene(scene);
	stage.setTitle("Return tokens");
	stage.setResizable(false);
	stage.show();
	
	
	}

	//big hbox placed in the middle of the pane with in it up to 6 for the gems of the player.
	private HBox buildGemcounterDisplay() throws RemoteException {
		HBox gemcounterDisplay = new HBox();
		gemcounterDisplay.setAlignment(Pos.CENTER);
		//setVgrow may be needed, test without first.
		//VBox.setVgrow(gemcounterDisplay, Priority.ALWAYS);
		
		// NULL POINTER EXCEPTION!!!! /// get the player tokens.
		//TokenList gemsCountList = player.getTokenList();
		//LinkedHashMap<Gem, Integer> gemsCount = gemsCountList.getTokenGemCount();
		
		//TODO switch out gemcount below with commented lines above. gems of playingField uses for testing.
		//System.out.println("hiergaathetmis");
		LinkedHashMap<Gem, Integer> gemsCount = playingField.getTokenGemCount();
		
		//Create a vbox for every gem with curent amount of player.
		for(Map.Entry<Gem, Integer> entry : gemsCount.entrySet()) {	
			VBox gemBox = createTokenGemCountBox(entry.getKey(), entry.getValue(), GameView.tokenSizeRadius);
			gemcounterDisplay.getChildren().add(gemBox);	
		}
		return gemcounterDisplay;
	}

	// the display of a gem type and amount of tokens of that type owned, with buttons to remove and add. 
	private VBox createTokenGemCountBox(Gem gemType, int count, int tokenSizeRadius) {
		
		//buttons to edit amount of coins and confirmation of the turn.
		//TODO add Button action events.
		Button removeTokenButton = new Button("-");
		Button returnTokenButton = new Button("+");
		
		TokenView tokenView = new TokenView(gemType, tokenSizeRadius);
        Label tokenCountLabel = new Label(String.valueOf(count));
        
        tokenCountLabel.getStyleClass().add("token-count");	
        tokenCountLabel.setFont(Font.font(tokenSizeRadius * 2));
        
		VBox tokenColum = new VBox(tokenView.asPane(),returnTokenButton, tokenCountLabel, removeTokenButton);
        tokenColum.setAlignment(Pos.CENTER);
        
		return tokenColum;
	}
}