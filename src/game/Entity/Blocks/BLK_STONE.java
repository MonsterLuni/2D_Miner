package game.Entity.Blocks;

import game.Entity.Entity;

public class BLK_STONE extends Entity {
    public BLK_STONE(){
        this.height = 25;
        this.width = 25;
        this.breakable = true;
        this.deactivateHitBox = false;
        this.hardness = 2;
        this.health = 30;
    }
    @Override
    public String getName() {
        return "stone";
    }
}
