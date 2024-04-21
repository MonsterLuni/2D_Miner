package game.Entity.Blocks;

import game.Entity.Entity;

public class BLK_OAK_WOOD extends Entity {
    public BLK_OAK_WOOD(){
        this.height = 25;
        this.width = 25;
        this.id = 15;
        this.breakable = true;
        this.penetrable = false;
        this.hardness = 1;
        this.health = 15;
        this.name = "oak_wood";
    }
}

