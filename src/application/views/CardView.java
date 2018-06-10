package application.views;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

/**
 * Card view can be extended by other classes to create card visuals.
 *
 * @author Sanchez
 */
public abstract class CardView implements UIComponent {

	protected Pane root;
	protected Rectangle rect;

	protected double sizeX, sizeY;

	/**
	 * Creates a new card view.
	 *
	 * @param sizeX the size horzontal
	 * @param sizeY the size vertical
	 */
	public CardView(double sizeX, double sizeY) {
		this.sizeX = sizeX;
		this.sizeY = sizeY;
	}

	/**
	 * Returns the path for this card's illustration.
	 *
	 * @return String
	 */
	protected abstract String getImagePath();

	/* (non-Javadoc)
	 * @see application.views.UIComponent#asPane()
	 */
	@Override
	public Pane asPane() {
		return root;
	}

}
