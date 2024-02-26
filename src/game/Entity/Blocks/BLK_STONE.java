package game.Entity.Blocks;

import game.Entity.Entity;
import game.UI;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class BLK_STONE extends Entity {
    public BLK_STONE(){
        this.height = 25;
        this.width = 25;
        this.id = 2;
        this.breakable = true;
        this.deactivateHitBox = false;
        this.hardness = 2;
        this.health = 30;
        try {
            this.sprite = ImageIO.read(new File("assets/tiles/stone.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public String getName() {
        return "stone";
    }

    @Override
    public void interact(UI ui) {

    }
}
