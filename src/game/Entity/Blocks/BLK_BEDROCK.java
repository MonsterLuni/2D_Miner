package game.Entity.Blocks;

import game.Entity.Entity;
import game.UI;


public class BLK_BEDROCK extends Entity {
    public BLK_BEDROCK(){
        this.height = 25;
        this.width = 25;
        this.id = 4;
        this.breakable = false;
        this.deactivateHitBox = false;
        this.hardness = 10;
        this.health = 100;
        this.name = "bedrock";
    }
}
