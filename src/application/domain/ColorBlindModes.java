package application.domain;

public enum ColorBlindModes {
	NORMAL,
	DEUTERANOPIA,
	PROTANOPIA,
	TRITANOPIA;
	
	public static ColorBlindModes CURRENT_MODE = NORMAL;
}
