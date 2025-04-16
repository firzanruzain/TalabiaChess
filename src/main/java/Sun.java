package main.java;

public class Sun extends Piece {

	public Boolean isReversed;

	protected Sun(Colour colour, Square square) {
		super(colour, square, new SunMovement());
		isReversed = false;
	}
	
	protected Sun(String colour, String position) {
		super(colour, position, new SunMovement());
		isReversed = false;
	}

	public void isReversed(){
		isReversed = true;
	}

	public static void main(String[] args) {
		new Sun(Colour.Yellow, new Square(new Position(3, 3))).isMoveValid(new Square(new Position(9, 5))); //Square is the coordinate
	}
}

