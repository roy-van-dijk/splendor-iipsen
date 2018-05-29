package application.domain;
/**
 * 
 * @author Sanchez
 *
 */
public class TokenImpl implements Token {
	private Gem gemType;

	public Gem getGemType() {
		return gemType;
	}

	public TokenImpl(Gem gemType) {
		this.gemType = gemType;
	}
	
	
}
