package liyu.test.sb.task;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author liyu
 * this class maybe stored in database.
 */
public class SimpleTask extends BaseTask{
	private static DateFormat sm = new SimpleDateFormat("MM/dd HH:mm:ss");
	
	private String jobName;
	private String jobGroupName;
	private String cronExpression;
	private String desc;
	
	@Override
	public void run() throws Exception{
		Date now = new Date();
		System.out.println(this.getClass().getName() +"doTask run at "+sm.format(now));
		System.out.println(this.getDesc());
		System.out.println("Invoke " +this.getTaskService().getClass().getSimpleName()+" finish work scucess."+
				this.getTaskService().getClass().getSimpleName() + " is managed by spring.");
	}

	public SimpleTask() {
		this.jobName = "simple";
		this.jobGroupName = "test";
		this.cronExpression = "0/5 * * * * ?";
		this.desc = "just for test,the filed value shoud be stored in database";
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getJobGroupName() {
		return jobGroupName;
	}

	public void setJobGroupName(String jobGroupName) {
		this.jobGroupName = jobGroupName;
	}

	public String getCronExpression() {
		return cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
}
