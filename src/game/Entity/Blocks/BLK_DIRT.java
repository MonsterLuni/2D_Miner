package game.Entity.Blocks;
import game.Entity.Entity;

public class BLK_DIRT extends Entity {
    public BLK_DIRT(){
        this.height = 25;
        this.width = 25;
        this.breakable = true;
        this.deactivateHitBox = false;
        this.hardness = 1;
        this.health = 10;
    }
    @Override
    public String getName() {
        return "dirt";
    }
}

