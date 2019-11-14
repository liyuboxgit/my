package liyu.test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

public class WatchedMap {
	private final Map<String,String> inner = new HashMap<String,String>(); 
	public WatchedMap() {
		Timer timer = new Timer(true);
	    timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				Set<String> set = inner.keySet();
				long e = System.currentTimeMillis();
				Iterator<String> is = set.iterator();
				
				while(is.hasNext()) {
					String v = inner.get(is.next());
					String l = v.substring(v.indexOf(",")+1);
					if(e-Long.valueOf(l)>1000) {
						is.remove();
					}
				}
			}
		}, 5*1000, 5*1000);
	}
	
	public void put(String key, String value) {
		this.inner.put(key, value+","+System.currentTimeMillis());
	}
	
	public String get(String key) {
		String s = this.inner.get(key);
		return s==null?null:s.substring(0,s.indexOf(","));
	}
	
	public static void main(String[] args) throws InterruptedException {
		Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                System.out.println("我要stop了...........................");
            }
        });
		WatchedMap map = new WatchedMap();
		map.put("k", "v1");
		//return v1
		System.out.println(map.get("k"));
		//return v1
		Thread.sleep(2000);
		System.out.println(map.get("k"));
		//return null
		Thread.sleep(4000);
		System.out.println(map.get("k"));
	}
}
