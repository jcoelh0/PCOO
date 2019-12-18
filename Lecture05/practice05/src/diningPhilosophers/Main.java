package diningPhilosophers;

/**
 *
 * @author João Coelho
 */
public class Main {
	public static void main(String[] args){
		
		int numberOfMeals = 2;
		
		Philosopher[] men = new Philosopher[5];
		
		for (int i = 0; i < men.length; i++) {
			men[i] = new Philosopher(numberOfMeals);
			men[i].start();
		}
	}
}
