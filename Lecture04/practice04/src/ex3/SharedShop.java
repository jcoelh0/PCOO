package ex3;


import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author João Coelho
 */
public class SharedShop {
	
	private final Queue<Integer> clients;

	public SharedShop() {
		this.clients = new LinkedList<>();
	}
	
	
	
	void goToQueue(int idClient){
		clients.add(idClient);
	}
	
	synchronized int attendClient(){
		return clients.poll();
	}
	
	synchronized void cutHair(int idClient, int time){
		System.out.println("Cutting hair to client "+idClient+" for "+time);
	}
	
}
