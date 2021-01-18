package group13.projectgame;
import java.awt.*;

public class Cell extends Rectangle {

    private String cellType;

    public String getCellType(){
        return this.cellType;
    }

    public void setCellType(String enemy) {
        this.cellType = enemy;
    }
}


