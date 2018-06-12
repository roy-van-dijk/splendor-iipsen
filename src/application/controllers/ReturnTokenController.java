package application.controllers;

import java.rmi.RemoteException;

import application.domain.Gem;
import application.domain.ReturnTokens;

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
		this.returnTokens.removeToken(gemType);
	}

	/**
	 * Plus token.
	 *
	 * @param gemType
	 * @throws RemoteException
	 */
	public void plusToken(Gem gemType) throws RemoteException {
		this.returnTokens.addToken(gemType);
	}

	/**
	 * Confirm button.
	 *
	 * @throws RemoteException
	 */
	public void confirmButton() throws RemoteException {
		this.returnTokens.confirmButton();
	}

}
