package game.Entity.Blocks;

import game.Entity.Entity;
import game.UI;

public class BLK_DIRT extends Entity {
    public BLK_DIRT(){
        this.height = 25;
        this.width = 25;
        this.id = 1;
        this.breakable = true;
        this.deactivateHitBox = false;
        this.hardness = 1;
        this.health = 10;
        this.name = "dirt";
    }
    @Override
    public void interact(UI ui) {

    }
}

