package game.Entity.Blocks;

import game.Entity.Entity;

public class BLK_BARRIER extends Entity {
    public BLK_BARRIER(){
        this.height = 25;
        this.width = 25;
        this.breakable = false;
        this.deactivateHitBox = false;
        this.hardness = 10;
        this.health = 100;
    }
    @Override
    public String getName() {
        return "barrier";
    }
}
