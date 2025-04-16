package main.java;

import javafx.scene.Parent;
import javafx.scene.Scene;

public abstract class GameView extends Scene{
	
	public GameView(Parent root, double Width, double Height) {
		super(root, Width, Height);
	}

	public abstract void render();
	
	public abstract void update();
}
