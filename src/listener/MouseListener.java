package listener;

import game.UI;

import java.awt.event.MouseEvent;

public class MouseListener implements java.awt.event.MouseListener {
    public UI ui;
    public boolean leftButtonPressed;
    public MouseListener(UI ui){
        this.ui = ui;
    }
    @Override
    public void mouseClicked(MouseEvent e) {}
    @Override
    public void mousePressed(MouseEvent e) {
        switch (e.getButton()){
            case MouseEvent.BUTTON1 -> {
                leftButtonPressed = true;
                if(ui.currentState == UI.gameState){
                    ui.map.findBlock(e.getPoint());
                }
            }
            case MouseEvent.BUTTON3 -> {
                if(ui.currentState == UI.gameState) {
                    ui.map.interactWorld(e.getPoint(), ui.p.hotbar.getKeyFromCoordinates(ui.p.hotbar.inventorySpaceX, 0));
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
