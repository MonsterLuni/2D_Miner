package game.Entity.Blocks;

import game.Entity.Entity;
import game.UI;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class BLK_BARRIER extends Entity {
    public BLK_BARRIER(){
        this.height = 25;
        this.width = 25;
        this.id = 5;
        this.breakable = false;
        this.deactivateHitBox = false;
        this.hardness = 10;
        this.health = 100;
        this.name = "barrier";
    }
    @Override
    public void interact(UI ui) {

    }
}
