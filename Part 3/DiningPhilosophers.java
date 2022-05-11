/**
 * Class DiningPhilosophers
 * The main starter.
 *
 * @author Serguei A. Mokhov, mokhov@cs.concordia.ca
 */



import java.util.Scanner;

public class DiningPhilosophers
{
	/*
	 * ------------
	 * Data members
	 * ------------
	 */

	/**
	 * This default may be overridden from the command line
	 */
	public static final int DEFAULT_NUMBER_OF_PHILOSOPHERS = 5;

	/**
	 * Dining "iterations" per philosopher thread
	 * while they are socializing there
	 */
	public static final int DINING_STEPS = 10;

	/**
	 * Our shared monitor for the philosphers to consult
	 */
	public static Monitor soMonitor = null;

	/*
	 * -------
	 * Methods
	 * -------
	 */
	public static int takeInput() {
		int desired_thinkers = DEFAULT_NUMBER_OF_PHILOSOPHERS;
		Scanner keyIn = new Scanner(System.in);
		int input = 0;
		while (input != -1) {
			System.out.println("Enter the desired number of philosophers, or enter -1 for the default amount: ");
			input = keyIn.nextInt();
			
			if (input != -1 && input < 0) {
				System.out.println(input + " is not a positive decimal integer. Please try again.");
			}
			
			if (input > 0) {
				System.out.println("Great! You've selected " + input + " philosophers.");
				desired_thinkers = input;
				keyIn.close();
				return desired_thinkers;
			}
			
		}
		
		keyIn.close();
		return desired_thinkers;
	}
	/**
	 * Main system starts up right here
	 */
	public static void main(String[] argv)
	{
		try
		{
			
			int iPhilosophers = DiningPhilosophers.takeInput(); //Instead of taking from the command line using the makefile, I instead opted to use a scanner.

			// Make the monitor aware of how many philosophers there are
			soMonitor = new Monitor(iPhilosophers);

			// Space for all the philosophers
			Philosopher aoPhilosophers[] = new Philosopher[iPhilosophers];
			
			System.out.println
			(
				iPhilosophers +
				" philosopher(s) came in for a dinner."
			);

			// Let 'em sit down
			for(int j = 0; j < iPhilosophers; j++)
			{
				aoPhilosophers[j] = new Philosopher();
				aoPhilosophers[j].start();
			}

			

			// Main waits for all its children to die...
			// I mean, philosophers to finish their dinner.
			for(int j = 0; j < iPhilosophers; j++)
				aoPhilosophers[j].join();

			System.out.println("All philosophers have left. System terminates normally.");
		}
		catch(InterruptedException e)
		{
			System.err.println("main():");
			reportException(e);
			System.exit(1);
		}
	} // main()

	/**
	 * Outputs exception information to STDERR
	 * @param poException Exception object to dump to STDERR
	 */
	public static void reportException(Exception poException)
	{
		System.err.println("Caught exception : " + poException.getClass().getName());
		System.err.println("Message          : " + poException.getMessage());
		System.err.println("Stack Trace      : ");
		poException.printStackTrace(System.err);
	}
}

// EOF
