package game.Entity;

import game.Map;
import game.UI;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Block extends Entity{
    public Point point;
    public Color color;
    public boolean hitLeft = false;
    public boolean hitRight = false;
    public boolean hitTop = false;
    public boolean hitBottom = false;
    public int hardness;
    public String name;
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
        color = Color.blue;
        sprite = spriteUnscaled.getScaledInstance(width,height, Image.SCALE_DEFAULT);
    }
    public void drawBlockVertices(Graphics g, Player p){
        g.setColor(color);
        g.drawRect(X + p.offsetX, Y - p.offsetY, width, height);
    }
    public void drawHitBox(Graphics g, Player p){
        if(hitTop){
            g.drawLine(X + p.offsetX, Y - p.offsetY,X + p.offsetX + 25,Y - p.offsetY);
        }
        if(hitBottom){
            g.drawLine(X + p.offsetX, Y - p.offsetY + 25,X + p.offsetX + 25,Y - p.offsetY + 25);
        }
        if(hitRight){
            g.drawLine(X + p.offsetX + 25, Y - p.offsetY,X + p.offsetX + 25,Y - p.offsetY + 25);
        }
        if(hitLeft){
            g.drawLine(X + p.offsetX, Y - p.offsetY,X + p.offsetX,Y - p.offsetY + 25);
        }
    }
    public void drawBlock(Graphics g, Player p){
        g.drawImage(sprite,X + p.offsetX,Y - p.offsetY,null);
    }
    public void drawBlockSpecial(Graphics g, Player p){
        g.drawRect(X + p.offsetX, Y - p.offsetY, 25, 25);
    }
    public void breakBlock(Map map, Image image, String blockName){
        hitTop = false;
        hitBottom = false;
        hitRight = false;
        hitLeft = false;
        deactivateHitBox = true;
        sprite = image;
        breakable = false;
        name = blockName;
        map.updateHitBoxes();
    }
    public boolean harvestable(Player p) {
        return p.currentHardness >= hardness;
    }
    @Override
    public String getName() {
        return name;
    }
    @Override
    public void interact(UI ui) {
        ui.map.getNewBlockFromID(id).interact(ui);
    }
}
