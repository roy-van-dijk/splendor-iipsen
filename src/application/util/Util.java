package application.util;

/** 
 * This is a generic helper class
 * Traditionally, this is against the OOP paradigm as it is procedural programming
 * I reckon we evaluate the necessity of this class at a later stage and make a decision whether to keep it or not.
 * 
 * @author Sanchez 
 */

public final class Util {

	private Util() {
		// Prevents instantiation
	}
	
	public static int StringToInt(String str, int defaultValue)
	{
		if(str.isEmpty()) return defaultValue;
		
		return Integer.parseInt(str);
	}

	
	// TODO: to be removed
	public static String randomNumberString(int min, int max)
	{
		return Integer.toString((int) (Math.random() * max + min));
	}
}
