package main.java;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class MainMenuScreen extends GameView{

	public final Text titleText;
	public final Button newGameButton;
	public final Button loadGameButton;
	public final BorderPane base;

	public final Popup loadPopup;
	public Button loadButton;
	public TextField loadTextField;
	public Label loadLabel;
	public final Popup changeNamePopup;
	public Button changeNameButton;
	TextField player1TField = new TextField("Player 1");
	TextField player2TField = new TextField("Player 2");
	
	public MainMenuScreen() {
		super(new BorderPane(),850,650);
		this.titleText = new Text("Talabia Chess");
		this.newGameButton = new Button("New");
		this.loadGameButton = new Button("Load");
		this.base = (BorderPane) getRoot();
		this.loadPopup = new Popup();
		this.changeNamePopup = new Popup();
		init();
	}

	@Override
	public void render() {
		titleText.setFont(Font.font("Abyssinica SIL", FontWeight.BOLD, FontPosture.REGULAR, 100));
		titleText.setFill(Color.BLACK);
		
		Text talabiaText = new Text("Talabia");
		Text chessText = new Text("Chess");
		
		talabiaText.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 100));
		chessText.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 100));
		
		StackPane chessTPane = new StackPane();
		chessTPane.setStyle("-fx-background-color:rgb(247, 152, 23);-fx-background-radius:10;");
		StackPane.setMargin(chessText, new Insets(15));
		
		chessTPane.getChildren().add(chessText);
		
		HBox titleBox = new HBox(10);
		titleBox.getChildren().addAll(talabiaText, chessTPane);
		titleBox.setAlignment(Pos.CENTER);
		
		HBox buttonBox = new HBox();
		buttonBox.getChildren().addAll(newGameButton, loadGameButton);
		buttonBox.setAlignment(Pos.CENTER);
		HBox.setMargin(newGameButton,new Insets(5,5,5,5));
		HBox.setMargin(loadGameButton,new Insets(5,5,5,5));
		
		VBox centerBox = new VBox();
		centerBox.getChildren().addAll(titleBox, buttonBox);
		centerBox.setAlignment(Pos.CENTER);
		centerBox.setSpacing(10);
		
		base.setCenter(centerBox);
		base.setLeft(new Text());
		drawloadPopup();
		drawChangeNamePopup();
	}

	@Override
	public void update() {
		render();
	}
	
	public void init() {
		update();
	}
	
	private void drawChangeNamePopup() {
		Text titleText = new Text("Change Player Name");
		titleText.setTextAlignment(TextAlignment.CENTER);
		Text player1 =  new Text("Player 1");
		Text player2 =  new Text("Player 2");
		
		
		
		changeNameButton = new Button("Done");
		
		GridPane changeNPane = new GridPane();
		
		changeNPane.add(titleText, 0, 0);
		changeNPane.add(player1, 0, 1);
		changeNPane.add(player1TField, 1, 1);
		changeNPane.add(player2, 0, 2);
		changeNPane.add(player2TField, 1, 2);
		changeNPane.add(changeNameButton, 0, 3);
		
		GridPane.setColumnSpan(titleText, 3);
		GridPane.setColumnSpan(changeNameButton, 3);
		GridPane.setHalignment(titleText, HPos.CENTER);
		GridPane.setHalignment(changeNameButton, HPos.CENTER);
		GridPane.setMargin(titleText, new Insets(10));
		GridPane.setMargin(player1, new Insets(10));
		GridPane.setMargin(player2, new Insets(10));
		GridPane.setMargin(changeNameButton, new Insets(10));
		
		
		changeNamePopup.getContent().add(changeNPane);
	}
	
	private void drawloadPopup() {
		Text titleText = new Text("Load Game");
		
		loadLabel = new Label("Enter File Name");
		loadTextField = new TextField();
		

		loadButton = new Button("Load");
		loadButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
			}
		});
		
		
		VBox loadBox = new VBox(10);
		loadBox.setAlignment(Pos.CENTER);
		
		loadBox.getChildren().addAll(titleText, loadLabel, loadTextField, loadButton);
		loadBox.setPadding(new Insets(10));
		loadBox.setStyle("-fx-background-color:rgb(255, 255, 255);-fx-background-radius:10;-fx-border-color:Black;-fx-border-radius:10;");
		
		loadPopup.getContent().add(loadBox);
		
		drawPopup(loadPopup);
	}
	
	public void showCNamePopup(Stage stage) {
		changeNamePopup.show(stage);
	}
	
	public void showLoadPopup(Stage mainStage) {
		loadPopup.show(mainStage);
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
}

