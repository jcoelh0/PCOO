package entities;

import InformationCenter.Interpol;
import city.Store;
import java.awt.Point;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import path.Node;
import path.PathFinder;
import pt.ua.gboard.GBoard;
import pt.ua.gboard.Gelem;
import pt.ua.gboard.basic.ImageGelem;
import pt.ua.gboard.games.Labyrinth;

/**
 *
 * @author João Coelho
 */
public class Thief extends Thread implements Actions {

	Labyrinth labyrinth;
	protected GBoard board;
	private final Random rand = new Random();
	Gelem ge;
	private final char[] symbols;
	private final Interpol interpol;
	private boolean caught;
	private Point currentPos;
	private final PathFinder path;
	private final Point[] storeLocations;
	private final Store store;
	private boolean goingToPrison = false;
	private int id;
	
	public Thief(Labyrinth labyrinth, char[] symbols, Interpol interpol, int[][] blocksArray, Point[] storeLocations, Store store, int id) {
		this.labyrinth = labyrinth;
		this.symbols = symbols;
		board = labyrinth.board;
		this.interpol = interpol;
		path = new PathFinder(labyrinth, symbols, blocksArray);
		this.storeLocations = storeLocations;
		this.store = store;
		this.id = id;
	}

	@Override
	public void run() {

		
		ge = new ImageGelem("src\\entities\\burglar2.png", board, 100);
		
		int l = getSafeHousePosition().x;
		int c = getSafeHousePosition().y;
		
		
		board.draw(ge, l, c, 1);
		
		int i = 0;

		int lin = l;
		int col = c;

		
		int timeUntilRobberyStarts = rand.nextInt(100);
		
		while (true) {
			
			currentPos = randomWalking(lin, col);
			
			lin = currentPos.x;
			col = currentPos.y;
			i++;
			
			if(i>timeUntilRobberyStarts){
				List<Node> astar = path.getGPSPositions(currentPos,decideWhichStoreToRob());
				
				
				currentPos = goToPosition(currentPos.x, currentPos.y, astar);
				break;
				
			}
		}
		robbing(currentPos);
		goToSafeHouse(currentPos);
		
		if(caught){
			interpol.safe(id);
			goToPrison();
			System.err.println("Thief "+ id + "caught!");
		}else{
			interpol.safe(id);
			System.err.println("Thief "+ id + "escaped to safe house!");
		}
		
		
	}
	
	public Point goToPosition(int currentLine, int currentColumn, List<Node> positions) {
		assert positions != null;
		
		for (Node node : positions) {
            // get line and col from positions
            if(!moveToPosition(currentLine, currentColumn, node.getRow(), node.getCol()))
				break;
			
			currentLine = node.getRow();
			currentColumn = node.getCol();
			
			
        }
        return new Point(currentLine, currentColumn);
    }
	
	public boolean moveToPosition(int currentLine, int currentColumn, int line, int column) {
		
		if(goingToPrison)
			GBoard.sleep(100);
		else
			GBoard.sleep(100);
		
		interpol.setThiefPosition(line, column, id);
		
		if(interpol.policeFoundThief(id) && !goingToPrison){
			//System.err.println("thief not moving");
			caught = true;
			//goToPrison();
			return false;
		}
		
		board.move(ge, currentLine, currentColumn, 1, line, column, 1);
		currentPos.x = line;
		currentPos.y = column;
		
		
        return true;
    }
	
	@Override
	public Point randomWalking(int lin, int col) {

		assert labyrinth.isRoad(lin, col);

		Point pos = new Point();

		int[][] options = new int[4][2];

		options[0][0] = lin;
		options[0][1] = col + 1;

		options[1][0] = lin + 1;
		options[1][1] = col;

		options[2][0] = lin;
		options[2][1] = col - 1;

		options[3][0] = lin - 1;
		options[3][1] = col;

		int n;

		while (true) {
			n = rand.nextInt(4);
			if (!labyrinth.isWall(options[n][0], options[n][1])) {

				break;
			}
		}
		
		GBoard.sleep(100);
		board.move(ge, lin, col, 1, options[n][0], options[n][1], 1);

		switch (n) {
			case 0:
				col = col + 1;
				break;
			case 1:
				lin = lin + 1;
				break;
			case 2:
				col = col - 1;
				break;
			case 3:
				lin = lin - 1;
				break;
			default:
				break;
		}
		

		pos.x = lin;
		pos.y = col;

		return pos;
	}

	private void robbing(Point currentPos){
		
		int timeRobbing = rand.nextInt(1000)+500;
		
		try {
			Thread.sleep(timeRobbing);
		} catch (InterruptedException ex) {
			Logger.getLogger(Thief.class.getName()).log(Level.SEVERE, null, ex);
			System.err.println("Error waiting - robbing()");
			System.exit(1);
		}
		
		store.robItem(currentPos, id);
		
	}
	
	private void goToSafeHouse(Point currentPos){
			
		List positions = path.getGPSPositions( new Point(currentPos.x, currentPos.y), getSafeHousePosition());
			
		Point newPos = goToPosition(currentPos.x, currentPos.y, positions);
			
		currentPos.x = newPos.x;
		currentPos.y = newPos.y;
	}
	
	private Point decideWhichStoreToRob(){
		return storeLocations[rand.nextInt(storeLocations.length)];
	}
	
	private Point getSafeHousePosition(){
		int x = labyrinth.symbolPositions(symbols[1])[0].line();
		int y = labyrinth.symbolPositions(symbols[1])[0].column();
		return new Point(x,y);
	}
	
	public Point getThiefCurrentPosition(){
		return currentPos;
	}
	
	private Point getPrisonPosition(){
		int x = labyrinth.symbolPositions(symbols[0])[0].line();
		int y = labyrinth.symbolPositions(symbols[0])[0].column();
		return new Point(x,y);
	}
	
	private void goToPrison(){
		goingToPrison = true;
		//System.err.println("goTOPrision");
		List positions = path.getGPSPositions( new Point(currentPos.x, currentPos.y), getPrisonPosition());
			
		currentPos = goToPosition(currentPos.x, currentPos.y, positions);
			
	}
}
