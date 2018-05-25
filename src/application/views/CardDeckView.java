package application.views;

import java.rmi.RemoteException;

import application.domain.Card;
import application.domain.CardDeckImpl;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeType;

public class CardDeckView extends CardView {
	
	private CardDeckImpl deck;
	
	public CardDeckView(CardDeckImpl deck, int sizeX, int sizeY) {
		super(sizeX, sizeY);
		this.deck = deck;
		
		this.buildUI();
	}
	
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
	
	@Override
	protected String getImagePath()
	{
		String path = "";
		try {
			path = String.format("file:resources/cards/%s/%s.png", deck.top().getLevel().name().toLowerCase(), deck.top().getLevel().name().toLowerCase());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		return path;
	}
}
