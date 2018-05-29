package application.controllers;

import java.rmi.RemoteException;

import application.domain.Card;
import application.domain.CardRowImpl;

/**
 * @author Sanchez
 *
 */
public interface GameController 
{
	public void reserveCard() throws RemoteException;
	
	public void endTurn() throws RemoteException;
	
}
