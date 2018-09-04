package com.zhiwei.credit.model.flow;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
*/
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * ProHandleComp Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * 
 */
public class ProHandleComp extends com.zhiwei.core.model.BaseModel {
	/**
	 * 实现listener接口
	 */
	public static final Short HANDLE_TYPE_LISTENER=1;
	/**
	 * 实现Handler接口
	 */
	public static final Short HANDLE_TYPE_HANDLER=2;
	
	/**
	 * 流程级别
	 */
	public static final Short EVENT_LEVEL_PROCESS=1;
	/**
	 * 节点级别
	 */
	public static final Short EVENT_LEVEL_NODE=1;
	/**
	 * 跳转级别
	 */
	public static final Short EVENT_LEVEL_TRANSITION=1;
	
	/**
	 * start event
	 */
	public static final String EVENT_START="start";
	/**
	 * end event
	 */
	public static final String EVENT_END="end";
	

    protected Long handleId;
	protected String deployId;
	protected String activityName;
	protected String tranName;
	protected String eventName;
	protected Short eventLevel;
	protected String exeCode;
	protected Short handleType;


	/**
	 * Default Empty Constructor for class ProHandleComp
	 */
	public ProHandleComp () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class ProHandleComp
	 */
	public ProHandleComp (
		 Long in_handleId
        ) {
		this.setHandleId(in_handleId);
    }

    

	/**
	 * 	 * @return Long
     * @hibernate.id column="HANDLE_ID" type="java.lang.Long" generator-class="native"
	 */
	public Long getHandleId() {
		return this.handleId;
	}
	
	/**
	 * Set the handleId
	 */	
	public void setHandleId(Long aValue) {
		this.handleId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="DEPLOY_ID" type="java.lang.String" length="128" not-null="true" unique="false"
	 */
	public String getDeployId() {
		return this.deployId;
	}
	
	/**
	 * Set the deployId
	 * @spring.validator type="required"
	 */	
	public void setDeployId(String aValue) {
		this.deployId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="ACTIVITY_NAME" type="java.lang.String" length="128" not-null="false" unique="false"
	 */
	public String getActivityName() {
		return this.activityName;
	}
	
	/**
	 * Set the activityName
	 */	
	public void setActivityName(String aValue) {
		this.activityName = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="TRAN_NAME" type="java.lang.String" length="128" not-null="false" unique="false"
	 */
	public String getTranName() {
		return this.tranName;
	}
	
	/**
	 * Set the tranName
	 */	
	public void setTranName(String aValue) {
		this.tranName = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="EVENT_NAME" type="java.lang.String" length="128" not-null="false" unique="false"
	 */
	public String getEventName() {
		return this.eventName;
	}
	
	/**
	 * Set the eventName
	 */	
	public void setEventName(String aValue) {
		this.eventName = aValue;
	}	

	/**
	 * 	 * @return Short
	 * @hibernate.property column="EVENT_LEVEL" type="java.lang.Short" length="6" not-null="false" unique="false"
	 */
	public Short getEventLevel() {
		return this.eventLevel;
	}
	
	/**
	 * Set the eventLevel
	 */	
	public void setEventLevel(Short aValue) {
		this.eventLevel = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="EXE_CODE" type="java.lang.String" length="4000" not-null="false" unique="false"
	 */
	public String getExeCode() {
		return this.exeCode;
	}
	
	/**
	 * Set the exeCode
	 */	
	public void setExeCode(String aValue) {
		this.exeCode = aValue;
	}	

	/**
	 * 	 * @return Long
	 * @hibernate.property column="HANDLE_TYPE" type="java.lang.Short" length="22" not-null="false" unique="false"
	 */
	public Short getHandleType() {
		return this.handleType;
	}
	
	/**
	 * Set the handleType
	 */	
	public void setHandleType(Short aValue) {
		this.handleType = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof ProHandleComp)) {
			return false;
		}
		ProHandleComp rhs = (ProHandleComp) object;
		return new EqualsBuilder()
				.append(this.handleId, rhs.handleId)
				.append(this.deployId, rhs.deployId)
				.append(this.activityName, rhs.activityName)
				.append(this.tranName, rhs.tranName)
				.append(this.eventName, rhs.eventName)
				.append(this.eventLevel, rhs.eventLevel)
				.append(this.exeCode, rhs.exeCode)
				.append(this.handleType, rhs.handleType)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.handleId) 
				.append(this.deployId) 
				.append(this.activityName) 
				.append(this.tranName) 
				.append(this.eventName) 
				.append(this.eventLevel) 
				.append(this.exeCode) 
				.append(this.handleType) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("handleId", this.handleId) 
				.append("deployId", this.deployId) 
				.append("activityName", this.activityName) 
				.append("tranName", this.tranName) 
				.append("eventName", this.eventName) 
				.append("eventLevel", this.eventLevel) 
				.append("exeCode", this.exeCode) 
				.append("handleType", this.handleType) 
				.toString();
	}



}
