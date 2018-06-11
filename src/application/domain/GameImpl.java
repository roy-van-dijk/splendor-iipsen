package application.domain;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import application.services.SaveGameDAO;
import application.util.Logger;
import application.util.Logger.Verbosity;
import application.views.GameView;

	/**
 * The Class GameImpl.
 *
 * @author Sanchez
 */
public class GameImpl extends UnicastRemoteObject implements Game, Serializable {
	
	private static final long serialVersionUID = -2852281344739846301L;
	
	private GameState gameState;
	
	private PlayingFieldImpl playingField;
	
	private int currentPlayerIdx;
	private int roundNr;
	private int maxPlayers;
	private boolean reserveCardInventoryFull;

	private List<Player> players; // Contains a list of PlayerImpl on server
	
	//private transient List<GameObserver> observers;
	private transient Map<GameObserver, Player> observers;
	

	private EndTurnImpl endTurn;
	

	/**
	 * Instantiates a new game impl.
	 *
	 * @param maxPlayers the max players
	 * @throws RemoteException the remote exception
	 */
	public GameImpl(int maxPlayers) throws RemoteException {
		this.maxPlayers = maxPlayers;
		
		this.roundNr = 0;
		this.currentPlayerIdx = -1;
		
		this.players = new ArrayList<Player>();
		this.observers = new LinkedHashMap<GameObserver, Player>();
		
		this.Test_Create4Players();
		
		this.playingField = new PlayingFieldImpl(this.maxPlayers);
		
		this.endTurn = new EndTurnImpl(this);
	}
	

	/*public Player getPlayer(GameObserver o) throws RemoteException
	{
		return playersMap.get(o);
	}*/

	
	/* (non-Javadoc)
	 * @see application.domain.Game#nextTurn()
	 */
	public void nextTurn() throws RemoteException
	{
		System.out.println("[DEBUG] GameImpl::nextTurn()::Next turn started");
		currentPlayerIdx++;
		if(currentPlayerIdx >= players.size() || currentPlayerIdx < 0) {
			currentPlayerIdx = 0;
		}
		
		try {
			playingField.newTurn();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.playingField.getTempHand().updatePlayer(this.getCurrentPlayer());
		System.out.printf("[DEBUG] GameImpl::nextTurn()::Current player: %s(ID: %d)\n", this.getCurrentPlayer().getName(), currentPlayerIdx);
		this.notifyObservers();
	}

	/* (non-Javadoc)
	 * @see application.domain.Game#isDisabled(application.domain.GameObserver)
	 */
	public boolean isDisabled(GameObserver o) throws RemoteException
	{
		Player player = observers.get(o); // THIS IS STILL A PROXY REFERENCE BECAUSE ADDOBSERVER ADDS A PROXY
/*		System.out.printf("Checking if %s is disabled, current player = %s\n", player.getName(), this.getCurrentPlayer().getName());
		System.out.printf("Checking if %s is disabled, current player = %s\n", player, this.getCurrentPlayer());
		System.out.println("is equal: " + (this.getCurrentPlayer().equals(player)));*/
		// Equals check won't work probably if one of the players is a PlayerImpl instead of a proxy ref to a Player
		return !(player.getName().equals(this.getCurrentPlayer().getName())); // Checking for name as a workaround for the proxy-ref problem.
	}
	
	/* (non-Javadoc)
	 * @see application.domain.Game#saveGame()
	 */
	@Override
	public void saveGame() throws RemoteException
	{
		try {
			SaveGameDAO.getInstance().saveGameToFile(this);
			
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Test create 4 players.
	 */
	private void Test_Create4Players()
	{
		try {
			
			PlayerImpl player = new PlayerImpl("Michael");
			/*
			for(int i = 0; i < 13; i++)
			{
				Gem[] allGems = Gem.values();
				int randomIdx = (int) (Math.random() * allGems.length);
				player.debugAddToken(new TokenImpl(allGems[randomIdx]));
			}
			*/
			this.players.add(player);
			this.players.add(new PlayerImpl("Bob"));
			this.players.add(new PlayerImpl("Peter"));
			this.players.add(new PlayerImpl("Martin"));
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/* (non-Javadoc)
	 * @see application.domain.Game#findSelectableCards(application.domain.MoveType)
	 */
	@Override
	public void findSelectableCards(MoveType moveType) throws RemoteException {
		this.playingField.getTempHand().setMoveType(moveType);
		
		if(moveType == MoveType.PURCHASE_CARD) {
			this.getCurrentPlayer().findSelectableCardsFromReserve();
		}
		playingField.findSelectableCardsFromField();
	}
	//TODO not yet tested
	public boolean reserveCardInventoryFull() throws RemoteException
	{
		if(this.getCurrentPlayer().getReservedCards().size() == 3)
		{
			reserveCardInventoryFull = false;
		}
		else 
		{
			reserveCardInventoryFull = true;
		}
		return reserveCardInventoryFull;
	}
	
	/* (non-Javadoc)
	 * @see application.domain.Game#setTokensSelectable(application.domain.MoveType)
	 */
	@Override
	public void setTokensSelectable(MoveType moveType) throws RemoteException {
		this.playingField.setTokensSelectable(moveType);
	}


	/* (non-Javadoc)
	 * @see application.domain.Game#getCurrentPlayerIdx()
	 */
	public int getCurrentPlayerIdx() throws RemoteException {
		return currentPlayerIdx;
	}

	/* (non-Javadoc)
	 * @see application.domain.Game#getRoundNr()
	 */
	public int getRoundNr() throws RemoteException {
		return roundNr;
	}

	/* (non-Javadoc)
	 * @see application.domain.Game#getPlayers()
	 */
	public List<Player> getPlayers() throws RemoteException {
		return players;
	}
	
	/**
	 * Sets the players.
	 *
	 * @param players the new players
	 */
	public void setPlayers(List<Player> players) {
		this.players = players;
	}

	/* (non-Javadoc)
	 * @see application.domain.Game#getPlayingField()
	 */
	public PlayingField getPlayingField() throws RemoteException {
		return playingField;
	}

	

	/* (non-Javadoc)
	 * @see application.domain.Game#getCurrentPlayer()
	 */
	public Player getCurrentPlayer() throws RemoteException {
		return players.get(currentPlayerIdx);
	}

	/**
	 * Gets the max players.
	 *
	 * @return the max players
	 * @throws RemoteException the remote exception
	 */
	public int getMaxPlayers() throws RemoteException {
		return maxPlayers;
	}

	/**
	 * Gets the game state.
	 *
	 * @return GameSate
	 * @throws RemoteException
	 */
	public GameState getGameState() throws RemoteException {
		return gameState;
	}
	
	/**
	 * Notify observers.
	 *
	 * @throws RemoteException
	 */
	private synchronized void notifyObservers() throws RemoteException
	{
		System.out.println("[DEBUG] GameImpl::notifyObservers()::Notifying all game observers of change");
		for(GameObserver o : observers.keySet())
		{
			o.modelChanged(this);
		}
	}

	/* (non-Javadoc)
	 * @see application.domain.Game#addObserver(application.domain.GameObserver)
	 */
	@Override
	public synchronized void addObserver(GameObserver o, Player player) throws RemoteException {
		this.observers.put(o, player);
		this.notifyObservers();
	}

	/* (non-Javadoc)
	 * @see application.domain.Game#removeObserver(application.domain.GameObserver)
	 */
	@Override
	public synchronized void removeObserver(GameObserver o) throws RemoteException {
		this.observers.remove(o);
		this.notifyObservers();
	}
	
	/* (non-Javadoc)
	 * @see application.domain.Game#updatePlayingFieldAndPlayerView()
	 */
	@Override
	public void cleanUpTurn() throws RemoteException {
		playingField.getTempHand().emptyHand();
	}

	/* (non-Javadoc)
	 * @see application.domain.Game#updatePlayingFieldAndPlayerView()
	 */
	public void updatePlayingFieldAndPlayerView() throws RemoteException {
		for(CardRow cardRow : playingField.getCardRows()) {
			cardRow.updateView();
		}
		this.getCurrentPlayer().updatePlayerView();
	}
	
	/* (non-Javadoc)
	 * @see application.domain.Game#cleanUpSelections()
	 */
	@Override
	public void cleanUpSelections() throws RemoteException {
		this.playingField.getTempHand().emptyHand();
		
		for(CardRow cardRow : playingField.getCardRows()) {
			cardRow.clearSelectableCards();
		}
		playingField.newTurn();
		this.getCurrentPlayer().clearSelectableCards();
		this.notifyObservers();
	}
	
	/* (non-Javadoc)
	 * @see application.domain.Game#addCardToTempFromReserve(application.domain.Card)
	 */
	@Override
	public void reserveCardFromDeck(int cardRowIdx) throws RemoteException {
		CardRow cardRow = this.playingField.getCardRows().get(cardRowIdx);
		Card card = cardRow.getCardDeck().top();
		this.addCardToTempHand(card, this.playingField.getTempHand());
		card.setReservedFromDeck(true);
		
		this.getPlayingField().setDeckSelected(cardRow);
		this.updatePlayingFieldAndPlayerView();	
		this.notifyObservers();
	}
	
	/* (non-Javadoc)
	 * @see application.domain.Game#addCardToTempFromField(int, int)
	 */
	@Override
	public void addCardToTempFromField(int cardRowIdx, int cardIdx) throws RemoteException {
		CardRow cardRow = this.playingField.getCardRows().get(cardRowIdx);
		Card card = cardRow.getCardSlots()[cardIdx];
		Logger.log("GameImpl::addCardToTempFromField::Card = " + card, Verbosity.DEBUG);
		TempHand tempHand = this.playingField.getTempHand();
		
		this.addCardToTempHand(card, tempHand);
		
		if(tempHand.getMoveType() == MoveType.RESERVE_CARD)
		{
			this.playingField.setDeckDeselected();
		}
		this.updatePlayingFieldAndPlayerView();
		this.notifyObservers();
	}
	
	/* (non-Javadoc)
	 * @see application.domain.Game#addCardToTempFromReserve(int)
	 */
	@Override
	public void addCardToTempFromReserve(int cardIdx) throws RemoteException {
		Card card = this.getCurrentPlayer().getReservedCards().get(cardIdx);
		TempHand tempHand = this.getPlayingField().getTempHand();
		
		this.addCardToTempHand(card, tempHand);
		
		this.updatePlayingFieldAndPlayerView();
		this.notifyObservers();
	}

	/* (non-Javadoc)
	 * @see application.domain.Game#getEndTurn()
	 */
	public EndTurn getEndTurn() throws RemoteException {
		return endTurn;
	}
	
	/**
	 * Adds the card to temp hand.
	 *
	 * @param card
	 * @param tempHand
	 * @throws RemoteException
	 */
	private void addCardToTempHand(Card card, TempHand tempHand) throws RemoteException {	
		MoveType moveType = tempHand.getMoveType();
		System.out.println("GameImpl::addCardToTemp()::Card = " + card);
		if(moveType == MoveType.PURCHASE_CARD) {
			tempHand.selectCardToBuy(card);
		} else if(moveType == MoveType.RESERVE_CARD) {
			tempHand.selectCardToReserve(card);
		}
	}

	/* (non-Javadoc)
	 * @see application.domain.Game#addTokenToTemp(application.domain.Gem)
	 */
	@Override
	public void addTokenToTemp(Gem gemType) throws RemoteException {
		this.playingField.addTokenToTemp(gemType);
		this.notifyObservers();
	}


}
