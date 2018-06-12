/**
 * 
 */
package nl.hsleiden.splendor.test;

import static org.junit.jupiter.api.Assertions.*;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import application.domain.GameImpl;
import application.domain.Gem;
import application.domain.LobbyImpl;
import application.domain.LobbyObserver;
import application.domain.LobbyImpl.LobbyStates;

/**
 * @author Alexander
 * 
 *
 */
class TestLobby {
	private LobbyImpl lobby;
	public enum LobbyStates { WAITING, STARTED_GAME, CLOSING };
	
	private LobbyStates lobbyState; 
	
	private LobbyObserver host;
	
	private GameImpl game;
	
	private String hostIP;
	private int maxPlayers;
	
	private Registry registry;
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
		//game = new GameImpl();
		//lobby = new LobbyImpl(game);
	}

	/**
	 * @throws java.lang.Exception
	 * void
	 */
	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testhostIp() throws RemoteException {
		try {
			hostIP = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e2) {
			fail("Not yet implemented");
			e2.printStackTrace();
		}
		try {
			lobby.getUnassignedPlayers();
		} catch (RemoteException e) {
			fail("Not yet implemented");
			e.printStackTrace();
		}
		try {
			assertEquals(lobby.getHostIP(),hostIP);
		} catch (RemoteException e) {
		
			e.printStackTrace();
		}
	}
	@Test 
	void test() {
		
	}

}
