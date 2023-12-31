import java.awt.event.MouseEvent;

public class MouseListener implements java.awt.event.MouseListener {
    public UI ui;
    public MouseListener(UI ui){
        this.ui = ui;
    }
    @Override
    public void mouseClicked(MouseEvent e) {


    }

    @Override
    public void mousePressed(MouseEvent e) {
        switch (e.getButton()){
            case MouseEvent.BUTTON1 -> System.out.println(e.getLocationOnScreen());
            case MouseEvent.BUTTON3 -> System.out.println("RightClick");
        }
    }
    @Override
    public void mouseReleased(MouseEvent e) {

    }
    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
