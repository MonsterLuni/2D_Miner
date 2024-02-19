package game.Entity.Items;

import game.Entity.Entity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ITM_PICKAXE_FEATHER extends Entity {
    public ITM_PICKAXE_FEATHER(){
        width = 25;
        height = 25;
        try {
            this.sprite = ImageIO.read(new File("assets/items/pickaxe_feather.png")).getScaledInstance(width,height, Image.SCALE_DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.miningDamage = 0;
        this.hardness = 0;
    }
    @Override
    public String getName() {
        return "Pickaxe_wood";
    }
}
