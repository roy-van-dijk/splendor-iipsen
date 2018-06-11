package application.domain;

/**
 * Enum for the 4 different colorblind modes.
 *
 * @author Roy
 */
public enum ColorBlindModes {
	NORMAL, 
	DEUTERANOPIA, 
	PROTANOPIA, 
	TRITANOPIA;

	public static ColorBlindModes CURRENT_MODE = NORMAL;
}
