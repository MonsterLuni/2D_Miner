package game.Entity;

import game.Map;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Block extends Entity{
    public Point point;
    public Color color;
    public boolean hitLeft = false;
    public boolean hitRight = false;
    public boolean hitTop = false;
    public boolean hitBottom = false;
    public boolean breakable;
    public boolean deactivateHitBox;
    public String name;
    public BufferedImage image;
    public Block(int h, int w, int i, int l,BufferedImage spriteUnscaled, boolean deactivateHitBox, boolean breakable, String name){
        this.height = h;
        this.deactivateHitBox = deactivateHitBox;
        this.width = w;
        this.name = name;
        this.breakable = breakable;
        image = spriteUnscaled;
        X = i*25;
        Y = l*25;
        point = new Point(this.X,this.Y);
        color = Color.blue;
        sprite = spriteUnscaled.getScaledInstance(width,height, Image.SCALE_DEFAULT);
    }
    public Block(int h, int w,BufferedImage spriteUnscaled, String name){
        this.height = h;
        this.width = w;
        this.name = name;
        image = spriteUnscaled;
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
    public void breakBlock(Map map, Image image){
        hitTop = false;
        hitBottom = false;
        hitRight = false;
        hitLeft = false;
        deactivateHitBox = true;
        sprite = image;
        map.updateHitBoxes();
    }
    public boolean breakable() {
        return breakable;
        //Todo: Add statement that states if a block is breakable or not
    }
    @Override
    public String getName() {
        return name;
    }
}
