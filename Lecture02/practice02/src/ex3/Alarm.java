package ex3;

/**
 *
 * @author João Coelho
 */
public class Alarm implements Runnable{
	
	private String message;
	private int time;
	
	public Alarm(String message, int time){
		this.message = message;
		this.time = time;
	}
	
	@Override
	public void run(){
		long start = System.currentTimeMillis();
		double elapsedSeconds = 0;
		while(elapsedSeconds != time){
			long tEnd = System.currentTimeMillis();
			long tDelta = tEnd - start;
			elapsedSeconds = tDelta / 1000.0;
		}
		System.out.println("Tempo passado: " + time + " seg. Mensagem do Alarme:" + message);
		System.out.println();
	}
	
}
