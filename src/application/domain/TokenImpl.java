package application.domain;

import java.io.Serializable;

/**
 * 
 * @author Sanchez
 *
 */
public class TokenImpl implements Token, Serializable {
	private Gem gemType;

	public Gem getGemType() {
		return gemType;
	}

	public TokenImpl(Gem gemType) {
		this.gemType = gemType;
	}
	
	
}
