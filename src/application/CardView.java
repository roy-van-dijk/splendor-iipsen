package application;

import java.util.Map;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CardView  {
	
	private Card card;
	private String backgroundImageName;
	private ImageView backgroundImage = new ImageView();
	
	public CardView(Card card, String backgroundImageName) {
		this.card = card;
		this.backgroundImageName = backgroundImageName;
	}


	public void setupBackgroundImage()
	{
		String imagePath = String.format("/resources/cards/%s/%s.png", card.getLevel().name().toLowerCase(), backgroundImageName);
		Image image = new Image(imagePath);
		backgroundImage.setImage(image);
	}

}
