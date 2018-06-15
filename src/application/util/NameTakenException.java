package application.util;

/**
 * The Class LobbyFullException.
 */
public class NameTakenException extends Exception {

	private static final long serialVersionUID = 6981984148963301820L;

	/**
	 * Instantiates a new lobby full exception.
	 *
	 * @param message
	 */
	public NameTakenException(String message) {
        super(message);
    }

}
