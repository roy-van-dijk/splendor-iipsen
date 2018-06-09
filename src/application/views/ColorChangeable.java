package application.views;

import application.domain.ColorBlindModes;

/**
 * Is implemented by views that need their colour changed based on the current
 * colour blind mode
 * 
 * @author Roy
 *
 */
public interface ColorChangeable {

	/**
	 * Updates the view to accompany the colour blind mode setting change
	 * 
	 * @param mode
	 * @return void
	 */
	public void updateView(ColorBlindModes mode);

}
