package game.Entity.Blocks;

import game.Entity.Entity;
import game.UI;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class BLK_IRON_ORE extends Entity {
    public BLK_IRON_ORE(){
        this.smeltable = true;
        this.height = 25;
        this.width = 25;
        this.id = 6;
        this.breakable = true;
        this.deactivateHitBox = false;
        this.hardness = 3;
        this.health = 50;
        this.name = "iron_ore";
    }
    @Override
    public void interact(UI ui) {

    }

}
