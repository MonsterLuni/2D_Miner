package listener;

import game.GameManager;
import game.UI;

import java.awt.event.MouseEvent;

public class MouseListener implements java.awt.event.MouseListener {
    public GameManager gm;
    public boolean leftButtonPressed;
    public MouseListener(GameManager gm){
        this.gm = gm;
    }
    @Override
    public void mouseClicked(MouseEvent e) {}
    @Override
    public void mousePressed(MouseEvent e) {
        switch (e.getButton()){
            case MouseEvent.BUTTON1 -> {
                leftButtonPressed = true;
                if(gm.currentState == GameManager.gameState){
                    gm.map.findBlock(e.getPoint());
                }
            }
            case MouseEvent.BUTTON3 -> {
                if(gm.currentState == GameManager.gameState) {
                    gm.map.interactWorld(e.getPoint(), gm.p.hotbar.getKeyFromCoordinates(gm.p.hotbar.inventorySpaceX, 0));
                }
            }
        }
    }
    @Override
    public void mouseReleased(MouseEvent e) {
        switch (e.getButton()) {
            case MouseEvent.BUTTON1 -> {
                leftButtonPressed = false;
            }
        }
    }
    @Override
    public void mouseEntered(MouseEvent e) {

    }
    @Override
    public void mouseExited(MouseEvent e) {

    }
}
