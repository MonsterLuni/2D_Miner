package game.Entity.Blocks;

import game.Entity.Entity;

public class BLK_LEAVE extends Entity {
    public BLK_LEAVE(){
        this.height = 25;
        this.width = 25;
        this.id = 16;
        this.breakable = true;
        this.penetrable = false;
        this.hardness = 0;
        this.health = 1;
        this.name = "leave";
    }
}

