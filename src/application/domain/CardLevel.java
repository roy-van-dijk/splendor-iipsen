package application.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * The Enum CardLevel.
 *
 * @author Sanchez
 */
public enum CardLevel {
	LEVEL1(1),
	LEVEL2(2),
	LEVEL3(3);
	
	private int level;

    /**
     * Instantiates a new card level.
     *
     * @param level
     */
    private CardLevel(int level) {
		this.level = level;
	}
    
    
	private static Map<Integer, CardLevel> map = new HashMap<Integer, CardLevel>();
	
    static {
        for (CardLevel cardLevel : CardLevel.values()) {
            map.put(cardLevel.level, cardLevel);
        }
    }
    
    
	/**
	 * Gets the level.
	 *
	 * @param level
	 * @return CardLevel
	 */
	public static CardLevel getLevel(int level)
	{
		return map.get(level);
	}
}
