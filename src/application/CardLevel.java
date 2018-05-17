package application;

import java.util.HashMap;
import java.util.Map;

public enum CardLevel {
	LEVEL1(1),
	LEVEL2(2),
	LEVEL3(3);
	
	private int level;

    private CardLevel(int level) {
		this.level = level;
	}
    
    
	private static Map<Integer, CardLevel> map = new HashMap<Integer, CardLevel>();
	
    static {
        for (CardLevel cardLevel : CardLevel.values()) {
            map.put(cardLevel.level, cardLevel);
        }
    }
    
    
	public static CardLevel getLevel(int level)
	{
		return map.get(level);
	}
}
