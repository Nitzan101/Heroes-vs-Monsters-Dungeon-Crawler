package Tiles;

public class Position {

    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX(){return x;}
    public int getY(){return y;}
    public void setPosition(int x, int y){this.x = x; this.y = y;}

    public static double distanse(Position p1, Position p2) {
        double a = Math.pow(p1.getX() - p2.getX(), 2);
        double b = Math.pow(p1.getY() - p2.getY(), 2);
        return Math.sqrt(a + b);
    }

}
