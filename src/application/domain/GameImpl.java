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

/**
 * The Class GameImpl.
 *
 * @author Sanchez
 */
public class GameImpl extends UnicastRemoteObject implements Game, Serializable {
	
	private static final long serialVersionUID = -2852281344739846301L;
	
	public GameState gameState;
	
	private int currentPlayerIdx;
	private int roundNr;
	
	private List<Player> players;
	//private Map<Integer, Player> players;
	
	private PlayingFieldImpl playingField;
	
	private int maxPlayers;

	private transient List<GameObserver> observers;
	
	private EndTurnImpl endTurn;
	
	/*
	 * The Game object knows how many players there are
	 * Therefore the game object determines how many tokens and nobles there need to be created. 
	 * This information is then passed onto the playingField, which generates those tokens and nobles.
	 */

	/**
	 * Instantiates a new game impl.
	 *
	 * @param maxPlayers
	 * @throws RemoteException
	 */
	public GameImpl(int maxPlayers) throws RemoteException {
		this.maxPlayers = maxPlayers;
		
		this.roundNr = 0;
		this.currentPlayerIdx = 0; // TODO: First opponent starts first for now (1 because 0 = Player in SP)
		
		this.players = new ArrayList<Player>(); // TODO: replace with: this.players = players;
		this.observers = new ArrayList<GameObserver>();
		
		Test_Create4Players();
		
		this.playingField = new PlayingFieldImpl(this.maxPlayers);
		
		playingField.getTempHand().updatePlayer(this.getCurrentPlayer());
		
		endTurn = new EndTurnImpl(this);
	}
	
	/* (non-Javadoc)
	 * @see application.domain.Game#nextTurn()
	 */
	public void nextTurn() throws RemoteException
	{
		System.out.println("Next turn started");
		currentPlayerIdx++;
		if(currentPlayerIdx >= players.size()) {
			currentPlayerIdx = 0;
		}
		
		try {
			playingField.newTurn();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(currentPlayerIdx);
		
		// TODO: remove once lobby done
		for(GameObserver o : observers)
		{
			o.setDisabled(true);
		}
		if(currentPlayerIdx < observers.size())
		{
			observers.get(currentPlayerIdx).setDisabled(false);
		}
		this.notifyObservers();
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
			for(int i = 0; i < 13; i++)
			{
				Gem[] allGems = Gem.values();
				int randomIdx = (int) (Math.random() * allGems.length);
				player.debugAddToken(new TokenImpl(allGems[randomIdx]));
			}
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
	 * @see application.domain.Game#findSelectableCards()
	 */
	public void findSelectableCards() throws RemoteException {
		if(playingField.getTempHand().getMoveType() == MoveType.PURCHASE_CARD) {
			this.getCurrentPlayer().findSelectableCardsFromReserve();
		}
		playingField.findSelectableCardsFromField();
	}

	/* (non-Javadoc)
	 * @see application.domain.Game#getCurrentPlayerIdx()
	 */
	public int getCurrentPlayerIdx() {
		return currentPlayerIdx;
	}

	/* (non-Javadoc)
	 * @see application.domain.Game#getRoundNr()
	 */
	public int getRoundNr() {
		return roundNr;
	}

	/* (non-Javadoc)
	 * @see application.domain.Game#getPlayers()
	 */
	public List<Player> getPlayers() {
		return players;
	}
	
	/**
	 * Sets the players.
	 *
	 * @param players
	 */
	public void setPlayers(List<Player> players) {
		this.players = players;
	}

	/* (non-Javadoc)
	 * @see application.domain.Game#getPlayingField()
	 */
	public PlayingField getPlayingField() {
		return playingField;
	}

	
	/* (non-Javadoc)
	 * @see application.domain.Game#getCurrentPlayer()
	 */
	public Player getCurrentPlayer() {
		return players.get(currentPlayerIdx);
	}

	/**
	 * Gets the max players.
	 *
	 * @return maxPlayers
	 */
	public int getMaxPlayers() {
		return maxPlayers;
	}

	/**
	 * Gets the game state.
	 *
	 * @return GameState
	 */
	public GameState getGameState() {
		return gameState;
	}
	
	/**
	 * Notify observers.
	 *
	 * @throws RemoteException
	 */
	private void notifyObservers() throws RemoteException
	{
		for(GameObserver o : observers)
		{
			o.modelChanged(this);
		}
	}

	/* (non-Javadoc)
	 * @see application.domain.Game#addObserver(application.domain.GameObserver)
	 */
	@Override
	public void addObserver(GameObserver o) throws RemoteException {
		this.observers.add(o);
		this.notifyObservers();
	}

	/* (non-Javadoc)
	 * @see application.domain.Game#removeObserver(application.domain.GameObserver)
	 */
	@Override
	public void removeObserver(GameObserver o) throws RemoteException {
		this.observers.remove(o);
		this.notifyObservers();
	}
	
	/* (non-Javadoc)
	 * @see application.domain.Game#updatePlayingFieldAndPlayerView()
	 */
	@Override
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
		for(CardRow cardRow : playingField.getCardRows()) {
			cardRow.clearSelectableCards();
		}
		playingField.newTurn();
		this.getCurrentPlayer().clearSelectableCards();
	}
	
	/* (non-Javadoc)
	 * @see application.domain.Game#addCardToTempFromReserve(application.domain.Card)
	 */
	@Override
	public void addCardToTempFromReserve(Card card) throws RemoteException {	
		MoveType moveType = this.getPlayingField().getTempHand().getMoveType();
		if(moveType == MoveType.PURCHASE_CARD) {
			this.getPlayingField().getTempHand().selectCardToBuy(card);
		} else if(moveType == MoveType.RESERVE_CARD) {
			this.getPlayingField().getTempHand().selectCardToReserve(card);
		}
		this.getCurrentPlayer().updatePlayerView();
	}
	
	/* (non-Javadoc)
	 * @see application.domain.Game#getEndTurn()
	 */
	@Override
	public EndTurn getEndTurn() throws RemoteException {
		return endTurn;
	}

}
