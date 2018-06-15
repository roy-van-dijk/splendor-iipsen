package application.views;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
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
import javafx.application.Platform;
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
 * The playing field view is the section that contains all the cards, nobles,
 * tokens, etc.
 * 
 * @author Sanchez
 *
 */
public class PlayingFieldView extends UnicastRemoteObject implements UIComponent, Disableable, PlayingFieldObserver {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8204038345887970683L;

	public final static int CARDSPACING = 15, TOKENSPACING = 10, TOKENSLABELSPACING = 25, TOKENSCARDSPADDING = 20,
			FIELDPADDING = 0;

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
	 * Instantiates a new playing field view.
	 *
	 * @param playingField
	 * @param gameController
	 * @throws RemoteException
	 */
	public PlayingFieldView(PlayingField playingField, GameController gameController) throws RemoteException {
		this.gameController = gameController;

		this.cardRowViews = new ArrayList<>();
		this.nobleViews = new ArrayList<>();
		this.tokenViews = new ArrayList<>();
		this.buildUI();
		
		System.out.println("[DEBUG] PlayingFieldView()::registering as observer");
		playingField.addObserver(this);
	}
	
	
	/**
	 * Update the playingFieldView.
	 *
	 * @param playingField
	 * @throws RemoteException
	 */
	public void modelChanged(PlayingField playingField) throws RemoteException
	{
		Platform.runLater(() ->
		{
			try {
				if(cardsPane.getChildren().isEmpty())
				{
					System.out.println("[DEBUG] PlayingFieldView::modelChanged()::Building card rows");
					this.initializeCardRows(playingField.getCardRows(), playingField.getTempHand());
				}
				this.updateFieldTokens(playingField.getTokenGemCount(), playingField.getSelectableTokens(), playingField.getTempHand());
				this.updateNobles(playingField.getNobles());
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}

	
	/**
	 * Builds the UI.
	 */
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

	/**
	 * Builds the cards and nobles display.
	 *
	 * @return VBox
	 */
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
	
	/**
	 * Initialize card rows.
	 *
	 * @param cardRows
	 * @param tempHand
	 * @throws RemoteException
	 */
	private void initializeCardRows(List<CardRow> cardRows, TempHand tempHand) throws RemoteException {
		System.out.println("Initializing cardrows");
		
		for (CardRow cardRow : cardRows) {
			CardRowView cardRowView = new CardRowView(cardRow, gameController, tempHand);
			cardRowViews.add(cardRowView);
			cardsPane.getChildren().add(cardRowView.asPane());
		}
	}

	/**
	 * Update nobles.
	 *
	 * @param nobles
	 */
	private void updateNobles(List<Noble> nobles) {
		noblesPane.getChildren().clear();

		for (Noble noble : nobles) {
			NobleView nobleView = new NobleView(noble, GameView.cardSizeX, GameView.cardSizeY * NOBLES_HEIGHT_FACTOR);
			nobleViews.add(nobleView);
			noblesPane.getChildren().add(nobleView.asPane());
		}
	}

	
	
	/**
	 * Update field tokens.
	 *
	 * @param gemsCount
	 * @param selectableTokens
	 * @param tempHand
	 * @throws RemoteException
	 */
	private void updateFieldTokens(Map<Gem, Integer> gemsCount, List<Gem> selectableTokens, TempHand tempHand) throws RemoteException {
		tokensPane.getChildren().clear();

		for (Map.Entry<Gem, Integer> entry : gemsCount.entrySet()) {
			HBox tokenGemCountDisplay = createTokenGemCountDisplay(entry.getKey(), entry.getValue(), selectableTokens, tempHand);
			tokensPane.getChildren().add(tokenGemCountDisplay);
		}
	}

	
	/**
	 * Creates the token gem count display.
	 *
	 * @param gemType
	 * @param count
	 * @param selectableTokens
	 * @param tempHand
	 * @return HBox
	 * @throws RemoteException
	 */
	private synchronized HBox createTokenGemCountDisplay(Gem gemType, int count, List<Gem> selectableTokens,TempHand tempHand) throws RemoteException {
		int radius = GameView.tokenSizeRadius;
		TokenView tokenView = new TokenView(gemType, radius);
		
		if(selectableTokens.contains(gemType)) {
			
			if(tempHand.getMoveType() == MoveType.TAKE_TWO_TOKENS) {
				tokenView.asPane().setOnMouseClicked(e -> {
					try {
						tempHand.emptyHand();
						gameController.onFieldTokenClicked(gemType);
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				});
			} else if(tempHand.getMoveType() == MoveType.TAKE_THREE_TOKENS) {
				if(tempHand.getSelectedGemTypes().contains(gemType)) {
					tokenView.asPane().setOnMouseClicked(e -> {
						try {
							gameController.selectedTokenClicked(gemType);
						} catch (RemoteException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					});
				} else if(tempHand.getSelectedTokensCount() < 3) {
					tokenView.asPane().setOnMouseClicked(e -> {
						try {
							gameController.onFieldTokenClicked(gemType);
						} catch (RemoteException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					});
				}
			}
			
			if(tempHand.getSelectedGemTypes().contains(gemType)) {
				tokenView.asPane().getStyleClass().remove("selectable");
				tokenView.asPane().getStyleClass().add("selected");
			} else {
				tokenView.asPane().getStyleClass().remove("selected");
				tokenView.asPane().getStyleClass().add("selectable");
			}
		}

		Label tokenCountLabel = new Label(String.valueOf(count));
		tokenCountLabel.setFont(Font.font(radius * 2));
		tokenCountLabel.getStyleClass().add("token-count");

		HBox tokenRow = new HBox(15, tokenView.asPane(), tokenCountLabel);
		tokenRow.setAlignment(Pos.CENTER);

		return tokenRow;
	}

	/**
	 * Builds the exit game display.
	 *
	 * @return VBox
	 */
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
	public void setDisabled(boolean disabled)  {
		this.rowsPane.setDisable(disabled);
		this.tokensPane.setDisable(disabled);
	}
}
