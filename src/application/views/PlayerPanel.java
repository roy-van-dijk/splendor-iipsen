package application.views;

import java.util.LinkedHashMap;
import java.util.Map;

import application.domain.Gem;
import application.domain.Player;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
/**
 * 
 * @author Sanchez
 *
 */
public class PlayerPanel implements UIComponent {

	private Pane root;
	private Player player;
	
	public PlayerPanel(Player player) {
		this.player = player;
		
		this.buildUI();
	}
	
	private void buildUI()
	{
		root = new HBox(25);
		root.setPadding(new Insets(15, 25, 15, 25));
		root.getStyleClass().add("player");
		
		// Player prestige
		Label prestigeLabel = new Label(String.valueOf(player.getPrestige()));
		prestigeLabel.setAlignment(Pos.CENTER);
		prestigeLabel.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 50));
		prestigeLabel.getStyleClass().add("prestige");
		StackPane prestige = new StackPane(prestigeLabel);
		
		GridPane playerTokens = buildTokensDisplay();
		
		Separator sep = new Separator();
		sep.setOrientation(Orientation.VERTICAL);
		sep.setValignment(VPos.CENTER);
		//sep.setPrefHeight(80);
		
		root.getChildren().addAll(prestige, sep, playerTokens);
	}
	

	private GridPane buildTokensDisplay()
	{
		GridPane tokensGrid = new GridPane();
		tokensGrid.setVgap(2);
		tokensGrid.setHgap(15);
		
		LinkedHashMap<Gem, Integer> gemsCount = player.getTokenList().getTokenGemCount();
		
		int col = 0, row = 0;	
		for(Map.Entry<Gem, Integer> entry : gemsCount.entrySet())
		{	
			HBox tokenGemCountDisplay = createTokenGemCountDisplay(entry.getKey(), entry.getValue(), GameView.tokenSizeRadius / 2);
			tokensGrid.add(tokenGemCountDisplay, col % 2, row % 3);
			col++;
			row++;
		}
		
		return tokensGrid;
	}
	
	private HBox createTokenGemCountDisplay(Gem gemType, int count, int radius)
	{
		TokenView tokenView = new TokenView(gemType, radius);
        
        Label tokenCountLabel = new Label(String.valueOf(count));
        tokenCountLabel.setAlignment(Pos.CENTER);
        tokenCountLabel.getStyleClass().add("token-count");	
        tokenCountLabel.setFont(Font.font(radius * 2));
        
		HBox tokenRow = new HBox(10, tokenView.asPane(), tokenCountLabel);
		tokenRow.setAlignment(Pos.CENTER);
		
		return tokenRow;
	}

	

	public Pane asPane() {
		return root;
	}

}
