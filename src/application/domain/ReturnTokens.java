package application.domain;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import application.controllers.ReturnTokenController;
import application.views.ReturnTokensView;

// TODO: Auto-generated Javadoc
/**
 * The Class ReturnTokens.
 */
public class ReturnTokens {
	private TokenList tokenListNew;
	private List<Token> removedTokens;
	
	private Player player;
	private PlayingField playingField;
	
	private ReturnTokensView view;
	
	private boolean allowConfirm;

	/**
	 * Instantiates a new return tokens.
	 *
	 * @param playingField
	 * @param player
	 */
	public ReturnTokens(PlayingField playingField, Player player)
	{
		this.player = player;
		this.playingField = playingField;
		
		this.allowConfirm = false;
		try {
			this.tokenListNew = new TokenList(player.getTokens());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
	 *
	 * @throws RemoteException
	 */
	public void notifyView() throws RemoteException 
	{
		view.modelChanged(this);
	}
	
	/**
	 * More than ten tokens.
	 *
	 * @param controller
	 * @throws RemoteException
	 */
	public void moreThanTenTokens(ReturnTokenController controller) throws RemoteException
	{
		if(tokenListNew.getAll().size() > 10)
		{
			ReturnTokensView view = new ReturnTokensView(this, controller);
		}
	}
	
	/**
	 * Validate new tokens.
	 *
	 * @throws RemoteException
	 */
	public void validateNewTokens() throws RemoteException
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
			player.returnTokensToField(removedTokens, playingField);
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
	public void registrate(ReturnTokensView view) throws RemoteException 
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
