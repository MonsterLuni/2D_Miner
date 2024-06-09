package listener;

import game.Entity.Entity;
import game.GameManager;
import game.UI;

import java.awt.*;
import java.awt.event.MouseEvent;

public class MouseMotionListener implements java.awt.event.MouseMotionListener {
    GameManager gm;
    public Point mousepoint = new Point(-1,-1);
    public MouseMotionListener(GameManager gm){
        this.gm = gm;
    }
    @Override
    public void mouseDragged(MouseEvent e) {}
    public Point getMouseBlockHover(Point coordinates){
        coordinates.x = (int) (coordinates.x / gm.ui.stretchingFactor);
        coordinates.y = (int) (coordinates.y / gm.ui.stretchingFactor);
        int x = (((coordinates.x - gm.p.offsetX) / gm.map.tileSize) * gm.map.tileSize) ;
        int y = (((coordinates.y + gm.p.offsetY) / gm.map.tileSize) * gm.map.tileSize) ;
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
                mousepoint = point;
            }
        }
    }
}