
/**
 * Class Monitor
 * To synchronize dining philosophers.
 *
 * @author Serguei A. Mokhov, mokhov@cs.concordia.ca
 */
import java.util.ArrayList;

public class Monitor {
	/*
	 * ------------ Data members ------------
	 */
	
	public final int POLITENESS = 5; //Set this higher or lower depending on how polite you want your philosophers to be. The lower it is, the more polite they are (Min: 1).
	
	boolean isSomeoneTalking; //Keep track of whether someone is talking or not.
	Chopstick chopsticks[]; //Our array of chopstick objects.
	int numOfPhilosophers; //Used to store the number of philosophers passed to us.
	ArrayList<Integer> lineUp; //Our queue setup.
	int [] turnsWaited; //Our patience monitor.

	private class Chopstick { //Chopstick inner class. Keeps track of whether this chopstick is held or not.
		public boolean held;

		public Chopstick() {
			held = false; //By default a chopstick is on the table.

		}

		public void pickUp() {
			this.held = true;
		}

		public void putDown() {
			this.held = false;
		}

	}

	/**
	 * Constructor
	 */
	public Monitor(int piNumberOfPhilosophers) {
		numOfPhilosophers = piNumberOfPhilosophers; 
		chopsticks = new Chopstick[numOfPhilosophers]; //Initialize the correct number of chopsticks.
		isSomeoneTalking = false; //Nobody should be talking by default.
		lineUp = new ArrayList<Integer>(); //Create our queue.
		turnsWaited = new int[numOfPhilosophers]; //Initialize everyone's wait time to 0.

		for (int i = 0; i < numOfPhilosophers; i++) {
			chopsticks[i] = new Chopstick(); //Initialize all of our chopsticks to their default values.
		}
	}

	

	/**
	 * Grants request (returns) to eat when both chopsticks/forks are available.
	 * Else forces the philosopher to wait()
	 */
	public synchronized void pickUp(final int piTID) 
														
	{
		System.out.println("Philosopher (" + piTID + ") is hungry and attempting to pick up their chopsticks.");
		int chopstick_index = (piTID - 1);

		while (true) {
			if (!(chopsticks[chopstick_index].held) && !(chopsticks[(chopstick_index + 1) % numOfPhilosophers].held) ) { //If your chopsticks are available..
				if (lineUp.size() == 0) { //Is the queue empty? If so go ahead and eat.
					chopsticks[chopstick_index].pickUp();
					chopsticks[(chopstick_index + 1) % numOfPhilosophers].pickUp();
					return;
				}
				else if (lineUp.get(0) == piTID || turnsWaited[lineUp.get(0) - 1] < POLITENESS){ //If the queue isn't empty, but you're first in line, or the person first in line hasn't waited that long it would probably be okay if you grabbed a quick bite.
					chopsticks[chopstick_index].pickUp();
					chopsticks[(chopstick_index + 1) % numOfPhilosophers].pickUp();
					if (turnsWaited[lineUp.get(0) - 1] < POLITENESS && !(lineUp.get(0) == piTID)) {
						System.out.println("Philosopher(" + piTID + ") has decided that Philosopher(" + lineUp.get(0) + ") can wait a bit longer.."); 
					}
					if (lineUp.get(0) == piTID) {
						System.out.println("Philosopher(" + piTID + ") has waited long enough! He gets to eat and has exited the queue.");
						lineUp.remove(0);
						turnsWaited[chopstick_index] = 0;
					}
					
					System.out.println("The queue looks like this: " + lineUp.toString()); //Update the queue in case someone left it, or is still waiting.
					return;
				}
				 
			}
			
			
				try {
					if (!lineUp.contains(piTID)) {
						System.out.println("Philosopher(" + piTID + ") has to wait to eat, and has entered the queue.");
						lineUp.add(piTID);
						System.out.println("The queue looks like this: " + lineUp.toString()); //Update the queue to show that someone new has joined it.
					}
					
					turnsWaited[chopstick_index] = turnsWaited[chopstick_index] + 1; //Increment how long this philosopher has waited by 1.
					wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
		}
	}

	/**
	 * When a given philosopher's done eating, they put the chopsticks/forks down
	 * and let others know they are available.
	 */
	public synchronized void putDown(final int piTID) {

		int chopstick_index = (piTID - 1);
		chopsticks[chopstick_index].putDown(); // Put down the chopstick to your left.
		chopsticks[(chopstick_index + 1) % numOfPhilosophers].putDown(); // Put down the chopstick to your right.
		System.out.println("Philosopher(" + piTID + ") put down their chopsticks.");
		notifyAll(); //Let all of the waiting threads know to try eating again.

	}

	/**
	 * Only one philosopher at a time is allowed to philosophy (while she is not
	 * eating).
	 */
	public synchronized void requestTalk() {
		while (isSomeoneTalking == true) {
			try {
				wait(); // If someone else is talking, you must wait until they are finished to start.
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		isSomeoneTalking = true; // Now that someone else finished, you can start talking.
	}

	/**
	 * When one philosopher is done talking stuff, others can feel free to start
	 * talking.
	 */
	public synchronized void endTalk() {
		isSomeoneTalking = false; // Once you're done talking, let every other philosopher know you're done
									// talking.
		notifyAll();
	}
}

// EOF
