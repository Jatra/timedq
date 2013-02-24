package uk.co.jatra.timedq;

public interface TimedElement { 
	public long getStartTime();
	public long getDuration();
	public boolean isDue();
	public boolean hasExpired();
	
}