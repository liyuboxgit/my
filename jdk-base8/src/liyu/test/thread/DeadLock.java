package liyu.test.thread;

import javax.swing.plaf.synth.SynthViewportUI;

public class DeadLock {
	private static Object lock1 = new Object();
	private static Object lock2 = new Object();
	static class T1 implements Runnable{
		@Override
		public void run() {
			synchronized(lock1) {
				try {
					Thread.sleep(1000);
					synchronized (lock2) {
						System.out.println("永远不会执行");
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	static class T2 implements Runnable{
		@Override
		public void run() {
			synchronized(lock2) {
				try {
					Thread.sleep(1000);
					synchronized (lock1) {
						System.out.println("永远不会执行");
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	/** 
	 * use jdk toll jstack as: %JAVA_HOME%/bin/jstack pid
	 * Found one Java-level deadlock:
		=============================
		"Thread-1":
		  waiting to lock monitor 0x0000000002ee8b88 (object 0x00000000d5cceab8, a java.lang.Object),
		  which is held by "Thread-0"
		"Thread-0":
		  waiting to lock monitor 0x0000000002ee62f8 (object 0x00000000d5cceac8, a java.lang.Object),
		  which is held by "Thread-1"
		
		Java stack information for the threads listed above:
		===================================================
		"Thread-1":
		        at liyu.test.thread.DeadLock$T2.run(DeadLock.java:32)
		        - waiting to lock <0x00000000d5cceab8> (a java.lang.Object)
		        - locked <0x00000000d5cceac8> (a java.lang.Object)
		        at java.lang.Thread.run(Thread.java:748)
		"Thread-0":
		        at liyu.test.thread.DeadLock$T1.run(DeadLock.java:15)
		        - waiting to lock <0x00000000d5cceac8> (a java.lang.Object)
		        - locked <0x00000000d5cceab8> (a java.lang.Object)
		        at java.lang.Thread.run(Thread.java:748)
			 * @param args
	 */
	public static void main(String[] args) {
		new Thread(new T1()).start();
		new Thread(new T2()).start();
	}
}
