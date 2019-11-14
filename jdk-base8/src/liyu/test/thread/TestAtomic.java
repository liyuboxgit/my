package liyu.test.thread;

import java.util.concurrent.atomic.AtomicStampedReference;

public class TestAtomic {
    static AtomicStampedReference<Thread> atomicThread = new AtomicStampedReference<>(null,1);

    public static void main(String args[]){
        new Thread(()->{
            myLock();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                myUnLock();
            }
        },"AAA").start();
        new Thread(()->{
            myLock();
            myUnLock();
        },"BBB").start();
    }

    public static void myLock(){
        Thread thread = Thread.currentThread();
        System.out.println(thread.getName()+" lock come in,version is "+atomicThread.getStamp());
        while(!atomicThread.compareAndSet(null,thread,1,2)){
            //System.out.println("加锁失败");
        }
        System.out.println("    "+thread.getName()+"加锁成功");
        System.out.println(thread.getName()+" lock come out,version is "+atomicThread.getStamp());
    }
    public static void myUnLock(){
        Thread thread = Thread.currentThread();
        System.out.println(thread.getName()+" unlock come in,version is "+atomicThread.getStamp());
        while(!atomicThread.compareAndSet(thread,null,2,1)){
            //System.out.println("解锁失败");
        }
        System.out.println(thread.getName()+"解锁成功");
        System.out.println(thread.getName()+" unlock come out,version is "+atomicThread.getStamp());
    }
}

