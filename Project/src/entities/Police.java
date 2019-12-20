/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import InformationCenter.Interpol;
import java.awt.Point;
import static java.lang.System.out;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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
public class Police extends Thread implements Actions {

	private boolean[] thiefFound;
	Labyrinth labyrinth;
	protected GBoard board;
	Random rand = new Random();
	Gelem ge;
	private Interpol interpol;
	private PathFinder path;
	private Point prison;
	private Point currentPos;
	private boolean thiefCaught = false;
	private int index;
	private boolean arrivedToPrison;
	private int id;

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

		//Gelem ge = new ImageGelem(Color.red, 90, N, N);
		ge = new ImageGelem("src\\entities\\police.png", board, 100);
		currentPos.x = prison.x;
		currentPos.y  = prison.y;

		
		board.draw(ge, currentPos.x, currentPos.y, 1);
		
		boolean thiefsFound;
		
		LinkedList<Integer> thiefs = new LinkedList<Integer>();
		
		for (int i = 0; i < interpol.getNumberOfThiefs(); i++) {
			thiefs.add(i);
		}
		
		while(interpol.getNumberOfThiefs() != 0){
			
			int idx = interpol.waitingForCrime();
			
			if(idx == -1)
				break;
			
			thiefsFound = false;
			
			
			//while (!thiefFound[idx]) {

				
				
			catchThief(idx);
			
			for (int i = 0; i < thiefFound.length; i++) {
				thiefsFound = thiefsFound || thiefFound[i];
			}
			
			goToPrison(thiefsFound);
			
			if(thiefsFound){
				thiefCaught = false;
				System.err.println("Police "+ id + "caught thief " + idx + "!");
			}
			
		}
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
		//System.out.println("lin:"+lin+",col:"+col);
		//System.out.println("lin:"+options[n][0]+",col:"+options[n][1]);
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
		GBoard.sleep(100);

		pos.x = lin;
		pos.y = col;

		return pos;
	}

	private void catchThief(int id) {
		
		
		while (interpol.thiefFound(id) && interpol.getNumberOfThiefs() != 0 ) { ///FIX THIS
			Point thiefPos = interpol.getThiefPosition(id);
			System.err.println("looooop");
			
			List<Node> positions = path.getGPSPositions(currentPos, thiefPos);
			
			currentPos = goToPosition(currentPos.x, currentPos.y, positions);
		}
		arrivedToPrison = false;
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
		
		GBoard.sleep(100);
		
		int thiefIdx = interpol.PoliceFoundThief(currentLine, currentColumn);
		if(thiefIdx != -1 && !thiefCaught){
            
			System.err.println("Cop found Thief!");
			thiefFound[thiefIdx] = true;
			index = thiefIdx;
			//thiefCaught = true;
			//while(true);
			return false;
		}
		
		board.move(ge, currentLine, currentColumn, 1, line, column, 1);
		
		
		
		return true;
	}

	private void goToPrison(boolean caught) {
		
		if(caught)
			thiefCaught = true;
		arrivedToPrison = true;
		List<Node> positions = path.getGPSPositions(currentPos, prison);
			
		currentPos = goToPosition(currentPos.x, currentPos.y, positions);
		
	}
	
	

}
