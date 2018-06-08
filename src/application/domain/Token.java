package application.domain;

import java.rmi.Remote;

/**
 * Gameplayer token
 * 
 * @author Sanchez
 *
 */
public interface Token extends Remote {

	/**
	 * Gets the gem type this token is associated with
	 * 
	 * @return
	 */
	public Gem getGemType();

}
