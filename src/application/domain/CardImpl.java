package application.domain;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;

/**
 * The Class CardImpl.
 *
 * @author Sanchez
 */
public class CardImpl extends UnicastRemoteObject implements Card, Serializable {

	
	private static final long serialVersionUID = -2889130830902711273L;
	
	private boolean reservedFromDeck = false;
	private CardLevel level;
	private int prestigeValue;
	private String illustration;
	private Gem bonusGem;
	private Map<Gem, Integer> costs;
	
	

	/**
	 * Instantiates a new card impl.
	 *
	 * @param level
	 * @param prestigeValue
	 * @param illustration
	 * @param bonus
	 * @param costs
	 * @throws RemoteException
	 */
	public CardImpl(CardLevel level, int prestigeValue, String illustration, Gem bonus, Map<Gem, Integer> costs) throws RemoteException {
		this.level = level;
		this.prestigeValue = prestigeValue;
		this.illustration = illustration;
		this.bonusGem = bonus;
		this.costs = costs;
	}
	
	
	/* (non-Javadoc)
	 * @see application.domain.Card#isReservedFromDeck()
	 */
	public boolean isReservedFromDeck() {
		return reservedFromDeck;
	}

	/* (non-Javadoc)
	 * @see application.domain.Card#setReservedFromDeck(boolean)
	 */
	// TODO: find a better way to make a card be a reserved card (perhaps separate class?)
	public void setReservedFromDeck(boolean reservedFromDeck) {
		this.reservedFromDeck = reservedFromDeck;
	}

	/* (non-Javadoc)
	 * @see application.domain.Card#getPrestigeValue()
	 */
	public int getPrestigeValue() {
		return prestigeValue;
	}

	/* (non-Javadoc)
	 * @see application.domain.Card#getIllustration()
	 */
	public String getIllustration() {
		return illustration;
	}


	/* (non-Javadoc)
	 * @see application.domain.Card#getBonusGem()
	 */
	public Gem getBonusGem() {
		return bonusGem;
	}


	/* (non-Javadoc)
	 * @see application.domain.Card#getCosts()
	 */
	public Map<Gem, Integer> getCosts() {
		return costs;
	}


	/* (non-Javadoc)
	 * @see application.domain.Card#getLevel()
	 */
	public CardLevel getLevel() {
		return level;
	}

	
}
