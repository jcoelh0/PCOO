package ex1;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author João Coelho
 */
public class Store {
	Random random;
	Queue<String> products;
	boolean limitProduction;
	
	public Store(){
		products = new LinkedList<>();
		random = new Random();
	}
	
	synchronized String addQueue(boolean limitProduction){
		this.limitProduction = limitProduction;
		int rand = random.nextInt(10 - 1 + 1) + 1;
		products.add("Produto "+rand);
		notify();
		return "Produto "+rand;
	}
	
	synchronized String consumeQueue() throws InterruptedException {
		
		String str = products.poll();
		
		while(!limitProduction && str == null){
			System.out.println("Checking if there is product...");
			
			try {
				wait();
			} catch (InterruptedException ex) {
				throw new InterruptedException();
			}
			str = products.poll();
		}
		return str;
	}
}
