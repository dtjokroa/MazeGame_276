package group13.projectgame;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Unit test cases Enemy Class")
public class EnemyTestUnit {
    public Enemy enemy;
    public MovingEnemy movingEnemy;

    @Test
    public void ConstructorTest(){
        enemy = new Enemy(32,32);
        assertEquals(32, enemy.x);
        assertEquals(32, enemy.y);
        assertTrue(enemy.valPunish < 3);
        assertFalse(enemy.visited);
    }

    @Test
    public void MovingConstructorTest(){
        movingEnemy = new MovingEnemy(32,32);
        assertEquals(32, movingEnemy.x);
        assertEquals(32, movingEnemy.y);
        assertFalse(movingEnemy.visited);
        assertTrue(movingEnemy.getDir() < 4);
    }

    @Test
    public void CellTypeTest(){
        enemy = new Enemy(32,32);
        movingEnemy = new MovingEnemy(32,32);
        enemy.setCellType("enemy");
        movingEnemy.setCellType("movingEnemy");
        assertEquals("enemy", enemy.getCellType());
        assertEquals("movingEnemy", movingEnemy.getCellType());
    }

    @Test
    public void VisitedTest(){
        enemy = new Enemy(32,32);
        movingEnemy = new MovingEnemy(32,32);
        enemy.visited();
        movingEnemy.visited();
        assertTrue(enemy.visited);
        assertTrue(movingEnemy.visited);
    }



}
