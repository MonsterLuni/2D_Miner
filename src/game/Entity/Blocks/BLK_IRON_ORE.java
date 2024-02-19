package game.Entity.Blocks;

import game.Entity.Entity;

public class BLK_IRON_ORE extends Entity {
    public BLK_IRON_ORE(){
        this.height = 25;
        this.width = 25;
        this.breakable = true;
        this.deactivateHitBox = false;
        this.hardness = 3;
        this.health = 50;
    }
    @Override
    public String getName() {
        return "iron_ore";
    }
}
