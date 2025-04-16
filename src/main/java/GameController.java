package main.java;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class GameController extends Application{
	
	private GameView currentScene;
	private GameScreen gameScreen;
	private final MainMenuScreen mainMenuScreen;
	private final Game modelGame;
	private GameState currentState;
	private Stage mainStage;
	private Player currentPlayer;
	
	public GameController() {
		this.gameScreen = null;
		this.modelGame = new Game();
		this.mainMenuScreen = new MainMenuScreen();
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		mainStage = primaryStage;
		mainStage.getIcons().add(new Image("Img\\icon2.jpg"));
		mainStage.setTitle("Talabia Chess");
		mainStage.setMinHeight(500);
		mainStage.setMinWidth(700);
		
		setButtonCommand();
		mainStage.setScene(mainMenuScreen);
		mainStage.show();
	}
	
	private void updateChessSquaresCommand() {
		if (modelGame.getAvailableSquares() != null)
		for (Square square : modelGame.getAvailableSquares()) {
			int x = square.getPosition().getXInt();
			int y = square.getPosition().getYInt();
			String commandString = x +"," + y;
			gameScreen.chessSquares[x][y].setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent event) {
					
					modelGame.executeCommand(commandString);
					commandSendToModel();
				}
			});
		}
	}
	
	public void commandSendToModel() {
		updateGameScreen();
		updateChessSquaresCommand();
	}
	

	private void updateCurrentPlayerView() {
		System.out.println(currentPlayer);
		if (currentPlayer == null) {
			currentPlayer = modelGame.getCurrentPlayerObject();
			if (currentPlayer == modelGame.getPlayer2()) {
				gameScreen.rotateBoard();
			}
		}
		if (modelGame.getCurrentPlayerObject() != currentPlayer && currentPlayer != null) {
			this.currentPlayer = modelGame.getCurrentPlayerObject();
			gameScreen.rotateBoard();
		}else {
			currentPlayer = modelGame.getCurrentPlayerObject();
			if (currentPlayer == modelGame.getPlayer2()) {
				//gameScreen.rotateBoard();
			}
		}
		
		
	}
	
	public void updateGameScreen() {
		gameScreen.update();
		setGameScreenButtonCommand();
		updateChessSquaresCommand();
		updateCurrentPlayerView();
	}
	
	public void setButtonCommand() {
		mainMenuScreen.newGameButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				newGame();
				goToGameScreen();
				mainMenuScreen.showCNamePopup(mainStage);
			}
		});
		
		mainMenuScreen.loadGameButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				mainMenuScreen.showLoadPopup(mainStage);
				
			}
		});
		
		mainMenuScreen.loadButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				String loadString = FileManager.Load(mainMenuScreen.loadTextField.getText(), modelGame);
				mainMenuScreen.loadLabel.setText(loadString);
				if (loadString.equalsIgnoreCase("File loaded")) {
					goToGameScreen();
					mainMenuScreen.loadPopup.hide();
				}
			}
		});
		
		mainMenuScreen.changeNamePopup.setOnShowing(new EventHandler<WindowEvent>() {
			
			@Override
			public void handle(WindowEvent event) {
				gameScreen.base.setEffect(new GaussianBlur(10));
				gameScreen.base.setDisable(true);
			}
		});
		mainMenuScreen.changeNamePopup.setOnHiding(new EventHandler<WindowEvent>() {
			
			@Override
			public void handle(WindowEvent event) {
				gameScreen.base.setEffect(null);
				gameScreen.base.setDisable(false);
			}
		});
		
		mainMenuScreen.changeNameButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				modelGame.getPlayer1().setName(mainMenuScreen.player1TField.getText());
				modelGame.getPlayer2().setName(mainMenuScreen.player2TField.getText());
				mainMenuScreen.changeNamePopup.hide();
				gameScreen.updateText();
			}
		});
	}
	
	public void setGameScreenButtonCommand() {
		gameScreen.pauseButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				gameScreen.showPausePopup();
				
			}
		});
		
		gameScreen.resumeButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				gameScreen.pausePopup.hide();
				
			}
		});
		
		gameScreen.saveButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				gameScreen.showSavePopup();
				gameScreen.pausePopup.hide();
				gameScreen.base.setEffect(new GaussianBlur(10));
				gameScreen.base.setDisable(true);
			}
		});
		
		gameScreen.exitButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				mainStage.setScene(mainMenuScreen);
				gameScreen.pausePopup.hide();
				gameScreen.gameoverPopup.hide();
			}
		});
	}
	
	public void newGame() {
		currentPlayer=null;
		this.modelGame.reset();
	}
	
	public void goToGameScreen() {
		currentPlayer = null;
		this.gameScreen = new GameScreen(modelGame, mainStage);
		updateGameScreen();
		mainStage.setScene(gameScreen);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
