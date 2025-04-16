package main.java;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private Board board;
    private Player player1;
    private  Player player2;
    private  final PlayerCommandProcessor playerCommandProcessor;
    private GameState gameState;
    public static int turnCounter;
	private Player winner;
    
    public Game() {
    	this.board = new Board();
    	this.player1 = new Player("Player 1", Colour.Yellow, true);
    	this.player2 = new Player("Player 2", Colour.Blue, false);
    	this.playerCommandProcessor = new PlayerCommandProcessor(this);
    	turnCounter = 0;
    	//gameState = GameState.Playing;
    }
    
    public void reset() {
    	this.board = new Board();
    	this.player1 = new Player("Player 1", Colour.Yellow, true);
    	this.player2 = new Player("Player 2", Colour.Blue, false);
    	turnCounter = 0;
    	gameState = GameState.Playing;
    }
    
    public void clear() {
    	this.board = null;
    	this.player1 = null;
    	this.player2 = null;
    	turnCounter = 0;
    }
    
    public void load(Board board, Player player1, Player player2, int turnCounter1) {
    	this.board = board;
    	this.player1 = player1;
    	this.player2 = player2;
    	turnCounter = turnCounter1;
    }
    
    public Player getCurrentPlayerObject() {
    	return player1.isTurn() ? player1 : player2;
    }
    
    public Player getNextPlayerObject() {
    	return player1.isTurn() ? player2 : player1;
    }
    
    public Board getBoard() {
    	return board;
    }
    
    public void test() {

    	executeCommand("4,0");
    }
    
    public void executeCommand(String commandString) {
    		Square endSquare = new Square(commandString);
    		playerCommandProcessor.processCommand(getCurrentPlayerObject(), endSquare, board);
    }
    
    /*public void executeFileCommand(String commandString, String fileName) {
    	if(commandString.equalsIgnoreCase("load")) {
    		FileManager.Load(fileName, this);
    	}else if(commandString.equalsIgnoreCase("save")) {
    		FileManager.Save(fileName, this);
    	}
    }*/
    
    public void increaseTurn() {
    	turnCounter++;
    	turnIncreased();
    }
    
    public void turnIncreased() {
    	
    	if(turnCounter%4 == 0) {

    		TimePlusSwitch();
    	}
    	
    	//if (isStaleMate()) gameState = GameState.Stalemate;
    	
    	
    	if (turnCounter%2 == 0) {
    		player1.setTurn(true);
    		player2.setTurn(false);
    	}else {
    		player1.setTurn(false);
    		player2.setTurn(true);
    	}
    	//System.out.println("checkmate? " + isCheckMate());
    	/*if(isCheckMate()) {
    		//gameState = GameState.Checkmate;
    		System.out.println("Checkmate");
    	}else if (isStaleMate()) {
    		//gameState = GameState.Stalemate;
    		System.out.println("Stalemate");
    	}*/
    	
    	if (sunIsDead(getCurrentPlayerObject())) {
    		System.out.println("Game Overr");
    		winner = getNextPlayerObject();
    		System.out.println("winner: " + winner.getName());
    	}
    }
    
    public void TimePlusSwitch() {
    	for (TimePlus timePlus : board.geTimePlus()) {
    		timePlus.switchs();
    	}
    }
    
    public List<Square> getAvailableSquares(){
    	if (winner != null) {
    		return null;
    	}
    	List<Square> availableSquares = new ArrayList<>();
    	
    	if (getCurrentPlayerObject().getSelectedPiece() == null) {
    		for (Piece piece : board.getPieces()) {
    			if (getCurrentPlayerObject().pieceSelectable(piece)) availableSquares.add(piece.getSquare());
    		}
    	}else {
    		Piece selectedPiece = getCurrentPlayerObject().getSelectedPiece();
    		for (Square square : selectedPiece.getMovableSquares(board)) {
    			availableSquares.add(square);
    		}
    		availableSquares.add(selectedPiece.square);
    	}
    	
    	return availableSquares;
    }
    
    public boolean areThereAnyValidMove() {
    	for (Piece piece : board.getPieces()) {
    		if (piece.pieceCanMove(board)) return true;
    	}
    	return false;
    }
    
    public boolean playerCanMove(Player player) {
    	
    	List<Piece> pieces = board.getPieces();
    	
    	
    	for (int i = 0; i<pieces.size(); i++) {
    		Piece piece = pieces.get(i);
    		if (piece.getColour() == player.getColour()) {
    			if (piece.pieceCanMove(board)) return true;
    		}
    	}
    	return false;
    }
    
    public boolean isCurrentPlayerInCheck() {
    	return board.isInCheck(board.getSun(getCurrentPlayerObject().getColour()));
    }
    
    public boolean isCheckMate() {
    	Colour currentPlayerColor = getCurrentPlayerObject().getColour();
        Sun sun = board.getSun(currentPlayerColor);
        return board.isInCheck(sun) &&!playerCanMove(getCurrentPlayerObject());
    }
    
    public Player getWinner() {
    	return winner;
    }
    
    public boolean isStaleMate() {
    	Colour currentPlayerColor = getCurrentPlayerObject().getColour();
        Sun sun = board.getSun(currentPlayerColor);
        return !board.isInCheck(sun) && !playerCanMove(getCurrentPlayerObject());
    }
    
    public GameState getCurrentState(){
    	return gameState;
    }
    
    public boolean sunIsDead(Player player) {
    	Colour colour = player.getColour();
    	
    	return !board.getPieces().stream().anyMatch(piece -> piece instanceof Sun && piece.getColour() == colour);
    }
    
    public Player getPlayer1(){
    	return player1;
    }
    
    public Player getPlayer2(){
    	return player2;
    }
    
    public static void main(String[] args) {
		new Game();
	}
    
}
