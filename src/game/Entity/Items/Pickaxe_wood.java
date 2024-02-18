package game.Entity.Items;

import game.Entity.Entity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Pickaxe_wood extends Entity {
    public Pickaxe_wood(){
        width = 25;
        height = 25;
        try {
            this.sprite = ImageIO.read(new File("assets/items/pickaxe_wood.png")).getScaledInstance(width,height, Image.SCALE_DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.miningDamage = 5;
        this.hardness = 2;
    }
    @Override
    public String getName() {
        return "Pickaxe_wood";
    }
}
