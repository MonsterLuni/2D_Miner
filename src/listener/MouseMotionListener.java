package listener;

import game.UI;

import java.awt.*;
import java.awt.event.MouseEvent;

public class MouseMotionListener implements java.awt.event.MouseMotionListener {
    UI ui;
    public MouseMotionListener(UI ui){
        this.ui = ui;
    }
    @Override
    public void mouseDragged(MouseEvent e) {
        if(ui.ml.leftButtonPressed){
            ui.map.findBlock(e.getLocationOnScreen());
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        int x = Math.round((float) e.getLocationOnScreen().x / 25) * 25 - (ui.p.offsetX/25)*25 - 650;
        int y = Math.round((float) e.getLocationOnScreen().y / 25) * 25 + (ui.p.offsetY/25)*25 - 350;
        Point point = new Point(x,y);
        for(int i=0; i< ui.blocks.size(); i++){
            if(point.x == ui.blocks.get(i).point.x && point.y == ui.blocks.get(i).point.y){
                ui.blocks.get(i).color = Color.red;
            }
            /*else {
                if(ui.blocks.get(i).color != Color.blue){
                    ui.blocks.get(i).color = Color.blue;
                }
            }*/
        }
    }
}
