package listener;

import game.Entity.Entity;
import game.GameManager;
import game.UI;

import java.awt.*;
import java.awt.event.MouseEvent;

public class MouseMotionListener implements java.awt.event.MouseMotionListener {
    GameManager gm;
    public MouseMotionListener(GameManager gm){
        this.gm = gm;
    }
    @Override
    public void mouseDragged(MouseEvent e) {}
    public Point getMouseBlockHover(Point coordinates){
        int x = ((coordinates.x - gm.p.offsetX) / gm.map.tileSize) * gm.map.tileSize;
        int y = ((coordinates.y + gm.p.offsetY) / gm.map.tileSize) * gm.map.tileSize;
        return new Point(x,y);
    }
    @Override
    public void mouseMoved(MouseEvent e) {
        Point point = getMouseBlockHover(e.getPoint());
        for (Entity block: gm.p.getOnlyVisibleBlocks()) {
            if(block != null){
                if(point.x == block.point.x && point.y == block.point.y){
                    block.color = Color.red;
                }
            }
        }
    }
}