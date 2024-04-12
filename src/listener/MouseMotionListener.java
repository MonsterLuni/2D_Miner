package listener;

import game.Entity.Entity;
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
        int x = ((coordinates.x - ui.p.offsetX) / ui.map.tileSize) * ui.map.tileSize;
        int y = ((coordinates.y + ui.p.offsetY) / ui.map.tileSize) * ui.map.tileSize;
        return new Point(x,y);
    }
    @Override
    public void mouseMoved(MouseEvent e) {
        Point point = getMouseBlockHover(e.getPoint());
        for (Entity block: ui.p.getOnlyVisibleBlocks()) {
            if(point.x == block.point.x && point.y == block.point.y){
                block.color = Color.red;
            }
        }
    }
}