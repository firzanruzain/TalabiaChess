package main.java;

import java.awt.Point;

public class Position extends Point{

    Position(int x, int y){
        super(x,y);
    }
    
    Position (double x, double y){
    	super((int)x,(int)y);
    }
    
    public double getXDelta(Position other) {
    	return Math.abs(this.getX() - other.getX());
    }
    
    public double getYDelta(Position other) {
    	return Math.abs(this.getY() - other.getY());
    }
    
    public static double getXPosDif(Position start, Position end) {
    	return end.getX() - start.getX();
    }
    
    public static double getYPosDif(Position start, Position end) {
    	return end.getY() - start.getY();
    }
    
    public int getXInt() {
    	return (int)getX();
    }
    
    public int getYInt() {
    	return (int)getY();
    }
}
