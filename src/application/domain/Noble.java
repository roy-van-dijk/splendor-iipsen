package application.domain;

import java.util.Map;

public class Noble {
	private int prestigeValue;
	private int illustrationID; // TODO: Replace with string read from nobles.csv instead. 
	private Map<Gem, Integer> requirements;
	
	public Noble(int prestigeValue, int illustrationID, Map<Gem, Integer> requirements) {
		this.prestigeValue = prestigeValue;
		this.illustrationID = illustrationID;
		this.requirements = requirements;
	}

	public int getPrestigeValue() {
		return prestigeValue;
	}

	public Map<Gem, Integer> getRequirements() {
		return requirements;
	}

	public int getIllustrationID() {
		return illustrationID;
	}
	
	
}
