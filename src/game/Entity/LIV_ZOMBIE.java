package game.Entity;

import game.Entity.Living.Living;
import game.UI;
import java.awt.*;

public class LIV_ZOMBIE extends Living {
    public int defaultWidth;
    public int hardness = 1;
    double rand = 0;
    Player p;
    public LIV_ZOMBIE(UI ui){
        this.ui = ui;
        defaultWidth = ui.map.tileSize;
        this.color = Color.gray;
        this.walkSpeed = 1;
        width = defaultWidth;
        height = defaultHeight;
        X = 350;
        Y = 950;
    }
    @Override
    public void walk() {
        if(waitforSeconds(4)){
            rand = Math.random();
        }
        if(waitForInterval(2)){
            Entity index = ui.map.getBlockFromCoordinates((((X - offsetX) / ui.map.tileSize) * p.ui.map.tileSize),(((Y + offsetY) / p.ui.map.tileSize) * p.ui.map.tileSize) + (height - p.ui.map.tileSize));
            if(rand > 0.5){
                Entity index2 = ui.map.getBlockFromCoordinates((((X - offsetX) / p.ui.map.tileSize) * p.ui.map.tileSize) - p.ui.map.tileSize,(((Y + offsetY) / p.ui.map.tileSize) * p.ui.map.tileSize) + (height - p.ui.map.tileSize));
                if(index2 != null && index2.hitRight){
                    if(checkOverlapX(index,1,false)){
                        offsetX += walkSpeed;
                    }
                }
                else {
                    offsetX += walkSpeed;
                }
            } else {
                Entity index2 = ui.map.getBlockFromCoordinates((((X - offsetX) / p.ui.map.tileSize) * p.ui.map.tileSize) + p.ui.map.tileSize,(((Y + offsetY) / p.ui.map.tileSize) * p.ui.map.tileSize) + (height - p.ui.map.tileSize));
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
    @Override
    public void interact(UI ui) {

    }
}
