import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

// import javax.swing.event.MouseInputListener;

public class Clicks implements MouseListener {
    int x, y;
    boolean clicked;
    @Override
    public void mouseClicked(MouseEvent e) {
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
x = e.getX();
        y = e.getY();
        System.out.println("click" + x + " " + y);
        clicked = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        clicked = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
