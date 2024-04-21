package game.Entity.Items;

import game.Entity.Entity;
import game.UI;
public class ITM_PICKAXE_WOOD extends Entity {
    public ITM_PICKAXE_WOOD(){
        width = 25;
        height = 25;
        this.id = 12;
        this.miningDamage = 5;
        this.hardness = 2;
        this.isPlacable = false;
    }
    @Override
    public String getName() {
        return "Pickaxe_wood";
    }
}
