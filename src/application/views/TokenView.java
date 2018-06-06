package application.views;

import application.controllers.GameController;
import application.domain.ColorBlindModes;
import application.domain.Gem;
import application.domain.Token;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

/**
 * @author Sanchez
 *
 */
public class TokenView implements UIComponent, ColorChangeable {
	
	// private Token token; // Made irrelevant on 17:43 20-05-2018 TODO: pending definite removal
	private Gem gemType;
	
	private StackPane root;
	private double radius;
	private Circle circle;

	public TokenView(Gem gem, double radius) {
		this.gemType = gem;
		this.radius = radius;
		
		GameView.colorBlindViews.add(this);
		
		this.buildUI();
	}
	
	// Probably useless
	public TokenView(Token token, double radius)
	{
		this(token.getGemType(), radius);
	}
	
	private void buildUI()
	{
		circle = new Circle(radius);
		String imagePath = String.format("file:resources/tokens/token_%s.png", getTokenImageFileName());
		Image image = new Image(imagePath);
		//Image image = ImageResources.getImage(imagePath);
        ImagePattern imagePattern = new ImagePattern(image);
        circle.setFill(imagePattern);
       
		root = new StackPane(circle);

		//root.getStyleClass().add("selectable");
	}	
	
	/**
	 *  This function is used to get the suffix of the image filename for a Token. This always equals the name of the token's gemType.  
	 *  For example, If a token's gemType is Gem.DIAMOND, this function returns "diamond".
	 *  
	 * @return String The suffix of the token's image filename.
	 */
	private String getTokenImageFileName()
	{
		return gemType.name().toLowerCase();
	}

	
	public Pane asPane() {
		return root;
	}

	@Override
	public void updateView(ColorBlindModes mode) {
		// Switches hue offset for color blind mode based on data in Gem class
        double hueOffset = this.gemType.hueOffset(this.gemType, mode);
        ColorAdjust colorAdjust = new ColorAdjust();
	    colorAdjust.setHue(+hueOffset);
	    circle.setEffect(colorAdjust);
	}
	
}
