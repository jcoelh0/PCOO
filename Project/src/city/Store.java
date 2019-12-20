package city;

import InformationCenter.Interpol;
import java.awt.Point;

/**
 *
 * @author João Coelho
 */
public class Store {
	
	private final Interpol interpol;
	
	
	public Store(Interpol interpol){
		this.interpol = interpol;
	}
	
	private void soundAlarm(Point storePoint, int id){
		//System.out.println("Ladrao nº "+id+"assaltou loja");
		interpol.theftReported(id);
		interpol.setThiefPosition(storePoint.x, storePoint.y, id);
	}
	
	public void robItem(Point storeLocation, int id){
		soundAlarm(storeLocation, id);
	}
}
