package group13.projectgame;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.api.AfterAll;



@DisplayName("Unit test cases for Tile class")

public class TileTestUnit {

	private Tile tile;
	
	@BeforeEach
	public void testSetUp() {
		tile = new Tile(0, 0);
	}
	
	@AfterAll
	public static void testEnd() {
		System.out.println("The tests have ended");
	}
	
	@Test
	public void constructorTest() {
		assertNotNull(tile);
	}
	
	@Test
	public void tileSizeTest() {
		int width = 32;
		int height = 32;
		assertEquals(width, tile.width);
		assertEquals(height, tile.height);
	}

}
