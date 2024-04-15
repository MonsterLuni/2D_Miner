package game.Entity.Items;

import game.Entity.Entity;
import game.UI;

public class ITM_IRON_BAR extends Entity {
    public ITM_IRON_BAR(){
        width = 25;
        height = 25;
        this.id = 9;
        this.miningDamage = 5;
        this.hardness = 3;
    }
    @Override
    public String getName() {
        return "iron_bar";
    }
}
