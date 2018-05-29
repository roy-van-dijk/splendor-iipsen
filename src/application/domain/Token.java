package application.domain;

import java.rmi.Remote;

/**
 * 
 * @author Sanchez
 *
 */
public interface Token extends Remote {
	
	public Gem getGemType();
	
}
