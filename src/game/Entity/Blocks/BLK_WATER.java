package game.Entity.Blocks;

import game.Entity.Entity;
import game.UI;

public class BLK_WATER extends Entity {
    public BLK_WATER(){
        this.height = 25;
        this.width = 25;
        this.id = 14;
        this.breakable = false;
        this.deactivateHitBox = true;
        this.hardness = 0;
        this.health = 0;
        this.name = "water";
    }
    @Override
    public void interact(UI ui) {

    }
}
