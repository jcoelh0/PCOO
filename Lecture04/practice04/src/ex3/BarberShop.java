package ex3;

import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author João Coelho
 */
public class BarberShop {
	public static void main(String[] args){
		
		int numBarbers = 2;
		int numClients = 10;
		
		Barber[] barbers = new Barber[numBarbers];
		Client[] clients = new Client[numClients];
		
		SharedShop shop = new SharedShop();
		
		for (int i = 0; i < numBarbers; i++) {
			barbers[i] = new Barber(shop, i+1);
			
			//threads[i].setDaemon(true);
			barbers[i].start();
		}
		
		for (int i = 0; i < numClients; i++) {
			clients[i] = new Client(shop, i+1);
			clients[i].start();
			
		}
		
		for (int i = 0; i < numClients; i++) {
			//clients[i].join();
		}
		
		//barbers[0].join();
		//barbers[1].join();
		
	}
}
