package game.Entity.Blocks;

import game.Entity.Entity;
import game.UI;

public class BLK_COAL_ORE extends Entity {
    public BLK_COAL_ORE(){
        this.fuel = true;
        this.height = 25;
        this.width = 25;
        this.id = 8;
        this.breakable = true;
        this.deactivateHitBox = false;
        this.hardness = 3;
        this.health = 50;
        this.name = "coal_ore";
    }
}
