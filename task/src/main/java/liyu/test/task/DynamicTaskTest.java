package liyu.test.task;

import java.util.List;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

public class DynamicTaskTest {
	@SuppressWarnings("resource")
	public static void main(String[] args) throws SchedulerException, InterruptedException {
		ApplicationContext context = new ClassPathXmlApplicationContext("dynamic-beans.xml");
		SchedulerFactoryBean schedulerFactoryBean = context.getBean(SchedulerFactoryBean.class);

		Scheduler scheduler = schedulerFactoryBean.getScheduler();

		List<ScheduleJob> jobList = DataWorkContext.getAllJob();

		for (ScheduleJob job : jobList) {
			job.setApplicationContext(context);
			TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());

			CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
			// 没有任务运行，则创建任务
			if (trigger == null) {
				JobDetail jobDetail = JobBuilder.newJob(QuartzJobFactory.class)
						.withIdentity(job.getJobName(), job.getJobGroup()).build();
				jobDetail.getJobDataMap().put("scheduleJob", job);

				CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());

				trigger = TriggerBuilder.newTrigger().withIdentity(job.getJobName(), job.getJobGroup())
						.withSchedule(scheduleBuilder).build();
				scheduler.scheduleJob(jobDetail, trigger);
			}
		}

		Thread.sleep(1000);
		//修改制定任务
		TriggerKey triggerKey = TriggerKey.triggerKey("data_import3", "dataWork");
		CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
		if (trigger != null) {
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0/2 * * * * ?");
			trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
			scheduler.rescheduleJob(triggerKey, trigger);
		}
		//删除任务
		Thread.sleep(10000);
		for (ScheduleJob job : jobList) {
			if (!"data_import3".equals(job.getJobName())) {
				JobKey jobKey = JobKey.jobKey(job.getJobName(), job.getJobGroup());
				scheduler.deleteJob(jobKey);
			}
		}
		
		/*//暂停任务
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
		scheduler.pauseJob(jobKey);*/
		
		/*//恢复任务
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
		scheduler.resumeJob(jobKey);*/
		
		/*//删除任务，所对应的trigger也将被删除
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
		scheduler.deleteJob(jobKey);*/
		
		/*//立即运行任务
		//这里的立即运行，只会运行一次
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
		scheduler.triggerJob(jobKey);*/
	}
}
