package game.Entity.Blocks;

import game.Entity.Entity;
import game.GameManager;

public class BLK_AIR extends Entity {
    public BLK_AIR(){
        this.height = 25;
        this.width = 25;
        this.id = 3;
        this.isBreakable = false;
        this.isPenetrable = true;
        this.hardness = 0;
        this.health = 0;
        this.name = "air";
        this.isSky = true;
    }
    @Override
    public void interact(GameManager gm) {

    }
}
