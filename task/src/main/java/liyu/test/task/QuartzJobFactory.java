package liyu.test.task;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

@DisallowConcurrentExecution
public class QuartzJobFactory implements Job {
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {		
		ScheduleJob scheduleJob = (ScheduleJob) context.getMergedJobDataMap().get("scheduleJob");
		BusynessService busynessService = scheduleJob.getApplicationContext().getBean(BusynessService.class);
		
		System.err.println("任务成功运行,任务名称 = [" + scheduleJob.getJobName() + "]"+"["+busynessService.toString()+"]");
	}

}