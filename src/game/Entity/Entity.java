package game.Entity;

import game.GameManager;
import game.Map;

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
    public boolean isFalling;
    public boolean isLiquid;
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
    public boolean penetrable;
    public int health;
    public void interact(GameManager gm){};
    public boolean harvestable(Player p) {
        return p.currentHardness >= hardness;
    }
    public void drawHitBox(Graphics g, Player p){
        if(hitTop){
            g.drawLine(X + p.offsetX, Y - p.offsetY,X + p.offsetX + p.gm.map.tileSize,Y - p.offsetY);
        }
        if(hitBottom){
            g.drawLine(X + p.offsetX, Y - p.offsetY + p.gm.map.tileSize,X + p.offsetX + p.gm.map.tileSize,Y - p.offsetY + p.gm.map.tileSize);
        }
        if(hitRight){
            g.drawLine(X + p.offsetX + p.gm.map.tileSize, Y - p.offsetY,X + p.offsetX + p.gm.map.tileSize,Y - p.offsetY + p.gm.map.tileSize);
        }
        if(hitLeft){
            g.drawLine(X + p.offsetX, Y - p.offsetY,X + p.offsetX,Y - p.offsetY + p.gm.map.tileSize);
        }
    }
    public void breakBlock(Map map, String blockName){
        hitTop = false;
        hitBottom = false;
        hitRight = false;
        hitLeft = false;
        penetrable = true;
        id = 3;
        breakable = false;
        name = blockName;
        map.updateHitBoxes();
    }
    public void drawBlock(Graphics g, Player p, GameManager gm) {
        g.drawImage(gm.map.getPictureForID(id),(X + p.offsetX),(Y - p.offsetY),null);
    }
    public void drawBlockVertices(Graphics g, Player p){
        g.setColor(Color.BLUE);
        g.drawRect(X + p.offsetX, Y - p.offsetY, width, height);
    }
    public void drawBlockSpecial(Graphics g, Player p){
        g.drawRect(X + p.offsetX, Y - p.offsetY, p.gm.map.tileSize, p.gm.map.tileSize);
    }
    public String getName() {
        return name;
    }
}
