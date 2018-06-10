package application.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
	
	public enum Verbosity { 
		RELEASE(0), 
		DEBUG(1);
		
	    private int val;

	    Verbosity(int val) {
	        this.val = val;
	    }

	    public int getLevel() {
	            return val;
	    }
	}
	
	public static Verbosity currentVerbosity = Verbosity.RELEASE;
	
	private Logger()
	{	
	}
	
	public static void log(String message, Verbosity verbosity)
	{
		if(currentVerbosity.getLevel() < verbosity.getLevel()) return;
		
		String outputMsg = String.format("%s [%s] %s", getCurrentLocalDateTimeStamp(), verbosity.toString().toUpperCase(), message);
		System.out.println(outputMsg);
	}
	
	private static String getCurrentLocalDateTimeStamp() {
	    return LocalDateTime.now()
	       .format(DateTimeFormatter.ofPattern("HH:mm:ss.SSS"));
	}
}
