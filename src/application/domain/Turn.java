package application.domain;

import java.util.ArrayList;
import java.util.List;
/**
 * 
 * @author Kees
 *
 */
public class Turn {
	
	private Player player;
	private List<Noble> nobles;
	private Card boughtCard;
	private Card reservedCard;
	private TokenList tokenList;
	
	public Turn(Player player) {
		this.nobles = new ArrayList<>();
		this.tokenList = new TokenList();
		
		this.player = player;
	}
	
	public Player getPlayer() {
		return this.player;
	}
	
	public void selectNoble(Noble noble) {
		nobles.add(noble);
	}
	
	public void selectCardToBuy(Card card) {
		boughtCard = card;
	}
	
	public void selectCardToReserve(Card card) {
		reservedCard = card;
	}
	
	public Card getBoughtCard() {
		return boughtCard;
	}
	
	public Card getReservedCard() {
		return reservedCard;
	}
	
	public TokenList getTokenList() {
		return tokenList;
	}
	
	public void emptyhand() {
		nobles.clear();
		tokenList.getAll().clear();
		reservedCard = null;
		boughtCard = null;
	}
	
}
