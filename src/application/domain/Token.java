package application.domain;

public class Token {
	private Gem gemType;

	public Gem getGemType() {
		return gemType;
	}

	public Token(Gem gemType) {
		this.gemType = gemType;
	}
	
	
}
