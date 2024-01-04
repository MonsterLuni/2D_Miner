package game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Block {
    public int height, width;
    public int X,Y;
    public Point point;
    public Image sprite;
    public Color color;
    public boolean hitLeft = false;
    public boolean hitRight = false;
    public boolean hitTop = false;
    public boolean hitBottom = false;
    public boolean air = false;
    public Block(int h, int w, int i, int l,BufferedImage sprite){
        this.height = h;
        this.width = w;
        this.X = i*25;
        this.Y = l*25;
        point = new Point(this.X,this.Y);
        color = Color.blue;
        this.sprite = sprite.getScaledInstance(width,height, Image.SCALE_DEFAULT);
    }
    public Block(int h, int w, int i, int l,BufferedImage sprite, boolean air){
        this.height = h;
        this.air = air;
        this.width = w;
        this.X = i*25;
        this.Y = l*25;
        point = new Point(this.X,this.Y);
        color = Color.blue;
        this.sprite = sprite.getScaledInstance(width,height, Image.SCALE_DEFAULT);
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
        air = true;
        sprite = image;
        map.updateHitBoxes();
    }

    public boolean breakable() {
        //Todo: Add statement that states if a block is breakable or not
        return true;
    }
}
