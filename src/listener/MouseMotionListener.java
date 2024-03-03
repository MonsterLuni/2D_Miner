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
    public void mouseDragged(MouseEvent e) {}
    public Point getMouseBlockHover(Point coordinates){
        int x = coordinates.x - ui.p.offsetX;
        x = Math.round((float) x / 25) * 25;
        int y = coordinates.y + ui.p.offsetY;
        y = Math.round((float) y / 25) * 25;
        return new Point(x,y);
    }
    @Override
    public void mouseMoved(MouseEvent e) {
        Point point = getMouseBlockHover(e.getLocationOnScreen());
        for(int i=0; i< ui.blocks.size(); i++){
            if(ui.p.getOnlyVisibleBlocks(i)){
                if(point.x - 150 == ui.blocks.get(i).point.x && point.y - 50 == ui.blocks.get(i).point.y){
                    ui.blocks.get(i).color = Color.red;
                }
            }
        }
    }
}