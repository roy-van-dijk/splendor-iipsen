package application.util;

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
	
	
	public static String getCSSname()
	{
		String css = "application.css";
		String sourceLocation = Util.class.getProtectionDomain().getCodeSource().getLocation().toExternalForm();
		if(sourceLocation.contains(".jar")) {
			css = "application_deployed.css";
		}
		System.out.println("Source location: " + Util.class.getProtectionDomain().getCodeSource().getLocation().toExternalForm());
		return css;
	}
	
	
	/**
	 * String to int.
	 *
	 * @param str
	 * @param default
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
	 * @param min
	 * @param max
	 * @return String
	 */
	// TODO: to be removed
	public static String randomNumberString(int min, int max)
	{
		return Integer.toString((int) (Math.random() * max + min));
	}
}
