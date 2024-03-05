package game.Entity.Items;

import game.Entity.Entity;
import game.UI;

public class ITM_PICKAXE_BEDROCK extends Entity {
    public ITM_PICKAXE_BEDROCK(){
        width = 25;
        height = 25;
        this.id = 10;
        this.miningDamage = 100;
        this.hardness = 5;
    }
    @Override
    public String getName() {
        return "Pickaxe_bedrock";
    }

    @Override
    public void interact(UI ui) {

    }
}
