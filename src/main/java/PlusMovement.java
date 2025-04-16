package main.java;

public class PlusMovement extends PieceMovement {

	@Override
	public boolean isMoveValid(Piece piece, Square square) {
		boolean isMoveValid = false;
		
		Position startPosition = piece.getSquare().getPosition();
		Position endPosition = square.getPosition();
		
		double yPosDif = Position.getXPosDif(startPosition, endPosition);
		double xPosDif = Position.getYPosDif(startPosition, endPosition);
		
		/*if (point.getColour() == Colour.Blue) {
			point.isReversed = !point.isReversed;
		}

		if (xPosDif != 0 && yPosDif != 0)
		{
			isMoveValid = false;
		}
		else
		{
			if (!point.isReversed)
			{
				if ((xPosDif <= 6 && yPosDif == 0) || (yPosDif <= 5 && xPosDif == 0))
				{
					isMoveValid = true;
				}
			}
			else
			{
				if ((xPosDif >= -6 && yPosDif == 0) || (yPosDif >= -5 && xPosDif == 0))
				{
					isMoveValid = true;
				}
			}	
		}*/
		
		boolean sameRow = startPosition.getX() == endPosition.getX() && startPosition.getY() != endPosition.getY();
		boolean sameColumn = startPosition.getY() == endPosition.getY() && startPosition.getX() != endPosition.getX();
		
		isMoveValid = sameColumn || sameRow;
		
		return isMoveValid;
	}

	

}

