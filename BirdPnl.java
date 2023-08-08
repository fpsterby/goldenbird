import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class BirdPnl extends JPanel implements Runnable {
    Thread t;
    Clicks clicks = new Clicks();
    Keys k = new Keys();

    final double phi = (1 + Math.sqrt(5)) / 2;
    final int width = 800, height = 600; 
    final double thetaChange = 0.075;
    
    List<Bird> birds = new ArrayList<>();
    List<Bird> birdsToRemove = new ArrayList<>();
    
    public BirdPnl(){
        this.setDoubleBuffered(true);
        this.addMouseListener(clicks);
        this.addKeyListener(k);
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
            update();
            repaint();
            sleep();
        }
    }
    
    public void update(){
        for (Bird bird : birds) {
            bird.angle += thetaChange;
            double r = Math.pow(phi, 2*bird.angle / Math.PI);
            bird.x = bird.startx + r * Math.cos(bird.angle);
            bird.y = bird.starty + r * Math.sin(bird.angle);
            if (r > Math.pow(Math.pow(height, 2) + Math.pow(width, 2), 0.5))
                birdsToRemove.add(bird);
            }
            for (Bird bird : birdsToRemove) {
                birds.remove(bird);
            } birdsToRemove.clear();
            
    }

    public void sleep(){
        try {
            Thread.sleep((long) 1000 / 60);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void checkControls(){
        if (clicks.clicked == true){
            System.out.println("beep");
            birds.add(new Bird(clicks.x, clicks.y));
            clicks.clicked = false;
        }

        if (k.l){
            System.out.println(birds.size());
            k.l = false;
        }
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.MAGENTA);
        int lim = width;
            if (lim < height)
                lim = height;
        for (int i = 100; i < lim; i += 100){
            g2.drawOval((width / 2) - (i / 2), (height / 2) - (i / 2), i, i);
        }
        for (Bird bird : birds) {
            g2.setColor(Color.getHSBColor((float) (1 / bird.angle), 1, 1));
            g2.fillOval((int) bird.x, (int) bird.y, 10,10);
        }
        g2.dispose();
    }
}
