package application.views;

import java.rmi.RemoteException;

import application.domain.Card;
import application.domain.CardDeck;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeType;
/**
 * 
 * @author Sanchez
 *
 */
public class CardDeckView extends CardView {
	
	private CardDeck deck;
	/**
	 * 
	 * @param deck CardDeck of cards of 1 kind like lvl 1 playingfield cards
	 * @param sizeX size horizontal
	 * @param sizeY size vertical 
	 */
	public CardDeckView(CardDeck deck, int sizeX, int sizeY) {
		super(sizeX, sizeY);
		this.deck = deck;
		
		this.buildUI();
	}
	
	/**
	 * build the view
	 */
	private void buildUI()
	{
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
	 * get the img of the card.
	 */
	@Override
	protected String getImagePath()
	{
		String path = "";
		path = String.format("file:resources/cards/%s/%s.png", deck.getLevel().name().toLowerCase(), deck.getLevel().name().toLowerCase());	
		return path;
	}
}
