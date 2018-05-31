package application.controllers;

import java.rmi.RemoteException;
import java.util.LinkedHashMap;

import application.domain.Game;
import application.domain.Gem;
import application.domain.Player;
import application.domain.PlayingField;
import application.domain.PlayingFieldImpl;
import application.domain.ReturnTokens;
import application.domain.Token;
import application.domain.TokenList;
import application.views.ReturnTokensView;



public class ReturnTokenControllerImpl implements ReturnTokenController{	
	private ReturnTokens returnTokens;

	public ReturnTokenControllerImpl(ReturnTokens returnTokens) {
		this.returnTokens = returnTokens;
	}
	
	public void minusToken(Gem gemType) throws RemoteException {
		returnTokens.removeToken(gemType);
	}

	public void plusToken(Gem gemType) throws RemoteException {
		returnTokens.addToken(gemType);
	}

	@Override
	public void confirmButton() throws RemoteException {
		returnTokens.confirmButton();
	}
	
}
