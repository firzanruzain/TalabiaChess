package main.java;

import java.util.List;

import javafx.animation.RotateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

public class GameScreen extends GameView{
	
	public final BorderPane base;
	public final GridPane boardBase;
	public final Button[][] chessSquares;
	public final Text currentPlayerText;
	public final Text nextPlayerText;
	public final Button pauseButton;
	public List<Square> selectableSquares;
	private Game modelGame;
	private double boardScale = 1;
	public final Popup gameoverPopup;
	private final Popup checkPopup;
	private final Popup savePopup;
	private final Popup loadPopup;
	private final Stage mainStage;
	public final Popup pausePopup;
	public  Button saveButton;
	public  Button resumeButton;
	public  Button exitButton;
	public Button loadButton;
	public TextField loadTextField;
	public Label loadLabel;
	public final Text turncounterText;
	public Label turncounterL = new Label();
	
	public GameScreen(Game modelGame, Stage stage) {
		super(new BorderPane(),850,650);
		this.modelGame = modelGame;
		this.base =(BorderPane) getRoot();
		this.boardBase = new GridPane();
		this.chessSquares = new Button[6][7];
		this.currentPlayerText = new Text();
		this.nextPlayerText = new Text();
		this.pauseButton = new Button();
		this.gameoverPopup = new Popup();
		this.checkPopup = new Popup();
		this.savePopup = new Popup();
		this.loadPopup = new Popup();
		this.pausePopup = new Popup();
		this.turncounterText = new Text("Turn: ");
		this.mainStage = stage;
		init();
	}

	@Override
	public void render() {
		drawText();
		drawPauseButton();
		drawBoard();
		
		if (modelGame.getWinner() != null) {
			showGameOverPopup();
		}
	}
	
	private void drawBoard() {
		boardBase.setId("boardBase");
		
		boardBase.setAlignment(Pos.CENTER);
		drawSquares();
		drawPieces();
		
		
		base.setCenter(boardBase);
	}
	
	public void rotateBoard() {
		RotateTransition rotate = new RotateTransition();  
        
        //Setting Axis of rotation   
        rotate.setAxis(Rotate.Z_AXIS);  
        // setting the angle of rotation   
        rotate.setByAngle(180);  
        rotate.setDuration(Duration.millis(30));
        rotate.setNode(boardBase);
        rotate.play();
        
		//boardScale = boardScale*-1;
		boardBase.setScaleY(boardScale);
	}
	
	private void drawPieces() {
		List<Piece> pieces = modelGame.getBoard().getPieces();
		for (Piece piece: pieces) {
			int x = piece.getSquare().getPosition().getXInt();
			int y = piece.getSquare().getPosition().getYInt();
			
			String colourString = piece.getColour().toString();
			String pieceTypeString = piece.getClassName();
			
			
			String piecePath = "/Img/" + colourString + "/" + pieceTypeString + ".png";
			
			Image pieceImage = new Image(getClass().getResourceAsStream(piecePath));
			//Image pieceImage = new Image(piecePath);
			ImageView img = new ImageView(pieceImage);
			
			DropShadow drop = new DropShadow();  
	        drop.setBlurType(BlurType.GAUSSIAN);  
	        drop.setColor(Color.BLACK);  
	        drop.setHeight(100);  
	        drop.setWidth(150);    
	        drop.setSpread(0.2);  
	        drop.setRadius(8);  
	        
	        /*if (piece instanceof Sun) {
	        	if (modelGame.getBoard().isInCheck((Sun) piece)) {
	        		ColorAdjust colorAdjust= new ColorAdjust();
	        		colorAdjust.setContrast(30);
	        		drop.setInput(colorAdjust);
	        	}
	        }*/
	        
	        
	        
	        img.setEffect(drop);
			
			if (piece instanceof Point) {
				if (((Point) piece).isReversed) {
					img.setScaleY(-1);
				}
			}
			
			img.setPreserveRatio(true);
			img.setFitWidth(60);
			img.setFitHeight(60);
			
			
			chessSquares[x][y].setGraphic(img);
		}
	}
	
	private void drawSquares() {
		boolean light = true;
		
		for (int i = 0; i < 6; i++) {
			if (i%2 != 0) {
				light = false;
			}
            for (int j = 0; j < 7; j++) {
            	
                Button b = new Button();
                b.setPrefWidth(100);
                b.setPrefHeight(100);
                
                Square square = new Square(new Position(i, j));
                if (selectableSquares != null) {
                if (selectableSquares.contains(square)) {
                	b.getStyleClass().add("selectableSquares");
                }}
                
                if(light) {
                	b.getStyleClass().add("lightSquare");
                }else {
                	b.getStyleClass().add("darkSquare");
                }
                
                chessSquares[i][j] = b;
                boardBase.add(b, j, i);
                light = !light;
            }
        }
	}
	
	public void drawPauseButton() {
		pauseButton.setText("Pause");
		
		BorderPane.setMargin(pauseButton, new Insets(5, 5, 5, 5));
		base.setRight(pauseButton);
	}
	
	public void drawText() {
		
		nextPlayerText.setFont(new Font(30));
		currentPlayerText.setFont(new Font(60));
		turncounterText.setFont(new Font(30));
		turncounterL.setFont(new Font(60));
		
		BorderPane.setAlignment(nextPlayerText, Pos.BOTTOM_CENTER);
		BorderPane.setAlignment(currentPlayerText, Pos.TOP_CENTER);
		
		BorderPane.setMargin(nextPlayerText, new Insets(5));
		BorderPane.setMargin(currentPlayerText, new Insets(5));
		base.setTop(nextPlayerText);
		base.setBottom(currentPlayerText);
		
		VBox turnCounterBox = new VBox(10);
		turncounterL.setText(String.valueOf(Game.turnCounter));
		turnCounterBox.setAlignment(Pos.CENTER);
		
		turnCounterBox.getChildren().addAll(turncounterText, turncounterL);
		
		base.setLeft(turnCounterBox);
	}
	
	private void showGameOverPopup() {
		Text titleText = new Text("Game Over");
		Text winnerText = new Text(modelGame.getWinner().getName() + " Wins");
		
		titleText.setFont(new Font(30));
		winnerText.setFont(new Font(30));
		VBox textVBox = new VBox();
		
		textVBox.getChildren().addAll(titleText, winnerText,exitButton);
		textVBox.setAlignment(Pos.CENTER);
		
		
		
		gameoverPopup.getContent().add(textVBox);
		drawPopup(gameoverPopup);
		gameoverPopup.show(mainStage);
	}
	
	
	private void drawPausePopup() {
		Text titleText = new Text("Pause");
		
		resumeButton = new Button("Resume");
		saveButton = new Button("Save");
		exitButton = new Button("Exit");
		
		VBox pauseBox = new VBox(10);
		pauseBox.getChildren().addAll(resumeButton, saveButton, exitButton);
		
		resumeButton.setMinWidth(100);
		saveButton.setMinWidth(100);
		exitButton.setMinWidth(100);
		
		pausePopup.getContent().add(pauseBox);
		drawPopup(pausePopup);
	}
	
	public void showPausePopup() {
		pausePopup.show(mainStage);
	}
	
	private void drawSavePopup() {
		Text titleText = new Text("Save Game");
		Button pSaveButton = new Button("Save");
		
		Label saveLabel = new Label("Enter New File Name");
		TextField saveTextField = new TextField();
		
		pSaveButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				FileManager.Save(saveTextField.getText(), modelGame);
				savePopup.hide();
			}
		});
		
		VBox saveBox = new VBox(10);
		saveBox.setAlignment(Pos.CENTER);
		
		saveBox.getChildren().addAll(titleText, saveLabel, saveTextField, pSaveButton);
		savePopup.getContent().add(saveBox);
		
		drawPopup(savePopup);
	}
	
	public void showSavePopup() {
		savePopup.show(mainStage);
	}
	
	private void drawPopup(Popup popup) {
		popup.setOnShowing(new EventHandler<WindowEvent>() {
			
			@Override
			public void handle(WindowEvent event) {
				base.setEffect(new GaussianBlur(10));
				base.setDisable(true);
			}
		});
		
		popup.setOnHiding(new EventHandler<WindowEvent>() {
			
			@Override
			public void handle(WindowEvent event) {
				base.setEffect(null);
				base.setDisable(false);
				
			}
		});
	}
	
	public void setModelGame(Game game) {
		this.modelGame = game;
	}
	
	public void updateText() {
		String currentPlayerString = modelGame.getCurrentPlayerObject().getName();
		String nextPlayerString = modelGame.getNextPlayerObject().getName();
		
		currentPlayerText.setText(currentPlayerString);
		nextPlayerText.setText(nextPlayerString);
	}
	
	private void updateSelectable() {
		selectableSquares = modelGame.getAvailableSquares();
	}
	
	
	
	public void update() {
		updateText();
		updateSelectable();
		render();
	}
	
	public void init() {
		getStylesheets().add(getClass().getResource("/GameScreen.css").toExternalForm());
		drawSavePopup();
		drawPausePopup();
		update();
	}
	
}
