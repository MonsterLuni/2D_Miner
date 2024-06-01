package game.Entity.Items;

import game.Entity.Entity;

public class ITM_TORCH extends Entity {
    public ITM_TORCH(){
        width = 25;
        height = 25;
        this.id = 18;
        this.miningDamage = 0;
        this.hardness = 0;
        this.isPlacable = true;
        this.lightEmission = true;
        this.minLightLevel = 13;
        this.lightLevel = this.minLightLevel;
        this.isPenetrable = true;
    }
    @Override
    public String getName() {
        return "torch";
    }
}
