package application.views;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Map;

import application.domain.Card;
import application.domain.Game;
import application.domain.Gem;
import application.domain.Player;
import application.domain.PlayerObserver;
import application.util.ImageCache;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * View with opponent information seen on the left side of the game view.
 *
 * @author Sanchez
 */
public class OpponentView extends UnicastRemoteObject implements UIComponent, PlayerObserver {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1628350266144323015L;
	
	
	private final static double CARDS_RESIZE_FACTOR = 0.55;
	private final static double TOKENS_RESIZE_FACTOR = 0.55;

	private Pane root;

	private HBox topFrame;
	private HBox reservedCardsFrame;
	private HBox tokensFrame;
	private HBox bonusFrame;

	private Label lblOpponentName;
	private Label lblOpponentPrestige;

	/**
	 * Creates a new opponent view.
	 *
	 * @param opponent the opponent
	 * @throws RemoteException the remote exception
	 */
	public OpponentView(Player opponent) throws RemoteException {
		this.buildUI();

		opponent.addObserver(this);
	}

	/**
	 * Builds the UI.
	 */
	private void buildUI() {
		topFrame = buildOpponentHeaderDisplay();
		this.updateCurrentPlayer(false);

		reservedCardsFrame = new HBox();
		reservedCardsFrame.setAlignment(Pos.CENTER);
		reservedCardsFrame.setPadding(new Insets(5));

		tokensFrame = new HBox();
		tokensFrame.setAlignment(Pos.CENTER);
		
		bonusFrame = new HBox();
		bonusFrame.setAlignment(Pos.CENTER);

		root = new VBox(topFrame, tokensFrame, bonusFrame, reservedCardsFrame);
		root.getStyleClass().add("opponent");
	}
	
	public void updateCurrentPlayer(boolean isNowPlaying) {
		if(isNowPlaying) {
			topFrame.setStyle("-fx-background-color: rgb(43, 57, 163);");
		} else {
			topFrame.setStyle("-fx-background-color: rgb(43, 57, 63);");
		}
	}
	

	/* (non-Javadoc)
	 * @see application.domain.PlayerObserver#modelChanged(application.domain.Player)
	 */
	public void modelChanged(Player opponent) throws RemoteException {
		
		Platform.runLater(() ->
		{
			try {
				lblOpponentName.setText(opponent.getName());
				lblOpponentPrestige.setText(String.valueOf(opponent.getPrestige()));
				
				this.updateOpponentTokens(opponent.getTokensGemCount());
				this.updateOpponentBonus(opponent.getBonus());
				this.updateOpponentsReservedCards(opponent.getReservedCards());
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}

	/**
	 * Update opponents reserved cards.
	 *
	 * @param opponentReservedCards
	 */
	private void updateOpponentsReservedCards(List<Card> opponentReservedCards) {
		reservedCardsFrame.getChildren().clear();
		for (Card reservedCard : opponentReservedCards) {
			CardView cardView;
			try {
				if (reservedCard.isReservedFromDeck()) {
					cardView = new RearCardView(reservedCard, GameView.cardSizeX * CARDS_RESIZE_FACTOR, GameView.cardSizeY * CARDS_RESIZE_FACTOR);
				} else {
					cardView = new FrontCardView(reservedCard, GameView.cardSizeX * CARDS_RESIZE_FACTOR, GameView.cardSizeY * CARDS_RESIZE_FACTOR);
				}

				StackPane paneCard = new StackPane(cardView.asPane());
				paneCard.setAlignment(Pos.CENTER);
				HBox.setHgrow(paneCard, Priority.ALWAYS);

				reservedCardsFrame.getChildren().add(paneCard);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	/**
	 * Update opponent tokens.
	 *
	 * @param gemsCount
	 */
	private void updateOpponentTokens(Map<Gem, Integer> gemsCount) {
		tokensFrame.getChildren().clear();

		for (Map.Entry<Gem, Integer> entry : gemsCount.entrySet()) {
			StackPane tokenGemCountDisplay = createTokenGemCountDisplay(entry.getKey(), entry.getValue(), GameView.tokenSizeRadius);
			tokensFrame.getChildren().add(tokenGemCountDisplay);
		}
	}
	
	/**
	 * Update opponent bonus.
	 *
	 * @param bonus
	 */
	private void updateOpponentBonus(Map<Gem, Integer> bonus) {
		bonusFrame.getChildren().clear();

		for (Map.Entry<Gem, Integer> entry : bonus.entrySet()) {
			StackPane bonusDisplay = createBonusDisplay(entry.getKey(), entry.getValue());
			bonusFrame.getChildren().add(bonusDisplay);
		}
	}

	/**
	 * Builds the opponent header display.
	 *
	 * @return HBox
	 */
	private HBox buildOpponentHeaderDisplay() {
		HBox topFrame = new HBox(10);

		topFrame.setAlignment(Pos.CENTER);

		lblOpponentName = new Label();
		lblOpponentName.setAlignment(Pos.CENTER_LEFT);
		lblOpponentName.getStyleClass().add("opponent-name");

		Pane spacer = new Pane();
		HBox.setHgrow(spacer, Priority.ALWAYS); // Give prestige points any extra space

		lblOpponentPrestige = new Label();
		lblOpponentPrestige.setAlignment(Pos.CENTER_RIGHT);
		lblOpponentPrestige.getStyleClass().add("prestige");

		topFrame.getChildren().addAll(lblOpponentName, spacer, lblOpponentPrestige);

		return topFrame;
	}

	/**
	 * Creates the token gem count display.
	 *
	 * @param gemType
	 * @param count
	 * @param radius
	 * @return StackPane
	 */
	private StackPane createBonusDisplay(Gem gemType, int count) {
		
		Rectangle r = new Rectangle(45, 45);
		String imagePath = String.format("resources/gems/%s.png", gemType.name().toLowerCase());
		Image image = ImageCache.getInstance().fetchImage(imagePath, true);
        ImagePattern imagePattern = new ImagePattern(image);
        
        r.setFill(imagePattern);
        r.setOpacity(0.5);

		Label bonusLabel = new Label(String.valueOf(count));
		bonusLabel.setAlignment(Pos.CENTER);
		bonusLabel.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 25));
		bonusLabel.setTextFill(Color.WHITE);
		bonusLabel.getStyleClass().add("dropshadow");
		
		StackPane bonusFrame = new StackPane(r, bonusLabel);
		bonusFrame.setAlignment(Pos.CENTER);
		bonusFrame.setPadding(new Insets(5));
		HBox.setHgrow(bonusFrame, Priority.ALWAYS);

		return bonusFrame;
	}
	
	/**
	 * Creates the bonus display.
	 *
	 * @param gemType
	 * @param count
	 * @param radius
	 * @return VBox
	 */
	private StackPane createTokenGemCountDisplay(Gem gemType, int count, int radius) {
		TokenView tokenView = new TokenView(gemType, radius * TOKENS_RESIZE_FACTOR);

		tokenView.asPane().setOpacity(0.5);
		
		Label tokenCountLabel = new Label(String.valueOf(count));
		tokenCountLabel.setAlignment(Pos.CENTER);
		tokenCountLabel.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 25));
		tokenCountLabel.setTextFill(Color.WHITE);
		
		StackPane tokenFrame = new StackPane(tokenView.asPane(), tokenCountLabel);
		tokenFrame.setAlignment(Pos.CENTER);
		tokenFrame.setPadding(new Insets(5));
		HBox.setHgrow(tokenFrame, Priority.ALWAYS);

		return tokenFrame;
	}
	
	public String getPlayerName() {
		return lblOpponentName.getText();
	}

	/* (non-Javadoc)
	 * @see application.views.UIComponent#asPane()
	 */
	public Pane asPane() {
		return root;
	}

}
