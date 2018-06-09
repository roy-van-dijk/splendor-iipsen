package application.views;

import java.rmi.RemoteException;

import application.domain.CardDeck;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;

/**
 * Card deck views display the back size of cards. They can be seen on the left
 * side of the playing field
 * 
 * @author Sanchez
 *
 */
public class CardDeckView extends CardView {

	private CardDeck deck;

	/**
	 * Creates a new view of a card deck
	 * 
	 * @param deck
	 * @param sizeX
	 * @param sizeY
	 */
	public CardDeckView(CardDeck deck, int sizeX, int sizeY) {
		super(sizeX, sizeY);
		this.deck = deck;

		this.buildUI();
	}

	private void buildUI() {
		rect = new Rectangle(sizeX, sizeY);

		// Add image
		ImagePattern imagePattern = new ImagePattern(new Image(getImagePath()));
		rect.setFill(imagePattern);

		// Make rounded corners
		rect.setArcHeight(10);
		rect.setArcWidth(10);
		rect.setStroke(Color.BLACK);
		rect.setStrokeType(StrokeType.INSIDE);
		rect.setStrokeWidth(5);

		// Add to root node
		root = new StackPane(rect);
	}

	/**
	 * Returns the path for this card's illustration
	 * 
	 * @throws RemoteException
	 * @return String
	 */
	@Override
	protected String getImagePath() {
		String path = "";
		path = String.format("file:resources/cards/%s/%s.png", deck.getLevel().name().toLowerCase(),
				deck.getLevel().name().toLowerCase());
		return path;
	}
}
