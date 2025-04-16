package main.java;

public class MoveCommand implements PlayerCommand{
	protected Player player;
	protected Square destinationSquare;
	protected Board board;
	protected Game game;
	
	public MoveCommand(Player player, Square end, Board board, Game game) {
		this.player = player;
		this.destinationSquare = end;
		this.board = board;
		this.game = game;
	}

	@Override
	public void execute() {
		player.Move(destinationSquare,board);
		game.increaseTurn();
	}

}
