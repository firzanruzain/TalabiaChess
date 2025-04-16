package main.java;

public class SunMovement extends PieceMovement {

	@Override
	public boolean isMoveValid(Piece piece, Square square) {
		Sun sun = (Sun) piece;
		Boolean isMoveValid = false;

		Position startPosition = piece.getSquare().getPosition();
		Position endPosition = square.getPosition();
		
		
		double xPosDif = startPosition.getXDelta(endPosition);
		double yPosDif = startPosition.getYDelta(endPosition);
		
		if (yPosDif <= 1 && xPosDif <= 1) {
			isMoveValid = true;
			
		}
		
		/*
		double xPosDif = Position.getXPosDif(startPosition, endPosition);
		double yPosDif = Position.getYPosDif(startPosition, endPosition);

		if (sun.getColour() == Colour.Blue) {
			sun.isReversed = !sun.isReversed;
		}
		if (xPosDif != 1 && xPosDif != -1 && yPosDif != 1 && yPosDif != -1) {
			isMoveValid = false;
		}

		else {
			if (!sun.isReversed) {
				if ((xPosDif == 0 && Math.abs(yPosDif) == 1) ||(Math.abs(xPosDif) == 1 && yPosDif == 0) || ((Math.abs(xPosDif) == 1 && (Math.abs(yPosDif) == 1)))) {
					isMoveValid = true;
				}
			} 
			else {
				if ((xPosDif == 0 && Math.abs(yPosDif) == 1) ||(Math.abs(xPosDif) == 1 && yPosDif == 0) || ((Math.abs(xPosDif) == 1 && (Math.abs(yPosDif) == 1)))) {
					isMoveValid = true;
				}
			}
		}*/
		return isMoveValid;
	}

	
}

