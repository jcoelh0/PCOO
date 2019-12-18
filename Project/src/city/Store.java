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
	
	private void soundAlarm(Point storePoint){
		interpol.theftReported();
		interpol.setThiefPosition(storePoint.x, storePoint.y);
	}
	
	public void robItem(Point storeLocation){
		soundAlarm(storeLocation);
	}
}
