package game.Entity.Blocks;

import game.Entity.Entity;

public class BLK_IRON_ORE extends Entity {
    public BLK_IRON_ORE(){
        this.isSmeltable = true;
        this.height = 25;
        this.width = 25;
        this.id = 6;
        this.isBreakable = true;
        this.isPenetrable = false;
        this.hardness = 3;
        this.health = 50;
        this.name = "iron_ore";
    }
}
