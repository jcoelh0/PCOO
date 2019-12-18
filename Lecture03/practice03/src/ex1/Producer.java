package ex1;

/**
 *
 * @author João Coelho
 */
public class Producer extends Thread{
	
	private final int id;
	private final int limit;
	Store st;
	
	public Producer(Store st, int id, int limit){
		this.id = id;
		this.st = st;
		this.limit = limit;
	}
	
	@Override
	public void run(){
		int i = 0;
		boolean limitProduction = false;
		while(limit>i){
			String prod = "";
			try {
				Thread.sleep((long)(Math.random() * 1000));
			} catch (InterruptedException ex) {
				
			}
			
			if(i==limit-1)
				limitProduction = true;
			prod = st.addQueue(limitProduction);
			
			System.out.println(prod+" adicionado à fila.");
			i++;
		}
	}
}
