import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JPanel;

public class BirdPnl extends JPanel implements Runnable {
    Thread t;
    Clicks clicks = new Clicks();
    Keys k = new Keys();
    boolean active = false;
    double timeActive, nextPeriod;

    final double phi = (1 + Math.sqrt(5)) / 2;
    final int width, height;
    
    final double thetaChange = 0.04;
    
    List<Bird> birds = new ArrayList<>();
    List<Bird> birdsToRemove = new ArrayList<>();

    public BirdPnl(){
        this(800,600);
    }

    public BirdPnl(int width, int height) {
        this.width = width;
        this.height = height;
        this.setDoubleBuffered(true);
        this.addMouseListener(clicks);
        this.addKeyListener(k);
        this.setPreferredSize(new Dimension(width, height));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
    }

    public void startThread() {
        t = new Thread(this);
        t.start();
    }

    final double birdDist = 3;
    public Bird newBirdNearBird(Bird bird){
        Random r = new Random(System.currentTimeMillis());
        double theta = r.nextInt(360);
        return new Bird(bird.x + (birdDist * Math.sin(theta)),
        bird.y + (birdDist * Math.cos(theta)),
        bird.angle);
    }

    @Override
    public void run() {
        while (t != null) {
            timeActive = System.currentTimeMillis();
            if (birds.size() == 0){
                timeActive = System.currentTimeMillis();
                nextPeriod = timeActive + (Math.random() % 1500) + 2500;
                birds.add(new Bird(width / 2, height / 2));
            } 

            if (timeActive > nextPeriod) {
                // timeActive = nextPeriod;
                nextPeriod = timeActive + (Math.random() % 1500) + 2500;
                if (birds.size() < 100){
                    int lim = birds.size();
                    for (int i = 0; i < lim; i++){
                        birds.add(newBirdNearBird(birds.get(0)));
                    }
                }
            }

            for (Bird bird : birds) {
                update(bird);
            }
            
            for (Bird bird : birdsToRemove) {
                birds.remove(bird);
            } birdsToRemove.clear();
            
            repaint();
            eep(1/60);
        }
    }

    public void update(Bird bird) {
        bird.angle += thetaChange;
        double r = Math.pow(phi, 2 * bird.angle / Math.PI);
        bird.x = bird.startx + r * Math.cos(bird.angle);
        bird.y = bird.starty + r * Math.sin(bird.angle);
        if (r > Math.pow(Math.pow(height, 2) + Math.pow(width, 2), 0.5))
            birdsToRemove.add(bird);
        
    }

    public void eep(double ms) { //shmimimimimi
        try {
            Thread.sleep((long) ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void checkControls() {
        if (clicks.clicked == true) {
            System.out.println("beep");
            birds.add(new Bird(clicks.x, clicks.y));
            clicks.clicked = false;
        }

        if (k.l) {
            System.out.println(birds.size());
            k.l = false;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.white);
        int lim = (int) Math.pow((Math.pow(height, 2) + Math.pow(width, 2)), 0.5);
        for (int i = 75; i < lim; i += 75) {
            g2.drawOval((width / 2) - (i / 2), (height / 2) - (i / 2), i, i);
        }
        for (Bird bird : birds) {
            g2.setColor(Color.getHSBColor((float) (1-(1 / bird.angle % 360)), 1, 1));
            g2.fillOval((int) bird.x, (int) bird.y, 10, 10);
        }
        g2.dispose();
    }
}
