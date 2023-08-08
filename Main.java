import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        
        BirdPnl birdp = new BirdPnl();

        frame.add(birdp);
        frame.pack();
        frame.setVisible(true);

        birdp.startThread();
    }
}