package ex1;

/**
 *
 * @author João Coelho
 */
public class MainEx31 {

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) throws InterruptedException {
		
		Producer[] producers = new Producer[5];
		Consumer[] consumers = new Consumer[5];
		
		Store store = new Store();
		
		for (int i = 0; i < 5; i++) {
			producers[i] = new Producer(store, i+1, 5);
			consumers[i] = new Consumer(store, i+1);
			//threads[i].setDaemon(true);
			producers[i].start();
			consumers[i].start();
		}
		
		for (int i = 0; i < args.length; i++) {
			producers[i].join();
			consumers[i].join();
		}
		
	}
	
}
