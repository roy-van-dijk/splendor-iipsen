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
	public int hueOffset(Gem gem, ColorBlindModes mode)
	{
		Map<ColorBlindModes, Integer> hueMap = new HashMap<>();
		switch(this)
		{
			case DIAMOND:
				hueMap.put(ColorBlindModes.NORMAL, 0);
				hueMap.put(ColorBlindModes.DEUTERANOPIA, 0);
				hueMap.put(ColorBlindModes.PROTANOPIA, 0);
				hueMap.put(ColorBlindModes.TRITANOPIA, 0);
			break;
				
			case EMERALD:
				hueMap.put(ColorBlindModes.NORMAL, 0);
				hueMap.put(ColorBlindModes.DEUTERANOPIA, 20);
				hueMap.put(ColorBlindModes.PROTANOPIA, 30);
				hueMap.put(ColorBlindModes.TRITANOPIA, 40);
			break;
				
			case RUBY:
				hueMap.put(ColorBlindModes.NORMAL, 0);
				hueMap.put(ColorBlindModes.DEUTERANOPIA, 20);
				hueMap.put(ColorBlindModes.PROTANOPIA, 30);
				hueMap.put(ColorBlindModes.TRITANOPIA, 40);
			break;
				
			case ONYX:
				hueMap.put(ColorBlindModes.NORMAL, 0);
				hueMap.put(ColorBlindModes.DEUTERANOPIA, 20);
				hueMap.put(ColorBlindModes.PROTANOPIA, 30);
				hueMap.put(ColorBlindModes.TRITANOPIA, 40);
			break;
				
			case SAPPHIRE:
				hueMap.put(ColorBlindModes.NORMAL, 0);
				hueMap.put(ColorBlindModes.DEUTERANOPIA, 0);
				hueMap.put(ColorBlindModes.PROTANOPIA, 0);
				hueMap.put(ColorBlindModes.TRITANOPIA, 0);
			break;
				
			case JOKER:
				hueMap.put(ColorBlindModes.NORMAL, 0);
				hueMap.put(ColorBlindModes.DEUTERANOPIA, 20);
				hueMap.put(ColorBlindModes.PROTANOPIA, 30);
				hueMap.put(ColorBlindModes.TRITANOPIA, 40);
			break;
		}
		
		return hueMap.get(mode);
	}
}