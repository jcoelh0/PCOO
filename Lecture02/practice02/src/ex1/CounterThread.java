package ex1;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author João Coelho
 */
public class CounterThread extends Thread{
	
	private int counter = 1;
	private final int limit;
	private final int id;
	
	public CounterThread(int limit, int id){
		this.limit = limit;
		this.id = id;
	}
	
	@Override
	public void run(){
		do{
			try {
				Thread.sleep((long) Math.random());
			} catch (InterruptedException ex) {
				Logger.getLogger(CounterThread.class.getName()).log(Level.SEVERE, null, ex);
			}
			
			System.out.println("thread #" + id + ": counter is " + counter);
			counter++;
		}while(counter != limit+1);
		
	}
	
}
