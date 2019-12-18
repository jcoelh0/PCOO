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
	private Map markedPositions;
	private Map finalMarkedPositions;
	private static Labyrinth labyrinth;
	private int[][] blocksArray;
	
	static char prisonSymbol;
	static char hindingPlaceSymbol;
	static char passerbyHouseSymbol;
	static char itemToStealSymbol;
	static char actualPositionSymbol;

	/**
	 *
	 * @param labyrinth
	 * @param extraSymbols
	 */
	public PathFinder(Labyrinth labyrinth, char[] extraSymbols, int[][] blocksArray) {
		markedPositions = new TreeMap<>();
		this.labyrinth = labyrinth;

		prisonSymbol = extraSymbols[0];
		itemToStealSymbol = extraSymbols[1];
		this.blocksArray = blocksArray;
	}

	/**
	 * getGPSPositions
	 *
	 * @param endPoint
	 * @param startPoint
	 * @return
	 */
	public List getGPSPositions(Point startPoint, Point endPoint) {
		
		endPosition = endPoint;
		
		Node initialNode = new Node(startPoint.x, startPoint.y);
        Node finalNode = new Node(endPoint.x, endPoint.y);
        int rows = 24;
        int cols = 24;
        AStar aStar = new AStar(rows, cols, initialNode, finalNode);
		
		aStar.setBlocks(blocksArray);
		
        List<Node> path = aStar.findPath();
        /*for (Node node : path) {
            System.out.println(node);
        }*/

		return path;
	}
	
	
	/**
	 * Backtracking path search algorithm
	 *
	 * @param distance
	 * @param lin
	 * @param col
	 * @param markedPositions
	 * @param color
	 * @return
	 */
	private boolean searchPath(int distance, int lin, int col, Map markedPositions, Color color) {

		boolean result = false;

		if (labyrinth.validPosition(lin, col) && labyrinth.isRoad(lin, col)) {
			if (lin == endPosition.y && col == endPosition.x) {

				unmarkPosition(lin, col, markedPositions);

				
				result = true;

				finalMarkedPositions = new HashMap<>(markedPositions);

			} else if (freePosition(lin, col, markedPositions)) {
				markPosition(lin, col, color);

				markedPositions.put(String.valueOf(lin) + "_" + String.valueOf(col), markedPositions.size());
				unmarkPosition(lin, col, markedPositions);

				if (searchPath(distance + 1, lin - 1, col, markedPositions, color)) // North
				{
					result = true;
				} else if (searchPath(distance + 1, lin, col + 1, markedPositions, color)) // East
				{
					result = true;
				} else if (searchPath(distance + 1, lin, col - 1, markedPositions, color)) // West
				{
					result = true;
				} else if (searchPath(distance + 1, lin + 1, col, markedPositions, color)) // South
				{
					result = true;
				} else {
					markPosition(lin, col, color);
					markedPositions.put(String.valueOf(lin) + "_" + String.valueOf(col), markedPositions.size());
					unmarkPosition(lin, col, markedPositions);
				}

				//GBoard.sleep(1);
				clearPosition(lin, col, markedPositions);

			}
		}

		return result;
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
