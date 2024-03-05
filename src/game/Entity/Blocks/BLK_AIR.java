package game.Entity.Blocks;

import game.Entity.Entity;
import game.UI;

public class BLK_AIR extends Entity {
    public BLK_AIR(){
        this.height = 25;
        this.width = 25;
        this.id = 3;
        this.breakable = false;
        this.deactivateHitBox = true;
        this.hardness = 0;
        this.health = 0;
        this.name = "air";
    }
    @Override
    public void interact(UI ui) {

    }
}
