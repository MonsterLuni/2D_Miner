package game.Entity.Blocks;
import game.Entity.Entity;
import game.UI;

public class BLK_GRASS extends Entity {
    public BLK_GRASS(){
        this.id = 0;
        this.breakable = true;
        this.deactivateHitBox = false;
        this.hardness = 1;
        this.health = 5;
        this.name = "grass";
    }
    @Override
    public void interact(UI ui) {

    }
}
