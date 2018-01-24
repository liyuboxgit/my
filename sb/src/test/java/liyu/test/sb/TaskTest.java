package liyu.test.sb;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import liyu.test.sb.task.BaseTask;
import liyu.test.sb.task.ExecuteJob;
import liyu.test.sb.task.TaskService;

@RunWith(SpringJUnit4ClassRunner.class)  
@SpringBootTest(classes=MainConfigure.class)
public class TaskTest {
	@Autowired
	private SchedulerFactoryBean schedulerFactoryBean;
	@Autowired
	private TaskService taskService;
	/**
	 * note jobGroupName can bean not set,is not necessary.
	 * @throws Exception
	 */
	@Test
	public void test() throws Exception {
		BaseTask task = (BaseTask) Class.forName("liyu.test.sb.task.SimpleTask").newInstance();
		task.setTaskService(taskService);
		
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		
		JobDetail jobDetail = scheduler.getJobDetail(JobKey.jobKey(task.getJobName(), task.getJobGroupName()));
		//启动任务
		if(jobDetail == null) {
			jobDetail = JobBuilder.newJob(ExecuteJob.class).withIdentity(task.getJobName(), task.getJobGroupName()).build();
			jobDetail.getJobDataMap().put("scheduleJob", task);
			
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(task.getCronExpression());
			CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(task.getJobName(), task.getJobGroupName())
					.withSchedule(scheduleBuilder).build();
			scheduler.scheduleJob(jobDetail, trigger);
			
			Thread.sleep(20000);
		}
		//如果任务已经启动，停止其再重新启动
		jobDetail = scheduler.getJobDetail(JobKey.jobKey(task.getJobName(), task.getJobGroupName()));
		if(jobDetail!=null) {
			scheduler.deleteJob(jobDetail.getKey());
			jobDetail = JobBuilder.newJob(ExecuteJob.class).withIdentity(task.getJobName(), task.getJobGroupName()).build();
			jobDetail.getJobDataMap().put("scheduleJob", task);
			
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0/1 * * * * ?");
			CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(task.getJobName(), task.getJobGroupName())
					.withSchedule(scheduleBuilder).build();
			scheduler.scheduleJob(jobDetail, trigger);
			
			Thread.sleep(20000);
		}
		//如果任务已经启动，将其删除
		jobDetail = scheduler.getJobDetail(JobKey.jobKey(task.getJobName(), task.getJobGroupName()));
		if(jobDetail!=null) {
			scheduler.deleteJob(jobDetail.getKey());
		}	
		
		Thread.sleep(20000);
	}
}
