package test;

import java.util.concurrent.CountDownLatch;

public class TestCountDownLatch {
	public static void main(String[] args) throws InterruptedException {
		int N = 10;

		CountDownLatch startSignal = new CountDownLatch(1);
		CountDownLatch doneSignal = new CountDownLatch(N);

		for (int i = 0; i < N; ++i) 
			new Thread(new Worker(startSignal, doneSignal,i)).start();

		System.out.println("各就各位，预备！");
		Thread.sleep(5000);
		
		System.out.println("开始跑！");
		startSignal.countDown(); 
		doneSignal.await();
		System.out.println("ok，结束！");
	}

	private static class Worker implements Runnable {
		private final CountDownLatch startSignal;
		private final CountDownLatch doneSignal;
		private int code;
		
		Worker(CountDownLatch startSignal, CountDownLatch doneSignal, int code) {
			this.startSignal = startSignal;
			this.doneSignal = doneSignal;
			this.code = code;
		}

		public void run() {
			try {
				startSignal.await();
				doWork();
				doneSignal.countDown();
			} catch (InterruptedException ex) {
			
			} 
		}

		void doWork() {
			System.out.println(this.code);
		}
	}
}
