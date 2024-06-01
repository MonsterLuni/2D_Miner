package game.Entity;

import game.GameManager;
import game.Map;

import java.awt.*;
import java.io.Serializable;
import java.util.Objects;

public abstract class Entity implements Serializable{
    public Point point = new Point(-1,-1);
    public Color color = Color.BLUE;
    public String name;
    public boolean hitLeft,hitRight,hitTop,hitBottom;
    public boolean isInteractive,isSmeltable,isFalling,isLiquid,isFuel,isBreakable = true,isPenetrable,isPlacable = true;
    public int id;
    public int width;
    public int height;
    // min 0 max 15
    public int lightLevel = 0;
    public int minLightLevel = 0;
    public boolean lightEmission;
    public int lightDampness = 1;
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
            g.drawLine(point.x + p.offsetX, point.y - p.offsetY,point.x + p.offsetX + p.gm.map.tileSize,point.y - p.offsetY);
        }
        if(hitBottom){
            g.drawLine(point.x + p.offsetX, point.y - p.offsetY + p.gm.map.tileSize,point.x + p.offsetX + p.gm.map.tileSize,point.y - p.offsetY + p.gm.map.tileSize);
        }
        if(hitRight){
            g.drawLine(point.x + p.offsetX + p.gm.map.tileSize, point.y - p.offsetY,point.x + p.offsetX + p.gm.map.tileSize,point.y - p.offsetY + p.gm.map.tileSize);
        }
        if(hitLeft){
            g.drawLine(point.x + p.offsetX, point.y - p.offsetY,point.x + p.offsetX,point.y - p.offsetY + p.gm.map.tileSize);
        }
    }
    public void drawBlock(Graphics g, Player p, GameManager gm) {
        g.drawImage(gm.ah.getPictureForID(id),(point.x + p.offsetX),(point.y - p.offsetY),null);
        if(Objects.equals(getName(), "air")){
            if(gm.daytime != 15){
                g.setColor(new Color(0, 0, 0, 255 - (17 * lightLevel) - 17));
            }else{
                g.setColor(new Color(0, 0, 0, 255 - (17 * lightLevel)));
            }
            g.fillRect((point.x + p.offsetX),(point.y - p.offsetY),25,25);
        }else{
            g.setColor(new Color(0, 0, 0, 255 - (17 * lightLevel)));
            g.fillRect((point.x + p.offsetX),(point.y - p.offsetY),25,25);
        }
    }
    public void drawBlockVertices(Graphics g, Player p){
        g.setColor(Color.BLUE);
        g.drawRect(point.x + p.offsetX, point.y - p.offsetY, width, height);
    }
    public void drawBlockSpecial(Graphics g, Player p){
        g.drawRect(point.x + p.offsetX, point.y - p.offsetY, p.gm.map.tileSize, p.gm.map.tileSize);
    }
    public String getName() {
        return name;
    }
}
