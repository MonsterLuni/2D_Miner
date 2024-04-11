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
        int x = ((coordinates.x - ui.p.offsetX) / 25) * 25;
        int y = ((coordinates.y + ui.p.offsetY) / 25) * 25;
        return new Point(x,y);
    }
    @Override
    public void mouseMoved(MouseEvent e) {
        Point point = getMouseBlockHover(e.getPoint());
        for(int i=0; i< ui.blocks.size(); i++){
            if(ui.p.getOnlyVisibleBlocks(i)){
                if(point.x == ui.blocks.get(i).point.x && point.y == ui.blocks.get(i).point.y){
                    ui.blocks.get(i).color = Color.red;
                }
            }
        }
    }
}