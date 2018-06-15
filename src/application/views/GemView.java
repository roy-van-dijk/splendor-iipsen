package application.views;

import java.rmi.RemoteException;

import application.domain.ColorBlindModes;
import application.domain.Gem;
import application.domain.Token;
import application.util.ImageCache;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

/**
 * Display of every gem in the game.
 *
 * @author Roy
 */
public class GemView implements UIComponent, ColorChangeable {

	private Gem gemType;

	private StackPane root;
	private double size;
	private Rectangle rectangle;

	/**
	 * Creates a new gem view.
	 *
	 * @param gem
	 * @param radius
	 */
	public GemView(Gem gem, double size) {
		this.gemType = gem;
		this.size = size;

		GameView.colorBlindViews.add(this);

		this.buildUI();
	}

	/**
	 * Creates a new gem view.
	 *
	 * @param token
	 * @param size
	 * @throws RemoteException 
	 */
	public GemView(Token token, double size) throws RemoteException {
		this(token.getGemType(), size);
	}

	/**
	 * Builds the UI.
	 */
	private void buildUI() {
		rectangle = new Rectangle(size, size);
		String imagePath = String.format("resources/gems/%s.png", getGemImageFileName());
		Image image = ImageCache.getInstance().fetchImage(imagePath, true);
        ImagePattern imagePattern = new ImagePattern(image);
        rectangle.setFill(imagePattern);
       
        this.updateColors();
		root = new StackPane(rectangle);
	}

	/**
	 * This function is used to get the suffix of the image filename for a Gem.
	 * This always equals the name of the gem's gemType. For example, If a gem's
	 * gemType is Gem.DIAMOND, this function returns "diamond".
	 * 
	 * @return String The suffix of the token's image filename.
	 */
	private String getGemImageFileName() {
		return gemType.name().toLowerCase();
	}

	/**
	 * Returns the gem view as a pane so that it can be modified with JavaFX
	 * methods.
	 *
	 * @return Pane
	 */
	public Pane asPane() {
		return root;
	}

	/**
	 * Updates the gem view's colour based on the colour blind mode that is applied.
	 */
	@Override
	public void updateColors() {
		// Switches hue offset for color blind mode based on data in Gem class
		double hueOffset = this.gemType.hueOffset(this.gemType, ColorBlindModes.CURRENT_MODE);
		ColorAdjust colorAdjust = new ColorAdjust();
		colorAdjust.setHue(+hueOffset);
		rectangle.setEffect(colorAdjust);
	}

}
