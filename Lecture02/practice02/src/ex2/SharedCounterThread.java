package ex2;

import ex1.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author João Coelho
 */
public class SharedCounterThread extends Thread{
	
	private int counter = 1;
	private final int limit;
	private final int id;
	private Counter count;
		
	public SharedCounterThread(int limit, int id, Counter count){
		this.count = count;
		this.limit = limit;
		this.id = id;
	}
	
	@Override
	public void run(){
		do{
			
			counter = count.increment(id);
			System.out.println("thread #" + id + ": counter is " + counter);
			
		}while(counter < limit-1);
		
	}
	
}
