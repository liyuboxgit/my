package liyu.test.activity.TEST1;

import java.util.HashMap;
import java.util.List;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DemoTest {
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("springActivity.xml");
		RepositoryService repositoryService = (RepositoryService) context.getBean(RepositoryService.class);

		Deployment deploy = repositoryService.createDeployment().addClasspathResource("liyu/test/activity/TEST1/test.bpmn").deploy();
		System.out.println(repositoryService.createDeploymentQuery().deploymentId(deploy.getId()).list().size());
		System.out.println(repositoryService.createProcessDefinitionQuery().deploymentId(deploy.getId()).list().size());
		
		String pdfId = repositoryService.createProcessDefinitionQuery().deploymentId(deploy.getId()).list().get(0).getId();
		
		RuntimeService runtimeService = context.getBean(RuntimeService.class);
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("出口退税流程1");
		
		TaskService taskService = context.getBean(TaskService.class);
		List<Task> list = taskService.createTaskQuery().processInstanceId(processInstance.getId()).taskAssignee("00000001").list();
		System.out.println(list.size());
		
		Task task = list.get(0);
		HashMap<String,Object> hashMap = new HashMap<String,Object>();
		hashMap.put("a", 1);
		
		taskService.complete(task.getId(),hashMap);
		
		List<Task> list2 = taskService.createTaskQuery().processInstanceId(processInstance.getId()).taskAssignee("lsuserId").list();
		System.out.println(list2.size());
		
		taskService.complete(list2.get(0).getId());
		
		HistoryService historyService = context.getBean(HistoryService.class);
		List<HistoricProcessInstance> instanceId = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstance.getId()).list();
		System.out.println(instanceId.get(0).getEndTime());
	}
}
