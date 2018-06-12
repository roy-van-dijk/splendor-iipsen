/**
 * 
 */
package nl.hsleiden.splendor.test;

import static org.junit.jupiter.api.Assertions.*;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import application.domain.CardImpl;
import application.domain.CardLevel;
import application.domain.GameImpl;
import application.domain.Gem;
import application.domain.Player;
import application.domain.PlayerImpl;
import application.domain.PlayingFieldImpl;
import application.domain.TempHand;
import application.domain.TokenImpl;

/**
 * @author alexi
 *
 */
class TestPlayingField {

	private PlayingFieldImpl playingField;
	private PlayerImpl player;
	private GameImpl game;
	/**
	 * @throws java.lang.Exception
	 * void
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 * void
	 */
	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 * void
	 */
	@BeforeEach
	void setUp() throws Exception {
		playingField = new PlayingFieldImpl(0);
		
		
	}

	/**
	 * @throws java.lang.Exception
	 * void
	 */
	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void tokenlist() throws RemoteException {
		
		assertEquals(40, playingField.getTokenList().getAll().size());
	}
	
	@Test
	void temphandTokens() throws RemoteException {
		TempHand temphand = playingField.getTempHand();
		TokenImpl token = new TokenImpl(Gem.DIAMOND);
		temphand.addToken(token);
		
		Gem seletedGem = temphand.getSelectedGemTypes().get(0);
		
		assertEquals(seletedGem, Gem.DIAMOND); 
		
	}
	@Test
	void playersetTest() throws RemoteException {
		player = new PlayerImpl("kees");
		game = new GameImpl(1);
		List<Player> players = new ArrayList<Player>();
		players.add(player);
		game.setPlayers(players);
		
		assertEquals(game.getPlayers().get(0), player); 	
	} 
	
	@Test
	void testPrestige() throws RemoteException {
		player = new PlayerImpl("kees");
		game = new GameImpl(1);
		List<Player> players = new ArrayList<Player>();
		players.add(player);
		game.setPlayers(players);
		CardImpl card = new CardImpl(CardLevel.LEVEL1, 13, null, null, null);
		game.nextTurn();
		game.getCurrentPlayer().addCard(card);
		System.out.println(game.getPlayers().get(0).getPrestige());
		
		assertEquals(13, game.getPlayers().get(0).getPrestige());
	}

}
