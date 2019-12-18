package ex3;

import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author João Coelho
 */
public class Client extends Thread{
	
	private int id;
	private int numHairCuts;
	private int timeOutside;
	private SharedShop shop;
		
	public Client(SharedShop shop, int id){
		this.id = id;
		numHairCuts = ThreadLocalRandom.current().nextInt(1, 1 + 1);
		timeOutside = ThreadLocalRandom.current().nextInt(5, 25 + 1);
	}
	
	@Override
	public synchronized void run(){
		
	}
}
