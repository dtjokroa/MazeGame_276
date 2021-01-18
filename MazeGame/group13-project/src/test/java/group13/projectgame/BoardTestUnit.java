package group13.projectgame;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.AfterAll;

import org.junit.jupiter.api.DisplayName;


@DisplayName("Unit test cases for Board class")

public class BoardTestUnit {
	private Board board;

	@BeforeEach
	public void testSetUp() {
		board = new Board("src/test/resources/map/ini_map.png");
	}
	
	@AfterAll
	public static void testEnd() {
		System.out.println("The tests have ended");
	}
	
	@Test
	public void fileReadTest() {
		assertNotNull(board);
	}
	
	@Test
	public void mapHeightTest() {
		int height = 480/32;
		assertEquals(height, board.height);
	}
	
	@Test
	public void mapWidthTest() {
		int width = 640/32;
		assertEquals(width, board.width);
	}

}
