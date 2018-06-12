package application.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;


/** 
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
