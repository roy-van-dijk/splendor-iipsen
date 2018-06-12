package application.domain;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import application.views.ReturnTokensView;

/**
 * The Class ReturnTokens.
 */
public class ReturnTokens {
	
	public enum ReturnTokenState { RETURNING, DONE };
	
	private ReturnTokenState returningState = ReturnTokenState.RETURNING;

	
	private TokenList tokenListNew;
	private List<Token> removedTokens;
	
	private Player player;
	private PlayingField playingField;
	private EndTurn endTurn;
	
	private ReturnTokensView view;
	
	private boolean allowConfirm;

	/**
	 * Instantiates a new return tokens.
	 *
	 * @param playingField
	 * @param player
	 * @throws RemoteException 
	 */
	public ReturnTokens(Game game) throws RemoteException
	{
		this.player = game.getCurrentPlayer();
		this.playingField = game.getPlayingField();
		this.endTurn = game.getEndTurn();
		
		List<Token> copyPlayerTokens = new ArrayList<>(player.getTokens());
		this.tokenListNew = new TokenList(copyPlayerTokens);
		
		this.allowConfirm = false;
		
		this.removedTokens = new ArrayList<>();
	}
	
	/**
	 * Gets the token from gem type.
	 *
	 * @param tokenArray
	 * @param gemType
	 * @return Token
	 * @throws RemoteException
	 */
	private Token getTokenFromGemType(List<Token> tokenArray, Gem gemType) throws RemoteException
	{
		for(Token token : tokenArray)
		{
			if(token.getGemType() == gemType) return token;
		}
		return null;
	}

	/**
	 * Removes the token.
	 *
	 * @param gemType
	 * @throws RemoteException
	 */
	public void removeToken(Gem gemType) throws RemoteException 
	{
		if(tokenListNew.getAll().size() > 10 && tokenListNew.getTokenGemCount().get(gemType) > 0) 
		{
			Token token = this.getTokenFromGemType(tokenListNew.getAll(), gemType);
			tokenListNew.remove(token);
			removedTokens.add(token);
		}
		validateNewTokens();
	}
	

	public ReturnTokenState getReturningState() {
		return returningState;
	}

	/**
	 * Adds the token.
	 *
	 * @param gemType
	 * @throws RemoteException
	 */
	public void addToken(Gem gemType) throws RemoteException  
	{
		Token token = this.getTokenFromGemType(removedTokens, gemType);
		if(token != null) 
		{
			tokenListNew.add(token);
			removedTokens.remove(token);
		}
		validateNewTokens();
	}
	
	/**
	 * Notify view.
	 */
	public void notifyView() 
	{
		view.modelChanged(this);
	}
	
	/**
	 * Validate new tokens.
	 *
	 * @throws RemoteException
	 */
	public void validateNewTokens()
	{
		if(tokenListNew.getAll().size() == 10) 
		{
			this.allowConfirm = true;
		} else {
			this.allowConfirm = false;
		}
		this.notifyView();
	}

	/**
	 * Confirm button.
	 *
	 * @throws RemoteException
	 */
	public void confirmButton() throws RemoteException 
	{
		if(allowConfirm)
		{
			//Logger.log("ReturnTokens::confirmButton()::Returning tokens", Verbosity.DEBUG);
			//for(Token token : removedTokens) {
			//	System.out.println(token.getGemType());
			//}
			player.returnTokensToField(removedTokens, playingField);
			//Logger.log("ReturnTokens::confirmButton()::Tokens returned", Verbosity.DEBUG);
			this.returningState = ReturnTokenState.DONE;
			//Logger.log("ReturnTokens::confirmButton()::Ending turn", Verbosity.DEBUG);
			this.endTurn.endTurn();
			//Logger.log("ReturnTokens::confirmButton()::Turn ended", Verbosity.DEBUG);
			
			this.notifyView();
		}
	}

	/**
	 * Gets the token list new.
	 *
	 * @return TokenList
	 */
	public TokenList getTokenListNew() 
	{
		return tokenListNew;
	}


	/**
	 * Registrate.
	 *
	 * @param view
	 * @throws RemoteException
	 */
	public void registrate(ReturnTokensView view) 
	{
		this.view = view;
		this.view.modelChanged(this);
	}


	/**
	 * Checks if is allow confirm.
	 *
	 * @return true, if is allow confirm
	 */
	public boolean isAllowConfirm() 
	{
		return allowConfirm;
	}
	
}
