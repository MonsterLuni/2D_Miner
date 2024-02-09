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
    public Point getMouseBlockHover(Point coordinates){
        int x = Math.round(((float) coordinates.x / 25) * 25 - ((float) ui.p.offsetX /25)*25) - ui.screenWidthDivideTwo;
        x = Math.round((float) x / 25) * 25;
        int y = Math.round(((float) coordinates.y / 25) * 25 + ((float) ui.p.offsetY /25)*25) - ui.screenHeightDivideTwo;
        y = Math.round((float) y / 25) * 25;
        return new Point(x,y);
    }
    @Override
    public void mouseMoved(MouseEvent e) {
        Point point = getMouseBlockHover(e.getLocationOnScreen());
        for(int i=0; i< ui.blocks.size(); i++){
            if(ui.map.getOnlyVisibleBlocks(i)){
                if(point.x == ui.blocks.get(i).point.x && point.y == ui.blocks.get(i).point.y){
                    ui.blocks.get(i).color = Color.red;
                }
            }
        }
    }
}