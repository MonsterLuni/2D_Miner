package game.Entity.Blocks;

import game.Entity.Entity;

public class BLK_WATER extends Entity {
    public BLK_WATER(){
        this.height = 25;
        this.width = 25;
        this.id = 14;
        this.isBreakable = false;
        this.isPenetrable = true;
        this.hardness = 0;
        this.isLiquid = true;
        this.health = 0;
        this.name = "water";
    }
}
