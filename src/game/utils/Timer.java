package game.utils;

public class Timer {

	long initialTime;
	long duration;
	boolean finished;
	boolean started;

	public Timer(long duration) {
		this.duration = duration;
	}

	public void start() {
		started = true;
		initialTime = System.currentTimeMillis();
	}

	public boolean isFinished() {
		return finished;
	}

	public boolean isStarted() {
		return started;
	}

	public void update() {
		finished = System.currentTimeMillis() - initialTime > duration;
	}

	public void reset() {
		finished = false;
		started = false;
	}

	public void finish() {
		finished = true;
		started = false;
	}

	public long getTime() {
		return System.currentTimeMillis() - initialTime;
	}

}
