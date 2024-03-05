package game.Entity;

import game.Map;
import game.UI;

import java.awt.*;
import java.io.Serializable;

public abstract class Entity implements Serializable{
    public Point point;
    public Color color = Color.BLUE;
    public boolean hitLeft = false;
    public boolean hitRight = false;
    public boolean hitTop = false;
    public boolean hitBottom = false;
    public int X;
    public int Y;
    public int inventoryX;
    public int inventoryY;
    public boolean interactive;
    public boolean smeltable;
    public boolean fuel;
    public int id;
    public int width;
    public int height;
    public String name;
    public int dropAmount = 1;
    public int miningDamage = 0;
    public int hardness = 0;
    public int stackSize = 64;
    public boolean breakable;
    public boolean deactivateHitBox;
    public int health;
    //public Image sprite;
    public abstract void interact(UI ui);
    public boolean harvestable(Player p) {
        return p.currentHardness >= hardness;
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
    public void breakBlock(Map map, String blockName){
        hitTop = false;
        hitBottom = false;
        hitRight = false;
        hitLeft = false;
        deactivateHitBox = true;
        id = 3;
        breakable = false;
        name = blockName;
        map.updateHitBoxes();
    }
    public void drawBlock(Graphics g, Player p, UI ui) {
        g.drawImage(ui.map.getPictureForID(id),(X + p.offsetX),(Y - p.offsetY),null);
    }
    public void drawBlockVertices(Graphics g, Player p){
        g.setColor(Color.BLUE);
        g.drawRect(X + p.offsetX, Y - p.offsetY, width, height);
    }
    public void drawBlockSpecial(Graphics g, Player p){
        g.drawRect(X + p.offsetX, Y - p.offsetY, 25, 25);
    }
    public String getName() {
        return name;
    }
}
