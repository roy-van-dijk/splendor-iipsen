package application.views;

import java.rmi.RemoteException;
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
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Separator;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * 
 * @author Sanchez
 *
 */
public class PlayerView implements UIComponent, Disableable, PlayerObserver {

	// Radio button toggle group
	final private ToggleGroup group = new ToggleGroup();

	private GameController gameController;
	private Player player;

	private Pane root;

	private GridPane tokensGrid;
	private HBox ownedCards;
	private HBox ownedNobles;
	private HBox reservedCards;

	private Label lblPrestigeValue;

	public PlayerView(Player player, GameController gameController) throws RemoteException {

		this.buildUI();
		this.player = player;
		player.addObserver(this);
		this.gameController = gameController;
	}

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

		root.getChildren().addAll(panePrestige, sep(), tokensGrid, sep(), ownedCards, sep(), ownedNobles, sep(), reservedCards, sep(), accessibility);
	}

	public void modelChanged(Player player) throws RemoteException {
		lblPrestigeValue.setText(String.valueOf(player.getPrestige()));

		this.updatePlayerTokens(player.getTokensGemCount());
		this.updatePlayerCards(player.getOwnedCards());
		this.updatePlayerNobles(player.getOwnedNobles());
		this.updatePlayerReservedCards(player.getReservedCards());
	} 

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

	private void updatePlayerReservedCards(List<Card> cards) {
		reservedCards.getChildren().clear();
		HBox reservedCardsDisplay = createReservedCardDisplay(cards, 110, 160);
		reservedCardsDisplay.setPrefWidth(350);
		reservedCards.getChildren().add(reservedCardsDisplay);
	}

	private HBox createReservedCardDisplay(List<Card> cards, int sizeX, int sizeY) {
		HBox reservedCards = new HBox(10);

		for (Card card : cards) {
			CardView cardView = new FrontCardView(card, sizeX, sizeY);
			
			cardView.asPane().getStyleClass().add("dropshadow");

			try {
				if(!player.getSelectableCardsFromReserve().isEmpty()) {
					System.out.println(gameController);
					System.out.println(card);
					if(gameController.getTempHand().getBoughtCard() == card) {
						cardView.asPane().getStyleClass().remove("selectable");
						cardView.asPane().getStyleClass().add("selected");
					} else if(player.getSelectableCardsFromReserve().contains(card)) {
						cardView.asPane().getStyleClass().add("selectable");
						cardView.asPane().setOnMouseClicked(e -> { 
							try {
								gameController.cardClickedFromReserve(card);
							} catch (RemoteException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						});
					}
				}
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			reservedCards.getChildren().add(cardView.asPane());
		}

		return reservedCards;
	}

	private void updatePlayerNobles(List<Noble> nobles) {
		ownedNobles.getChildren().clear();

		VBox nobleDisplay = createNobleDisplay(nobles, 110, 110);
		nobleDisplay.setPrefWidth(175);
		ownedNobles.getChildren().add(nobleDisplay);
	}

	private VBox createNobleDisplay(List<Noble> nobles, int sizeX, int sizeY) {
		int offset = 0;

		StackPane nobleStack = new StackPane();

		for (Noble noble : nobles) {
			NobleView nobleView = new NobleView(noble, sizeX, sizeY);
			nobleView.asPane().setTranslateY(offset);
			nobleView.asPane().getStyleClass().add("dropshadow");
			offset = offset + 50;
			nobleStack.getChildren().add(nobleView.asPane());
		}

		nobleStack.setAlignment(Pos.CENTER);

		VBox nobleDisplay = new VBox(nobleStack);

		return nobleDisplay;
	}

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

	private void updatePlayerTokens(Map<Gem, Integer> gemsCount) throws RemoteException {
		tokensGrid.getChildren().clear();

		int col = 0, row = 0;
		for (Map.Entry<Gem, Integer> entry : gemsCount.entrySet()) {
			HBox tokenGemCountDisplay = createTokenGemCountDisplay(entry.getKey(), entry.getValue(), GameView.tokenSizeRadius / 2);
			tokensGrid.add(tokenGemCountDisplay, col % 2, row % 3);
			col++;
			row++;
		}
	}

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

	private HBox buildManualButton() {
		HBox manualContainer = new HBox();
		Button manualButton = new Button("?");

		manualButton.getStyleClass().addAll("button", "manual-button-game");
		manualButton.setOnAction(e -> new ManualWindowView());

		manualContainer.getChildren().add(manualButton);
		manualContainer.setAlignment(Pos.TOP_RIGHT);

		return manualContainer;
	}

	private Separator sep() {
		Separator sep = new Separator();
		sep.setOrientation(Orientation.VERTICAL);
		sep.setValignment(VPos.CENTER);
		// sep.setPrefHeight(80);

		return sep;
	}

	public Pane asPane() {
		return root;
	}

	@Override
	public void setDisabled(boolean disabled) {
		reservedCards.setDisable(disabled);
	}

}