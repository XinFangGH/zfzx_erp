package com.zhiwei.credit.model.flow;

public class JbpmTask {

	private Long taskId;
	private String assignee;

	

	public Long getTaskId() {
		return taskId;
	}
	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}
	public String getAssignee() {
		return assignee;
	}
	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	public JbpmTask(){
	}
}
