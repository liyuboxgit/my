package liyu.test.jmx;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.MemoryUsage;
import java.util.List;

public class Monitor {
	public void monitorMemory() {  
        StringBuilder sb = new StringBuilder("Memory:");  
  
        MemoryMXBean mmbean = ManagementFactory.getMemoryMXBean();  
        MemoryUsage hmu = mmbean.getHeapMemoryUsage();  
        sb.append("[HeapMemoryUsage:");  
        sb.append(" Used=" + formatBytes(hmu.getUsed()));  
        sb.append(" Committed=" + formatBytes(hmu.getCommitted()));  
        sb.append("]");  
  
        MemoryUsage nhmu = mmbean.getNonHeapMemoryUsage();  
        sb.append("[NonHeapMemoryUsage:");  
        sb.append(" Used=" + formatBytes(nhmu.getUsed()));  
        sb.append(" Committed=" + formatBytes(nhmu.getCommitted()));  
        sb.append("]");  
  
        System.out.println(sb);
    }

	public void monitorMemoryPool() {  
        StringBuilder sb = new StringBuilder("MemoryPool:");  
  
        List<MemoryPoolMXBean> pools = ManagementFactory.getMemoryPoolMXBeans();  
  
        for (MemoryPoolMXBean p : pools) {  
            sb.append("[" + p.getName() + ":");  
            MemoryUsage u = p.getUsage();  
            sb.append(" Used=" + formatBytes(u.getUsed()));  
            sb.append(" Committed=" + formatBytes(u.getCommitted()));  
            sb.append("]");  
  
        }  
        System.out.println(sb.toString());  
  
    }  
	
	public void monitorGC() {  
        StringBuilder sb = new StringBuilder("GC:");  
  
        List<GarbageCollectorMXBean> gcmbeans = ManagementFactory.getGarbageCollectorMXBeans();  
  
        for (GarbageCollectorMXBean gc : gcmbeans) {  
            sb.append("[" + gc.getName() + ": ");  
            sb.append("Count=" + gc.getCollectionCount());  
            sb.append(" GCTime=" + gc.getCollectionTime());  
            sb.append("]");  
        }  
  
        System.out.println(sb.toString());  
    }  
	
	private String formatBytes(long used) {
		return String.valueOf(used/1024)+"K";
	}  
	
	public static void main(String[] args) {
		Monitor monitor = new Monitor();
		
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(true) {					
					monitor.monitorMemory();
					monitor.monitorMemoryPool();
					monitor.monitorGC();
					System.out.println("----------------------------------");
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
			}
		}).start();
	}
}
