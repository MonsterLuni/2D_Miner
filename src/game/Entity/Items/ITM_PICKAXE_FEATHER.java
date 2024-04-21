package game.Entity.Items;

import game.Entity.Entity;
import game.UI;

public class ITM_PICKAXE_FEATHER extends Entity {
    public ITM_PICKAXE_FEATHER(){
        width = 25;
        height = 25;
        this.id = 11;
        this.miningDamage = 0;
        this.hardness = 0;
        this.isPlacable = false;
    }
    @Override
    public String getName() {
        return "Pickaxe_feather";
    }
}
