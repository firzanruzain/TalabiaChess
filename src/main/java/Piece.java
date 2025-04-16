package main.java;

import java.util.ArrayList;
import java.util.List;

public abstract class Piece {
	protected final Colour colour;
	protected PieceMovement movement;
	protected Square square;
	public Boolean selected;
	
	protected Piece(Colour colour, Square square, PieceMovement movement) {
		this.colour = colour;
		this.square = square;
		this.movement = movement;
		selected = false;
	}
	
	protected Piece(String colour, String position, PieceMovement movement) {
		if (colour.equalsIgnoreCase("blue")) this.colour = Colour.Blue;
		else this.colour = Colour.Yellow;
		
		Square square = new Square(position);
		this.square = square;
		
		this.movement = movement;
	}
	
	public Colour getColour() {
		return colour;
	}
	
	public Square getSquare() {
		return square;
	}
	
	public void setSquare(Square square) {
		this.square = square;
	}
	
	public PieceMovement getMovement() {
		return movement;
	}
	
	public boolean isMoveValid(Square square) {
		return movement.isMoveValid(this, square);
	}
	
	public boolean pieceCanMove(Board board) {
		return (!this.getMovableSquares(board).isEmpty());
	}
	
	public String getClassName() {
		return this.getClass().getSimpleName();
	}
	
	public void isSelected() {
		selected = true;
	}
	
	public void isDeselected() {
		selected = false;
	}
	
	public boolean canCapture(Piece other, Board board) {
		boolean canCapture = false;
		boolean isMoveValid = this.isMoveValid(other.getSquare());
		boolean isPathClear = this.movement.isPathClear(this.getSquare(), other.getSquare(), board);
		
		if (isMoveValid && isPathClear) canCapture = true;
		
		return canCapture;
	}
	
	public List<Square> getMovableSquares(Board board){
		return movement.getMovableSquares(this, board);
	}
	
	public List<Square> getAttackableSquares(Board board){
		List<Square> attackableSquares = new ArrayList<>();
		List<Square> movableSquares = this.getMovableSquares(board);
		
		for (Square square : movableSquares) {
			if (board.isSquareOccupiedByPosition(square.getPosition())) {
				Piece pieceOnSquare = board.getPieceByPosition(square.getPosition());
				if(pieceOnSquare.getColour() != this.getColour()) {
					attackableSquares.add(square);
				}
			}
		}
		
		return attackableSquares;
	}
	
	@Override
	public String toString() {
		return getClassName()+ "/" +colour+"/" + this.square.getPosition().getXInt() + "," + this.square.getPosition().getYInt();
	}
	
	public void test() {
	}
	
	public static void main(String[] args) {
		new Point(Colour.Yellow, new Square(new Position(0, 0))).test();
	}
}
