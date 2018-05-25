package application.views;


import java.rmi.RemoteException;

import application.domain.Card;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class FrontCardView extends CardView {
	
	private Card card;
	
	public FrontCardView(Card card, double sizeX, double sizeY) {
		super(sizeX, sizeY);
		this.card = card;
		
		this.buildUI();
	}
	
	private void buildUI()
	{
    	rect = new Rectangle(sizeX, sizeY);
    	
    	// Add image
        ImagePattern imagePattern = new ImagePattern(new Image(getImagePath()));
        rect.setFill(imagePattern);
        
        // TODO: add costs & gem type
        
        // Make rounded corners
        rect.setArcHeight(10);
        rect.setArcWidth(10);
        
        // Add to root node
        root = new StackPane(rect);
	}
	
	// TODO: Add actual image resources & replace "bg1" with Card model's assigned illustration. 
	@Override
	protected String getImagePath()
	{
		String path = "";
		try {
			path = String.format("file:resources/cards/%s/%s.png", card.getLevel().name().toLowerCase(), "bg1");
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		return path;
	}

}
