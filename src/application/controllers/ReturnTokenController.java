package application.controllers;

import java.rmi.RemoteException;

import application.domain.Gem;
import application.domain.Token;

public interface ReturnTokenController {
	
	public void minusToken(Gem gemType) throws RemoteException;
	
	public void plusToken(Gem gemType) throws RemoteException;
	
	public void confirmButton() throws RemoteException;

}
