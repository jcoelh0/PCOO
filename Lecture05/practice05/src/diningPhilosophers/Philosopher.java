package diningPhilosophers;

/**
 *
 * @author João Coelho
 */
public class Philosopher extends Thread {
	
	private int timeEating = 20;
	private int numberOfMeals;
	
	public Philosopher(int numberOfMeals){
		this.numberOfMeals = numberOfMeals;
	}
	
}
