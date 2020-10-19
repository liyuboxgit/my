package liyu.test.activity.GATEWAY;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.task.Task;
import org.activiti.image.ProcessDiagramGenerator;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import liyu.test.activity.util.ExportUtil;

public class Test {
	//1,流程变量、 排他网关、 流程资源（bpmn文件和图片）导出、轨迹高亮图片
	public static void main(String[] args) throws IOException {
		ApplicationContext context = new ClassPathXmlApplicationContext("springActivity.xml");
		// Get Activiti services
		RepositoryService repositoryService = (RepositoryService) context.getBean(RepositoryService.class);
		RuntimeService runtimeService = (RuntimeService) context.getBean(RuntimeService.class);
		TaskService taskService = (TaskService) context.getBean(TaskService.class);
		// Deploy the process definition
		Deployment deploy = repositoryService.createDeployment()
				.addClasspathResource("liyu/test/activity/GATEWAY/gateWayTest.bpmn")
				.addClasspathResource("liyu/test/activity/GATEWAY/gateWayTest.png").deploy();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("day", 4);
		String procId = runtimeService.startProcessInstanceByKey("myProcess").getId();
		for (int i = 0; i < 5; i++) {
			List<Task> tasks = taskService.createTaskQuery().processInstanceId(procId).list();
			for (Task task : tasks) {
				System.out.println("===============================================================>"+task.getName());
				if(i==0)
					taskService.complete(task.getId(),map);
				else if(i==2){
					///导出流程图片
					try {
						//ExportUtil.exportImg(procId,context,new File("h:/monitor.png"));
						taskService.complete(task.getId());						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else{
					taskService.complete(task.getId());
				}
			}
		}
		/*HistoryService historyService = context.getBean(HistoryService.class);
		HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
				.processInstanceId(procId).singleResult();
		System.out.println("Process instance end time: " + historicProcessInstance.getEndTime());
		
		// 从仓库中找需要展示的文件
		String deploymentId = deploy.getId();
		List<String> names = repositoryService.getDeploymentResourceNames(deploymentId);
		
		for (String name : names) {
			if (name.indexOf(".bpmn") >= 0) {
				File f = new File("h:/bpmn.bpmn");
				// 通过部署ID和文件名称得到文件的输入流
				InputStream in = repositoryService.getResourceAsStream(deploymentId, name);
				OutputStream os = new FileOutputStream(f);
				int bytesRead = 0;
				byte[] buffer = new byte[8192];
				while ((bytesRead = in.read(buffer, 0, 8192)) != -1) {
					os.write(buffer, 0, bytesRead);
				}
				os.close();
				in.close();
				System.out.println("生成定义文件：h:/bpmn.bpmn");
			}
			if (name.indexOf(".png") >= 0) {
				File f = new File("h:/png.png");
				// 通过部署ID和文件名称得到文件的输入流
				InputStream in = repositoryService.getResourceAsStream(deploymentId, name);
				OutputStream os = new FileOutputStream(f);
				int bytesRead = 0;
				byte[] buffer = new byte[8192];
				while ((bytesRead = in.read(buffer, 0, 8192)) != -1) {
					os.write(buffer, 0, bytesRead);
				}
				os.close();
				in.close();
				System.out.println("生成图片文件：h:/png.png");
			}
		}*/
	}
}
