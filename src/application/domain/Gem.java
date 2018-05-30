package application.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Sanchez
 *
 */
public enum Gem {
	DIAMOND,
	EMERALD,
	RUBY,
	ONYX,
	SAPPHIRE,
	JOKER;
	
	
	/**
	 * @author Sanchez
	 * @param gem
	 * @param mode
	 * @return
	 */
	public double hueOffset(Gem gem, ColorBlindModes mode)
	{
		Map<ColorBlindModes, Double> hueMap = new HashMap<>();
		switch(this)
		{
			case DIAMOND:
				hueMap.put(ColorBlindModes.NORMAL, 0.0);
				hueMap.put(ColorBlindModes.DEUTERANOPIA, 0.0);
				hueMap.put(ColorBlindModes.PROTANOPIA, 0.0);
				hueMap.put(ColorBlindModes.TRITANOPIA, 0.0);
			break;
				
			case EMERALD:
				hueMap.put(ColorBlindModes.NORMAL, 0.0);
				hueMap.put(ColorBlindModes.DEUTERANOPIA, 0.25);
				hueMap.put(ColorBlindModes.PROTANOPIA, 0.0);
				hueMap.put(ColorBlindModes.TRITANOPIA, 0.65);
			break;
				
			case RUBY:
				hueMap.put(ColorBlindModes.NORMAL, 0.0);
				hueMap.put(ColorBlindModes.DEUTERANOPIA, 0.0);
				hueMap.put(ColorBlindModes.PROTANOPIA, 0.5);
				hueMap.put(ColorBlindModes.TRITANOPIA, 0.0);
			break;
				
			case ONYX:
				hueMap.put(ColorBlindModes.NORMAL, 0.0);
				hueMap.put(ColorBlindModes.DEUTERANOPIA, 0.0);
				hueMap.put(ColorBlindModes.PROTANOPIA, 0.0);
				hueMap.put(ColorBlindModes.TRITANOPIA, 0.0);
			break;
				
			case SAPPHIRE:
				hueMap.put(ColorBlindModes.NORMAL, 0.0);
				hueMap.put(ColorBlindModes.DEUTERANOPIA, 0.3);
				hueMap.put(ColorBlindModes.PROTANOPIA, 0.0);
				hueMap.put(ColorBlindModes.TRITANOPIA, 0.0);
			break;
				
			case JOKER:
				hueMap.put(ColorBlindModes.NORMAL, 0.0);
				hueMap.put(ColorBlindModes.DEUTERANOPIA, 0.0);
				hueMap.put(ColorBlindModes.PROTANOPIA, 0.0);
				hueMap.put(ColorBlindModes.TRITANOPIA, 0.4);
			break;
		}
		
		return hueMap.get(mode);
	}
}