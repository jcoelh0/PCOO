package InformationCenter;

import entities.Thief;
import java.awt.Point;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author João Coelho
 */
public class Interpol {

	private boolean thiefFound;
	private Point thiefPosition;
	private Point lastThiefPosition;
	private final boolean thiefCaught;
	private boolean policeFoundThief;
	private boolean thiefGoingToPrison = false;
	
	
	public Interpol(){
		thiefCaught = false;
		thiefFound = false;
		thiefPosition = new Point();
		
		
	}
	
	public synchronized void theftReported(){
		System.out.println("TheftReported");
		thiefFound = true;
		notifyAll();
		
	}
	
	public synchronized boolean waitingForCrime(){
		
		while(!thiefFound){
			
			System.out.println("waiting for crime");
			try {
				wait();
			} catch (InterruptedException ex) {
				Logger.getLogger(Interpol.class.getName()).log(Level.SEVERE, null, ex);
				System.err.println("Error ocurred in waitingForCrime");
			}
		}
		
		return true;
	}
	
	public boolean PoliceFoundThief(int lin, int col) {
        if(thiefPosition == null)
            return false;
        
        if(lin == thiefPosition.x && col == thiefPosition.y) {
            this.policeFoundThief = true;
            return true;
        }
        else return false;
    }
	
	public synchronized boolean policeFoundThief() {
		return this.policeFoundThief;
    }
	
	public void setThiefPosition(int lin, int col){
		thiefPosition.setLocation(lin, col);
	}
	
	public Point getThiefPosition(){
		return thiefPosition;
	}
	
	public boolean getThiefSituation(){
		return thiefFound;
	}
	
	public void setThiefGoingToPrison(){
		thiefGoingToPrison = true;
	}
	
	public synchronized boolean getThiefGoingToPrison(){
		return thiefGoingToPrison;
	}
}
