package entities;

import InformationCenter.Interpol;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author João Coelho
 */
public class Helicopter extends Thread{
	
	private Thief thief;
	private Interpol interpol;
	
	public Helicopter(Thief thief, Interpol interpol){
		this.thief = thief;
		this.interpol = interpol;
		
	}
	
	@Override
	public void run() {
		while(true){
			if(interpol.getThiefSituation()){
				reportThiefPosition();
				System.out.println("entities.Helicopter.run()");
			}
			try {
				Thread.sleep(5000);
			} catch (InterruptedException ex) {
				Logger.getLogger(Helicopter.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}
	
	private void reportThiefPosition(){
		interpol.setThiefPosition(thief.getThiefCurrentPosition().x, thief.getThiefCurrentPosition().y);
	}
}
