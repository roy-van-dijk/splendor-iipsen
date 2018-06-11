package nl.hsleiden.splendor.test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileInputStream;
import java.rmi.RemoteException;
import java.rmi.server.RemoteRef;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import application.domain.Game;
import application.domain.GameImpl;
import application.services.SaveGameDAO;

// TODO: Auto-generated Javadoc
/**
 * The Class TestOpslaanSpel.
 */
class TestOpslaanSpel {
	private GameImpl game;
	private int maxPlayers = 4;
	/**
	 * Sets the up before class.
	 *
	 * @throws Exception the exception
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	/**
	 * Tear down after class.
	 *
	 * @throws Exception the exception
	 */
	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		game = new GameImpl(maxPlayers);
		
		
	}

	/**
	 * Tear down.
	 *
	 * @throws Exception the exception
	 */
	@AfterEach
	void tearDown() throws Exception {
	}

	/**
	 * Test.
	 */
	@Test
	void test() {
		File file =  new File(SaveGameDAO.getInstance().createSavePath("Bob.splendor"));
		long length = file.lastModified();
		file.length();
		
		
		try {
			game.saveGame();
		} catch (RemoteException e) {
			fail("Not yet implemented");
			e.printStackTrace();
		}
		File file2 =  new File(SaveGameDAO.getInstance().createSavePath("Bob.splendor"));
		assertNotSame(length, file2.lastModified());
	}
	

}
