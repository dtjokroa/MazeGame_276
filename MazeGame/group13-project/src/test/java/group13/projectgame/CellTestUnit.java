package group13.projectgame;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

@DisplayName("Unit test cases for Cell class")

public class CellTestUnit {
    private Cell cell = new Cell();

    @AfterAll
    static void testEnd() {
        System.out.println("The tests have ended");
    }

    @Test
    public void setCellTypeTest(){
        cell.setCellType("enemy");
        Assertions.assertEquals("enemy",cell.getCellType());
    }
}
