import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class BirdPnl extends JPanel implements Runnable {
    Thread t;
    Clicks clicks = new Clicks();
    final double phi = (1 + Math.sqrt(5)) / 2;
    final int width = 800, height = 600; 

    List<Bird> birds = new ArrayList<>();
    public BirdPnl(){
        this.setDoubleBuffered(true);
        this.addMouseListener(clicks);
        this.setPreferredSize(new Dimension(width,height));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
    }

    public void startThread(){
        t = new Thread(this);
        t.start();
    }

    @Override
    public void run() {
        while (t != null){
            checkControls();
            
            for (Bird bird : birds) {
                bird.angle += 0.1;
                double r = Math.pow(phi, 2*bird.angle / Math.PI);
                bird.x += r * Math.cos(bird.angle);
                bird.y += r * Math.sin(bird.angle);
            }

            repaint();
            sleep();
        }
    }
    
    public void sleep(){
        try {
            Thread.sleep((long) 1000 / 5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void checkControls(){
        if (clicks.clicked = true){
            birds.add(new Bird(clicks.x, clicks.y));
            clicks.clicked = false;
        }
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.MAGENTA);
        for (Bird bird : birds) {
            // g2.drawArc((int) bird.x, (int) bird.y, 10,10, 0, 110);
            g2.fillOval((int) bird.x, (int) bird.y, 10,10);
            System.out.println(bird.x + " " +bird.y);
        }
        g2.dispose();
    }
}
