package entities;

import InformationCenter.Interpol;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author João Coelho
 */
public class Helicopter extends Thread{
	
	private final Thief[] thiefs;
	private final Interpol interpol;
	
	public Helicopter(Thief[] thiefs, Interpol interpol){
		this.thiefs = thiefs;
		this.interpol = interpol;
	}
	
	@Override
	public void run() {
		int i =0;
		
		while(interpol.getNumberOfThiefs()!=0){
			if(interpol.getTheftStatus())
				reportThiefsPosition();
			
			try {
				Thread.sleep(5000);
				
			} catch (InterruptedException ex) {
				Logger.getLogger(Helicopter.class.getName()).log(Level.SEVERE, null, ex);
			}
			i++;
		}
		
		//wake police that it's over
		interpol.wakePolice();
	}
	
	private void reportThiefsPosition(){
		for (int i = 0; i < thiefs.length; i++) {
			interpol.setThiefPosition(thiefs[i].getThiefCurrentPosition().x, thiefs[i].getThiefCurrentPosition().y, i);
		}
		
	}
}
