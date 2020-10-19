package liyu.test.activity.reject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.HistoryService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.HistoricActivityInstanceEntity;
import org.activiti.engine.impl.persistence.entity.HistoricActivityInstanceEntityManager;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import liyu.test.activity.util.ExportUtil;

public class Test {

	public static void main(String[] args) {
		// main0(args);
		// main1(args); //拷完代码，还未测试
	}

	/**
	 * 正常流程
	 * 
	 * @param args
	 */
	public static void main0(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("springActivity.xml");
		RepositoryService repositoryService = (RepositoryService) context.getBean(RepositoryService.class);
		RuntimeService runtimeService = (RuntimeService) context.getBean(RuntimeService.class);
		TaskService taskService = (TaskService) context.getBean(TaskService.class);

		Map<String, Object> v = new HashMap<String, Object>();
		v.put("je", 1200);

		Deployment deploy = repositoryService.createDeployment()
				.addClasspathResource("liyu/test/activity/reject/reject.bpmn").deploy();

		ProcessInstance instance = runtimeService.startProcessInstanceByKey("reject", v);

		while (true) {
			Task task = taskService.createTaskQuery().processDefinitionKey("reject").singleResult();
			if (task == null) {
				break;
			} else {
				String assignee = task.getAssignee();
				System.out.println(assignee + "完成任务：" + task.getName());
				taskService.complete(task.getId());
				try {
					Thread.sleep(1100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		HistoryService historyService = context.getBean(HistoryService.class);
		List<HistoricProcessInstance> list = historyService.createHistoricProcessInstanceQuery()
				.processDefinitionKey("reject").list();
		if (list.size() > 0) {
			System.out.println(list.get(0).getProcessDefinitionName() + " end at " + list.get(0).getEndTime());
		}

		try {
			ExportUtil.generateProcessHightLightImg(instance.getId(), context, new File("info.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 驳回流程
	 * 
	 * @param args
	 */
	public static void main1(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("springActivity.xml");
		RepositoryService repositoryService = (RepositoryService) context.getBean(RepositoryService.class);
		RuntimeService runtimeService = (RuntimeService) context.getBean(RuntimeService.class);
		TaskService taskService = (TaskService) context.getBean(TaskService.class);

		Map<String, Object> v = new HashMap<String, Object>();
		v.put("je", 1200);

		Deployment deploy = repositoryService.createDeployment()
				.addClasspathResource("liyu/test/activity/reject/reject.bpmn").deploy();

		ProcessInstance instance = runtimeService.startProcessInstanceByKey("reject", v);

		while (true) {
			Task task = taskService.createTaskQuery().processInstanceId(instance.getId()).processDefinitionKey("reject")
					.singleResult();
			if (task.getName().equals("董事长审批")) {
				break;
			} else {
				String assignee = task.getAssignee();
				System.out.println(assignee + "完成任务：" + task.getName());
				taskService.complete(task.getId());
			}
		}

		HistoryService historyService = context.getBean(HistoryService.class);
		List<HistoricProcessInstance> list = historyService.createHistoricProcessInstanceQuery()
				.processInstanceId(instance.getId()).processDefinitionKey("reject").list();
		if (list.size() > 0) {
			System.out.println(list.get(0).getProcessDefinitionName() + " end at " + list.get(0).getEndTime());
		}

		try {
			ExportUtil.generateProcessHightLightImg(instance.getId(), context,
					new File("img" + instance.getId() + "info.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 驳回任务方封装
	 *
	 * @param destinationTaskID
	 *            驳回的任务ID 目标任务ID
	 * @param messageContent
	 *            驳回的理由
	 * @param currentTaskID
	 *            当前正要执行的任务ID
	 * @return 驳回结果 携带下个任务编号
	 */
	public String rejectTask(String destinationTaskID, String currentTaskID, String messageContent,
			ApplicationContext context) {
		HistoryService historyService = context.getBean(HistoryService.class);
		RepositoryService repositoryService = context.getBean(RepositoryService.class);
		ManagementService managementService = context.getBean(ManagementService.class);
		ProcessEngine processEngine = context.getBean(ProcessEngine.class);
		TaskService taskService = context.getBean(TaskService.class);
		RuntimeService runtimeService = context.getBean(RuntimeService.class);

		// region 目标任务实例 historicDestinationTaskInstance 带流程变量，任务变量
		HistoricTaskInstance historicDestinationTaskInstance = historyService.createHistoricTaskInstanceQuery()
				.taskId(destinationTaskID).includeProcessVariables().includeTaskLocalVariables().singleResult();
		// endregion
		// region 正在执行的任务实例 historicCurrentTaskInstance 带流程变量，任务变量
		HistoricTaskInstance historicCurrentTaskInstance = historyService.createHistoricTaskInstanceQuery()
				.taskId(currentTaskID).includeProcessVariables().includeTaskLocalVariables().singleResult();
		// endregion
		// 流程定义ID
		String processDefinitionId = historicCurrentTaskInstance.getProcessDefinitionId();
		// 流程实例ID
		final String processInstanceId = historicCurrentTaskInstance.getProcessInstanceId();
		// 流程定义实体
		ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) repositoryService
				.getProcessDefinition(processDefinitionId);
		// region 根据任务创建时间正序排序获取历史任务实例集合 historicTaskInstanceList 含流程变量，任务变量
		List<HistoricTaskInstance> historicTaskInstanceList = historyService.createHistoricTaskInstanceQuery()
				.processInstanceId(processInstanceId).includeProcessVariables().includeTaskLocalVariables()
				.orderByTaskCreateTime().asc().list();
		// endregion
		// region 历史活动节点实例集合 historicActivityInstanceList
		List<HistoricActivityInstance> historicActivityInstanceList = historyService
				.createHistoricActivityInstanceQuery().processInstanceId(processInstanceId)
				.orderByHistoricActivityInstanceStartTime().asc().list();
		// endregion
		// 获取目标任务的节点信息
		ActivityImpl destinationActivity = processDefinition
				.findActivity(historicDestinationTaskInstance.getTaskDefinitionKey());
		// 定义一个历史任务集合，完成任务后任务删除此集合中的任务
		List<HistoricTaskInstance> deleteHistoricTaskInstanceList = new ArrayList<HistoricTaskInstance>();
		// 定义一个历史活动节点集合，完成任务后要添加的历史活动节点集合
		final List<HistoricActivityInstanceEntity> insertHistoricTaskActivityInstanceList = new ArrayList<HistoricActivityInstanceEntity>();
		// 目标任务编号
		Integer destinationTaskInstanceId = Integer.valueOf(destinationTaskID);
		// 有序
		for (HistoricTaskInstance historicTaskInstance : historicTaskInstanceList) {
			Integer historicTaskInstanceId = Integer.valueOf(historicTaskInstance.getId());
			if (destinationTaskInstanceId <= historicTaskInstanceId) {
				deleteHistoricTaskInstanceList.add(historicTaskInstance);
			}
		}
		// 有序
		for (int i = 0; i < historicActivityInstanceList.size() - 1; i++) {
			HistoricActivityInstance historicActivityInstance = historicActivityInstanceList.get(i);
			// 历史活动节点的任务编号
			Integer historicActivityInstanceTaskId;
			String taskId = historicActivityInstance.getTaskId();
			if (taskId != null) {
				historicActivityInstanceTaskId = Integer.valueOf(taskId);
				if (historicActivityInstanceTaskId <= destinationTaskInstanceId) {
					insertHistoricTaskActivityInstanceList
							.add((HistoricActivityInstanceEntity) historicActivityInstance);
				}
			} else {
				if (historicActivityInstance.getActivityType().equals("startEvent")) {
					insertHistoricTaskActivityInstanceList
							.add((HistoricActivityInstanceEntity) historicActivityInstance);
				} else if (historicActivityInstance.getActivityType().equals("exclusiveGateway")) {
					insertHistoricTaskActivityInstanceList
							.add((HistoricActivityInstanceEntity) historicActivityInstance);
				}
			}
		}
		// 获取流程定义的节点信息
		List<ActivityImpl> processDefinitionActivities = processDefinition.getActivities();
		// 用于保存正在执行的任务节点信息
		ActivityImpl currentActivity = null;
		// 用于保存原来的任务节点的出口信息
		PvmTransition pvmTransition = null;
		// 保存原来的流程节点出口信息
		for (ActivityImpl activity : processDefinitionActivities) {
			if (historicCurrentTaskInstance.getTaskDefinitionKey().equals(activity.getId())) {
				currentActivity = activity;
				// 备份
				pvmTransition = activity.getOutgoingTransitions().get(0);
				// 清空当前任务节点的出口信息
				activity.getOutgoingTransitions().clear();
			}
		}
		// 执行流程转向
		managementService.executeCommand(
				new RejectTaskCMD(historicDestinationTaskInstance, historicCurrentTaskInstance, destinationActivity));
		// 获取正在执行的任务的流程变量
		Map<String, Object> taskLocalVariables = historicCurrentTaskInstance.getTaskLocalVariables();
		// 获取目标任务的流程变量，修改任务不自动跳过，要求审批
		Map<String, Object> processVariables = historicDestinationTaskInstance.getProcessVariables();
		// 获取流程发起人编号
		Integer employeeId = (Integer) processVariables.get(ProcessConstant.PROCESS_START_PERSON);
		processVariables.put(ProcessConstant.SKIP_EXPRESSION, false);
		taskLocalVariables.put(ProcessConstant.SKIP_EXPRESSION, false);
		// 设置驳回原因
		taskLocalVariables.put(ProcessConstant.REJECT_REASON, messageContent);
		// region 流程变量转换
		// 修改下个任务的任务办理人
		processVariables.put(ProcessConstant.DEAL_PERSON_ID, processVariables.get(ProcessConstant.CURRENT_PERSON_ID));
		// 修改下个任务的任务办理人姓名
		processVariables.put(ProcessConstant.DEAL_PERSON_NAME,
				processVariables.get(ProcessConstant.CURRENT_PERSON_NAME));
		// 修改下个任务的任务办理人
		taskLocalVariables.put(ProcessConstant.DEAL_PERSON_ID, processVariables.get(ProcessConstant.CURRENT_PERSON_ID));
		// 修改下个任务的任务办理人姓名
		taskLocalVariables.put(ProcessConstant.DEAL_PERSON_NAME,
				processVariables.get(ProcessConstant.CURRENT_PERSON_NAME));
		// endregion
		// 完成当前任务，任务走向目标任务
		Task task = taskService.createTaskQuery().taskId(currentTaskID).singleResult();
		runtimeService.setVariables(task.getExecutionId(), taskLocalVariables);
		taskService.setVariables(task.getId(), taskLocalVariables);

		if (currentActivity != null) {
			// 清空临时转向信息
			currentActivity.getOutgoingTransitions().clear();
		}
		if (currentActivity != null) {
			// 恢复原来的走向
			currentActivity.getOutgoingTransitions().add(pvmTransition);
		}
		// 删除历史任务
		for (HistoricTaskInstance historicTaskInstance : deleteHistoricTaskInstanceList) {
			historyService.deleteHistoricTaskInstance(historicTaskInstance.getId());
		}
		// 删除活动节点
		processEngine.getManagementService().executeCommand(

				new Command<List<HistoricActivityInstanceEntity>>() {

					public List<HistoricActivityInstanceEntity> execute(CommandContext commandContext) {
						// TODO Auto-generated method stub
						HistoricActivityInstanceEntityManager historicActivityInstanceEntityManager = commandContext
								.getHistoricActivityInstanceEntityManager();
						// 删除所有的历史活动节点
						historicActivityInstanceEntityManager
								.deleteHistoricActivityInstancesByProcessInstanceId(processInstanceId);
						// 提交到数据库
						commandContext.getDbSqlSession().flush();
						// 添加历史活动节点的
						for (HistoricActivityInstanceEntity historicActivityInstance : insertHistoricTaskActivityInstanceList) {
							historicActivityInstanceEntityManager
									.insertHistoricActivityInstance(historicActivityInstance);
						}
						// 提交到数据库
						commandContext.getDbSqlSession().flush();
						return null;
					}
				});
		// 返回下个任务的任务ID
		return currentTaskID; // 这里返回当前
	}

}
