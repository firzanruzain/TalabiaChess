package main.java;

public class Plus extends Piece {

	public Boolean isReversed;

	protected Plus(Colour colour, Square square) {
		super(colour, square, new PlusMovement());
		isReversed = false;
	}
	
	protected Plus(String colour, String position) {
		super(colour, position, new PlusMovement());
		isReversed = false;
	}

	public void isReversed() {
		isReversed = true;
	}

	public static void main(String[] args) {
		new Plus(Colour.Yellow, new Square(new Position(1, 3))).isMoveValid(new Square(new Position(1, 3)));
	}
}

