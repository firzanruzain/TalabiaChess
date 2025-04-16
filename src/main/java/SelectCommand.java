package main.java;

public class SelectCommand implements PlayerCommand{
	private Player player;
	private Piece piece;
	
	public SelectCommand(Player player, Piece piece) {
		this.player = player;
		this.piece = piece;
	}

	@Override
	public void execute() {
		player.Select(piece);
		
	}
	

	}


