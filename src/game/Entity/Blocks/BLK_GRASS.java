package game.Entity.Blocks;
import game.Entity.Entity;

public class BLK_GRASS extends Entity {
    public BLK_GRASS(){
        this.height = 25;
        this.width = 25;
        this.breakable = true;
        this.deactivateHitBox = false;
        this.hardness = 1;
        this.health = 5;
    }
    @Override
    public String getName() {
        return "grass";
    }
}
