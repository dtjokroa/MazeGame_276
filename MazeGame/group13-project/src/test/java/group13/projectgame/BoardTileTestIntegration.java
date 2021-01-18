package group13.projectgame;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.AfterAll;

import org.junit.jupiter.api.DisplayName;

import group13.projectgame.Board;

import group13.projectgame.Tile;

@DisplayName("Integration test cases for Board/Tile class")

public class BoardTileTestIntegration {

	private Board board;
	
	private Tile tile;
	
	@BeforeEach
	public void testSetUp() {
		board = new Board("src/test/resources/map/ini_map.png");
		tile = new Tile(0,0);
	}
	
	@AfterAll
	public static void testEnd() {
		System.out.println("The tests have ended");
	}
	
	@Test
	public void initialTiletest() {
		boolean bingo = false;
		if(board.gametiles[0][0]== tile || board.gametiles[0][0] != null)
			bingo = true;
		assertTrue(bingo);
	}

	@Test
	public void boardTileSizeTest() {
		int size = 151;
		int count = 0;
		for(int x = 0; x < board.width; x++) {
			for(int y = 0; y < board.height; y++) {
				if(board.gametiles[x][y] != null)
					count++;
			}
		}
		assertEquals(size, count);
	}
		
	
}
