package InformationCenter;

import entities.Thief;
import java.awt.Point;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author João Coelho
 */
public class Interpol {

	private boolean[] thiefFound;
	private Point[] thiefPosition;
	private boolean thiefSearh = true;
	private final boolean thiefCaught;
	private final boolean[] policeFoundThief;
	private boolean thiefGoingToPrison = false;
	private int numberOfThiefs;
	//private boolean thiefsFound = false;
	private boolean lookingForThief = false;
	LinkedList<Integer> thiefs = new LinkedList<>();
	private boolean theftHappening = false;
	private Point prison;
	
	
	public Interpol(int numberOfThiefs, Point prison){
		policeFoundThief = new boolean[numberOfThiefs];
		thiefPosition = new Point[numberOfThiefs];
		thiefFound = new boolean[numberOfThiefs];	
		
		for (int i = 0; i < numberOfThiefs; i++) {
			policeFoundThief[i] = false;
			thiefPosition[i] = new Point();
			thiefFound[i] = false;
		}
		thiefCaught = false;
		this.numberOfThiefs = numberOfThiefs;
		this.prison = prison;
		
	}
	
	public synchronized void theftReported(int id){
		theftHappening = true;
		//thiefsFound = true;
		lookingForThief = true;
		thiefs.add(id);
		notify();
		
	}
	
	public synchronized int waitingForCrime(){		
		
		while(!lookingForThief && numberOfThiefs!=0){
			
			try {
				wait();	
			} catch (InterruptedException ex) {
				Logger.getLogger(Interpol.class.getName()).log(Level.SEVERE, null, ex);
				System.err.println("Error ocurred in waitingForCrime");
			}
			
			/*thiefsFound = false;
			for (int i = 0; i < thiefFound.length; i++) {

				thiefsFound = thiefsFound | thiefFound[i];
				if(thiefFound[i]){
					System.err.println("thief found lets see "+i);
					idx = i;
					break;
				}
				System.err.println("thief foundd "+i);
			}*/
			
		}
		lookingForThief = false;
		if(numberOfThiefs==0)
			return -1;
		return thiefs.remove();
	}
	
	public synchronized int PoliceFoundThief(int lin, int col) {
        assert thiefPosition != null;
		
        for (int i = 0; i < numberOfThiefs; i++) {
			
			if((lin == thiefPosition[i].x && col == thiefPosition[i].y) 
				&& lin!= prison.x && col!=prison.y)
				
				
				/* || // to fix the same position and not "catching"
				(lin-1 == thiefPosition[i].x && col == thiefPosition[i].y) ||
				(lin == thiefPosition[i].x && col+1 == thiefPosition[i].y) ||
				(lin == thiefPosition[i].x && col-1 == thiefPosition[i].y) ||
				(lin+1 == thiefPosition[i].x && col == thiefPosition[i].y))*/ {
				policeFoundThief[i] = true;
				return i;
			}
		}
        return -1;
    }
	
	public synchronized boolean policeFoundThief(int id) {
		return policeFoundThief[id];
    }
	
	public synchronized void setThiefPosition(int lin, int col, int id){
		thiefFound[id] = true;
		//thiefsFound = true;
		thiefPosition[id].setLocation(lin, col);
	}
	
	public synchronized Point getThiefPosition(int id){
		return thiefPosition[id];
	}
	
	public synchronized boolean getTheftStatus(){
		return theftHappening;
	}
	
	public synchronized void setThiefGoingToPrison(){
		thiefGoingToPrison = true;
	}
	
	public synchronized boolean getThiefGoingToPrison(){
		return thiefGoingToPrison;
	}
	
	public synchronized int getNumberOfThiefs(){
		return numberOfThiefs;
	}
	
	public synchronized void safe(int id){
		thiefFound[id] = false;
		//thiefsFound = false;
		numberOfThiefs -= 1;
	}
	
	public synchronized boolean thiefFound(int id){
		return thiefFound[id];
	}
	
	public synchronized void wakePolice(){
		notifyAll();
	}
}
