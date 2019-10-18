package liyu.test.thread;

public class TestWaitNotify implements Runnable{
	private int i;
	private Object obj;
	
	public TestWaitNotify(int i,Object obj){
		this.i = i;
		this.obj = obj;
	}
	
	@Override
	public void run() {
		while(true){
            synchronized(obj){
            	obj.notifyAll();
            	try {
					obj.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

                System.out.println(i);
            }
        }
	}
	
	public static void main(String[] args) {
		final Object obj = new Object();
		new Thread(new TestWaitNotify(1, obj)).start();
		new Thread(new TestWaitNotify(2, obj)).start();
	}

}
