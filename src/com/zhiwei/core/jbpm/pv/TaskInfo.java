package com.zhiwei.core.jbpm.pv;
/*
 *  北京互融时代软件有限公司
 *  Copyright (C) 2008-2011 BeiJin HuRong Software Company
*/
import java.util.Date;
import org.jbpm.pvm.internal.task.TaskImpl;

public class TaskInfo {
	//用于显示任务名称，一般会包括流程名称在前
	private String taskName;
	//活动名称
	private String activityName;
	private String assignee;
	private Date createTime;
	private Date dueDate;
	private String executionId;
	/**
	 * process instance id
	 */
	private String piId;
	
	private Long taskId;
	
	/**
	 * 任务是否可由多人来执行，默认为0,则表示该任务只能由特定的人来执行。
	 */
	private Short isMultipleTask=0;
	
	//候选人员
	private String candidateUsers="";//taskImpl.getParticipations();
	//候选角色
	private String candidateRoles="";
	/**
	 * 	标注该节点是否为会签节点
	 */
	private Short isSigned;
	
	public TaskInfo() {
	}
	
	public TaskInfo(TaskImpl taskImpl){
		this.taskName=taskImpl.getActivityName();
		
		this.activityName=taskImpl.getActivityName();
		this.assignee=taskImpl.getAssignee();
		this.dueDate=taskImpl.getDuedate();
		this.createTime=taskImpl.getCreateTime();
		if(taskImpl.getSuperTask()!=null){
			this.piId=taskImpl.getSuperTask().getProcessInstance().getId();
			this.executionId=taskImpl.getSuperTask().getExecutionId();
		}else{
			this.piId=taskImpl.getProcessInstance().getId();
			this.executionId=taskImpl.getExecutionId();
		}
		
		this.taskId=taskImpl.getDbid();
		
		if(taskImpl.getParticipations().size()>0){//可由其他人来执行
			isMultipleTask=1;
		}
	}
    
	public Short getIsSigned() {
		return isSigned;
	}

	public void setIsSigned(Short isSigned) {
		this.isSigned = isSigned;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public String getAssignee() {
		return assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public String getExecutionId() {
		return executionId;
	}

	public void setExecutionId(String executionId) {
		this.executionId = executionId;
	}

	public String getCandidateUsers() {
		return candidateUsers;
	}

	public void setCandidateUsers(String candidateUsers) {
		this.candidateUsers = candidateUsers;
	}

	public String getCandidateRoles() {
		return candidateRoles;
	}

	public void setCandidateRoles(String candidateRoles) {
		this.candidateRoles = candidateRoles;
	}

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public Short getIsMultipleTask() {
		return isMultipleTask;
	}

	public void setIsMultipleTask(Short isMultipleTask) {
		this.isMultipleTask = isMultipleTask;
	}

	public String getPiId() {
		return piId;
	}

	public void setPiId(String piId) {
		this.piId = piId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	
}
