package game.Entity;

import game.Entity.Living.Living;
import game.GameManager;
import game.UI;
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
        X = 350;
        Y = 950;
    }
    @Override
    public void walk() {
        if(walkCounter.waitforSeconds(4)){
            rand = Math.random();
        }
        if(walkCounter.waitforSeconds(2)){
            Entity index = gm.getBlock((((X - offsetX) / gm.map.tileSize) * p.gm.map.tileSize),(((Y + offsetY) / p.gm.map.tileSize) * p.gm.map.tileSize) + (height - p.gm.map.tileSize));
            if(rand > 0.5){
                Entity index2 = gm.getBlock((((X - offsetX) / p.gm.map.tileSize) * p.gm.map.tileSize) - p.gm.map.tileSize,(((Y + offsetY) / p.gm.map.tileSize) * p.gm.map.tileSize) + (height - p.gm.map.tileSize));
                if(index2 != null && index2.hitRight){
                    if(checkOverlapX(index,1,false)){
                        offsetX += walkSpeed;
                    }
                }
                else {
                    offsetX += walkSpeed;
                }
            } else {
                Entity index2 = gm.getBlock((((X - offsetX) / p.gm.map.tileSize) * p.gm.map.tileSize) + p.gm.map.tileSize,(((Y + offsetY) / p.gm.map.tileSize) * p.gm.map.tileSize) + (height - p.gm.map.tileSize));
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
        g.fillRect((X + p.offsetX) - offsetX,(Y - p.offsetY) + offsetY,width,height);
    }
    @Override
    public String getName() {
        return "Zombie";
    }
}
