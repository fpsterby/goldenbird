public class Bird {
    double x,y;
    final double startx,starty;
    double angle = 0;

    public Bird(){
        this(0,0);
    }

    public Bird(double x, double y){
        this.startx = x;
        this.starty = y;
    }
}
