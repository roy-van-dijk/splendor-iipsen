package application.domain;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

/**
 * Cards on the playing field and inventory
 * 
 * @author Sanchez
 *
 */
public interface Card extends Remote {

	/**
	 * Gets the prestige value this card would provide for the player
	 * 
	 * @return int
	 * @throws RemoteException
	 */
	public int getPrestigeValue() throws RemoteException;

	/**
	 * Gets the file name for the card's illustration
	 * 
	 * @return String
	 * @throws RemoteException
	 */
	public String getIllustration() throws RemoteException;

	/**
	 * Gets the bonus gem (discount) the card provides
	 * 
	 * @return Gem
	 * @throws RemoteException
	 */
	public Gem getBonusGem() throws RemoteException;

	/**
	 * Gets the token cost of the card
	 * 
	 * @return Map<Gem, Integer>
	 * @throws RemoteException
	 */
	public Map<Gem, Integer> getCosts() throws RemoteException;

	/**
	 * Gets the level (1/2/3) of the card
	 * 
	 * @return CardLevel
	 * @throws RemoteException
	 */
	public CardLevel getLevel() throws RemoteException;

	/**
	 * Checks if the card is reserved from the deck
	 * 
	 * @return boolean
	 * @throws RemoteException
	 */
	public boolean isReservedFromDeck() throws RemoteException;

	/**
	 * Sets the card as reserved from the deck
	 * 
	 * @return boolean
	 * @throws RemoteException
	 */
	public void setReservedFromDeck(boolean reservedFromDeck) throws RemoteException;

}
