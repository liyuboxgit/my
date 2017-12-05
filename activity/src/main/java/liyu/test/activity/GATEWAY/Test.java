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

public class Test {
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
						byte[] bytes = exportImg(procId,context);
						File file = new File("e:/monitor.png");
						FileUtils.writeByteArrayToFile(file, bytes);
						taskService.complete(task.getId());						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else{
					taskService.complete(task.getId());
				}
			}
		}
		HistoryService historyService = context.getBean(HistoryService.class);
		HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
				.processInstanceId(procId).singleResult();
		System.out.println("Process instance end time: " + historicProcessInstance.getEndTime());
		
		// 从仓库中找需要展示的文件
		String deploymentId = deploy.getId();
		List<String> names = repositoryService.getDeploymentResourceNames(deploymentId);
		
		for (String name : names) {
			if (name.indexOf(".bpmn") >= 0) {
				File f = new File("e:/bpmn.bpmn");
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
				System.out.println("生成定义文件：e:/bpmn.bpmn");
			}
			if (name.indexOf(".png") >= 0) {
				File f = new File("e:/png.png");
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
				System.out.println("生成图片文件：e:/png.png");
			}
		}
	}
	
	private static  byte[] exportImg(String processInstanceId, ApplicationContext context){
		return traceProcessImage(processInstanceId,context);
	}
	
	 /* 得到带有高亮节点的流程图
	 * @param processInstanceId	流程实例id
	 * @return
	 */
	public static byte[] traceProcessImage(String processInstanceId, ApplicationContext context) {
		TaskService taskService = (TaskService) context.getBean(TaskService.class);
		RepositoryService repositoryService = (RepositoryService) context.getBean(RepositoryService.class);
		HistoryService historyService = (HistoryService) context.getBean(HistoryService.class);
		RuntimeService runtimeService = (RuntimeService) context.getBean(RuntimeService.class);
		SpringProcessEngineConfiguration processEngineConfiguration = context.getBean(SpringProcessEngineConfiguration.class);
		String taskId = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult().getId();
		if (StringUtils.isBlank(taskId))
            throw new IllegalArgumentException("任务ID不能为空！");
        // 当前任务节点
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (task == null)
            throw new IllegalArgumentException("任务不存在！");

        BpmnModel bpmnModel = repositoryService.getBpmnModel(task.getProcessDefinitionId());
        // List<String> activeActivityIds = runtimeService.getActiveActivityIds(task.getExecutionId());

        // 必须添加此行才能取到配置文件中的字体，待根本解决问题后删除
        // Context.setProcessEngineConfiguration(processEngineConfiguration);
        // return ProcessDiagramGenerator.generateDiagram(bpmnModel, "PNG", activeActivityIds);

        // 经过的节点
        List<String> activeActivityIds = new ArrayList<String>();
        List<String> finishedActiveActivityIds = new ArrayList<String>();

        // 已执行完的任务节点
        List<HistoricActivityInstance> finishedInstances = historyService.createHistoricActivityInstanceQuery().processInstanceId(task.getProcessInstanceId()).finished().list();
        for (HistoricActivityInstance hai : finishedInstances) {
            finishedActiveActivityIds.add(hai.getActivityId());
        }

        // 已完成的节点+当前节点
        activeActivityIds.addAll(finishedActiveActivityIds);
        activeActivityIds.addAll(runtimeService.getActiveActivityIds(task.getProcessInstanceId()));

        // 经过的流
        ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity) repositoryService.getProcessDefinition(task.getProcessDefinitionId());
        List<String> highLightedFlows = new ArrayList<String>();
        getHighLightedFlows(processDefinitionEntity.getActivities(), highLightedFlows, activeActivityIds);

        ProcessDiagramGenerator pdg = processEngineConfiguration.getProcessDiagramGenerator();
        InputStream inputStream = pdg.generateDiagram(bpmnModel, "jpg", finishedActiveActivityIds,highLightedFlows,"宋体","宋体",null,null, 1.0);
       try {
           return IOUtils.toByteArray(inputStream);
        } catch (Exception e) {
            throw new RuntimeException("生成流程图异常！", e);
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
	}
	

	/*
     * 递归查询经过的流
     */
    private static void getHighLightedFlows(List<ActivityImpl> activityList, List<String> highLightedFlows, List<String> historicActivityInstanceList) {
        for (ActivityImpl activity : activityList) {
            if (activity.getProperty("type").equals("subProcess")) {
                // get flows for the subProcess
                getHighLightedFlows(activity.getActivities(), highLightedFlows, historicActivityInstanceList);
            }

            if (historicActivityInstanceList.contains(activity.getId())) {
                List<PvmTransition> pvmTransitionList = activity.getOutgoingTransitions();
                for (PvmTransition pvmTransition : pvmTransitionList) {
                    String destinationFlowId = pvmTransition.getDestination().getId();
                    if (historicActivityInstanceList.contains(destinationFlowId)) {
                        highLightedFlows.add(pvmTransition.getId());
                    }
                }
            }
        }
    }
}
