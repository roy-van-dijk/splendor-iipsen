import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import application.domain.Player;
import application.domain.PlayingField;

class TestReturnTokens {
	

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		PlayingField playingField;
		Player player;
		allowConfirm = false;
		tokenListNew = player.getTokenList();
		removedTokens = new ArrayList<>();
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void test() {
	
	}

}
