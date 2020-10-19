package activity523.rebuild;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.CommonDataSource;
import javax.sql.DataSource;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricTaskInstanceQuery;
import org.activiti.engine.impl.HistoricTaskInstanceQueryImpl;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.HistoricTaskInstanceEntity;
import org.activiti.engine.impl.persistence.entity.SuspensionState;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
/**
 * 驳回到上一环节
 * @author liyu
 *
 */
public class RejectCMD implements Command<Void>{
	private String taskId;
	private String rejectReason;
	private JdbcTemplate tplt;
	
	public RejectCMD(String taskId,String rejectReason,JdbcTemplate tplt) {
		this.taskId = taskId;
		this.rejectReason = rejectReason;
		this.tplt = tplt;
	}
	public Void execute(CommandContext commandContext) {
		TaskEntity task = commandContext.getTaskEntityManager().findTaskById(taskId);
		
		/*List<HistoricTaskInstance> list = commandContext.getHistoricTaskInstanceEntityManager()
				.findHistoricTaskInstancesByNativeQuery(
						new PMap().set("sql", "select * from act_hi_taskinst where EXECUTION_ID_="+task.getExecutionId()+" order by cast(id_ as signed) desc").get()
						, 0, Integer.MAX_VALUE);*/
				 
		String sql = "select * from act_hi_taskinst where EXECUTION_ID_='"+task.getExecutionId()+"' order by cast(id_ as signed) desc";		
		List<HistoricTaskInstance> list = this.tplt.query(sql, new RowMapper<HistoricTaskInstance>() {
			public HistoricTaskInstance mapRow(ResultSet rs, int rowNum) throws SQLException {
				HistoricTaskInstanceEntity o = new HistoricTaskInstanceEntity();
				o.setId(rs.getString("ID_"));
				o.setProcessDefinitionId(rs.getString("PROC_DEF_ID_"));
				o.setProcessInstanceId(rs.getString("PROC_INST_ID_"));
				o.setExecutionId(rs.getString("EXECUTION_ID_"));
				o.setName(rs.getString("NAME_"));
				o.setParentTaskId(rs.getString("PARENT_TASK_ID_"));
				o.setDescription(rs.getString("DESCRIPTION_"));
				o.setOwner(rs.getString("OWNER_"));
				o.setAssignee(rs.getString("ASSIGNEE_"));
				o.setStartTime(rs.getDate("START_TIME_"));
				o.setClaimTime(rs.getDate("CLAIM_TIME_"));
				o.setEndTime(rs.getDate("END_TIME_"));
				o.setDurationInMillis(rs.getLong("DURATION_"));
				o.setDeleteReason(rs.getString("DELETE_REASON_"));
				o.setTaskDefinitionKey(rs.getString("TASK_DEF_KEY_"));
				o.setFormKey(rs.getString("FORM_KEY_"));
				o.setPriority(rs.getInt("PRIORITY_"));
				o.setDueDate(rs.getDate("DUE_DATE_"));
				o.setCategory(rs.getString("CATEGORY_"));
				o.setTenantId(rs.getString("TENANT_ID_"));
				return o;
			}
		});
		
		
		
		
		
		if(list.size()>1 && list.get(0).getEndTime()==null) {
			System.out.println("ok 当前任务节点是"+list.get(0).getName()+"可以驳回到上一任务节点"+list.get(1).getName());
			
			//1，历史变量表1，历史任务表1，历史节点表1，历史id表1，任务表1，id表1，变量表1，---插入（新）
			//2，历史任务表0，历史节点表0，实例表---更新（旧）
			//3，任务表0---删除（旧）
			
			//删除新变量表、id表
			// TODO tplt.update("delete from ACT_RU_VARIABLE where TASK_ID_ = ?",list.get(0).getId());
			// TODO tplt.update("delete from ACT_RU_IDENTITYLINK where TASK_ID_ = ?",list.get(0).getId());
			
			//删除新任务表
			tplt.update("delete from ACT_RU_TASK where ID_ = ?",list.get(0).getId());
			
			//从旧历史任务表恢复创建 任务表
			MyTaskEntity taskEntity = /*TaskEntity.create(new Date())*/ new MyTaskEntity();
			copy(taskEntity,list.get(1));
			//commandContext.getTaskEntityManager().insert(taskEntity);
			Object[] taskEntityPO = new Object[] {
					taskEntity.getId(),
					taskEntity.getName(),
					taskEntity.getParentTaskId(),
					taskEntity.getDescription(),
					taskEntity.getPriority(),
					taskEntity.getCreateTime(),
					taskEntity.getOwner(),
					taskEntity.getAssignee(),
					taskEntity.getDelegationStateString(),
					taskEntity.getExecutionId(),
					taskEntity.getProcessInstanceId(),
					taskEntity.getProcessDefinitionId(),
					taskEntity.getTaskDefinitionKey(),
					taskEntity.getDueDate(),
					taskEntity.getCategory(),
					taskEntity.getSuspensionState(),
					taskEntity.getTenantId(),
					taskEntity.getFormKey()
			};
					
			tplt.update("insert into ACT_RU_TASK (ID_, REV_, NAME_, PARENT_TASK_ID_, DESCRIPTION_, PRIORITY_, CREATE_TIME_, OWNER_, ASSIGNEE_, DELEGATION_, EXECUTION_ID_, PROC_INST_ID_, PROC_DEF_ID_, TASK_DEF_KEY_, DUE_DATE_, CATEGORY_, SUSPENSION_STATE_, TENANT_ID_, FORM_KEY_) values (?, 1, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )",taskEntityPO);
			
			//实例表更新
			//ExecutionEntity execution = task.getExecution();
			//execution.setActivity(new ActivityImpl(list.get(1).getTaskDefinitionKey(), null));
			tplt.update("update ACT_RU_EXECUTION set ACT_ID_ = ? where ID_= ?",list.get(1).getTaskDefinitionKey(),list.get(1).getExecutionId());
			
			//更新旧历史任务表，旧历史节点表
			//HistoricTaskInstanceEntity historicTaskInstance = (HistoricTaskInstanceEntity) list.get(1);
			//historicTaskInstance.setEndTime(null);
			//historicTaskInstance.setDurationInMillis(null);
			tplt.update("update ACT_HI_TASKINST set END_TIME_ = null,DURATION_ = null where ID_ = ?",list.get(1).getId());
			/*commandContext.getHistoricActivityInstanceEntityManager().findHistoricActivityInstancesByNativeQuery(parameterMap, 0, 1);*/		
			tplt.update("update ACT_HI_ACTINST set END_TIME_ = null,DURATION_ = null where TASK_ID_ = ?",list.get(1).getId());
			
			//删除新历史任务表，新历史节点表
			tplt.update("delete from ACT_HI_TASKINST where ID_ = ?",list.get(0).getId());
			tplt.update("delete from ACT_HI_ACTINST where TASK_ID_ = ?",list.get(0).getId());
			
			//恢复旧变量表、旧id表
			// TODO 
		}
		return null;
	}
	
	private void copy(MyTaskEntity taskEntity, HistoricTaskInstance historicTaskInstance) {
		taskEntity.setExecutionId(historicTaskInstance.getExecutionId());
		taskEntity.setProcessInstanceId(historicTaskInstance.getProcessInstanceId());
		taskEntity.setProcessDefinitionId(historicTaskInstance.getProcessDefinitionId());
		taskEntity.setName(historicTaskInstance.getName());
		taskEntity.setParentTaskId(historicTaskInstance.getParentTaskId());
		taskEntity.setDescription(historicTaskInstance.getDescription());
		taskEntity.setTaskDefinitionKey(historicTaskInstance.getTaskDefinitionKey());
		taskEntity.setOwner(historicTaskInstance.getOwner());
		taskEntity.setAssignee(historicTaskInstance.getAssignee());
		taskEntity.setDelegationStateString(null); // TODO
		taskEntity.setPriority(historicTaskInstance.getPriority());
		taskEntity.setCreateTime(new Date());
		taskEntity.setDueDate(historicTaskInstance.getDueDate());
		taskEntity.setCategory(historicTaskInstance.getCategory());
		taskEntity.setSuspensionState(SuspensionState.ACTIVE.getStateCode());
		taskEntity.setTenantId(historicTaskInstance.getTenantId());
		taskEntity.setFormKey(historicTaskInstance.getFormKey());
		taskEntity.setId(historicTaskInstance.getId());
	}

	public static void main(String[] args) {
		/*ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("springActivity.xml");
		ProcessEngine processEngine = context.getBean(ProcessEngine.class);
		
		List<Task> list = processEngine.getTaskService().createTaskQuery().taskAssignee("ub").list();
		
		if(list.size()==1) {
			DataSource bean = context.getBean(DataSource.class);
			JdbcTemplate jdbcTemplate = new JdbcTemplate(bean);
			processEngine.getManagementService().executeCommand(new RejectCMD(list.get(0).getId(), "",jdbcTemplate));
		}

		context.close();	*/	
		
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("springActivity.xml");
		ProcessEngine processEngine = context.getBean(ProcessEngine.class);
		
		List<Task> list = processEngine.getTaskService().createTaskQuery().taskAssignee("ua").list();
		
		if(list.size()==1) {
			processEngine.getTaskService().complete(list.get(0).getId());
		}
		
		List<Task> list2 = processEngine.getTaskService().createTaskQuery().taskAssignee("ub").list();
		
		if(list2.size()==1) {
			processEngine.getTaskService().complete(list2.get(0).getId());
		}

		List<HistoricProcessInstance> list3 = processEngine.getHistoryService().createHistoricProcessInstanceQuery().list();
		if(list3.size()==1) {
			System.out.println(list3.get(0).getEndTime());
		}
		
		context.close();	
	}
}
