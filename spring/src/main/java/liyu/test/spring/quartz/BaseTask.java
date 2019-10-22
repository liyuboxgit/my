package liyu.test.spring.quartz;

public abstract class BaseTask {
	private TaskService taskService;

	public TaskService getTaskService() {
		return taskService;
	}

	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}
	public abstract String getJobName();
	public abstract String getCronExpression();
	public abstract String getDesc();
	public abstract String getJobGroupName();
	/**
	 * 任务操作
	 * @throws Exception
	 */
	public abstract void run() throws Exception;

}
