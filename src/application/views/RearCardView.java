package application.views;


import application.domain.Card;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeType;

public class RearCardView extends CardView {	
	
	private Card card;
	
	public RearCardView(Card card, double sizeX, double sizeY) {
		super(sizeX, sizeY);
		this.card = card;
		
		this.buildUI();
	}
	
	private void buildUI()
	{
    	rect = new Rectangle(GameView.cardSizeX, GameView.cardSizeY);
    	
    	// Add image
        ImagePattern imagePattern = new ImagePattern(new Image(getImagePath()));
        rect.setFill(imagePattern);
        
        // Make rounded corners
        rect.setArcHeight(10);
        rect.setArcWidth(10);
        
        // Add to root node
        root = new StackPane(rect);
	}

	@Override
	protected String getImagePath()
	{
		return String.format("file:resources/cards/%s/%s.png", card.getLevel().name().toLowerCase(), card.getLevel().name().toLowerCase());
	}

}