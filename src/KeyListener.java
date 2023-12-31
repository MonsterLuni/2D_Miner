import java.awt.event.KeyEvent;

public class KeyListener implements java.awt.event.KeyListener {
    public UI ui;
    public boolean aPressed,dPressed, spacePressed;
    public KeyListener(UI ui){
        this.ui = ui;
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()){
            case KeyEvent.VK_A -> aPressed = true;
            case KeyEvent.VK_D -> dPressed = true;
            case KeyEvent.VK_F11 -> ui.toggleFullscreen();
            case KeyEvent.VK_SPACE -> spacePressed = true;
            case KeyEvent.VK_S -> {
                if(ui.p.height == ui.p.defaultHeight) {
                    ui.p.Y += 20;
                    ui.p.height = 30;
                }
            }
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()){
            case KeyEvent.VK_A -> aPressed = false;
            case KeyEvent.VK_D -> dPressed = false;
            case KeyEvent.VK_SPACE -> spacePressed = false;
            case KeyEvent.VK_S -> {
                ui.p.Y -= 20;
                ui.p.height = ui.p.defaultHeight;
            }
        }
    }
}
