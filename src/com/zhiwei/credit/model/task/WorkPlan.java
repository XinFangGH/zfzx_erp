package com.zhiwei.credit.model.task;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/


import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

import com.google.gson.annotations.Expose;
import com.zhiwei.credit.model.system.FileAttach;
import com.zhiwei.credit.model.system.GlobalType;

/**
 * WorkPlan Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * ������������
 */
public class WorkPlan extends com.zhiwei.core.model.BaseModel {

    @Expose
	protected Long planId;
    @Expose
	protected String planName;
    @Expose
	protected String planContent;
    @Expose
	protected java.util.Date startTime;
    @Expose
	protected java.util.Date endTime;
    @Expose
	protected String issueScope;
    @Expose
	protected String participants;
    @Expose
	protected String principal;
    @Expose
	protected String note;
    @Expose
	protected Short status;
    @Expose
	protected Short isPersonal;
    @Expose
	protected String icon;
//    @Expose
//    protected Long typeId;
    @Expose
    protected String typeName;
    @Expose
    protected GlobalType globalType;
//    @Expose
//	protected com.zhiwei.credit.model.task.PlanType planType;
    @Expose
	protected com.zhiwei.credit.model.system.AppUser appUser;
    @Expose
	protected java.util.Set<FileAttach> planFiles = new java.util.HashSet<FileAttach>();
    protected java.util.Set<PlanAttend> planAttends= new java.util.HashSet<PlanAttend>();

	/**
	 * Default Empty Constructor for class WorkPlan
	 */
	public WorkPlan () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class WorkPlan
	 */
	public WorkPlan (
		 Long in_planId
        ) {
		this.setPlanId(in_planId);
    }

	
	public java.util.Set<PlanAttend> getPlanAttends() {
		return planAttends;
	}

	public void setPlanAttends(java.util.Set<PlanAttend> planAttends) {
		this.planAttends = planAttends;
	}

//	public com.zhiwei.credit.model.task.PlanType getPlanType () {
//		return planType;
//	}	
//	
//	public void setPlanType (com.zhiwei.credit.model.task.PlanType in_planType) {
//		this.planType = in_planType;
//	}
	
	public com.zhiwei.credit.model.system.AppUser getAppUser () {
		return appUser;
	}	
	
	public void setAppUser (com.zhiwei.credit.model.system.AppUser in_appUser) {
		this.appUser = in_appUser;
	}

	public java.util.Set<FileAttach> getPlanFiles () {
		return planFiles;
	}	
	
	public void setPlanFiles (java.util.Set<FileAttach> in_planFiles) {
		this.planFiles = in_planFiles;
	}
    

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
	 * 计划名称	 * @return String
	 * @hibernate.property column="planName" type="java.lang.String" length="128" not-null="true" unique="false"
	 */
	public String getPlanName() {
		return this.planName;
	}
	
	/**
	 * Set the planName
	 * @spring.validator type="required"
	 */	
	public void setPlanName(String aValue) {
		this.planName = aValue;
	}	

	/**
	 * 计划内容	 * @return String
	 * @hibernate.property column="planContent" type="java.lang.String" length="5000" not-null="false" unique="false"
	 */
	public String getPlanContent() {
		return this.planContent;
	}
	
	/**
	 * Set the planContent
	 */	
	public void setPlanContent(String aValue) {
		this.planContent = aValue;
	}	

	/**
	 * 开始日期	 * @return java.util.Date
	 * @hibernate.property column="startTime" type="java.util.Date" length="19" not-null="true" unique="false"
	 */
	public java.util.Date getStartTime() {
		return this.startTime;
	}
	
	/**
	 * Set the startTime
	 * @spring.validator type="required"
	 */	
	public void setStartTime(java.util.Date aValue) {
		this.startTime = aValue;
	}	

	/**
	 * 结束日期	 * @return java.util.Date
	 * @hibernate.property column="endTime" type="java.util.Date" length="19" not-null="true" unique="false"
	 */
	public java.util.Date getEndTime() {
		return this.endTime;
	}
	
	/**
	 * Set the endTime
	 * @spring.validator type="required"
	 */	
	public void setEndTime(java.util.Date aValue) {
		this.endTime = aValue;
	}	

//	/**
//	 * 计划类型	 * @return Long
//	 */
//	public Long getTypeId() {
//		return this.getPlanType()==null?null:this.getPlanType().getTypeId();
//	}
//	
//	/**
//	 * Set the typeId
//	 */	
//	public void setTypeId(Long aValue) {
//	    if (aValue==null) {
//	    	planType = null;
//	    } else if (planType == null) {
//	        planType = new com.zhiwei.credit.model.task.PlanType(aValue);
//	        planType.setVersion(new Integer(0));//set a version to cheat hibernate only
//	    } else {
//			planType.setTypeId(aValue);
//	    }
//	}	

	/**
	 * 员工ID	 * @return Long
	 */
	public Long getUserId() {
		return this.getAppUser()==null?null:this.getAppUser().getUserId();
	}
	
	/**
	 * Set the userId
	 */	
	public void setUserId(Long aValue) {
	    if (aValue==null) {
	    	appUser = null;
	    } else if (appUser == null) {
	        appUser = new com.zhiwei.credit.model.system.AppUser(aValue);
	        appUser.setVersion(new Integer(0));//set a version to cheat hibernate only
	    } else {
			appUser.setUserId(aValue);
	    }
	}	

	/**
	 * 发布范围
            0则代表全部部门
            存放所有的参与部门ID
            	 * @return String
	 * @hibernate.property column="issueScope" type="java.lang.String" length="2000" not-null="false" unique="false"
	 */
	public String getIssueScope() {
		return this.issueScope;
	}
	
	/**
	 * Set the issueScope
	 */	
	public void setIssueScope(String aValue) {
		this.issueScope = aValue;
	}	

	/**
	 * 参与人
            0则代表全部参与
            参与人,即员工ID列表	 * @return String
	 * @hibernate.property column="participants" type="java.lang.String" length="2000" not-null="false" unique="false"
	 */
	public String getParticipants() {
		return this.participants;
	}
	
	/**
	 * Set the participants
	 */	
	public void setParticipants(String aValue) {
		this.participants = aValue;
	}	

	/**
	 * 负责人	 * @return String
	 * @hibernate.property column="principal" type="java.lang.String" length="256" not-null="true" unique="false"
	 */
	public String getPrincipal() {
		return this.principal;
	}
	
	/**
	 * Set the principal
	 * @spring.validator type="required"
	 */	
	public void setPrincipal(String aValue) {
		this.principal = aValue;
	}	

	/**
	 * 备注	 * @return String
	 * @hibernate.property column="note" type="java.lang.String" length="500" not-null="false" unique="false"
	 */
	public String getNote() {
		return this.note;
	}
	
	/**
	 * Set the note
	 */	
	public void setNote(String aValue) {
		this.note = aValue;
	}	

	/**
	 * 状态
            1=激活
            0=禁用	 * @return Short
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
	 * 是否为个人计划
            1=则为个人工作计划，这时发布范围，参与人均为空，负责人为当前用户
            0=则代表为其他任务	 * @return Short
	 * @hibernate.property column="isPersonal" type="java.lang.Short" length="5" not-null="true" unique="false"
	 */
	public Short getIsPersonal() {
		return this.isPersonal;
	}
	
	/**
	 * Set the isPersonal
	 * @spring.validator type="required"
	 */	
	public void setIsPersonal(Short aValue) {
		this.isPersonal = aValue;
	}	

	/**
	 * 图标	 * @return String
	 * @hibernate.property column="icon" type="java.lang.String" length="128" not-null="false" unique="false"
	 */
	public String getIcon() {
		return this.icon;
	}
	
	/**
	 * Set the icon
	 */	
	public void setIcon(String aValue) {
		this.icon = aValue;
	}	

	public Long getProTypeId() {
		return this.globalType==null?null:this.globalType.getProTypeId();
	}

	public void setProTypeId(Long typeId) {
		if(typeId==null){
			globalType=null;
		}else if(globalType==null){
			globalType=new com.zhiwei.credit.model.system.GlobalType(typeId);
			globalType.setVersion(new Integer(0));
		}else{
			globalType.setProTypeId(typeId);
		}
	}

	
	
	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public GlobalType getGlobalType() {
		return globalType;
	}

	public void setGlobalType(GlobalType globalType) {
		this.globalType = globalType;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof WorkPlan)) {
			return false;
		}
		WorkPlan rhs = (WorkPlan) object;
		return new EqualsBuilder()
				.append(this.planId, rhs.planId)
				.append(this.planName, rhs.planName)
				.append(this.planContent, rhs.planContent)
				.append(this.startTime, rhs.startTime)
				.append(this.endTime, rhs.endTime)
								.append(this.issueScope, rhs.issueScope)
				.append(this.participants, rhs.participants)
				.append(this.principal, rhs.principal)
				.append(this.note, rhs.note)
				.append(this.status, rhs.status)
				.append(this.isPersonal, rhs.isPersonal)
				.append(this.icon, rhs.icon)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.planId) 
				.append(this.planName) 
				.append(this.planContent) 
				.append(this.startTime) 
				.append(this.endTime) 
								.append(this.issueScope) 
				.append(this.participants) 
				.append(this.principal) 
				.append(this.note) 
				.append(this.status) 
				.append(this.isPersonal) 
				.append(this.icon) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("planId", this.planId) 
				.append("planName", this.planName) 
				.append("planContent", this.planContent) 
				.append("startTime", this.startTime) 
				.append("endTime", this.endTime) 
								.append("issueScope", this.issueScope) 
				.append("participants", this.participants) 
				.append("principal", this.principal) 
				.append("note", this.note) 
				.append("status", this.status) 
				.append("isPersonal", this.isPersonal) 
				.append("icon", this.icon) 
				.toString();
	}



}
