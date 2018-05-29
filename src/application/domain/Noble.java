package application.domain;

import java.util.Map;
/**
 * 
 * @author Sanchez
 *
 */
public class Noble {
	private int prestigeValue;
	private String illustration; 
	private Map<Gem, Integer> requirements;
	
	public Noble(int prestigeValue, String illustration, Map<Gem, Integer> requirements) {
		this.prestigeValue = prestigeValue;
		this.illustration = illustration;
		this.requirements = requirements;
	}

	public int getPrestigeValue() {
		return prestigeValue;
	}

	public Map<Gem, Integer> getRequirements() {
		return requirements;
	}

	public String getIllustration() {
		return illustration;
	}
	
	
}
