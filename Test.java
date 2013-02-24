import uk.co.jatra.timedq.TimedElement;
import uk.co.jatra.timedq.TimedQueue;

public class Test {
	public static void main(String[] args) throws Exception {
		long initialWait = 500;
		TimedQueue queue = new TimedQueue();
		long now = System.currentTimeMillis();
		TestItem oneSecond = new TestItem(now+1000, 2000, "one");
		TestItem twoSecond = new TestItem(now+2000, 2000, "two");
		TestItem threeSecond = new TestItem(now+3000, 2000, "three");
		TestItem fourSecond = new TestItem(now+4000, 2000, "four");
		TestItem fiveSecond = new TestItem(now+5000, 2000, "five");
		TestItem tenSecond = new TestItem(now+10000, 2000, "ten");
		queue.offer(tenSecond); 
		queue.offer(fiveSecond); 
		queue.offer(twoSecond); 
		queue.offer(oneSecond); 
		queue.offer(fourSecond); 
		queue.offer(threeSecond); 
		
		if (args.length > 0) {
			initialWait = Integer.parseInt(args[0]);
		}
		Thread.sleep(initialWait);
		
		while (queue.size() > 0) {
			TimedElement item = queue.poll();
			if (item == null) {
				if (queue.size() == 0) {
					break;
				}
				System.out.println("... wait for 500");
				Thread.sleep(500);
			} else {
				System.out.println(System.currentTimeMillis()/1000+": "+item);
				Thread.sleep(item.getDuration());
			}
		}
	}
	
	
	static class TestItem implements TimedElement {
		private long startTime;
		private long duration;
		private String name;
		public TestItem(long startTime, long duration, String name) {
			this.startTime = startTime;
			this.duration = duration;
			this.name = name;
		}
		public long getStartTime() {
			return startTime;
		}
		public boolean isDue() {
			long now = System.currentTimeMillis();
			return now > startTime && now < (startTime + duration);
		}
		public boolean hasExpired() {
			return System.currentTimeMillis() > startTime + duration;
		}
		public String toString() {
			return name+"["+startTime/1000+ " to "+(startTime+duration)/1000+"]";
		}
		public long getDuration() {
			return duration;
		}
	}
}