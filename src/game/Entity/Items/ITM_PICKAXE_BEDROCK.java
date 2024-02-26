package game.Entity.Items;

import game.Entity.Entity;
import game.UI;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ITM_PICKAXE_BEDROCK extends Entity {
    public ITM_PICKAXE_BEDROCK(){
        width = 25;
        height = 25;
        try {
            this.sprite = ImageIO.read(new File("assets/items/pickaxe_bedrock.png")).getScaledInstance(width,height, Image.SCALE_DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
