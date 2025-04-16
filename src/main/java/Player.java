package main.java;
import java.util.*;

public class Player {
    private final Colour colour;
    private final List<Piece> capturedPieces = new ArrayList<>();
    private String name;
    private boolean isTurn;
    private Piece selectedPiece;

    public Player(String name, Colour colour, boolean isTurn){
        this.colour = colour;
        this.name = name;
        this.isTurn = isTurn;
    }
    
    public Player(String[] playerStrings) {
    	String nameString = playerStrings[0];
    	Colour colour;
    	Boolean isturn;
    	if (playerStrings[2].equalsIgnoreCase("true")) isturn = true;
    	else isturn = false;
    	if (playerStrings[1].equalsIgnoreCase("Yellow")) colour = Colour.Yellow;
    	else colour = Colour.Blue;
    	
    	this.colour = colour;
    	this.name = nameString;
    	this.isTurn = isturn;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public Colour getColour(){
        return colour;
    }
    
    public Boolean isTurn() {
    	return isTurn;
    }
    
    public void setTurn(boolean turn) {
    	isTurn = turn;
    }
    
    public void Move(Square end, Board board) {
    	
    	
    	if(selectedPiece != null) {

    		for (Square sqaure : selectedPiece.getMovableSquares(board)) {
    		}
    		
    		if (selectedPiece.getMovableSquares(board).contains(end)) {
    			selectedPiece.setSquare(end);
    			
    			if (selectedPiece instanceof Point) {
    				if (board.reachedEnd(selectedPiece)) {
    					((Point) selectedPiece).isReversed();
    				}
    			}
    			selectedPiece = null;
    		}
    	}else {
    		throw new IllegalArgumentException("invalid command");
    	}
    }
    
    public void Attack(Square end, Board board) {
    	Piece capturedPiece = board.getPieceByPosition(end.getPosition());
    	Move(end, board);
    	this.capturedPieces.add(capturedPiece);
    	board.removePiece(capturedPiece);
    }
    
    public void Select(Piece piece) {
    	if (pieceSelectable(piece)) {
    		if(selectedPiece != null) {
    			if(selectedPiece == piece) {
    				piece.isDeselected();
        			selectedPiece = null;
    			}else {
    				selectedPiece.isDeselected();
    				piece.isSelected();
    				selectedPiece = piece;
    			}
    		}else {
    			piece.isSelected();
    			selectedPiece = piece;
    		}
    	}
    }
    
    public Boolean pieceSelectable(Piece piece) {
    	if (piece.getColour() == this.getColour()) {
    		return true;
    	}else {
    		return false;
    	}
    }
    
    public Piece getSelectedPiece() {
    	return selectedPiece;
    }
    
    public List<Piece> getCapturedPieces(){
    	return capturedPieces;
    }
    
    
    
    public void test() {
    }
    
    public static void main(String[] args) {
	}
    
}
