package application.views;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import application.controllers.GameController;
import application.domain.Card;
import application.domain.ColorBlindModes;
import application.domain.Gem;
import application.domain.Noble;
import application.domain.Player;
import application.domain.PlayerObserver;
import application.domain.TempHand;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Separator;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * This is the view with the player's own information.
 *
 * @author Sanchez
 */
public class PlayerView extends UnicastRemoteObject implements UIComponent, Disableable, PlayerObserver {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4476145574334789147L;

	// Radio button toggle group
	private final ToggleGroup group = new ToggleGroup();

	private GameController gameController;
	private Player player;

	private Pane root;

	private GridPane tokensGrid;
	private HBox ownedCards;
	private HBox ownedNobles;
	private HBox reservedCards;

	private Label lblPrestigeValue;

	/**
	 * Creates a new player view.
	 *
	 * @param player this player.
	 * @param gameController
	 * @throws RemoteException
	 */
	public PlayerView(Player player, GameController gameController) throws RemoteException {

		this.buildUI();
		this.player = player;
		player.addObserver(this);
		this.gameController = gameController;
	}

	/**
	 * Builds the UI.
	 *
	 * @throws RemoteException
	 */
	private void buildUI() throws RemoteException {
		root = new HBox(25);
		root.setPadding(new Insets(15, 0, 15, 25));
		root.getStyleClass().add("player");

		// Player prestige
		lblPrestigeValue = new Label();
		lblPrestigeValue.setAlignment(Pos.CENTER);
		lblPrestigeValue.setFont(Font.font("Times New Roman", FontWeight.EXTRA_BOLD, 50));
		lblPrestigeValue.getStyleClass().add("prestige");
		lblPrestigeValue.setMinWidth(135.0);

		StackPane panePrestige = new StackPane(lblPrestigeValue);

		tokensGrid = new GridPane();
		tokensGrid.setVgap(2);
		tokensGrid.setHgap(15);

		ownedCards = new HBox(10);
		ownedNobles = new HBox(10);
		reservedCards = new HBox(10);

		VBox accessibility = buildAccessibilityMenu();

		root.getChildren().addAll(panePrestige, sep(), tokensGrid, sep(), ownedCards, sep(), ownedNobles, sep(),
				reservedCards, sep(), accessibility);
	}

	/* (non-Javadoc)
	 * @see application.domain.PlayerObserver#modelChanged(application.domain.Player)
	 */
	public void modelChanged(Player player) throws RemoteException {
		//System.out.println("[DEBUG] PlayerView::modelChanged()::Player has " + player.getTokensGemCount());
		Platform.runLater(() ->
		{
			try {
				lblPrestigeValue.setText(String.valueOf(player.getPrestige()));
		
				this.updatePlayerTokens(player.getTokensGemCount());
				this.updatePlayerCards(player.getOwnedCards());
				this.updatePlayerNobles(player.getOwnedNobles());
				this.updatePlayerReservedCards(player.getReservedCards());
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}

	/**
	 * Builds the accessibility menu.
	 *
	 * @return VBox
	 */
	private VBox buildAccessibilityMenu() {
		VBox accessibility = new VBox(10);
		accessibility.setPadding(new Insets(0, 22, 0, 0));
		accessibility.setAlignment(Pos.BOTTOM_RIGHT);

		HBox btnManual = buildManualButton();
		RadioButton rbAccNormal = new RadioButton();
		RadioButton rbAccDeuteranopia = new RadioButton();
		RadioButton rbAccProtanopia = new RadioButton();
		RadioButton rbAccTritanopia = new RadioButton();

		btnManual.setPadding(new Insets(0, -8, 0, 0));
		rbAccNormal.setSelected(true);
		rbAccNormal.setToggleGroup(group);
		rbAccDeuteranopia.setToggleGroup(group);
		rbAccProtanopia.setToggleGroup(group);
		rbAccTritanopia.setToggleGroup(group);

		rbAccNormal.setOnAction(e -> {
			System.out.println("Switching to normal accessiblity mode");
			GameView.changeColorBlindMode(ColorBlindModes.NORMAL);
		});

		rbAccDeuteranopia.setOnAction(e -> {
			System.out.println("Switching to Deuteranopia accessiblity mode");
			GameView.changeColorBlindMode(ColorBlindModes.DEUTERANOPIA);
		});

		rbAccProtanopia.setOnAction(e -> {
			System.out.println("Switching to Protanopia accessiblity mode");
			GameView.changeColorBlindMode(ColorBlindModes.PROTANOPIA);
		});

		rbAccTritanopia.setOnAction(e -> {
			System.out.println("Switching to Tritanopia accessiblity mode");
			GameView.changeColorBlindMode(ColorBlindModes.TRITANOPIA);
		});

		accessibility.getChildren().addAll(rbAccNormal, rbAccDeuteranopia, rbAccProtanopia, rbAccTritanopia, btnManual);

		HBox.setHgrow(accessibility, Priority.ALWAYS);

		return accessibility;
	}

	/**
	 * Update player reserved cards.
	 *
	 * @param List<Card>
	 */
	private void updatePlayerReservedCards(List<Card> cards) throws RemoteException {
		reservedCards.getChildren().clear();
		HBox reservedCardsDisplay = updateReservedCardDisplay(cards, 110, 160);
		reservedCardsDisplay.setPrefWidth(350);
		reservedCards.getChildren().add(reservedCardsDisplay);
	}

	/**
	 * Updates the reserved card display.
	 *
	 * @param cards
	 * @param sizeX the size horizontal
	 * @param sizeY the size vertical
	 * @return HBox
	 */
	private HBox updateReservedCardDisplay(List<Card> cards, int sizeX, int sizeY) throws RemoteException {
		HBox reservedCards = new HBox(10);

		TempHand tempHand = gameController.getTempHand();
		Card boughtCard = tempHand.getBoughtCard();
		List<Card> selectableReservedCards = player.getSelectableCardsFromReserve();
		
		for (int i = 0; i < cards.size(); i++) {
			int cardIdx = i;
			Card card = cards.get(i);
			CardView cardView = new FrontCardView(card, sizeX, sizeY);

			cardView.asPane().getStyleClass().add("dropshadow");

			if (!selectableReservedCards.isEmpty()) {
				System.out.println(gameController);
				System.out.println(card);
				if ((boughtCard != null && boughtCard.equals(card))) {
					cardView.asPane().getStyleClass().remove("selectable");
					cardView.asPane().getStyleClass().add("selected");
				} else if (selectableReservedCards.contains(card)) {
					cardView.asPane().getStyleClass().add("selectable");
					cardView.asPane().setOnMouseClicked(e -> {
						try {
							gameController.onReservedCardClicked(cardIdx);
						} catch (RemoteException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					});
				}
			}
			reservedCards.getChildren().add(cardView.asPane());
		}

		return reservedCards;
	}

	/**
	 * Update player nobles.
	 *
	 * @param nobles
	 */
	private void updatePlayerNobles(List<Noble> nobles) {
		ownedNobles.getChildren().clear();

		VBox nobleDisplay = createNobleDisplay(nobles, 110, 110);
		nobleDisplay.setPrefWidth(110);
		ownedNobles.getChildren().add(nobleDisplay);
	}

	/**
	 * Creates the noble display.
	 *
	 * @param nobles
	 * @param sizeX the size horizontal
	 * @param sizeY the size vertical
	 * @return VBox 
	 */
	private VBox createNobleDisplay(List<Noble> nobles, int sizeX, int sizeY) {
		int offset = 0;

		StackPane nobleStack = new StackPane();

		for (Noble noble : nobles) {
			NobleView nobleView = new NobleView(noble, sizeX, sizeY);
			nobleView.asPane().setTranslateY(offset);
			nobleView.asPane().getStyleClass().add("dropshadow");
			offset = offset + 35;
			nobleStack.getChildren().add(nobleView.asPane());
		}

		nobleStack.setAlignment(Pos.CENTER);

		VBox nobleDisplay = new VBox(nobleStack);

		return nobleDisplay;
	}

	/**
	 * Update player cards.
	 *
	 * @param cards
	 * @throws RemoteException
	 */
	private void updatePlayerCards(List<Card> cards) throws RemoteException {
		ownedCards.getChildren().clear();

		ArrayList<Card> diamondCards = new ArrayList<Card>();
		ArrayList<Card> sapphireCards = new ArrayList<Card>();
		ArrayList<Card> emeraldCards = new ArrayList<Card>();
		ArrayList<Card> rubyCards = new ArrayList<Card>();
		ArrayList<Card> onyxCards = new ArrayList<Card>();

		for (Card c : cards) {
			switch (c.getBonusGem()) {
			case DIAMOND:
				diamondCards.add(c);
				break;
			case SAPPHIRE:
				sapphireCards.add(c);
				break;
			case EMERALD:
				emeraldCards.add(c);
				break;
			case RUBY:
				rubyCards.add(c);
				break;
			case ONYX:
				onyxCards.add(c);
				break;
			default:
				break;
			}
		}

		VBox diamondDisplay = createCardDisplay(diamondCards, diamondCards.size(), 110, 160);
		VBox sapphireDisplay = createCardDisplay(sapphireCards, sapphireCards.size(), 110, 160);
		VBox emeraldDisplay = createCardDisplay(emeraldCards, emeraldCards.size(), 110, 160);
		VBox rubyDisplay = createCardDisplay(rubyCards, rubyCards.size(), 110, 160);
		VBox onyxDisplay = createCardDisplay(onyxCards, onyxCards.size(), 110, 160);

		ownedCards.getChildren().addAll(diamondDisplay, sapphireDisplay, emeraldDisplay, rubyDisplay, onyxDisplay);
	}

	/**
	 * Creates the card display.
	 *
	 * @param cards
	 * @param count 
	 * @param sizeX the size horizontal
	 * @param sizeY the size vertical
	 * @return VBox
	 */
	private VBox createCardDisplay(ArrayList<Card> cards, int count, int sizeX, int sizeY) {
		int offset = 60;

		StackPane cardStack = new StackPane();

		Label cardCountLabel = new Label(String.valueOf(count));
		cardCountLabel.setAlignment(Pos.TOP_CENTER);
		cardCountLabel.getStyleClass().add("card-count");
		cardCountLabel.setFont(Font.font("Times New Roman", FontWeight.BOLD, 40));
		cardCountLabel.setPrefWidth(125);
		cardCountLabel.setTextFill(Color.WHITE);

		cardStack.getChildren().add(cardCountLabel);

		for (Card card : cards) {
			CardView cardView = new FrontCardView(card, sizeX, sizeY);
			cardView.asPane().setTranslateY(offset);
			cardView.asPane().getStyleClass().add("dropshadow");
			offset = offset + 25;
			cardStack.getChildren().add(cardView.asPane());
		}

		cardStack.setAlignment(Pos.TOP_CENTER);

		VBox ownedCardDisplay = new VBox(10, cardStack);

		return ownedCardDisplay;
	}

	/**
	 * Update player tokens.
	 *
	 * @param  gemsCount
	 * @throws RemoteException
	 */
	private void updatePlayerTokens(Map<Gem, Integer> gemsCount) throws RemoteException {
		tokensGrid.getChildren().clear();

		int col = 0, row = 0;
		for (Map.Entry<Gem, Integer> entry : gemsCount.entrySet()) {
			HBox tokenGemCountDisplay = createTokenGemCountDisplay(entry.getKey(), entry.getValue(),
					GameView.tokenSizeRadius / 2);
			tokensGrid.add(tokenGemCountDisplay, col % 2, row % 3);
			col++;
			row++;
		}
	}

	/**
	 * Creates the token gem count display.
	 *
	 * @param gemType 
	 * @param count
	 * @param radius
	 * @return HBox
	 */
	private HBox createTokenGemCountDisplay(Gem gemType, int count, int radius) {
		TokenView tokenView = new TokenView(gemType, radius);
		tokenView.asPane().getStyleClass().add("dropshadow");

		Label tokenCountLabel = new Label(String.valueOf(count));
		tokenCountLabel.setAlignment(Pos.CENTER);
		tokenCountLabel.getStyleClass().add("token-count");
		tokenCountLabel.setFont(Font.font(radius * 2));

		HBox tokenRow = new HBox(10, tokenView.asPane(), tokenCountLabel);
		tokenRow.setAlignment(Pos.CENTER);

		return tokenRow;
	}

	/**
	 * Builds the manual button.
	 *
	 * @return HBox
	 */
	private HBox buildManualButton() {
		HBox manualContainer = new HBox();
		Button manualButton = new Button("?");

		manualButton.getStyleClass().addAll("button", "manual-button-game");
		manualButton.setOnAction(e -> new ManualWindowView());

		manualContainer.getChildren().add(manualButton);
		manualContainer.setAlignment(Pos.TOP_RIGHT);

		return manualContainer;
	}

	/**
	 * Seperator.
	 * 
	 * @return Seperator
	 */
	private Separator sep() {
		Separator sep = new Separator();
		sep.setOrientation(Orientation.VERTICAL);
		sep.setValignment(VPos.CENTER);
		// sep.setPrefHeight(80);

		return sep;
	}

	/* (non-Javadoc)
	 * @see application.views.UIComponent#asPane()
	 */
	public Pane asPane() {
		return root;
	}

	/* (non-Javadoc)
	 * @see application.views.Disableable#setDisabled(boolean)
	 */
	@Override
	public void setDisabled(boolean disabled) {
		reservedCards.setDisable(disabled);
	}

}