import InformationCenter.Interpol;
import city.Map;
import city.Store;
import entities.Helicopter;
import entities.Police;
import entities.Thief;
import java.awt.Color;
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
		
		int numberOfThiefs = 10;
		int numberOfPolice = 10;


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
	
		
		
		
		
		Map map = new Map(symbols, gelems);
		
		Labyrinth labyrinth = map.getLabyrinth();
		
		Interpol interpol = new Interpol(numberOfThiefs);
		
		Store store = new Store(interpol);
		
		List<int[]> blocksArrayList = map.getBlocksArray();
		
		int[][] blocksArray = new int[blocksArrayList.size()][];
		for (int i = 0; i < blocksArrayList.size(); i++) {
			blocksArray[i]= blocksArrayList.get(i);
		}
		
		Police[] pol = new Police[numberOfPolice];
		Thief[] thf = new Thief[numberOfThiefs];
		
		for (int i = 0; i < pol.length; i++) {
			pol[i] = new Police(labyrinth, symbols, interpol, blocksArray, map.getPrisonPosition());
			
		}
		
		for (int i = 0; i < thf.length; i++) {
			thf[i] = new Thief(labyrinth, symbols, interpol, blocksArray, map.getStorePositions(), store, i);
		}
		
		
		
		for (int i = 0; i < pol.length; i++) {
			pol[i].start();
		}
		
		for (int i = 0; i < thf.length; i++) {
			thf[i].start();
		}
		
		Helicopter heli = new Helicopter(thf, interpol);
		
		heli.start();
		
		
		try {
			for (int i = 0; i < pol.length; i++) {
				pol[i].join();
				
				System.err.println("Police " + i + " died!");
			}	
		} catch (InterruptedException ex) {
			//Logger.getLogger(CityRun.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		try {
			for (int i = 0; i < thf.length; i++) {
				thf[i].join();
				System.err.println("Thief "+i+" died!");	
			}
		} catch (InterruptedException ex) {
			//Logger.getLogger(CityRun.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		//map.terminate();
		
		try {
			heli.join();
			System.err.println("Heli died!");
		} catch (InterruptedException ex) {
			Logger.getLogger(CityRun.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}
