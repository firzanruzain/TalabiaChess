package main.java;

import java.util.List;

public class TimePlus extends Piece{
	private Time time;
    private Plus plus;
    private Piece currentPiece;
    private PieceMovement currentMovement;
    
    public TimePlus(Piece piece) {
    	super(piece.getColour(), piece.getSquare(), piece.movement);
    	
    	if (piece instanceof Time) {
    		this.time = (Time)piece;
    		this.plus = new Plus(time.getColour(), time.getSquare());
    	}else if (piece instanceof Plus) {
    		this.plus = (Plus)piece;
    		this.time = new Time(plus.getColour(), plus.getSquare());
		}
    	currentPiece = piece;
    }
    
    public void switchToTime() {
    	currentPiece = this.time;
    	currentMovement = this.time.movement;
    }
    
    public void switchToPlus() {
    	currentPiece = this.plus;
    	currentMovement = this.plus.movement;
    }
    
    public Time getTime() {
    	return time;
    }
    
    public Plus getPlus() {
    	return plus;
    }
    
    public Piece getCurrentPiece() {
    	return currentPiece;
    }
    
    public void switchs() {
    	if (currentPiece instanceof Time) {
    		switchToPlus();
    	}else {
    		switchToTime();
    	}
    }
    
    @Override
    public void setSquare(Square square) {
    	this.square = square;
    	this.time.setSquare(square);
    	this.plus.setSquare(square);
    }
    
    @Override
    public String getClassName() {
    	return currentPiece.getClassName();
    }
    
    @Override
    public void isSelected() {
		selected = true;
	}
    
    @Override
	public void isDeselected() {
		selected = false;
	}
    
    @Override
	public List<Square> getMovableSquares(Board board){
		return this.getCurrentPiece().movement.getMovableSquares(this.currentPiece, board);
	}
}
