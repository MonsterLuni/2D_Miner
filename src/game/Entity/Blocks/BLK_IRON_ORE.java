package game.Entity.Blocks;

import game.Entity.Entity;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class BLK_IRON_ORE extends Entity {
    public BLK_IRON_ORE(){
        this.height = 25;
        this.width = 25;
        this.breakable = true;
        this.deactivateHitBox = false;
        this.hardness = 3;
        this.health = 50;
        try {
            this.sprite = ImageIO.read(new File("assets/tiles/iron_ore.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public String getName() {
        return "iron_ore";
    }
}
