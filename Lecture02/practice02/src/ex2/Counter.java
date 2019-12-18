package ex2;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author João Coelho
 */
public class Counter {
	
	private int counter = 0;
	
	public synchronized int increment(int id){
		counter++;
		try {
			Thread.sleep((long) Math.random());
		} catch (InterruptedException ex) {
			Logger.getLogger(Counter.class.getName()).log(Level.SEVERE, null, ex);
		}
		return counter;
	}
	
}
