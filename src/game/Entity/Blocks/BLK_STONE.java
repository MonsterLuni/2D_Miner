package game.Entity.Blocks;

import game.Entity.Entity;

public class BLK_STONE extends Entity {
    public BLK_STONE(){
        this.height = 25;
        this.smeltable = true;
        this.width = 25;
        this.id = 2;
        this.breakable = true;
        this.penetrable = false;
        this.hardness = 2;
        this.health = 30;
        this.name = "stone";
    }
}
