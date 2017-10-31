package cn.sowell.ddxyz.weixin.controller.kanteen;


public class UniqueWaitBlocker {
	private Thread runningThread;
	private Thread waitingThread;
	public final byte[] RUNNING = new byte[0];
	public final byte[] WAIT = new byte[0];
	private Runnable removeFromMap;
	public UniqueWaitBlocker(
			Runnable removeFromMap) {
		this.removeFromMap = removeFromMap;
	}
	public synchronized Thread getRunningThread() {
		return runningThread;
	}
	public synchronized void setRunningThread(Thread runningThread) {
		this.runningThread = runningThread;
	}
	public synchronized Thread getWaitingThread() {
		return waitingThread;
	}
	public synchronized void setWaitingThread(Thread waitingThread) {
		this.waitingThread = waitingThread;
	}
	public synchronized boolean hasWaitThread() {
		return waitingThread != null;
	}
	public synchronized void releaseRunningLock() {
		this.setRunningThread(getWaitingThread());
		this.setWaitingThread(null);
		try {
			//唤醒等待的线程
			WAIT.notify();
		} catch (Exception e) {}
		if(getRunningThread() == null && removeFromMap != null){
			removeFromMap.run();
		}
	}
	
	
	
	
}
