package entities;

import InformationCenter.Interpol;
import java.awt.Point;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
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
public class Police extends Thread implements Person {

	private final boolean[] thiefFound;
	Labyrinth labyrinth;
	protected GBoard board;
	Random rand = new Random();
	Gelem ge;
	private final Interpol interpol;
	private final PathFinder path;
	private final Point prison;
	private Point currentPos;
	private boolean thiefCaught = false;
	private final int id;

	public Police(Labyrinth labyrinth, char[] symbols, Interpol interpol, int[][] blocksArray, Point prison, int id) {
		this.labyrinth = labyrinth;
		board = labyrinth.board;
		this.interpol = interpol;
		path = new PathFinder(labyrinth, symbols, blocksArray);
		this.prison = prison;
		currentPos = new Point();
		thiefFound = new boolean[interpol.getNumberOfThiefs()];
		for (int i = 0; i < thiefFound.length; i++) {
			thiefFound[i] = false;
		}
		this.id = id;
	}

	@Override
	public void run() {

		ge = new ImageGelem("src\\entities\\police.png", board, 100);
		currentPos.x = prison.x;
		currentPos.y  = prison.y;

		board.draw(ge, currentPos.x, currentPos.y, 1);
		
		boolean thiefsFound;
		
		while(interpol.getNumberOfThiefs() != 0){
			
			int idx = interpol.waitingForCrime();
			
			if(idx == -1)
				break;
			
			thiefsFound = false;
						
			catchThief(idx);
			
			for (int i = 0; i < thiefFound.length; i++) {
				thiefsFound = thiefsFound || thiefFound[i];
			}
			
			goToPrison(thiefsFound);
			
			if(thiefsFound){
				thiefCaught = false;
			}
			
		}
	}


	private void catchThief(int id) {
		
		
		while (interpol.thiefFound(id) && interpol.getNumberOfThiefs() != 0 ) { 
			Point thiefPos = interpol.getThiefPosition(id);
			
			List<Node> positions = path.getGPSPositions(currentPos, thiefPos);
			
			currentPos = goToPosition(currentPos.x, currentPos.y, positions);
		}
	}

	@Override
	public Point goToPosition(int currentLine, int currentColumn, List<Node> positions) {
		assert positions != null;
		
		
		for (Node node : positions) {
			
            if(!moveToPosition(currentLine, currentColumn, node.getRow(), node.getCol()))
				break;
			
			currentLine = node.getRow();
			currentColumn = node.getCol();
			
        }
        return new Point(currentLine, currentColumn);
    }

	@Override
	public boolean moveToPosition(int currentLine, int currentColumn, int line, int column) {
		
		GBoard.sleep(100);
		
		int thiefIdx = interpol.PoliceFoundThief(currentLine, currentColumn);
		if(thiefIdx != -1 && !thiefCaught){
            
			thiefFound[thiefIdx] = true;
			
			return false;
		}
		
		board.move(ge, currentLine, currentColumn, 1, line, column, 1);
		
		
		
		return true;
	}

	private void goToPrison(boolean caught) {
		
		if(caught)
			thiefCaught = true;
		List<Node> positions = path.getGPSPositions(currentPos, prison);
			
		currentPos = goToPosition(currentPos.x, currentPos.y, positions);
		
	}
}
