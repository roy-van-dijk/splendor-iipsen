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

/**
 * @author Tom
 *
 */
public class ReturnTokenController {
	private ReturnTokens returnTokens;

	/**
	 * 
	 * @param returnTokens
	 */
	public ReturnTokenController(ReturnTokens returnTokens) {
		this.returnTokens = returnTokens;
	}

	/**
	 * 
	 * @param gemType
	 * @throws RemoteException
	 * void
	 */
	public void minusToken(Gem gemType) throws RemoteException {
		returnTokens.removeToken(gemType);
	}
/**
 * 
 * @param gemType
 * @throws RemoteException
 * void
 */
	public void plusToken(Gem gemType) throws RemoteException {
		returnTokens.addToken(gemType);
	}

	/**
	 * 
	 * @throws RemoteException
	 *             void
	 */
	public void confirmButton() throws RemoteException {
		returnTokens.confirmButton();
	}

}
