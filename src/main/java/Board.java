package main.java;
import java.util.*;

public class Board {

    public static final int Rows = 6;
    public static final int Columns = 7;
    private List<Piece> pieces = new ArrayList<>();
    private final Square[][] squares = new Square[6][7];
    
    public Board() {
    	init();
    }
    
    public Board(List<Piece> pieces) {
    	this.pieces = pieces;
    	for (int i = 0; i<Rows; i++) {
    		for(int j = 0; j<Columns; j++) {
    			Position position = new Position(i, j);
    			Square square = new Square(position);
    			squares[i][j] = square;
    		}
    	}
    }
    
    public List<Piece> getPieces() {
        return pieces;
    }
    
    public void removePiece(Piece piece) {
    	pieces.remove(piece);
    }
    
    public Square[][] getSquares(){
    	return squares;
    }
    
    public boolean isSquareOccupied(Square square) {
        if (square == null) {
          throw new NullPointerException("Square argument is null.");
        }
        return pieces.stream().anyMatch(piece -> piece.getSquare().equals(square));
      }
    
    public boolean isSquareOccupiedByPosition(Position position) {
    	return pieces.stream().anyMatch(piece -> piece.getSquare().getPosition().equals(position));
    }

    public void addPieceToBoard(Piece piece) {
    	if (!isSquareOccupiedByPosition(piece.getSquare().getPosition())) pieces.add(piece);
    	else {
    		
    		
    		throw new IllegalArgumentException("Square is already occupied!");
    	}
    }

    // tested ok let's gooo!
    public boolean isThereAPieceDiagonal(Square start, Square end) {
    	double xPosDif = start.getPosition().getXDelta(end.getPosition());
    	double yPosDif = start.getPosition().getYDelta(end.getPosition());
    	
    	int xOffset = end.getPosition().getX() < start.getPosition().getX() ? -1 : 1;
    	int yOffset = end.getPosition().getY() < start.getPosition().getY() ? -1 : 1;
    	
    	double x = start.getPosition().getX();
    	double y = start.getPosition().getY();
    	
    	for (double i = x + xOffset; i != end.getPosition().getX(); i += xOffset) {
    		y += yOffset;
    		Square cuurent = new Square(new Position(i, y));
    		if (isSquareOccupiedByPosition(cuurent.getPosition())) {
    			return true;
    		}
    	}
    	return false;
    }
    
    //tested ok
    public boolean isThereAPieceAxis(Square startSquare, Square end) {
		Position startPosition = startSquare.getPosition();
		Position endPosition = end.getPosition();
		
		Boolean sameRow = startPosition.getXInt() == endPosition.getXInt();
		Boolean sameColumn= startPosition.getYInt() == endPosition.getYInt();
		
		int offset = 1;
		int start;
		int limit;
		
		if(sameRow) {
			
			if (endPosition.getY() < startPosition.getY()) offset = -1;
			start = startPosition.getYInt();
			limit = endPosition.getYInt();
		}
		else {
			
			if (endPosition.getX() < startPosition.getX()) offset = -1;
			start = startPosition.getXInt();
			limit = endPosition.getXInt();
		}
		
		for (int position = start+offset; position != limit; position+=offset) {
			Square current;
			
			if (sameRow) current = squares[startPosition.getXInt()][position];
			else current = squares[position][startPosition.getYInt()];
			
			if (isSquareOccupied(current)) {
				return true;
			}
		}
		return false;
	}
    
    //tested should be okay
    public boolean isThereAPieceAlongPath(Square start, Square end) {
    	
    	
    	
    	Position startPosition = start.getPosition();
		Position endPosition = end.getPosition();
		
		Boolean sameRow = startPosition.getXInt() == endPosition.getXInt();
		Boolean sameColumn= startPosition.getYInt() == endPosition.getYInt();
		
		double rowDiff = endPosition.getXDelta(startPosition);
		double columnDiff = endPosition.getYDelta(startPosition);
		
		if (sameRow || sameColumn) {
			return isThereAPieceAxis(start, end);
		}else if (rowDiff == columnDiff) {
			return isThereAPieceDiagonal(start, end);
		}else {
			return false;
		}
		
    }
    
    public Piece getPieceByPosition(Position position) {
    	Piece piece1 = null;
    	for (Piece piece : pieces) {
    		if (piece instanceof TimePlus) {
    			piece = ((TimePlus) piece);
    		}
    		if(piece.getSquare().getPosition().equals(position)) {
    			piece1 = piece;
    		}
    	}
    	return piece1;
    }
    
    public List<TimePlus> geTimePlus(){
    	List<TimePlus> timePlus = new ArrayList<TimePlus>();
    	
    	for (Piece piece : pieces) {
    		if (piece instanceof TimePlus) {
    			timePlus.add((TimePlus) piece);
    		}
    	}
    	
    	return timePlus;
    }
    
    public Sun getSun(Colour colour) {
    	return (Sun)
    	        pieces.stream()
    	            .filter(piece -> piece.getColour() == colour && piece instanceof Sun)
    	            .findFirst()
    	            .orElseThrow(() -> new NoSuchElementException("Cannot find Sun."));
    }
    
    public boolean isInCheck(Sun sun) {
    	boolean inCheck = 
    			pieces.stream()
    			.filter(pieces -> pieces.getColour() !=sun.getColour())
    			.anyMatch(pieces -> pieces.canCapture(sun, this));
    	return inCheck;
    }
    
    public boolean moveResultInCheck(Piece piece, Square end) {
    	Square start = piece.getSquare();
    	
    	Sun sun = getSun(piece.getColour());
    	piece.setSquare(end);
    	boolean isInCheck = isInCheck(sun);
    	
    	
    	if (isSquareOccupied(end)) {
    		Piece capturedPiece = getPieceByPosition(end.getPosition());
    		removePiece(capturedPiece);
    		
    		
    		isInCheck = isInCheck(sun);
    		piece.setSquare(start);
    		addPieceToBoard(capturedPiece);
    	}
    	
    
    	piece.setSquare(start);
    	return isInCheck;
    }
    
    public void init() {
    	// Squares
    	for (int i = 0; i<Rows; i++) {
    		for(int j = 0; j<Columns; j++) {
    			Position position = new Position(i, j);
    			Square square = new Square(position);
    			squares[i][j] = square;
    		}
    	}
    	
    	// for blue pieces
    	for (int i = 0; i<2; i++) {
    		
    		for (int j = 0; j<Columns; j++) {
    			
    			//Position position = new Position(i, j);
    			Square square = squares[i][j];
    			Piece piece = null;
    			Colour colour = Colour.Blue;
    			
    			if(i!=1) {
    				if (j==0 || j==6) {
    					piece = new TimePlus(new Plus(colour, square));
    				}else if (j==1 || j==5) {
						piece = new Hourglass(colour, square);
					}else if (j==2 || j==4) {
						piece = new TimePlus(new Time(colour, square));
					}else {
						piece = new Sun(colour, square);
					}
    					
    			}else {
    				piece = new Point(colour, square);
    			}
    			addPieceToBoard(piece);
    			}
    		}
    	
    	
    	// for Yellow pieces
    	for (int i = 4; i<6; i++) {
    		for (int j = 0; j<Columns; j++) {
    			//Position position = new Position(i, j);
    			Square square = squares[i][j];
    			Piece piece = null;
    			Colour colour = Colour.Yellow;
    			
    			if(i!=4) {
    				if (j==0 || j==6) {
    					piece = new TimePlus(new Plus(colour, square));
    				}else if (j==1 || j==5) {
						piece = new Hourglass(colour, square);
					}else if (j==2 || j==4) {
						piece = new TimePlus(new Time(colour, square));
					}else {
						piece = new Sun(colour, square);
					}
    					
    			}else {
    				piece = new Point(colour, square);
    			}
    			addPieceToBoard(piece);
    			
    			}
    			
    		}
    	
    	
    	
    	}
    
    public boolean squareExists(Square square) {
    	boolean squareExists = false;
    	for (Square[] squareR : squares) {
    		for(Square square1: squareR) {
    			if (square.equals(square1)) squareExists = true;
    		}
    	}
    	return squareExists;
    }
    
    public boolean reachedEnd(Piece piece) {
    	boolean reachedEnd = false;
    	Colour colour = piece.getColour();
    	
    	if(piece.getSquare().getPosition().getX() == Rows-Rows || piece.getSquare().getPosition().getX() == Rows-1) {
    		reachedEnd = true;
    	}

    	return reachedEnd;
    }
    
    @Override
    public String toString() {
    	String boardString = "Board{\n";
    	for (Piece piece : pieces) {
    		boardString += piece.toString()+"@";
    	}
    	boardString = boardString.substring(0, boardString.length()-1);
    	return boardString;
    }
    
    public void test() {
    	System.out.println(toString());
    }
    
    public static void main(String[] args) {
		new Board().test();
	}
    
    }
    
