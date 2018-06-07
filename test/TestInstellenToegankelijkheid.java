import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import application.controllers.GameController;
import application.domain.ColorBlindModes;
import application.domain.Game;
import application.views.GameView;
import application.views.TokenView;

class TestInstellenToegankelijkheid {
	private Game game;
	private GameController gameController;	
	private ColorBlindModes mode;
	GameView view = new GameView(game, gameController);

	//TokenView token = new TokenView(Gem, double);
	

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void test() {
		view.changeColorBlindMode(mode.DEUTERANOPIA);
		
		
		}

}
