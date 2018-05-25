package application.domain;

import java.util.Map;

/**
 * @author Sanchez
 *
 */
public interface Card {
	public int getPrestigeValue();

	public String getIllustration();

	public Gem getBonusGem();

	public Map<Gem, Integer> getCosts();

	public CardLevel getLevel();
	
	public boolean isReservedFromDeck();

	public void setReservedFromDeck(boolean reservedFromDeck);

}
