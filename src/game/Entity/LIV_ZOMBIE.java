package game.Entity;

import game.Entity.Living.Living;
import game.GameManager;
import game.Wait;

import java.awt.*;

public class LIV_ZOMBIE extends Living {
    public int defaultWidth;
    public int hardness = 1;
    double rand = 0;
    Player p;
    Wait walkCounter = new Wait();
    public LIV_ZOMBIE(GameManager gm){
        this.gm = gm;
        defaultWidth = gm.map.tileSize;
        this.color = Color.gray;
        this.walkSpeed = 1;
        width = defaultWidth;
        height = defaultHeight;
        point.x = gm.map.worldWidth/2;
        point.y = 950;
    }
    @Override
    public void walk() {
        if(walkCounter.waitforSeconds(4)){
            rand = Math.random();
        }
        if(walkCounter.waitForFrames(1)){
            Entity index = gm.getBlock((((point.x - offsetX) / gm.map.tileSize) * p.gm.map.tileSize),(((point.y + offsetY) / p.gm.map.tileSize) * p.gm.map.tileSize) + (height - p.gm.map.tileSize));
            if(rand > 0.5){
                Entity index2 = gm.getBlock((((point.x - offsetX) / p.gm.map.tileSize) * p.gm.map.tileSize) - p.gm.map.tileSize,(((point.y + offsetY) / p.gm.map.tileSize) * p.gm.map.tileSize) + (height - p.gm.map.tileSize));
                if(index2 != null && index2.hitRight){
                    if(checkOverlapX(index,1,false)){
                        offsetX += walkSpeed;
                    }
                }
                else {
                    offsetX += walkSpeed;
                }
            } else {
                Entity index2 = gm.getBlock((((point.x - offsetX) / p.gm.map.tileSize) * p.gm.map.tileSize) + p.gm.map.tileSize,(((point.y + offsetY) / p.gm.map.tileSize) * p.gm.map.tileSize) + (height - p.gm.map.tileSize));
                if(index2 != null && index2.hitLeft){
                    if(checkOverlapX(index,0,true)){
                        offsetX -= walkSpeed;
                    }
                }
                else {
                    offsetX -= walkSpeed;
                }
            }
        }
    }
    @Override
    public void draw(Graphics g, Player p) {
        this.p = p;
        g.setColor(color);
        g.drawImage(gm.ah.getPictureForID(-3),(point.x + p.offsetX) - offsetX,(point.y - p.offsetY) + offsetY,null);
    }
    @Override
    public String getName() {
        return "Zombie";
    }
}
