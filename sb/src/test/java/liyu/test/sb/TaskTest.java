package liyu.test.sb;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
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
	
	@Test
	public void test() throws Exception {
		BaseTask task = (BaseTask) Class.forName("liyu.test.sb.task.SimpleTask").newInstance();
		task.setTaskService(taskService);
		
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		TriggerKey triggerKey = TriggerKey.triggerKey(task.getJobName(), task.getJobGroupName());
		CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
		
		if (trigger == null) {
			JobDetail jobDetail = JobBuilder.newJob(ExecuteJob.class)
					.withIdentity(task.getJobName(), task.getJobGroupName()).build();
			jobDetail.getJobDataMap().put("scheduleJob", task);
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(task.getCronExpression());
			
			trigger = TriggerBuilder.newTrigger().withIdentity(task.getJobName(), task.getJobGroupName())
					.withSchedule(scheduleBuilder).build();
			scheduler.scheduleJob(jobDetail, trigger);
		}
		
		Thread.sleep(20000);
	}
}
