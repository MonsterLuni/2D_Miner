package game.Entity.Blocks;

import game.Entity.Entity;
import game.UI;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class BLK_AIR extends Entity {
    public BLK_AIR(){
        this.height = 25;
        this.width = 25;
        this.id = 3;
        this.breakable = false;
        this.deactivateHitBox = true;
        this.hardness = 0;
        this.health = 0;
        try {
            this.sprite = ImageIO.read(new File("assets/tiles/air.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public String getName() {
        return "air";
    }
    @Override
    public void interact(UI ui) {

    }
}
