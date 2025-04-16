package main.java;

import java.util.Objects;

public class Square {
    private final Position position;
    private final String stringCoordinate;

    public Square(Position position){
        this.position = position;
        String xString = Double.toString(position.getX());
        String yString = Double.toString(position.getY());
        this.stringCoordinate = xString +","+ yString;
    }
    
    public Square(String stringCoordinate) {
    	this.stringCoordinate = stringCoordinate;
    	String[] stringCoordinateArr = stringCoordinate.split(",");
    	int x = Integer.parseInt(stringCoordinateArr[0]);
    	int y = Integer.parseInt(stringCoordinateArr[1]);
    	this.position = new Position(x, y);
    }
    
    public Position getPosition() {
    	return position;
    }
    
    public String getStringCoordinate() {
    	return stringCoordinate;
    }
    @Override
    public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      }
      if (obj == null || !(obj instanceof Square)) {
        return false;
      }
      Square square = (Square) obj;
      return square.getPosition().equals(this.getPosition());
    }
    
    @Override
    public int hashCode() {
      return Objects.hash(position);
    }

    @Override
    public String toString() {
      return "Square{" + "x=" + position.getXInt() + ", y=" + position.getYInt() + '}';
    }
}
