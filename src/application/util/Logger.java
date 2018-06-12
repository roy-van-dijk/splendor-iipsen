package application.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * The Class Logger.
 *
 * @author Sanchez
 */
public class Logger {
	
	/**
	 * The Enum Verbosity.
	 */
	public enum Verbosity { 
		RELEASE(0), 
		DEBUG(1);
		
	    private int val;

	    /**
    	 * Instantiates a new verbosity.
    	 *
    	 * @param val the val
    	 */
    	Verbosity(int val) {
	        this.val = val;
	    }

	    /**
    	 * Gets the level.
    	 *
    	 * @return the level
    	 */
    	public int getLevel() {
	            return val;
	    }
	}
	
	public static Verbosity currentVerbosity = Verbosity.RELEASE;
	
	/**
	 * Instantiates a new logger.
	 */
	private Logger()
	{	
	}
	
	/**
	 * Log.
	 *
	 * @param message
	 * @param  the verbosity
	 */
	public static void log(String message, Verbosity verbosity)
	{
		if(currentVerbosity.getLevel() < verbosity.getLevel()) return;
		
		String outputMsg = String.format("%s [%s] %s", getCurrentLocalDateTimeStamp(), verbosity.toString().toUpperCase(), message);
		System.out.println(outputMsg);
	}
	
	/**
	 * Gets the current local date time stamp.
	 *
	 * @return the current
	 */
	private static String getCurrentLocalDateTimeStamp() {
	    return LocalDateTime.now()
	       .format(DateTimeFormatter.ofPattern("HH:mm:ss.SSS"));
	}
}
