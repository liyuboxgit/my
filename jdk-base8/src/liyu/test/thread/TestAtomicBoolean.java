package liyu.test.thread;

import java.util.concurrent.atomic.AtomicBoolean;

public class TestAtomicBoolean {
	private static AtomicBoolean init = new AtomicBoolean();
	
	public static void init() throws InterruptedException {
		Thread thread = Thread.currentThread();
		if(init.compareAndSet(false, true)) {
			System.out.println(thread.getName()+" init成功！");
		}
		else System.out.println("已初始化！");
	}
	
	public static void main(String[] args) {
		for(int i=0;i<10;i++) {
			new Thread(()->{
				try {
					init();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			},"t"+i).start();
		}
	}
}
