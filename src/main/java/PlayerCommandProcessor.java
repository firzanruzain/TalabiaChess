package main.java;

public class PlayerCommandProcessor{
	
	private PlayerCommand lastCommand;
	private Player lastplayer;
	private final PlayerCommandExecutor playerCommandExecutor;
	private final Game modelGame;
	
	public PlayerCommandProcessor(Game game) {
		this.playerCommandExecutor = new PlayerCommandExecutor();
		this.modelGame = game;
	}
	
	public void processCommand(Player player, Square end, Board board) {
		PlayerCommand command = createCommand(player, end, board);
		
		playerCommandExecutor.executeCommand(command);
		
		lastCommand = command;
		lastplayer = player;
	}
	
	public PlayerCommand createCommand(Player player, Square end, Board board) {
		
		
		if (lastplayer != player) {
			Piece piece = board.getPieceByPosition(end.getPosition());
			return new SelectCommand(player, piece);
		}else if (player.getSelectedPiece() == null) {
			Piece piece = board.getPieceByPosition(end.getPosition());
			return new SelectCommand(player, piece);
		}else {
			if(lastCommand instanceof SelectCommand) {
				
				if(player.getSelectedPiece().getSquare().equals(end)) {
					Piece piece = board.getPieceByPosition(end.getPosition());
					System.out.println("DeSelect Command");
					return new SelectCommand(player, piece);
				}
				
				else if (player.getSelectedPiece().getAttackableSquares(board).contains(end)) {
					System.out.println("Attack Command");
					return new AttackCommand(player, end, board, modelGame);
				}else {
					System.out.println("Mov Command");
					return new MoveCommand(player, end, board,modelGame);
				}
			}
		}
		throw new IllegalAccessError();
		
	}

}
