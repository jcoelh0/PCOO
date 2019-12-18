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

	private boolean thiefFound = false;
	Labyrinth labyrinth;
	protected GBoard board;
	Random rand = new Random();
	Gelem ge;
	private Interpol interpol;
	private PathFinder path;
	private Point prison;
	private Point currentPos;
	private boolean thiefCaught = false;

	public Police(Labyrinth labyrinth, char[] symbols, Interpol interpol, int[][] blocksArray, Point prison) {
		this.labyrinth = labyrinth;
		board = labyrinth.board;
		this.interpol = interpol;
		path = new PathFinder(labyrinth, symbols, blocksArray);
		this.prison = prison;
		currentPos = new Point();
	}

	@Override
	public void run() {

		//Gelem ge = new ImageGelem(Color.red, 90, N, N);
		ge = new ImageGelem("src\\entities\\police.png", board, 100);
		currentPos.x = prison.x;//board.numberOfLines() / 2;
		currentPos.y  = prison.y;//board.numberOfColumns() / 2;

		
		board.draw(ge, currentPos.x, currentPos.y, 1);

		while (!thiefFound) {

			if(!interpol.waitingForCrime())
				break;
			
			
			catchThief();
		}
		if(thiefFound)
			bringThiefToPrison();
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

	private void catchThief() {
		while (!thiefFound) {
			Point thiefPos = interpol.getThiefPosition();
			
			List<Node> positions = path.getGPSPositions(currentPos, thiefPos);
			
			currentPos = goToPosition(currentPos.x, currentPos.y, positions);
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
		
		GBoard.sleep(100);
		
		if(interpol.PoliceFoundThief(currentLine, currentColumn) && !thiefCaught){
            System.err.println("Cop found Thief!");
			thiefFound = true;
			//while(true);
			return false;
		}
		
		board.move(ge, currentLine, currentColumn, 1, line, column, 1);
		
		
		
		return true;
	}

	private void bringThiefToPrison() {
		
		thiefCaught = true;
		List<Node> positions = path.getGPSPositions(currentPos, prison);
			
		currentPos = goToPosition(currentPos.x, currentPos.y, positions);
		
	}

}
