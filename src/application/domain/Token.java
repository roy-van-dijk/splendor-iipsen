package application.domain;
/**
 * 
 * @author Sanchez
 *
 */
public class Token {
	private Gem gemType;

	public Gem getGemType() {
		return gemType;
	}

	public Token(Gem gemType) {
		this.gemType = gemType;
	}
	
	
}
