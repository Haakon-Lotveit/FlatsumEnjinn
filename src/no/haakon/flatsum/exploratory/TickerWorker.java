package no.haakon.flatsum.exploratory;

import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TickerWorker implements Runnable {
	private ConcurrentLinkedQueue<Actor> actors;
	long nanosecondsBetweenTicks;
	long lastTick;
	
	public TickerWorker(long nanosecondsBetweenTicks) {
		this.nanosecondsBetweenTicks = nanosecondsBetweenTicks;
		this.lastTick = System.nanoTime();
		resetActors();
	}
	public synchronized void addActor(Actor actor) {
		actors.add(actor);
	}
	
	public synchronized void resetActors() {
		actors = new ConcurrentLinkedQueue<>();
	}
	
	public synchronized void setActors(Collection<Actor> actors) {
		actors = new ConcurrentLinkedQueue<>(actors);
	}
	
	@Override
	public void run() {				
		// It's supposed to be faster if I declare them outside the loop.
		// How would I measure this?
		long currentTime, timeSinceLastTick, timeUntilNextTick;
		
		while(!Thread.currentThread().isInterrupted()) {
			currentTime = System.nanoTime();
			timeSinceLastTick = currentTime - lastTick;
			timeUntilNextTick = Math.max(0, nanosecondsBetweenTicks - timeSinceLastTick);
			
			try {
				Thread.sleep(timeUntilNextTick / 1_000_000);
				lastTick = System.nanoTime();
				for(Actor actor : actors) {
					actor.tick();
				}
				
			} catch (InterruptedException e) {
				// If we get an interruption, that means we're done here.
				System.out.println("Ticker (the AI-thread thing) has received an interruption and will shut down");
				resetActors();
				return;
			}
		}
		
	}

}
