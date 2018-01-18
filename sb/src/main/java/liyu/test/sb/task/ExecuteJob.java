package liyu.test.sb.task;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class ExecuteJob implements Job{

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		BaseTask task = (BaseTask)context.getMergedJobDataMap().get("scheduleJob");
		try {
			task.run();
		} catch (Exception e) {
			throw new JobExecutionException(e.getMessage(), e.getCause());
		}
	}

}
