package group13.projectgame;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.api.AfterAll;

import group13.projectgame.SpriteSheet;;

@DisplayName("Unit test cases for SpriteSheet class")

public class SpritesheetTestUnit {
	
	private SpriteSheet spritesheet;
	
	@BeforeEach
	public void testSetUp() {
		spritesheet = new SpriteSheet("src/test/resources/entity/entity_pic.png");
	}
	
	@AfterAll
	public static void testEnd() {
		System.out.println("The tests have ended");
	}
	
	@Test
	public void fileReadtest() {
		assertNotNull(spritesheet);
	}
	
	@Test
	public void heroPicSizeTest1() {
		int width = 16;
		int height = 16;
		assertEquals(width, spritesheet.getPicture(0,0).getWidth());
		assertEquals(height, spritesheet.getPicture(0,0).getHeight());	
	}
	
	@Test
	public void enemyPicSizeTest2() {
		int width = 32;
		int height = 16;
		assertEquals(width, spritesheet.getPicture(16, 0).getWidth());
		assertEquals(height, spritesheet.getPicture(16,0).getHeight());	
	}
	
	@Test
	public void trapPicSizeTest1() {
		int width = 16;
		int height = 32;
		assertEquals(width, spritesheet.getPicture(0,16).getWidth());
		assertEquals(height, spritesheet.getPicture(0,16).getHeight());	
	}

}
