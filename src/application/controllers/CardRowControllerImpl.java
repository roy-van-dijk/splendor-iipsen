package application.controllers;

import application.domain.Card;
import application.domain.CardRow;
import application.domain.Game;

public class CardRowControllerImpl implements CardRowController {
	private Game game;
	private CardRow cardRow;

	public CardRowControllerImpl(CardRow cardRow, Game game) {
		this.game = game;
		this.cardRow = cardRow;
	}

	@Override
	public void reserveCardFromField(Card card) {
		this.cardRow.removeCard(card);
	}
	
	
}
