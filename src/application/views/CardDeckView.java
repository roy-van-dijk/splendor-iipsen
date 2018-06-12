package application.views;

import java.rmi.RemoteException;

import application.domain.CardDeck;
import application.util.ImageCache;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;


// TODO: Auto-generated Javadoc
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
	 * Creates a new view of a card deck.
	 *
	 * @param deck the deck
	 * @param sizeX the size horizontal
	 * @param sizeY the size vertical
	 */
	public CardDeckView(CardDeck deck, int sizeX, int sizeY) {
		super(sizeX, sizeY);
		this.deck = deck;

		this.buildUI();
	}

	/**
	 * Builds the UI.
	 */
	private void buildUI() {
		rect = new Rectangle(sizeX, sizeY);

		// Add image
    	Image image = ImageCache.getInstance().fetchImage(getImagePath(), true);
        ImagePattern imagePattern = new ImagePattern(image);
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


	/* (non-Javadoc)
	 * @see application.views.CardView#getImagePath()
	 */
	@Override
	protected String getImagePath() {
		String path = "";
		try {
			path = String.format("file:resources/cards/%s/%s.png", deck.getLevel().name().toLowerCase(),
					deck.getLevel().name().toLowerCase());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return path;
	}
}
