package com.zhiwei.credit.model.flow;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
*/
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * TaskSignData Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class TaskSignData extends com.zhiwei.core.model.BaseModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 投票同意
	 */
	public static final Short IS_AGREE=1;
	/**
	 * 投票拒绝
	 */
	public static final Short IS_NOT_AGREE=2;
	
	/**
	 * 会签情况对应的中文描述。现在可由用户自定义某个节点是否为会签节点。
	 * 需要把投票情况累加到意见与说明后面。类似审贷会等情况会有专门的列表或窗口查询。
	 * add by lu 2013.06.17
	 */
	public static final String V_CN_AGREE = "【同意】";
	public static final String V_CN_REFUSE = "【否决】";
	public static final String V_CN_CONDITIONAL = "【有条件通过】";
	public static final String V_CN_AFRESH = "【打回】";
	public static final String V_CN_UNPOLLED = "【弃权】";
	
    protected Long dataId;
	protected Long voteId;
	protected String voteName;
	protected java.util.Date voteTime;
	protected String taskId;
	protected Short isAgree;
	protected Long runId;
	
	//add by lu 2012.05.25
	protected Date createTime;//创建时间
	protected Date taskLimitTime;//任务期限
	protected Integer totalScore;//总分数(待删除。也可保留,可能别的流程需要打分的情况需要用到。)
	protected Float averageScore;//平均分(待删除。也可保留,可能别的流程需要打分的情况需要用到。)
	protected String position;//职位
	
	protected String fromTaskId;//form表中的fromTaskId，方便区分多次会签的情况。为了获取当前的会签信息。

    protected String comments;
    
    //add by lu 2012.06.20 中铭常规流程审保会集体决议多个说明
    protected String premiumRateComments;//保费费率调整意见
	protected String mortgageComments;//抵质押物调整意见
	protected String assureTimeLimitComments;//担保期限调整意见
	protected String assureTotalMoneyComments;//担保总额调整意见
	protected String feedbackComments;//反馈意见
	
	protected Integer voteCounts;//系统设置会签节点投票通过票数。
	protected Long formId;//process_form表主键,根据此主键查询对应的意见与说明。
	protected String activityName;//当前列表有可能需要显示两个或以上会签的记录信息,增加此字段区分显示会签任务。
	protected Integer sbhTimes;//审保会次数
	
	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	/**
	 * Default Empty Constructor for class TaskSignData
	 */
	public TaskSignData () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class TaskSignData
	 */
	public TaskSignData (
		 Long in_dataId
        ) {
		this.setDataId(in_dataId);
    }

    

	/**
	 * 	 * @return Long
     * @hibernate.id column="dataId" type="java.lang.Long" generator-class="native"
	 */
	public Long getDataId() {
		return this.dataId;
	}
	
	/**
	 * Set the dataId
	 */	
	public void setDataId(Long aValue) {
		this.dataId = aValue;
	}	

	/**
	 * 投票人	 * @return Long
	 * @hibernate.property column="voteId" type="java.lang.Long" length="19" not-null="true" unique="false"
	 */
	public Long getVoteId() {
		return this.voteId;
	}
	
	/**
	 * Set the voteId
	 * @spring.validator type="required"
	 */	
	public void setVoteId(Long aValue) {
		this.voteId = aValue;
	}	

	/**
	 * 投票人名	 * @return String
	 * @hibernate.property column="voteName" type="java.lang.String" length="64" not-null="false" unique="false"
	 */
	public String getVoteName() {
		return this.voteName;
	}
	
	/**
	 * Set the voteName
	 */	
	public void setVoteName(String aValue) {
		this.voteName = aValue;
	}	

	/**
	 * 投票时间	 * @return java.util.Date
	 * @hibernate.property column="voteTime" type="java.util.Date" length="19" not-null="true" unique="false"
	 */
	public java.util.Date getVoteTime() {
		return this.voteTime;
	}
	
	/**
	 * Set the voteTime
	 * @spring.validator type="required"
	 */	
	public void setVoteTime(java.util.Date aValue) {
		this.voteTime = aValue;
	}	

	/**
	 * 任务Id	 * @return Long
	 * @hibernate.property column="taskId" type="java.lang.String" length="64" not-null="true" unique="false"
	 */
	public String getTaskId() {
		return this.taskId;
	}
	
	/**
	 * Set the taskId
	 * @spring.validator type="required"
	 */	
	public void setTaskId(String aValue) {
		this.taskId = aValue;
	}	

	/**
	 * 是否同意
            1=同意
            0=拒绝	 * @return Short
	 * @hibernate.property column="isAgree" type="java.lang.Short" length="5" not-null="true" unique="false"
	 */
	public Short getIsAgree() {
		return this.isAgree;
	}
	
	/**
	 * Set the isAgree
	 * @spring.validator type="required"
	 */	
	public void setIsAgree(Short aValue) {
		this.isAgree = aValue;
	}	

	public Long getRunId() {
		return runId;
	}

	public void setRunId(Long runId) {
		this.runId = runId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getTaskLimitTime() {
		return taskLimitTime;
	}

	public void setTaskLimitTime(Date taskLimitTime) {
		this.taskLimitTime = taskLimitTime;
	}

	public Integer getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(Integer totalScore) {
		this.totalScore = totalScore;
	}

	public Float getAverageScore() {
		return averageScore;
	}

	public void setAverageScore(Float averageScore) {
		this.averageScore = averageScore;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getFromTaskId() {
		return fromTaskId;
	}

	public void setFromTaskId(String fromTaskId) {
		this.fromTaskId = fromTaskId;
	}

	public String getPremiumRateComments() {
		return premiumRateComments;
	}

	public void setPremiumRateComments(String premiumRateComments) {
		this.premiumRateComments = premiumRateComments;
	}

	public String getMortgageComments() {
		return mortgageComments;
	}

	public void setMortgageComments(String mortgageComments) {
		this.mortgageComments = mortgageComments;
	}

	public String getAssureTimeLimitComments() {
		return assureTimeLimitComments;
	}

	public void setAssureTimeLimitComments(String assureTimeLimitComments) {
		this.assureTimeLimitComments = assureTimeLimitComments;
	}

	public Integer getVoteCounts() {
		return voteCounts;
	}

	public void setVoteCounts(Integer voteCounts) {
		this.voteCounts = voteCounts;
	}

	public String getAssureTotalMoneyComments() {
		return assureTotalMoneyComments;
	}

	public void setAssureTotalMoneyComments(String assureTotalMoneyComments) {
		this.assureTotalMoneyComments = assureTotalMoneyComments;
	}

	public String getFeedbackComments() {
		return feedbackComments;
	}

	public void setFeedbackComments(String feedbackComments) {
		this.feedbackComments = feedbackComments;
	}

	public Long getFormId() {
		return formId;
	}

	public void setFormId(Long formId) {
		this.formId = formId;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public Integer getSbhTimes() {
		return sbhTimes;
	}

	public void setSbhTimes(Integer sbhTimes) {
		this.sbhTimes = sbhTimes;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof TaskSignData)) {
			return false;
		}
		TaskSignData rhs = (TaskSignData) object;
		return new EqualsBuilder()
				.append(this.dataId, rhs.dataId)
				.append(this.voteId, rhs.voteId)
				.append(this.voteName, rhs.voteName)
				.append(this.voteTime, rhs.voteTime)
				.append(this.taskId, rhs.taskId)
				.append(this.isAgree, rhs.isAgree)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.dataId) 
				.append(this.voteId) 
				.append(this.voteName) 
				.append(this.voteTime) 
				.append(this.taskId) 
				.append(this.isAgree) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("dataId", this.dataId) 
				.append("voteId", this.voteId) 
				.append("voteName", this.voteName) 
				.append("voteTime", this.voteTime) 
				.append("taskId", this.taskId) 
				.append("isAgree", this.isAgree) 
				.toString();
	}



}
