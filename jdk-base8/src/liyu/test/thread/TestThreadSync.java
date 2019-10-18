package liyu.test.thread;

import java.util.Vector;
import java.util.concurrent.locks.ReentrantLock;
/**
 * 
 * @author Administrator
 * 线程同步，通过枷锁（两种方式，通过synchronized和reentrantlock加锁）
 * 通过使用线程安全的Vector
 */
public class TestThreadSync {
	//private static List<Integer> l = new ArrayList<Integer>();
	private static Vector<Integer> l = new Vector<Integer>();
	private static ReentrantLock lock = new ReentrantLock();
	
	public static void main(String[] args) throws InterruptedException {
		for(int i=0;i<100;i++) {
			Thread t = new Thread(new Runnable() {
				@Override
				public void run() {
					//lock.lock();
					//synchronized (l) {						
						for(int j=0;j<100;j++) {
							l.add(1);
						}
					//}
					//lock.unlock();
				}
			});
			t.start();
			t.join();
		}
		
		System.out.println(l.size());
	}
}
