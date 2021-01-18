package group13.projectgame;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Rectangle;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.AfterAll;

import org.junit.jupiter.api.DisplayName;


@DisplayName("Integration test cases for Board/Player class")

class BoardPlayerTestIntegration {
	
	private Board board;
	
	private Player player;

	
	@BeforeEach
	public void testSetUp() {
		board = new Board("src/test/resources/map/ini_map.png");
	}
	
	@AfterAll
	public static void testEnd() {
		System.out.println("The tests have ended");
	}

	@Test
	void initialPointest() {
		player = new Player(320,224);
		boolean result = false;
		Rectangle bounds  = new Rectangle(player.x, player.y, 32, 32);
		for(int x = 0; x < board.gametiles.length; x++) {
            for(int y = 0; y < board.gametiles[0].length; y++) {
                if (board.gametiles[x][y] != null) {
                	if(bounds.intersects(board.gametiles[x][y])) {
                		result = true;
                	}
                }
            }
		}
		assertFalse(result);
	}
	
	@Test
	void randomPointTest() {
		player = new Player(0,0);
		boolean result = false;
		Rectangle bounds  = new Rectangle(player.x, player.y, 32, 32);
		for(int x = 0; x < board.gametiles.length; x++) {
            for(int y = 0; y < board.gametiles[0].length; y++) {
                if (board.gametiles[x][y] != null) {
                	if(bounds.intersects(board.gametiles[x][y])) {
                		result = true;
                	}
                }
            }
		}
		assertTrue(result);
	}
	
	@Test
	void randomPointTest2() {
		player = new Player(352,256);
		boolean result = false;
		Rectangle bounds  = new Rectangle(player.x, player.y, 32, 32);
		for(int x = 0; x < board.gametiles.length; x++) {
            for(int y = 0; y < board.gametiles[0].length; y++) {
                if (board.gametiles[x][y] != null) {
                	if(bounds.intersects(board.gametiles[x][y])) {
                		result = true;
                	}
                }
            }
		}
		assertTrue(result);
	}

}
