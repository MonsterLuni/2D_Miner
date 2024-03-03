package game.Entity.Blocks;

import game.Entity.Entity;
import game.UI;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class BLK_COAL_ORE extends Entity {
    public BLK_COAL_ORE(){
        this.fuel = true;
        this.height = 25;
        this.width = 25;
        this.id = 8;
        this.breakable = true;
        this.deactivateHitBox = false;
        this.hardness = 3;
        this.health = 50;
        this.name = "coal_ore";
        try {
            this.sprite = ImageIO.read(new File("assets/tiles/coal_ore.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void interact(UI ui) {

    }
}
