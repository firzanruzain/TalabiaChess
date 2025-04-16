package main.java;

public class PointMovement extends PieceMovement {

	@Override
	public boolean isMoveValid(Piece piece, Square square) {
		Point point = (Point) piece;
		boolean isMoveValid = false;
		
		Position startPosition = piece.getSquare().getPosition();
		Position endPosition = square.getPosition();
		
		double yPosDif = Position.getXPosDif(startPosition, endPosition);
		double xPosDif = Position.getYPosDif(startPosition, endPosition);
		
		boolean forward = true;
		
		if (point.getColour() == Colour.Blue) {
			forward = point.isReversed;
		}else {
			forward = !point.isReversed;
		}
		
		if (xPosDif != 0 || yPosDif == 0)
		{
			isMoveValid = false;
		}
		else
		{
			if (!forward)
			{
				if ((yPosDif <= 2 && yPosDif > 0))
				{
					isMoveValid = true;
				}
			}
			else
			{
				if ((yPosDif >= -2 && yPosDif < 0)) {
					isMoveValid = true;
				}
			}
			
		}
		
		
		
		
		
		
		return isMoveValid;
	}

	


}
