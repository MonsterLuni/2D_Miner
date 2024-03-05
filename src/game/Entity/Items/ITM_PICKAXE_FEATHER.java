package game.Entity.Items;

import game.Entity.Entity;
import game.UI;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ITM_PICKAXE_FEATHER extends Entity {
    public ITM_PICKAXE_FEATHER(){
        width = 25;
        height = 25;
        this.id = 11;
        this.miningDamage = 0;
        this.hardness = 0;
    }
    @Override
    public String getName() {
        return "Pickaxe_feather";
    }
    @Override
    public void interact(UI ui) {

    }
}
