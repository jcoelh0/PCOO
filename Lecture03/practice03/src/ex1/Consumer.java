package ex1;

import java.io.UncheckedIOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author João Coelho
 */
public class Consumer extends Thread{
	
	private final int id;
	Store st;
	public Consumer(Store st, int id){
		this.id = id;
		this.st = st;
	}
	
	@Override
	public void run(){
		String prod = "";
		while(prod!=null){
			
			
			prod = null;
			try {
				Thread.sleep((long)(Math.random() * 1000));
			} catch (InterruptedException ex) {
				
			}
			
			prod = st.consumeQueue();
			
			if(prod == null)
				break;
			System.out.println(prod+" retirado da fila.");
			
		}
	}
}

