package main.java;

public class AttackCommand extends MoveCommand implements PlayerCommand {
	
	public AttackCommand(Player player, Square end, Board board, Game game) {
		super(player, end, board, game);
	}
	
	@Override
	public void execute() {
		this.player.Attack(destinationSquare,board);
		game.increaseTurn();
	}


}
