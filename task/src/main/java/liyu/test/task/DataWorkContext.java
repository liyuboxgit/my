package liyu.test.task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class DataWorkContext {
	private static Map<String, ScheduleJob> jobMap = new HashMap<String, ScheduleJob>();

	static {
		  for (int i = 0; i < 5; i++) {
		    ScheduleJob job = new ScheduleJob();
		    job.setJobId("10001" + i);
		    job.setJobName("data_import" + i);
		    job.setJobGroup("dataWork");
		    job.setJobStatus("1");
		    job.setCronExpression("0/5 * * * * ?");
		    job.setDesc("数据导入任务");
		    addJob(job);
		  }
		}
		 
	/**
	 * 添加任务
	 * @param scheduleJob
	 */
	public static void addJob(ScheduleJob scheduleJob) {
		jobMap.put(scheduleJob.getJobGroup() + "_" + scheduleJob.getJobName(), scheduleJob);
	}

	public static List<ScheduleJob> getAllJob() {
		Collection<ScheduleJob> values = jobMap.values();
		List<ScheduleJob> ret = new ArrayList<ScheduleJob>();
		for (Iterator<ScheduleJob> iterator = values.iterator(); iterator.hasNext();) {
			ScheduleJob scheduleJob = (ScheduleJob) iterator.next();
			ret.add(scheduleJob);
		}
		
		
		return ret;
	}
	
}
