package game.Entity.Blocks;

import game.Entity.Entity;

public class BLK_SAND extends Entity {
    public BLK_SAND(){
        this.height = 25;
        this.width = 25;
        this.id = 13;
        this.isPenetrable = false;
        this.hardness = 1;
        this.health = 5;
        this.isFalling = true;
        this.name = "sand";
    }
}
