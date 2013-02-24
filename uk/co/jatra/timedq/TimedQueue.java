package uk.co.jatra.timedq;

import java.util.Collection;
import java.util.Comparator;
import java.util.concurrent.PriorityBlockingQueue;


public class TimedQueue extends PriorityBlockingQueue<TimedElement> {

	public TimedQueue() {
		this(20);
	}
	public TimedQueue(int initialCapacity) {
		super(initialCapacity, new Comparator<TimedElement>() {
					@Override
					public int compare(TimedElement rhs, TimedElement lhs) {
						return (int) (rhs.getStartTime() - lhs.getStartTime());
					}
				});
	}


	public TimedElement poll() {
		TimedElement timedElement = peek();
		while (timedElement != null && timedElement.hasExpired()) {
			System.out.println("** "+timedElement+" expired");
			super.poll();
			timedElement = peek();
		}
		if (timedElement != null && timedElement.isDue()) {
			return super.poll();
		} else {
			return null;
		}
	}		
}
