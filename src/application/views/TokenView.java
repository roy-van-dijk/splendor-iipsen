package application.views;

import application.domain.ColorBlindModes;
import application.domain.Gem;
import application.domain.Token;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

/**
 * Display of every token in the game
 * 
 * @author Sanchez
 *
 */
public class TokenView implements UIComponent, ColorChangeable {

	private Gem gemType;

	private StackPane root;
	private double radius;
	private Circle circle;

	/**
	 * Creates a new token view
	 * 
	 * @param gem
	 * @param radius
	 */
	public TokenView(Gem gem, double radius) {
		this.gemType = gem;
		this.radius = radius;

		GameView.colorBlindViews.add(this);

		this.buildUI();
	}

	/**
	 * Creates a new token view
	 * 
	 * @param token
	 * @param radius
	 */
	public TokenView(Token token, double radius) {
		this(token.getGemType(), radius);
	}

	private void buildUI() {
		circle = new Circle(radius);
		String imagePath = String.format("file:resources/tokens/token_%s.png", getTokenImageFileName());
		Image image = new Image(imagePath);
		// Image image = ImageResources.getImage(imagePath);
		ImagePattern imagePattern = new ImagePattern(image);
		circle.setFill(imagePattern);

		root = new StackPane(circle);
	}

	/**
	 * This function is used to get the suffix of the image filename for a Token.
	 * This always equals the name of the token's gemType. For example, If a token's
	 * gemType is Gem.DIAMOND, this function returns "diamond".
	 * 
	 * @return String The suffix of the token's image filename.
	 */
	private String getTokenImageFileName() {
		return gemType.name().toLowerCase();
	}

	/**
	 * Returns the token view as a pane so that it can be modified with JavaFX
	 * methods
	 * 
	 * @return Pane
	 */
	public Pane asPane() {
		return root;
	}

	/**
	 * Updates the token view's colour based on the colour blind mode that is applied
	 * @return void
	 */
	@Override
	public void updateView(ColorBlindModes mode) {
		// Switches hue offset for color blind mode based on data in Gem class
		double hueOffset = this.gemType.hueOffset(this.gemType, mode);
		ColorAdjust colorAdjust = new ColorAdjust();
		colorAdjust.setHue(+hueOffset);
		circle.setEffect(colorAdjust);
	}

}
