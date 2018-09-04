package com.zhiwei.credit.model.flow;

/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
 */

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * @description 流程人员设置管理
 * @class ProUserAssign
 * @author 智维软件
 * @updater YHZ
 * @company www.credit-software.com
 * @data 2010-12-30AM
 * 
 */
@SuppressWarnings("serial")
public class ProUserAssign extends com.zhiwei.core.model.BaseModel {
	/**
	 * 会签任务
	 */
	public final static Short IS_SIGNED_TASK=1;
	/**
	 * 非会签任务
	 */
	public final static Short IS_NOT_SIGNED_TASK=1;
	
	protected Long assignId;
	protected String deployId;
	protected String activityName;
	protected String roleId;
	protected String roleName;
	protected String userId;
	protected String username;

	protected Short isSigned;
	protected String jobId;
	protected String jobName;
	protected String depPosIds;
	protected String depPosNames;
	protected String reJobId;
	protected String reJobName;
	protected String depIds;
	protected String depNames;
	protected Short posUserFlag;
	protected Short taskTimeLimit;//add by lu 2012.05.17 设置流程信息的时候设置任务节点处理时限
	protected Short timeLimitType;//add by lu 2012.05.17 设置任务节点时限类型：工作日、正常日
	protected Short isJumpToTargetTask;//add by lu 2012.05.21 设置是否允许跳转至目标节点：0，否；1，是。默认为是。
	protected String taskSequence;//add by lu 2012.05.21 设置节点顺序。
	protected Short isProjectChange;//add by lu 2012.08.17 设置是否允许当前节点进行项目换人：0，否；1，是。默认为是。
	
	
	/*
	 * 分离说明  指门店分离
	 * 0或者null代表 不分离
	 * 1代表 分离
	 */
	private String isseparate; //是否业务分离
	
	private String isReSetNext;//是否流程节点重新指派人 0或者null否,1是
	
	public String getIsseparate() {
		return isseparate;
	}

	public void setIsseparate(String isseparate) {
		this.isseparate = isseparate;
	}
	
	/**
	 * Default Empty Constructor for class ProUserAssign
	 */
	public ProUserAssign() {
		super();
	}

	/**
	 * Default Key Fields Constructor for class ProUserAssign
	 */
	public ProUserAssign(Long in_assignId) {
		this.setAssignId(in_assignId);
	}

	public Short getIsSigned() {
		return isSigned;
	}

	public void setIsSigned(Short isSigned) {
		this.isSigned = isSigned;
	}

	/**
	 * 授权ID * @return Long
	 * 
	 * @hibernate.id column="assignId" type="java.lang.Long"
	 *               generator-class="native"
	 */
	public Long getAssignId() {
		return this.assignId;
	}

	/**
	 * Set the assignId
	 */
	public void setAssignId(Long aValue) {
		this.assignId = aValue;
	}

	/**
	 * jbpm流程定义的id * @return String
	 * 
	 * @hibernate.property column="deployId" type="java.lang.String"
	 *                     length="128" not-null="true" unique="false"
	 */
	public String getDeployId() {
		return this.deployId;
	}

	/**
	 * Set the deployId
	 * 
	 * @spring.validator type="required"
	 */
	public void setDeployId(String aValue) {
		this.deployId = aValue;
	}

	/**
	 * 流程节点名称 * @return String
	 * 
	 * @hibernate.property column="activityName" type="java.lang.String"
	 *                     length="128" not-null="true" unique="false"
	 */
	public String getActivityName() {
		return this.activityName;
	}

	/**
	 * Set the activityName
	 * 
	 * @spring.validator type="required"
	 */
	public void setActivityName(String aValue) {
		this.activityName = aValue;
	}

	/**
	 * 角色Id * @return String
	 * 
	 * @hibernate.property column="roleId" type="java.lang.String" length="128"
	 *                     not-null="false" unique="false"
	 */
	public String getRoleId() {
		return this.roleId;
	}

	/**
	 * Set the roleId
	 */
	public void setRoleId(String aValue) {
		this.roleId = aValue;
	}

	/**
	 * 用户ID * @return String
	 * 
	 * @hibernate.property column="userId" type="java.lang.String" length="128"
	 *                     not-null="false" unique="false"
	 */
	public String getUserId() {
		return this.userId;
	}

	/**
	 * Set the userId
	 */
	public void setUserId(String aValue) {
		this.userId = aValue;
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	
	public String getDepPosIds() {
		return depPosIds;
	}

	public void setDepPosIds(String depPosIds) {
		this.depPosIds = depPosIds;
	}

	public String getDepPosNames() {
		return depPosNames;
	}

	public void setDepPosNames(String depPosNames) {
		this.depPosNames = depPosNames;
	}

	public String getReJobId() {
		return reJobId;
	}

	public void setReJobId(String reJobId) {
		this.reJobId = reJobId;
	}

	public String getReJobName() {
		return reJobName;
	}

	public void setReJobName(String reJobName) {
		this.reJobName = reJobName;
	}
	

	public Short getTaskTimeLimit() {
		return taskTimeLimit;
	}

	public void setTaskTimeLimit(Short taskTimeLimit) {
		this.taskTimeLimit = taskTimeLimit;
	}

	public Short getTimeLimitType() {
		return timeLimitType;
	}

	public void setTimeLimitType(Short timeLimitType) {
		this.timeLimitType = timeLimitType;
	}

	public Short getIsJumpToTargetTask() {
		return isJumpToTargetTask;
	}

	public void setIsJumpToTargetTask(Short isJumpToTargetTask) {
		this.isJumpToTargetTask = isJumpToTargetTask;
	}


	public String getTaskSequence() {
		return taskSequence;
	}

	public void setTaskSequence(String taskSequence) {
		this.taskSequence = taskSequence;
	}

	/**
	 * Return the name of the first key column
	 */
	public String getFirstKeyColumnName() {
		return "assignId";
	}

	/**
	 * Return the Id (pk) of the entity, must be Integer
	 */
	public Long getId() {
		return assignId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getDepIds() {
		return depIds;
	}

	public void setDepIds(String depIds) {
		this.depIds = depIds;
	}

	public String getDepNames() {
		return depNames;
	}

	public void setDepNames(String depNames) {
		this.depNames = depNames;
	}
	
	public Short getPosUserFlag() {
		return posUserFlag;
	}

	public void setPosUserFlag(Short posUserFlag) {
		this.posUserFlag = posUserFlag;
	}

	public Short getIsProjectChange() {
		return isProjectChange;
	}

	public void setIsProjectChange(Short isProjectChange) {
		this.isProjectChange = isProjectChange;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof ProUserAssign)) {
			return false;
		}
		ProUserAssign rhs = (ProUserAssign) object;
		return new EqualsBuilder().append(this.assignId, rhs.assignId).append(
				this.deployId, rhs.deployId).append(this.activityName,
				rhs.activityName).append(this.roleId, rhs.roleId).append(
				this.userId, rhs.userId).append(this.jobId, rhs.jobId).append(
				this.jobName, rhs.jobName).append(this.reJobId, rhs.reJobId)
				.append(this.reJobName, rhs.reJobName).append(this.isSigned,rhs.isSigned)
				.append(this.depIds, rhs.depIds).append(this.depNames, rhs.depNames)
				.append(this.posUserFlag, rhs.posUserFlag)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.assignId)
				.append(this.deployId).append(this.activityName).append(
						this.roleId).append(this.userId).append(this.jobId)
				.append(this.jobName).append(this.reJobId).append(
						this.reJobName).append(this.isSigned).append(this.depIds)
				.append(this.depNames).append(this.posUserFlag).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("assignId", this.assignId)
				.append("deployId", this.deployId).append("activityName",
						this.activityName).append("roleId", this.roleId)
				.append("userId", this.userId).append("jobId", this.jobId)
				.append("jobName", this.jobName)
				.append("reJobId", this.reJobId).append("reJobName",
						this.reJobName).append("isSigned", this.isSigned)
				.append("depIds",this.depIds).append("depNames",this.depNames)
				.append("posUserFlag",this.posUserFlag)
				.toString();
	}

	public String getIsReSetNext() {
		return isReSetNext;
	}

	public void setIsReSetNext(String isReSetNext) {
		this.isReSetNext = isReSetNext;
	}

}
