package application.views;

import java.rmi.Remote;
import java.rmi.RemoteException;


/**
 * Is implemented by views that can be disabled.
 *
 * @author Sanchez
 */
public interface Disableable {

	/**
	 * Updates the view to accompany the disabled change.
	 *
	 * @param disabled the new disabled
	 */
	public void setDisabled(boolean disabled);
}
