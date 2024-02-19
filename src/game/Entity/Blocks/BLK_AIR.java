package game.Entity.Blocks;

import game.Entity.Entity;

public class BLK_AIR extends Entity {
    public BLK_AIR(){
        this.height = 25;
        this.width = 25;
        this.breakable = false;
        this.deactivateHitBox = true;
        this.hardness = 0;
        this.health = 0;
    }
    @Override
    public String getName() {
        return "air";
    }
}
