package main.java;

public class Point extends Piece {

	public Boolean isReversed;
	
	protected Point(Colour colour, Square square) {
		super(colour, square, new PointMovement());
		isReversed = false;
	}
	
	protected Point(String colour, String position) {
		super(colour, position, new PointMovement());
		isReversed = false;
	}

	public void isReversed() {
		isReversed = !isReversed;
	}
	
	public static void main(String[] args) {
		new Point(Colour.Yellow, new Square(new Position(1, 0))).isMoveValid(new Square(new Position(0, 0)));
	}
	
}
