import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileInputStream;
import java.rmi.RemoteException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import application.domain.Game;
import application.domain.GameImpl;
import application.services.SaveGameDAO;

class TestOpslaanSpel {
	private GameImpl game;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		game = new GameImpl(4);
		
		
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void test() {
		File file =  new File(SaveGameDAO.getInstance().createSavePath("Bob.splendor"));
		long length = file.lastModified();
		file.length();
		
		
		try {
			game.saveGame();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		File file2 =  new File(SaveGameDAO.getInstance().createSavePath("Bob.splendor"));
		assertNotSame(length, file2.lastModified());
		//assertEquals()
	}
	

}
