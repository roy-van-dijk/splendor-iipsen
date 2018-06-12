/**
 * 
 */
package nl.hsleiden.splendor.test;

import static org.junit.jupiter.api.Assertions.*;

import java.rmi.RemoteException;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import application.domain.Gem;
import application.domain.PlayingFieldImpl;
import application.domain.TempHand;
import application.domain.TokenList; 
import application.domain.TokenImpl;

/**
 * @author alexi
 *
 */
class TestTokenList {
	private PlayingFieldImpl playingField;
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
	void temphandGems() throws RemoteException {
		TempHand temphand = playingField.getTempHand();
		TokenImpl token = new TokenImpl(Gem.DIAMOND);
		temphand.addToken(token);
		
		Gem seletedGem = temphand.getSelectedGemTypes().get(0);
		
		assertEquals(seletedGem, Gem.DIAMOND); 
		
	}
}
