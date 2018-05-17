package application;

import java.util.Map;

public class Card {

	private CardLevel level;
	private int prestigeValue;
	private String illustration;
	private Gem bonus;
	private Map<Gem, Integer> costs;
	
	
	public Card(CardLevel level, int prestigeValue, String illustration, Gem bonus, Map<Gem, Integer> costs) {
		this.level = level;
		this.prestigeValue = prestigeValue;
		this.illustration = illustration;
		this.bonus = bonus;
		this.costs = costs;
	}


	public CardLevel getLevel() {
		return level;
	}


	public void setLevel(CardLevel level) {
		this.level = level;
	}

	
	
}
