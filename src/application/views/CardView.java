package application.views;

import application.domain.Card;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
/**
 * 
 * @author Sanchez
 *
 */
public abstract class CardView implements UIComponent {
	
	protected Pane root;
	protected Rectangle rect;
	
	protected double sizeX, sizeY;

	public CardView(double sizeX, double sizeY) {
		this.sizeX = sizeX;
		this.sizeY = sizeY;
	}
	
	protected abstract String getImagePath();

	@Override
	public Pane asPane() {
		return root;
	}

}
