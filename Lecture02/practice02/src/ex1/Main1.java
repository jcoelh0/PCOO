package ex1;

/**
 *
 * @author João Coelho
 */
public class Main1 {
	
	public static void main(String[] args) throws InterruptedException{
		
		int[] limits = new int[args.length];
		for (int i = 0; i < args.length; i++) {
			limits[i] = Integer.parseInt(args[i]);
		}
		
		CounterThread[] threads = new CounterThread[args.length];
		
		for (int i = 0; i < args.length; i++) {
			threads[i] = new CounterThread(limits[i],i+1);
			threads[i].setDaemon(true);
			threads[i].start();
		}
		
		for (int i = 0; i < args.length; i++) {
			threads[i].join();
		}
		
	}
}
