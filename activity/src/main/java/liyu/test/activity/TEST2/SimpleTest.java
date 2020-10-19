package liyu.test.activity.TEST2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
/**
 * 
 * @ClassName: SimpleTest 
 * @Description: TODO
 * @author: liyu
 * @date: 2017年10月12日 下午4:03:26
 * 
 * 
 * activity表：
 * act_re_procdef,act_re_deployment,act_ge_bytearray流程定义相关
 */
//taskCandidateGroup 流程变量
public class SimpleTest {
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("springActivity.xml");
		

		// Get Activiti services
		RepositoryService repositoryService = (RepositoryService) context.getBean(RepositoryService.class);
		RuntimeService runtimeService = (RuntimeService) context.getBean(RuntimeService.class);

		// Deploy the process definition
		repositoryService.createDeployment()
			.addClasspathResource("liyu/test/activity/TEST2/test.bpmn").deploy();
			
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("ga", "ga");
		map.put("gb", "gb");
		
		// Start a process instance
		String procId = runtimeService.startProcessInstanceByKey("myProcess",map).getId();

		// Get the first task
		TaskService taskService = context.getBean(TaskService.class);
		List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup("ga").list();
		for (Task task : tasks) {
			System.out.println("Following task is available for ga group: " + task.getName());

			// claim it
			taskService.claim(task.getId(), "fozzie");
		}
		// Verify Fozzie can now retrieve the task
		tasks = taskService.createTaskQuery().taskAssignee("fozzie").list();
		for (Task task : tasks) {
			System.out.println("Task for fozzie: " + task.getName());

			// Complete the task
			taskService.complete(task.getId());
		}

		System.out
				.println("Number of tasks for fozzie: " + taskService.createTaskQuery().taskAssignee("fozzie").count());

		// Retrieve and claim the second task
		tasks = taskService.createTaskQuery().taskCandidateGroup("gb").list();
		for (Task task : tasks) {
			System.out.println("Following task is available for gb group: " + task.getName());
			taskService.claim(task.getId(), "kermit");
		}

		// Completing the second task ends the process
		for (Task task : tasks) {
			taskService.complete(task.getId());
		}

		// verify that the process is actually finished
		HistoryService historyService = context.getBean(HistoryService.class);
		HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
				.processInstanceId(procId).singleResult();
		System.out.println("Process instance end time: " + historicProcessInstance.getEndTime());
		
	}
}
