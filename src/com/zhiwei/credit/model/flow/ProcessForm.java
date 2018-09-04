package com.zhiwei.credit.model.flow;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/

import java.util.Date;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;


/**
 * ProcessForm Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * 
 */
public class ProcessForm extends com.zhiwei.core.model.BaseModel {
	/**
	 * 驳回状态
	 */
	public static final Short STATUS_BACK=-1;
	/**
	 * 初始状态
	 */
	public static final Short STATUS_INIT=0;
	/**
	 * 通过状态
	 */
	public static final Short STATUS_PASS=1;
	
	/**
	 * 任务已跳转状态
	 * add by lu 2012.04.23
	 */
	public static final Short STATUS_JUMP=2;
	
	/**
	 * 打回重做
	 * add by lu 2012.04.25
	 */
	public static final Short STATUS_AFRESH=3;
	
	/**
	 * 追回
	 * add by lu 2012.07.11
	 */
	public static final Short STATUS_RECOVER=4;
	
	 /**
	 * 任务换人
	 * add by lu 2012.08.29
	 */
	public static final Short STATUS_TASKASSIGN=5;
	
	/**
	 * 项目换人
	 * add by lu 2012.08.29
	 */
	public static final Short STATUS_PROJECTASSIGN=6;
	
	/**
	 * 项目终止
	 * add by lu 2012.08.29
	 */
	public static final Short STATUS_PROJECTSTOP=7;
	
    protected Long formId;
	protected String activityName;
	protected Date createtime;
	
	protected Long creatorId;
	protected String creatorName;
	public Long getDurtimes() {
		return durtimes;
	}

	public void setDurtimes(Long durtimes) {
		this.durtimes = durtimes;
	}

	//2.0 add the fields below for the process history
	protected Date endtime;
	protected Long durtimes;


	protected String fromTask;
	protected String fromTaskId;
	protected String taskId;
	protected String transTo;
	
	/**
	 * status
	 * -1：驳回
	 * 0   ：初始状态
	 * 1   ：审批通过
	 * 2   ：流程跳转
	 * 3   ：打回重做
	 * 4   ：追回
	 * 5   ：任务换人
	 * 6   ：项目换人
	 * 7   ：项目终止
	 */
	protected Short status;
	
	protected String comments;
	
	protected Long preFormId;
	
	protected Date taskLimitTime;
	
	protected Short safeLevel;//安全级别
	
	protected com.zhiwei.credit.model.flow.ProcessRun processRun;

	//protected java.util.Set formDatas = new java.util.HashSet();
	protected java.util.Set formFiles = new java.util.HashSet();

	protected String previousCreator;//上一个节点处理人
	
	protected String taskSequenceNodeKey;//流程节点的顺序及key add by lu 2012.07.05
	
	protected String projectAssign;//项目换人记录(1_2：由原来的处理人id 1换为2) add by lu 2012.08.20
	
	protected String countersignRefuse;//会签不通过,项目终止的标记。更新意见与说明与备份系统日志说明。add by lu 2013.01.11
	 
	//已办结任务提高效率，避免得到已办结任务再次查询VCommonProjectFlow进行数据匹配，
	//在返回的list中直接处理以下两个字段。add by lu 2013.06.06
	protected String projectName;
	//protected String projectNumber;
	protected String businessType;
	//流程任务runId
	protected Long processRunId;
	//判断是否逾期
	protected Long minTime;
	 
	public Long getMinTime() {
		return minTime;
	}

	public void setMinTime(Long minTime) {
		this.minTime = minTime;
	}

	public Long getProcessRunId() {
		return processRunId;
	}

	public void setProcessRunId(Long processRunId) {
		this.processRunId = processRunId;
	}

	public Date getCreatetime() {
		if(createtime==null){
			createtime=new Date();
		}
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	/**
	 * Default Empty Constructor for class ProcessForm
	 */
	public ProcessForm () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class ProcessForm
	 */
	public ProcessForm (
		 Long in_formId
        ) {
		this.setFormId(in_formId);
    }

	
	public com.zhiwei.credit.model.flow.ProcessRun getProcessRun () {
		return processRun;
	}	
	
	public void setProcessRun (com.zhiwei.credit.model.flow.ProcessRun in_processRun) {
		this.processRun = in_processRun;
	}

//	public java.util.Set getFormDatas () {
//		return formDatas;
//	}	
//	
//	public void setFormDatas (java.util.Set in_formDatas) {
//		this.formDatas = in_formDatas;
//	}

	public java.util.Set getFormFiles () {
		return formFiles;
	}	
	
	public void setFormFiles (java.util.Set in_formFiles) {
		this.formFiles = in_formFiles;
	}
    

	/**
	 * 	 * @return Long
     * @hibernate.id column="formId" type="java.lang.Long" generator-class="native"
	 */
	public Long getFormId() {
		return this.formId;
	}
	
	/**
	 * Set the formId
	 */	
	public void setFormId(Long aValue) {
		this.formId = aValue;
	}	

	/**
	 * 所属运行流程	 * @return Long
	 */
	public Long getRunId() {
		return this.getProcessRun()==null?null:this.getProcessRun().getRunId();
	}
	
	/**
	 * Set the runId
	 */	
	public void setRunId(Long aValue) {
	    if (aValue==null) {
	    	processRun = null;
	    } else if (processRun == null) {
	        processRun = new com.zhiwei.credit.model.flow.ProcessRun(aValue);
	        processRun.setVersion(new Integer(0));//set a version to cheat hibernate only
	    } else {
			processRun.setRunId(aValue);
	    }
	}	

	/**
	 * 活动或任务名称	 * @return String
	 * @hibernate.property column="activityName" type="java.lang.String" length="128" not-null="true" unique="false"
	 */
	public String getActivityName() {
		return this.activityName;
	}
	
	/**
	 * Set the activityName
	 * @spring.validator type="required"
	 */	
	public void setActivityName(String aValue) {
		this.activityName = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof ProcessForm)) {
			return false;
		}
		ProcessForm rhs = (ProcessForm) object;
		return new EqualsBuilder()
				.append(this.formId, rhs.formId)
						.append(this.activityName, rhs.activityName)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.formId) 
						.append(this.activityName) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("formId", this.formId) 
						.append("activityName", this.activityName) 
				.toString();
	}


	public Long getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public Date getEndtime() {
		return endtime;
	}

	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}



	public String getFromTask() {
		return fromTask;
	}

	public void setFromTask(String fromTask) {
		this.fromTask = fromTask;
	}

	public String getFromTaskId() {
		return fromTaskId;
	}

	public void setFromTaskId(String fromTaskId) {
		this.fromTaskId = fromTaskId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getTransTo() {
		return transTo;
	}

	public void setTransTo(String transTo) {
		this.transTo = transTo;
	}

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Long getPreFormId() {
		return preFormId;
	}

	public void setPreFormId(Long preFormId) {
		this.preFormId = preFormId;
	}

	public Date getTaskLimitTime() {
		return taskLimitTime;
	}

	public void setTaskLimitTime(Date taskLimitTime) {
		this.taskLimitTime = taskLimitTime;
	}

	public String getPreviousCreator() {
		return previousCreator;
	}

	public void setPreviousCreator(String previousCreator) {
		this.previousCreator = previousCreator;
	}

	public Short getSafeLevel() {
		return safeLevel;
	}

	public void setSafeLevel(Short safeLevel) {
		this.safeLevel = safeLevel;
	}

	public String getTaskSequenceNodeKey() {
		return taskSequenceNodeKey;
	}

	public void setTaskSequenceNodeKey(String taskSequenceNodeKey) {
		this.taskSequenceNodeKey = taskSequenceNodeKey;
	}

	public String getProjectAssign() {
		return projectAssign;
	}

	public void setProjectAssign(String projectAssign) {
		this.projectAssign = projectAssign;
	}

	public String getCountersignRefuse() {
		return countersignRefuse;
	}

	public void setCountersignRefuse(String countersignRefuse) {
		this.countersignRefuse = countersignRefuse;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}
	
}
