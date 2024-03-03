package game.Entity;

import game.Entity.Living.Living;
import game.UI;
import java.awt.*;

public class LIV_ZOMBIE extends Living {
    public int defaultWidth = 25;
    public int hardness = 1;
    double rand = 0;
    Player p;
    public LIV_ZOMBIE(UI ui){
        this.ui = ui;
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
            int index = ui.map.getBlockFromCoordinates((((X - offsetX) / 25) * 25),(((Y + offsetY) / 25) * 25) + (height - 25));
            if(rand > 0.5){
                int index2 = ui.map.getBlockFromCoordinates((((X - offsetX) / 25) * 25) - 25,(((Y + offsetY) / 25) * 25) + (height - 25));
                if(ui.blocks.get(index2).hitRight){
                    if(checkOverlapX(index,1,false)){
                        offsetX += walkSpeed;
                    }
                }
                else {
                    offsetX += walkSpeed;
                }
            } else {
                int index2 = ui.map.getBlockFromCoordinates((((X - offsetX) / 25) * 25) + 25,(((Y + offsetY) / 25) * 25) + (height - 25));
                if(ui.blocks.get(index2).hitLeft){
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
