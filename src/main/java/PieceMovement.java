package main.java;

import java.util.ArrayList;
import java.util.List;

public abstract class PieceMovement {
	
	public abstract boolean isMoveValid(Piece piece, Square square);

	public boolean isPathClear(Square startSquare, Square end, Board board) {
		return !board.isThereAPieceAlongPath(startSquare, end);
	}
	
	public List<Square> getMovableSquares(Piece piece, Board board){
		if (piece instanceof TimePlus) piece = ((TimePlus) piece).getCurrentPiece();
		Square[][] squares = board.getSquares();
		List<Square> movableSquares = new ArrayList<Square>();
		
		for (Square[] squareR : squares) {
			for (Square square1 : squareR) {
				if(validateMove(piece, board, square1)) {
					movableSquares.add(square1);
				}
			}
		}
		return movableSquares;
	}
	
	public boolean validateMove(Piece piece, Board board, Square end) {
		Square startSquare = piece.getSquare();
		boolean moveValid = false;
		
		if (isMoveValid(piece, end)) {
			if (startSquare.equals(end)) {
				return false;
			}
			else if (board.isSquareOccupied(end)) {
				Piece pieceOnEndPiece = board.getPieceByPosition(end.getPosition());
				if(piece.getColour() == pieceOnEndPiece.getColour()) {
					
					return false;
				}else {
					
					moveValid = true;
					
				}
			}
			
			boolean canMove = isPathClear(startSquare, end, board);
			if (!canMove) {
				
				return false;
			}else {
				
				moveValid = true;
			}
		}else {
			
			return false;
		}
		
		/*if (startSquare.equals(end)) {
			System.out.println("Square the same");
			return false;
		}
		if (board.isSquareOccupied(end)) {
			Piece pieceOnEndPiece = board.getPieceByPosition(end.getPosition());
			if(piece.getColour() == pieceOnEndPiece.getColour()) {
				System.out.println("Square occupied by friend");
				System.out.println(end);
				return false;
			}else {
				System.out.println("Square occupied by enemy! Attackk");
				moveValid = true;
				
			}
		}
		if (isMoveValid(piece, end)) {
			boolean canMove = isPathClear(startSquare, end, board);
			if (!canMove) {
				System.out.println("pieces on the way :(");
				return false;
			}else {
				System.out.println("All good");
				moveValid = true;
			}
		}else {
			System.out.println("Not in pattern");
			moveValid = false;
		}*/
		
		if (moveValid == true) {
			//if (board.moveResultInCheck(piece, end)) moveValid = false;
		}
		
		return moveValid;
		
	}

	
}
