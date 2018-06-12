package application.views;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Map;

import application.domain.Card;
import application.domain.Gem;
import application.domain.Player;
import application.domain.PlayerObserver;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
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

	private HBox reservedCardsFrame;
	private HBox tokensFrame;

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
		HBox topFrame = buildOpponentHeaderDisplay();

		reservedCardsFrame = new HBox();
		reservedCardsFrame.setAlignment(Pos.CENTER);
		reservedCardsFrame.setPadding(new Insets(10));

		tokensFrame = new HBox();
		tokensFrame.setAlignment(Pos.CENTER);

		root = new VBox(topFrame, tokensFrame, reservedCardsFrame);
		root.getStyleClass().add("opponent");
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
			VBox tokenGemCountDisplay = createTokenGemCountDisplay(entry.getKey(), entry.getValue(), GameView.tokenSizeRadius);
			tokensFrame.getChildren().add(tokenGemCountDisplay);
		}
	}

	/**
	 * Builds the opponent header display.
	 *
	 * @return HBox
	 */
	private HBox buildOpponentHeaderDisplay() {
		HBox topFrame = new HBox(10);
		topFrame.getStyleClass().add("opponent-header");

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
	 * @return VBox
	 */
	private VBox createTokenGemCountDisplay(Gem gemType, int count, int radius) {
		TokenView tokenView = new TokenView(gemType, radius * TOKENS_RESIZE_FACTOR);

		Label tokenCountLabel = new Label(String.valueOf(count));
		tokenCountLabel.setAlignment(Pos.CENTER);
		tokenCountLabel.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 20));

		VBox tokenFrame = new VBox(10, tokenView.asPane(), tokenCountLabel);
		tokenFrame.setAlignment(Pos.CENTER);
		tokenFrame.setPadding(new Insets(5));
		HBox.setHgrow(tokenFrame, Priority.ALWAYS);

		return tokenFrame;
	}

	/* (non-Javadoc)
	 * @see application.views.UIComponent#asPane()
	 */
	public Pane asPane() {
		return root;
	}

}
