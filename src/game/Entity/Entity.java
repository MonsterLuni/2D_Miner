package game.Entity;

import game.GameManager;
import game.Map;

import java.awt.*;
import java.io.Serializable;

public abstract class Entity implements Serializable{
    public Point point;
    public Color color = Color.BLUE;
    public String name;
    public boolean hitLeft,hitRight,hitTop,hitBottom;
    public boolean isInteractive,isSmeltable,isFalling,isLiquid,isFuel,isBreakable = true,isPenetrable,isPlacable = true;
    public int X;
    public int Y;
    public int inventoryX;
    public int inventoryY;
    public int id;
    public int width;
    public int height;
    public int dropAmount = 1;
    public int miningDamage = 0;
    public int hardness = 0;
    public int stackSize = 64;
    public int health;
    public void interact(GameManager gm){}
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
        isPenetrable = true;
        id = 3;
        isBreakable = false;
        name = blockName;
        map.updateHitBoxes();
    }
    public void drawBlock(Graphics g, Player p, GameManager gm) {
        g.drawImage(gm.ah.getPictureForID(id),(X + p.offsetX),(Y - p.offsetY),null);
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
