package application.util;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

// TODO: Auto-generated Javadoc
/** 
 * This is a generic helper class
 * Traditionally, this is against the OOP paradigm as it is procedural programming
 * I reckon we evaluate the necessity of this class at a later stage and make a decision whether to keep it or not.
 * 
 * @author Sanchez 
 */

public final class Util {

	/**
	 * Instantiates a new util.
	 */
	private Util() {
		// Prevents instantiation
	}
	
	/**
	 * String to int.
	 *
	 * @param str the str
	 * @param defaultValue the default value
	 * @return int
	 */
	public static int StringToInt(String str, int defaultValue)
	{
		if(str.isEmpty()) return defaultValue;
		
		return Integer.parseInt(str);
	}

	/**
	 * Gets the key by value.
	 *
	 * @param <T> the generic type
	 * @param <E> the element type
	 * @param map the map
	 * @param value the value
	 * @return T
	 */
	public static <T, E> T getKeyByValue(Map<T, E> map, E value) {
	    for (Entry<T, E> entry : map.entrySet()) {
	        if (Objects.equals(value, entry.getValue())) {
	            return entry.getKey();
	        }
	    }
	    return null;
	}
	
	/**
	 * Random number string.
	 *
	 * @param min the min
	 * @param max the max
	 * @return the string
	 */
	// TODO: to be removed
	public static String randomNumberString(int min, int max)
	{
		return Integer.toString((int) (Math.random() * max + min));
	}
}
