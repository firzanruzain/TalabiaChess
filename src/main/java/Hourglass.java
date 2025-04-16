package main.java;

public class Hourglass extends Piece {

	public Boolean isReversed;

	protected Hourglass(Colour colour, Square square) {
		super(colour, square, new HourGlassMovement());
		isReversed = false;
	}
	
	protected Hourglass(String colour, String position) {
		super(colour, position, new HourGlassMovement());
		isReversed = false;
	}
	
	public void test() {
		Position position = new Position(2, 6);
		Square square = new Square(position);
		
		isMoveValid(square);
	}
	

	public void isReversed() {
		isReversed = true;
	}

}
