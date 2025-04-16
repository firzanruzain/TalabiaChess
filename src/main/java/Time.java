package main.java;

public class Time extends Piece {

	public Boolean isReversed;

	protected Time(Colour colour, Square square) {
		super(colour, square, new TimeMovement());
		isReversed = false;
	}
	
	protected Time(String colour, String position) {
		super(colour, position, new TimeMovement());
		isReversed = false;
	}
	
	public void test() {
		Position endPosition = new Position(2, 3);
	}
	
	public static void main(String[] args) {
		new Time(Colour.Yellow, new Square(new Position(0, 2))).test();
	}
    
}

