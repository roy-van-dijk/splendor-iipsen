package application.views;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import application.controllers.GameController;
import application.domain.CardRow;
import application.domain.Gem;
import application.domain.MoveType;
import application.domain.Noble;
import application.domain.PlayingField;
import application.domain.PlayingFieldObserver;
import application.domain.TempHand;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

/**
 * 
 * @author Sanchez
 *
 */
public class PlayingFieldView implements UIComponent, Disableable, PlayingFieldObserver {

	public final static int CARDSPACING = 15, TOKENSPACING = 10, TOKENSLABELSPACING = 25, TOKENSCARDSPADDING = 20, FIELDPADDING = 0;

	private final static double NOBLES_HEIGHT_FACTOR = 0.67;

	private HBox root;

	private VBox rowsPane;
	private VBox cardsPane;
	private HBox noblesPane;
	private VBox tokensPane;

	private VBox exitGamePane;

	private List<CardRowView> cardRowViews;
	private List<NobleView> nobleViews;
	private List<TokenView> tokenViews;

	private GameController gameController;

	/**
	 * 
	 * @param PlayingField
	 *            playingField
	 * @param GameController
	 *            gameController
	 * @throws RemoteException
	 */
	public PlayingFieldView(PlayingField playingField, GameController gameController) throws RemoteException {
		this.gameController = gameController;

		this.cardRowViews = new ArrayList<>();
		this.nobleViews = new ArrayList<>();
		this.tokenViews = new ArrayList<>();
		this.buildUI();

		playingField.addObserver(this);
	}
	/**
	 * update the playingFieldView
	 */
	public void modelChanged(PlayingField playingField) throws RemoteException {
		if (cardsPane.getChildren().isEmpty()) {
			this.initializeCardRows(playingField.getCardRows(), playingField.getTempHand());
		}
		this.updateFieldTokens(playingField.getTokenGemCount(), playingField.getSelectableTokens(), playingField.getTempHand());
		this.updateNobles(playingField.getNobles());
	}

	private void buildUI() {
		rowsPane = buildCardsAndNoblesDisplay();
		exitGamePane = buildExitGameDisplay();

		tokensPane = new VBox(TOKENSPACING);
		tokensPane.setAlignment(Pos.CENTER);
		HBox.setHgrow(tokensPane, Priority.ALWAYS);

		root = new HBox(TOKENSCARDSPADDING, rowsPane, tokensPane, exitGamePane);
		root.getStyleClass().add("playing-field");
		root.setAlignment(Pos.CENTER);
		root.setPadding(new Insets(FIELDPADDING));
	}

	private VBox buildCardsAndNoblesDisplay() {
		noblesPane = new HBox(CARDSPACING);
		noblesPane.setAlignment(Pos.CENTER);

		cardsPane = new VBox(CARDSPACING);
		cardsPane.setAlignment(Pos.CENTER);

		VBox rowsPane = new VBox(CARDSPACING, noblesPane, cardsPane);
		rowsPane.setAlignment(Pos.CENTER);
		HBox.setHgrow(rowsPane, Priority.ALWAYS);
		return rowsPane;
	}

	private void initializeCardRows(List<CardRow> cardRows, TempHand tempHand) {
		System.out.println("Initializing cardrows");

		Collections.reverse(cardRows);

		for (CardRow cardRow : cardRows) {
			CardRowView cardRowView = new CardRowView(cardRow, gameController, tempHand);
			cardRowViews.add(cardRowView);
			cardsPane.getChildren().add(cardRowView.asPane());
		}
	}

	private void updateNobles(List<Noble> nobles) {
		noblesPane.getChildren().clear();

		for (Noble noble : nobles) {
			NobleView nobleView = new NobleView(noble, GameView.cardSizeX, GameView.cardSizeY * NOBLES_HEIGHT_FACTOR);
			nobleViews.add(nobleView);
			noblesPane.getChildren().add(nobleView.asPane());
		}
	}

	private void updateFieldTokens(Map<Gem, Integer> gemsCount, List<Gem> selectableTokens, TempHand tempHand) {
		tokensPane.getChildren().clear();

		for (Map.Entry<Gem, Integer> entry : gemsCount.entrySet()) {
			HBox tokenGemCountDisplay = createTokenGemCountDisplay(entry.getKey(), entry.getValue(),
					GameView.tokenSizeRadius, selectableTokens, tempHand);
			tokensPane.getChildren().add(tokenGemCountDisplay);
		}
	}

	private HBox createTokenGemCountDisplay(Gem gemType, int count, int radius, List<Gem> selectableTokens,
			TempHand tempHand) {
		TokenView tokenView = new TokenView(gemType, radius);

		if (gemType != Gem.JOKER) {
			if ((tempHand.getMoveType() == MoveType.TAKE_TWO_TOKENS && tempHand.getSelectedTokensCount() < 1)
					|| (tempHand.getMoveType() == MoveType.TAKE_THREE_TOKENS && tempHand.getSelectedTokensCount() < 3 && !tempHand.getSelectedGemTypes().contains(gemType))) {
				tokenView.asPane().setOnMouseClicked(e -> {
					try {
						gameController.onFieldTokenClicked(gemType);
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				});

			}

			if (selectableTokens.contains(gemType)) {
				tokenView.asPane().getStyleClass().add("selectable");
			}
			if (tempHand.getTokenList().getTokenGemCount().get(gemType) > 0) {
				tokenView.asPane().getStyleClass().remove("selectable");
				tokenView.asPane().getStyleClass().add("selected");
			}
		}

		Label tokenCountLabel = new Label(String.valueOf(count));
		tokenCountLabel.getStyleClass().add("token-count");
		tokenCountLabel.setFont(Font.font(radius * 2));

		HBox tokenRow = new HBox(15, tokenView.asPane(), tokenCountLabel);
		tokenRow.setAlignment(Pos.CENTER);

		return tokenRow;
	}

	private VBox buildExitGameDisplay() {
		Button exit = new Button("X");

		VBox vbox = new VBox();
		exit.getStyleClass().addAll("button", "manual-button");
		vbox.getChildren().add(exit);

		exit.setOnAction(e -> {
			gameController.leaveGame();
		});
		return vbox;
	}

	public Pane asPane() {
		return root;
	}

	@Override
	public void setDisabled(boolean disabled) throws RemoteException {
		this.rowsPane.setDisable(disabled);
		this.tokensPane.setDisable(disabled);
	}
}
