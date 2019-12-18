package ex3;

import java.util.Scanner;

/**
 *
 * @author João Coelho
 */
public class Main3 {
	static Scanner in = new Scanner(System. in);
	public static void main(String[] args) throws InterruptedException{
		int time = 5;
		while(time != 0){
			System.out.print("Introduza o tempo em que o alarme vai tocar (em seg):");
			time = in.nextInt();
			in.nextLine();
			System.out.print("Mensagem do alarme:");
			String message = in.nextLine();

			Alarm alarm = new Alarm(message, time);
			Thread thread = new Thread(alarm);
			thread.start();

			System.out.println("\nMensagem programada com sucesso!");
			
			
		}
		
	}
}
