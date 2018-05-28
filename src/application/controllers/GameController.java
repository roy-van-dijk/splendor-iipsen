package application.controllers;

import application.domain.Card;
import application.domain.CardRowImpl;

/**
 * @author Sanchez
 *
 */
public interface GameController 
{
	public void reserveCard();
	
	public void endTurn();
	
}
