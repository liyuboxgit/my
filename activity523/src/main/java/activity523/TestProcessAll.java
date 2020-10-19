package activity523;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestProcessAll {
	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("springActivity.xml");
		ProcessEngine processEngine = context.getBean(ProcessEngine.class);
		
		
		Deployment deployment = processEngine.getRepositoryService().createDeployment().addClasspathResource("diagrams/QingJia.bpmn").deploy();
		System.out.println("deploy "+deployment.getId()+","+deployment.getName());
		
		long count = processEngine.getRepositoryService().createDeploymentQuery().processDefinitionKey("myProcess").count();
		System.out.println(count);
		
		
		ProcessInstance instance = processEngine.getRuntimeService().startProcessInstanceByKey("myProcess");
		String procdef = instance.getProcessDefinitionId()+instance.getActivityId();
		System.out.println("``````````````````````````````````"+procdef);
		
		Task task = processEngine.getTaskService().createTaskQuery().processInstanceId(instance.getId()).singleResult();
		while(task!=null) {
			Map<String,Object> p = new HashMap<String,Object>();
			p.put("k", task.getName());
			System.out.println("===================================="+task.getName());
			processEngine.getTaskService().complete(task.getId(),p);
			task = processEngine.getTaskService().createTaskQuery().processInstanceId(instance.getId()).singleResult();
		}
		
		HistoricProcessInstance result = processEngine.getHistoryService().createHistoricProcessInstanceQuery().processInstanceId(instance.getId()).singleResult();
		System.out.println("-------------------------"+result.getEndTime());
		
		context.close();
		
		
	}
}
