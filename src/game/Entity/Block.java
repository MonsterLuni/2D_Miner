/*package game.Entity;

import game.UI;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Block extends Entity{
    public int hardness;
    public BufferedImage image;
    public Block(int h, int w, int i, int l,BufferedImage spriteUnscaled, boolean deactivateHitBox, boolean breakable, String name, int hardness,int health, boolean interactive, int id, boolean smeltable, boolean fuel){
        this.height = h;
        this.smeltable = smeltable;
        this.fuel = fuel;
        this.id = id;
        this.deactivateHitBox = deactivateHitBox;
        this.width = w;
        this.interactive = interactive;
        this.health = health;
        this.name = name;
        this.breakable = breakable;
        this.hardness = hardness;
        image = spriteUnscaled;
        X = i*25;
        Y = l*25;
        point = new Point(this.X,this.Y);
        sprite = spriteUnscaled.getScaledInstance(width,height, Image.SCALE_DEFAULT);
    }
    @Override
    public String getName() {
        return name;
    }
}
*/