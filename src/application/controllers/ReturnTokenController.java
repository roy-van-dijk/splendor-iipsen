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
 * The Class ReturnTokenController.
 *
 * @author Tom
 */
public class ReturnTokenController {
	private ReturnTokens returnTokens;

	/**
	 * Instantiates a new return token controller.
	 *
	 * @param returnTokens
	 */
	public ReturnTokenController(ReturnTokens returnTokens) {
		this.returnTokens = returnTokens;
	}

	/**
	 * Minus token.
	 *
	 * @param gemType
	 * @throws RemoteException
	 */
	public void minusToken(Gem gemType) throws RemoteException {
		returnTokens.removeToken(gemType);
	}

	/**
	 * Plus token.
	 *
	 * @param gemType
	 * @throws RemoteException
	 */
	public void plusToken(Gem gemType) throws RemoteException {
		returnTokens.addToken(gemType);
	}

	/**
	 * Confirm button.
	 *
	 * @throws RemoteException
	 */
	public void confirmButton() throws RemoteException {
		returnTokens.confirmButton();
	}

}
