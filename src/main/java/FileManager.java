package main.java;
import java.io.*;
import java.util.*;

public class FileManager {

    String fileContent = "This is an example text file.";
    final public static String rootPath = System.getProperty("user.dir") + "\\SaveFiles\\";

    public static void Save(String name, Game game) {
    	
    	String filename = name + ".txt";
    	String filePath = rootPath + filename;
    	File gameFile = new File(filePath);
    	
    	
        Board board = game.getBoard();
        Player player1 = game.getPlayer1();
        Player player2 = game.getPlayer2();
        int turnCounter = Game.turnCounter;
        
        String gameString = "";
        gameString += board.toString();
        gameString += ",\n" + player1.getName() + "," + player1.getColour() + "," + player1.isTurn() ;
        gameString += ",\n" + player2.getName() + "," + player2.getColour() + "," + player2.isTurn() ;
        gameString += ",\n" + turnCounter;
        
        
        
        FileWriter writer;
		try {
			writer = new FileWriter(gameFile);
			writer.write(gameString);
	        writer.flush();
	        writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }

    public static String Load(String name, Game game){
    	String filename = name + ".txt";
    	String filePath = rootPath + filename;
    	File gameFile = new File(filePath);
    	
    	if (gameFile.isFile()) {
    		loadFile(gameFile, game);
    		return "File loaded";
    	}else {
    		return "File does not exist";
    	}
    }
    
    private static void loadFile(File file, Game game) {

    	File gameFile = file;
    	String boardString = null;
    	String player1String = null;
    	String player2String =null;
    	String turnCounter =null;
    	
    	BufferedReader reader;
    	int linecounter = 0;

		try {
			reader = new BufferedReader(new FileReader(gameFile));
			String line = reader.readLine();

			while (line != null) {
				if (linecounter == 1) {
					boardString = line.substring(0,line.length()-1);
				}else if(linecounter == 2) {
					player1String = line.substring(0,line.length()-1);
				}else if(linecounter == 3) {
					player2String = line.substring(0,line.length()-1);
				}else if(linecounter == 4) {
					turnCounter = line;
				}
				
				// read next line
				linecounter++;
				line = reader.readLine();
			}

			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// creating new player
		String[] player1args = player1String.split(",");
		String[] player2args = player2String.split(",");
		
		Player player1 = new Player(player1args);
		Player player2 = new Player(player2args);
		
		String[] pieceStrings = boardString.split("@");
		
		List<Piece> pieces = new ArrayList<>();
		
		for (String string : pieceStrings) {
			String[] pieceString = string.split("/");
			
			String classNameString = pieceString[0];
			String colourString = pieceString[1];
			String positionString = pieceString[2];
			
			Piece piece = null;
			
			if (classNameString.contentEquals("Sun")) piece = new Sun(colourString, positionString);
			else if (classNameString.contentEquals("Point")) piece = new Point(colourString, positionString);
			else if (classNameString.contentEquals("Hourglass")) piece = new Hourglass(colourString, positionString);
			else if (classNameString.contentEquals("Time")) {
				piece = new TimePlus(new Time(colourString, positionString));
			}
			else if (classNameString.contentEquals("Plus")) {
				piece = new TimePlus(new Plus(colourString, positionString));
			}
			
			pieces.add(piece);
		}
		
		Board board = new Board(pieces);
		
		int turnCounterI = Integer.parseInt(turnCounter);
		game.clear();
		game.load(board, player1, player2, turnCounterI);
    }
    
    public static void main(String[] args) {
		Load("testing_save", new Game());
	}
}
