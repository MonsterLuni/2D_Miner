package game.Entity.Blocks;

import game.Entity.Entity;
import game.UI;

public class BLK_SAND extends Entity {
    public BLK_SAND(){
        this.height = 25;
        this.width = 25;
        this.id = 13;
        this.breakable = true;
        this.deactivateHitBox = false;
        this.hardness = 1;
        this.health = 5;
        this.gravity = true;
        this.name = "sand";
    }
}
