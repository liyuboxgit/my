package liyu.test.thread;

import java.util.concurrent.CountDownLatch;

public class TestCountDownLatch {
	public static void main(String[] args) throws Throwable {
        // 同步计数器从5开始计数
        final CountDownLatch countDownLatch = new CountDownLatch(5);
 
        // 启动子线程，处理“其他”业务
        for(int index = 0 ; index < 5 ; index++) {
            Thread childThread = new Thread("thread.index-"+index) {
                @Override
                public void run() {
                    //等待，以便模型业务处理过程消耗的时间
                    synchronized (this) {
                        try {
                            this.wait(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
 
                    // 完成业务处理过程，计数器-1
                    String threadname = Thread.currentThread().getName();
                    System.out.println("子线程(" + threadname + ")执行完成！");
                    countDownLatch.countDown();
                }
            };
            childThread.start();
        }
 
        // 等待所有子线程的业务都处理完成（计数器的count为0时）
        countDownLatch.await();
        System.out.println("所有子线程的处理都完了，主线程继续执行...");
    }

}
