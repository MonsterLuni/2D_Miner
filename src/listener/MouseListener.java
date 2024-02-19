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
                ui.map.findBlock(e.getLocationOnScreen());
            }
            case MouseEvent.BUTTON3 -> System.out.println("RightClick");
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
