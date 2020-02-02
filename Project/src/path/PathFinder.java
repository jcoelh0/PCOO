package path;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import java.util.Map;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;


import pt.ua.gboard.GBoard;
import pt.ua.gboard.games.Labyrinth;

/**
 *
 * @author João Coelho
 */
public class PathFinder {

	static public int pause = 0;
	private static Point endPosition;
	private static Labyrinth labyrinth;
	private final int[][] blocksArray;
	
	static char prisonSymbol;
	static char hindingPlaceSymbol;
	static char passerbyHouseSymbol;
	static char itemToStealSymbol;
	static char actualPositionSymbol;

	/**
	 *
	 * @param labyrinth labyrinth of map
	 * @param extraSymbols symbols in the map
	 * @param blocksArray array of points without road
	 */
	public PathFinder(Labyrinth labyrinth, char[] extraSymbols, int[][] blocksArray) {
		this.labyrinth = labyrinth;

		prisonSymbol = extraSymbols[0];
		itemToStealSymbol = extraSymbols[1];
		this.blocksArray = blocksArray;
	}

	/**
	 * getGPSPositions
	 *
	 * @param endPoint point that ends
	 * @param startPoint start point
	 * @return a list
	 */
	public List getGPSPositions(Point startPoint, Point endPoint) {
		
		endPosition = endPoint;
		
		Node initialNode = new Node(startPoint.x, startPoint.y);
        Node finalNode = new Node(endPoint.x, endPoint.y);
        int rows = labyrinth.numberOfLines;
        int cols = labyrinth.numberOfColumns;
        AStar aStar = new AStar(rows, cols, initialNode, finalNode);
		
		aStar.setBlocks(blocksArray);
		
        List<Node> path = aStar.findPath();
        /*for (Node node : path) {
            System.out.println(node);
        }*/

		return path;
	}
	
	

	/**
	 * isSymbolPosition
	 *
	 * @param lin
	 * @param col
	 * @return
	 */
	static boolean isSymbolPosition(int lin, int col) {
		assert labyrinth.isRoad(lin, col);

		return labyrinth.roadSymbol(lin, col) == itemToStealSymbol
			|| labyrinth.roadSymbol(lin, col) == hindingPlaceSymbol
			|| labyrinth.roadSymbol(lin, col) == prisonSymbol
			|| labyrinth.roadSymbol(lin, col) == passerbyHouseSymbol;
	}

	/**
	 * freePosition
	 *
	 * @param lin
	 * @param col
	 * @param markedPositions
	 * @return
	 */
	static boolean freePosition(int lin, int col, Map markedPositions) {
		assert labyrinth.isRoad(lin, col);

		if (markedPositions.containsKey(String.valueOf(lin) + "_" + String.valueOf(col))) {
			return false;
		}

		return labyrinth.roadSymbol(lin, col) == ' '
			|| isSymbolPosition(lin, col);
	}

	/**
	 * markPosition
	 *
	 * @param lin
	 * @param col
	 * @param color
	 */
	static void markPosition(int lin, int col, Color color) {
		assert labyrinth.isRoad(lin, col);

		GBoard.sleep(pause);
	}

	/**
	 * clearPosition
	 *
	 * @param lin
	 * @param col
	 * @param markedPositions
	 */
	static void clearPosition(int lin, int col, Map markedPositions) {
		assert labyrinth.isRoad(lin, col);

		markedPositions.remove(String.valueOf(lin) + "_" + String.valueOf(col));

		if (isSymbolPosition(lin, col)) {
			//maze.putRoadSymbol(lin, col, hindingPlaceSymbol);
		} else {
			//maze.putRoadSymbol(lin, col, ' ');
			// maze.board.erase(lin, col, 1, 1);
		}
		GBoard.sleep(pause);
	}

	/**
	 * unmarkPosition
	 *
	 * @param lin
	 * @param col
	 * @param markedPositions
	 */
	static void unmarkPosition(int lin, int col, Map markedPositions) {
		assert labyrinth.isRoad(lin, col);

		//markedPositions.remove(String.valueOf(lin) + "_" + String.valueOf(col));
		if (!isSymbolPosition(lin, col)) {
			//maze.putRoadSymbol(lin, col, ' ');
			//maze.board.erase(lin, col, 1, 1);
		}
		GBoard.sleep(pause);
	}

	/**
	 *
	 * @param <K>
	 * @param <V>
	 * @param map
	 * @return
	 */
	static <K, V extends Comparable<? super V>>
		SortedSet<Map.Entry<K, V>> entriesSortedByValues(Map<K, V> map) {
		SortedSet<Map.Entry<K, V>> sortedEntries = new TreeSet<Map.Entry<K, V>>(
			new Comparator<Map.Entry<K, V>>() {
			@Override
			public int compare(Map.Entry<K, V> e1, Map.Entry<K, V> e2) {
				int res = e1.getValue().compareTo(e2.getValue());
				return res != 0 ? res : 1;
			}
		}
		);
		sortedEntries.addAll(map.entrySet());
		return sortedEntries;
	}
	
}
