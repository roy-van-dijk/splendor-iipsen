/**
 * 
 */
package nl.hsleiden.splendor.test;

import static org.junit.jupiter.api.Assertions.*;

import java.rmi.RemoteException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import application.domain.GameImpl;
import application.domain.Gem;
import application.domain.TokenImpl;

/**
 * @author alexi
 *
 */
class TestToken {
	private GameImpl game;
	private TokenImpl token;
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
	}

	/**
	 * @throws java.lang.Exception
	 * void
	 */
	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testToken() {
		try {
			token = new TokenImpl(Gem.DIAMOND);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(token.getGemType(), Gem.DIAMOND);
	}

}
