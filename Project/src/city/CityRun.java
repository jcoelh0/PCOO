
//import static TestLabyrinth.board;
import InformationCenter.Interpol;
import city.Map;
import city.Store;
import entities.Helicopter;
import path.PathFinder;
import entities.Police;
import entities.Thief;
import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import static java.lang.System.err;
import static java.lang.System.exit;
import static java.lang.System.out;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import path.PathFinder;
import pt.ua.gboard.GBoard;
import pt.ua.gboard.Gelem;
import pt.ua.gboard.basic.FilledGelem;
import pt.ua.gboard.basic.ImageGelem;
import pt.ua.gboard.basic.StringGelem;
import pt.ua.gboard.games.Labyrinth;
import pt.ua.gboard.games.LabyrinthGelem;
import pt.ua.gboard.games.PacmanGelem;

/**
 *
 * @author João Coelho
 */
public class CityRun {

	public static void main(String[] args) {
		
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
		
		Interpol interpol = new Interpol();
		
		Store store = new Store(interpol);
		
		List<int[]> blocksArrayList = map.getBlocksArray();
		
		int[][] blocksArray = new int[blocksArrayList.size()][];
		for (int i = 0; i < blocksArrayList.size(); i++) {
			blocksArray[i]= blocksArrayList.get(i);
		}
		
		
		Police pol1 = new Police(labyrinth, symbols, interpol, blocksArray, map.getPrisonPosition());
		Thief thf1 = new Thief(labyrinth, symbols, interpol, blocksArray, map.getStorePositions(), store);
		Helicopter heli = new Helicopter(thf1, interpol);
		
		pol1.start();
		thf1.start();
		heli.start();
		
		try {
			pol1.join();
			System.err.println("Police died!");	
		} catch (InterruptedException ex) {
			Logger.getLogger(CityRun.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		try {
			thf1.join();
			System.err.println("Thief died!");	
		} catch (InterruptedException ex) {
			Logger.getLogger(CityRun.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}
