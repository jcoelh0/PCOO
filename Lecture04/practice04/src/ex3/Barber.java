package ex3;

import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author João Coelho
 */
public class Barber extends Thread{
	
	private int id;
	private SharedShop ss;
	
	public Barber(SharedShop ss, int id){
		this.ss = ss;
		this.id = id;
		
	}
	
	@Override
	public synchronized void run(){
		
		while(true){
			
			int idClient = ss.attendClient();
			int timeHairCut = ThreadLocalRandom.current().nextInt(3, 10 + 1);
			
			ss.cutHair(idClient, timeHairCut);
			
		}
	}
}
