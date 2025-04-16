package main.java;

public class HourGlassMovement extends PieceMovement {

	@Override
	public boolean isMoveValid(Piece piece, Square square) {
		
		boolean isMoveValid = false;
		
		Position startPosition = piece.getSquare().getPosition();
		Position endPosition = square.getPosition();
		
		double rowDiff = endPosition.getXDelta(startPosition);
		double columnDiff = endPosition.getYDelta(startPosition);
		
		if (rowDiff == 2) {
			if (columnDiff == 1) {
				isMoveValid = true;
			}
		}else if (columnDiff == 2) {
			if (rowDiff == 1) {
				isMoveValid = true;
			}
		}
		
		return isMoveValid;
	}

	

}
