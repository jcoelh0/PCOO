package ex2;

import ex1.*;

/**
 *
 * @author João Coelho
 */
public class Main2 {
	
	public static void main(String[] args) throws InterruptedException{
		
		Counter count = new Counter();
		
		int limit = 4;
		
		
		SharedCounterThread[] threads = new SharedCounterThread[args.length];
		
		for (int i = 0; i < args.length; i++) {
			threads[i] = new SharedCounterThread(limit, i+1, count);
			threads[i].setDaemon(true);
			threads[i].start();
		}
		
		for (int i = 0; i < args.length; i++) {
			threads[i].join();
		}
		
	}
}
