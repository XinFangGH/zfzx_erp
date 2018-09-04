package com.zhiwei.credit.model.task;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

import com.google.gson.annotations.Expose;

/**
 * CalendarPlan Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * 
 */
public class CalendarPlan extends com.zhiwei.core.model.BaseModel {
	//完成状态
	//未完成
	public static final Short STATUS_UNFINISHED=0;
	//完成 
	public static final Short STATUS_FINISHED=1;
	
	//重要程度
	//一般
	public static final Short URGENT_COMMON=0;
	//重要
	public static final Short URGENT_IMPORTANT=1;
	//紧急
	public static final Short URGENT_INST=2;
	
	
	public String getColor(){
		if(STATUS_FINISHED.equals(status)){
			return "#D5E4BD";
		}
		if(URGENT_INST.equals(urgent)){
			return "#94B98D";
		}else if(URGENT_IMPORTANT.equals(urgent)){
			return "#FFBDB4";
		}else{
			return "#7799BF";
		}
	}
	
	@Expose
    protected Long planId;
	@Expose
	protected java.util.Date startTime;
	@Expose
	protected java.util.Date endTime;
	@Expose
	protected Short urgent;
	@Expose
	protected String content;
	@Expose
	protected Short status;
	@Expose
	protected String fullname;
	@Expose
	protected Long assignerId;
	@Expose
	protected String assignerName;
	@Expose
	protected String feedback;
	@Expose
	protected Short showStyle;
	@Expose
	protected Short taskType;
	@Expose
	protected Long userId;
	@Expose
	protected String summary;
	//protected com.zhiwei.credit.model.system.AppUser appUser;
	

	
	
	
	
	
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getStatusName(){
		if(status.equals(STATUS_FINISHED)){
			return "完成";
		}else{
			return "未完成";
		}
	}
	
	public String getUrgentName(){
		if(urgent.equals(URGENT_COMMON)){
			return "一般";
		}else if(urgent.equals(URGENT_IMPORTANT)){
			return "重要";
		}else{
			return "紧急";
		}
	}

	/**
	 * Default Empty Constructor for class CalendarPlan
	 */
	public CalendarPlan () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class CalendarPlan
	 */
	public CalendarPlan (
		 Long in_planId
        ) {
		this.setPlanId(in_planId);
    }

	
//	public com.zhiwei.credit.model.system.AppUser getAppUser () {
//		return appUser;
//	}	
//	
//	public void setAppUser (com.zhiwei.credit.model.system.AppUser in_appUser) {
//		this.appUser = in_appUser;
//	}
    

	/**
	 * 	 * @return Long
     * @hibernate.id column="planId" type="java.lang.Long" generator-class="native"
	 */
	public Long getPlanId() {
		return this.planId;
	}
	
	/**
	 * Set the planId
	 */	
	public void setPlanId(Long aValue) {
		this.planId = aValue;
	}	

	/**
	 * 开始时间	 * @return java.util.Date
	 * @hibernate.property column="startTime" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getStartTime() {
		return this.startTime;
	}
	
	/**
	 * Set the startTime
	 */	
	public void setStartTime(java.util.Date aValue) {
		this.startTime = aValue;
	}	

	/**
	 * 结束时间	 * @return java.util.Date
	 * @hibernate.property column="endTime" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getEndTime() {
		return this.endTime;
	}
	
	/**
	 * Set the endTime
	 */	
	public void setEndTime(java.util.Date aValue) {
		this.endTime = aValue;
	}	

	/**
	 * 紧急程度
            0=一般
            1=重要
            2=紧急	 * @return Short
	 * @hibernate.property column="urgent" type="java.lang.Short" length="5" not-null="true" unique="false"
	 */
	public Short getUrgent() {
		return this.urgent;
	}
	
	/**
	 * Set the urgent
	 * @spring.validator type="required"
	 */	
	public void setUrgent(Short aValue) {
		this.urgent = aValue;
	}	

	/**
	 * 内容	 * @return String
	 * @hibernate.property column="content" type="java.lang.String" length="1200" not-null="true" unique="false"
	 */
	public String getContent() {
		return this.content;
	}
	
	/**
	 * Set the content
	 * @spring.validator type="required"
	 */	
	public void setContent(String aValue) {
		this.content = aValue;
	}	

	/**
	 * 状态
            0=未完成
            1=完成	 * @return Short
	 * @hibernate.property column="status" type="java.lang.Short" length="5" not-null="true" unique="false"
	 */
	public Short getStatus() {
		return this.status;
	}
	
	/**
	 * Set the status
	 * @spring.validator type="required"
	 */	
	public void setStatus(Short aValue) {
		this.status = aValue;
	}	

	/**
	 * 员工ID	 * @return Long
	 */
//	public Long getUserId() {
//		return this.getAppUser()==null?null:this.getAppUser().getUserId();
//	}
//	
//	/**
//	 * Set the userId
//	 */	
//	public void setUserId(Long aValue) {
//	    if (aValue==null) {
//	    	appUser = null;
//	    } else if (appUser == null) {
//	        appUser = new com.zhiwei.credit.model.system.AppUser(aValue);
//	        appUser.setVersion(new Integer(0));//set a version to cheat hibernate only
//	    } else {
//			appUser.setUserId(aValue);
//	    }
//	}	

	/**
	 * 员工名	 * @return String
	 * @hibernate.property column="fullname" type="java.lang.String" length="32" not-null="false" unique="false"
	 */
	public String getFullname() {
		return this.fullname;
	}
	
	/**
	 * Set the fullname
	 */	
	public void setFullname(String aValue) {
		this.fullname = aValue;
	}	

	/**
	 * 分配人	 * @return Long
	 * @hibernate.property column="assignerId" type="java.lang.Long" length="19" not-null="true" unique="false"
	 */
	public Long getAssignerId() {
		return this.assignerId;
	}
	
	/**
	 * Set the assignerId
	 * @spring.validator type="required"
	 */	
	public void setAssignerId(Long aValue) {
		this.assignerId = aValue;
	}	

	/**
	 * 分配人名	 * @return String
	 * @hibernate.property column="assignerName" type="java.lang.String" length="32" not-null="false" unique="false"
	 */
	public String getAssignerName() {
		return this.assignerName;
	}
	
	/**
	 * Set the assignerName
	 */	
	public void setAssignerName(String aValue) {
		this.assignerName = aValue;
	}	

	/**
	 * 反馈意见	 * @return String
	 * @hibernate.property column="feedback" type="java.lang.String" length="500" not-null="false" unique="false"
	 */
	public String getFeedback() {
		return this.feedback;
	}
	
	/**
	 * Set the feedback
	 */	
	public void setFeedback(String aValue) {
		this.feedback = aValue;
	}	

	/**
	 * 显示方式
            1=仅在任务中显示
            2=在日程与任务中显示	 * @return Short
	 * @hibernate.property column="showStyle" type="java.lang.Short" length="5" not-null="true" unique="false"
	 */
	public Short getShowStyle() {
		return this.showStyle;
	}
	
	/**
	 * Set the showStyle
	 * @spring.validator type="required"
	 */	
	public void setShowStyle(Short aValue) {
		this.showStyle = aValue;
	}	

	/**
	 * 任务类型
            1=限期任务
            2=非限期任务	 * @return Short
	 * @hibernate.property column="taskType" type="java.lang.Short" length="5" not-null="true" unique="false"
	 */
	public Short getTaskType() {
		return this.taskType;
	}
	
	/**
	 * Set the taskType
	 * @spring.validator type="required"
	 */	
	public void setTaskType(Short aValue) {
		this.taskType = aValue;
	}	

	/**
	 * 概要  * @return String
	 * @hibernate.property column="Summary" type="java.lang.String" length="5" not-null="true" unique="false"
	 */
	public String getSummary() {
		return this.summary;
	}
	
	/**
	 * Set the summary
	 * @spring.validator type="required"
	 */	
	public void setSummary(String aValue) {
		this.summary = aValue;
	}
	
	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof CalendarPlan)) {
			return false;
		}
		CalendarPlan rhs = (CalendarPlan) object;
		return new EqualsBuilder()
				.append(this.planId, rhs.planId)
				.append(this.startTime, rhs.startTime)
				.append(this.endTime, rhs.endTime)
				.append(this.urgent, rhs.urgent)
				.append(this.content, rhs.content)
				.append(this.status, rhs.status)
						.append(this.fullname, rhs.fullname)
						.append(this.userId, rhs.userId)
				.append(this.assignerId, rhs.assignerId)
				.append(this.assignerName, rhs.assignerName)
				.append(this.feedback, rhs.feedback)
				.append(this.showStyle, rhs.showStyle)
				.append(this.taskType, rhs.taskType)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.planId) 
				.append(this.startTime) 
				.append(this.endTime) 
				.append(this.urgent) 
				.append(this.content) 
				.append(this.status) 
						.append(this.fullname) 
						.append(this.userId) 
				.append(this.assignerId) 
				.append(this.assignerName) 
				.append(this.feedback) 
				.append(this.showStyle) 
				.append(this.taskType) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("planId", this.planId) 
				.append("startTime", this.startTime) 
				.append("endTime", this.endTime) 
				.append("urgent", this.urgent) 
				.append("content", this.content) 
				.append("status", this.status) 
						.append("fullname", this.fullname) 
				.append("assignerId", this.assignerId) 
				.append("userId", this.userId) 
				.append("assignerName", this.assignerName) 
				.append("feedback", this.feedback) 
				.append("showStyle", this.showStyle) 
				.append("taskType", this.taskType) 
				.toString();
	}



}
