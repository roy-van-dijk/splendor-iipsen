package application.domain;

import java.io.Serializable;

/**
 * The Class TokenImpl.
 *
 * @author Sanchez
 */
public class TokenImpl implements Token, Serializable {
	private Gem gemType;

	/* (non-Javadoc)
	 * @see application.domain.Token#getGemType()
	 */
	public Gem getGemType() {
		return gemType;
	}

	/**
	 * Instantiates a new token impl.
	 *
	 * @param gemType
	 */
	public TokenImpl(Gem gemType) {
		this.gemType = gemType;
	}
	
	
}
