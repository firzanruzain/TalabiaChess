package main.java;

public class TimeMovement extends PieceMovement {

	@Override
	public boolean isMoveValid(Piece piece, Square square) {
		boolean isMoveValid = false;
		Position startPosition = piece.getSquare().getPosition();
		Position endPosition = square.getPosition();
		
		isMoveValid = startPosition.getXDelta(endPosition) == startPosition.getYDelta(endPosition);
		
		return isMoveValid;
	}

	

}
