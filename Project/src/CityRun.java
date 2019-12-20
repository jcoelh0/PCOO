import InformationCenter.Interpol;
import city.Map;
import city.Store;
import entities.Helicopter;
import entities.Police;
import entities.Thief;
import java.awt.Color;
import java.awt.Point;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import pt.ua.gboard.Gelem;
import pt.ua.gboard.basic.StringGelem;
import pt.ua.gboard.games.Labyrinth;

/**
 *
 * @author João Coelho
 */
public class CityRun {

	public static void main(String[] args) {
		
		int numberOfThiefs = 7;
		int numberOfPolice = 10;
		
		Point[] points = new Point[4];
		
		points[0] = new Point(18,22); //prison
		points[1] = new Point(17,1); //safe house
		points[2] = new Point(4,22); //store1
		points[3] = new Point(9,19); //store2

		char prisonSymbol = 'P';
		char store1 = '1';
		char store2 = '2';
		char thiefHome = 'T';
		
		char[] symbols = {	
			prisonSymbol,
			store1,
			store2,
			thiefHome
		};
		
		
		
		Gelem[] gelems = {
			new StringGelem("" + prisonSymbol, Color.blue, 1, 1),
			new StringGelem("" + thiefHome, Color.orange, 1, 1),
			new StringGelem("" + store1, Color.green, 1, 1),
			new StringGelem("" + store2, Color.green, 1, 1)
		};
	
		
		
		Map map = new Map(symbols, gelems, points);
		
		Labyrinth labyrinth = map.getLabyrinth();
		
		Interpol interpol = new Interpol(numberOfThiefs, map.getPrisonPosition());
		
		Store store = new Store(interpol);
		
		List<int[]> blocksArrayList = map.getBlocksArray();
		int[][] blocksArray = new int[blocksArrayList.size()][];
		
		for (int i = 0; i < blocksArrayList.size(); i++) {
			blocksArray[i]= blocksArrayList.get(i);
		}
		
		
		//Creation of threads
		Police[] pol = new Police[numberOfPolice];
		Thief[] thf = new Thief[numberOfThiefs];
		for (int i = 0; i < pol.length; i++) {
			pol[i] = new Police(labyrinth, symbols, interpol, blocksArray, map.getPrisonPosition(), i);	
		}
		
		for (int i = 0; i < thf.length; i++) {
			thf[i] = new Thief(labyrinth, symbols, interpol, blocksArray, map.getStorePositions(), store, i);
		}
		Helicopter heli = new Helicopter(thf, interpol);
		
		
		//Start threads
		for (Police pol1 : pol) {
			pol1.start();
		}
		for (Thief thf1 : thf) {
			thf1.start();
		}
		heli.start();
		
		//Wait until threads die naturally
		try {
			for (int i = 0; i < pol.length; i++) {
				pol[i].join();
				
				System.err.println("Police " + i + " died!");
			}	
			for (int i = 0; i < thf.length; i++) {
				thf[i].join();
				System.err.println("Thief "+i+" died!");	
			}
			heli.join();
			System.err.println("Heli died!");
		} catch (InterruptedException ex) {
			//exception not handled as it's not necessary in this case
			//Logger.getLogger(CityRun.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		//map.terminate();
		//to eventually terminate the map
	}
}
