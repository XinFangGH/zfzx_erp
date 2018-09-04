package com.zhiwei.credit.model.flow;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * 
 * @author 
 *
 */
/**
 * TaskProxy Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class TaskProxy extends com.zhiwei.core.model.BaseModel {

    protected Long id;
	protected Long principalId;
	protected Long agentId;
	protected java.util.Date startDate;
	protected java.util.Date endDate;
	protected Long createId;
	protected java.util.Date createDate;
	
	//不与数据库映射字段
	protected String principalName;
	protected String agentName;
	protected String createName;
	protected Short status;


	/**
	 * Default Empty Constructor for class TaskProxy
	 */
	public TaskProxy () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class TaskProxy
	 */
	public TaskProxy (
		 Long in_id
        ) {
		this.setId(in_id);
    }

    

	/**
	 * 	 * @return Long
     * @hibernate.id column="id" type="java.lang.Long" generator-class="native"
	 */
	public Long getId() {
		return this.id;
	}
	
	/**
	 * Set the id
	 */	
	public void setId(Long aValue) {
		this.id = aValue;
	}	

	/**
	 * 被代理人id	 * @return Long
	 * @hibernate.property column="principalId" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getPrincipalId() {
		return this.principalId;
	}
	
	/**
	 * Set the principalId
	 */	
	public void setPrincipalId(Long aValue) {
		this.principalId = aValue;
	}	

	/**
	 * 代理人	 * @return Long
	 * @hibernate.property column="agentId" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getAgentId() {
		return this.agentId;
	}
	
	/**
	 * Set the agentId
	 */	
	public void setAgentId(Long aValue) {
		this.agentId = aValue;
	}	

	/**
	 * 代理开始时间	 * @return java.util.Date
	 * @hibernate.property column="startDate" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getStartDate() {
		return this.startDate;
	}
	
	/**
	 * Set the startDate
	 */	
	public void setStartDate(java.util.Date aValue) {
		this.startDate = aValue;
	}	

	/**
	 * 代理结束时间	 * @return java.util.Date
	 * @hibernate.property column="endDate" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getEndDate() {
		return this.endDate;
	}
	
	/**
	 * Set the endDate
	 */	
	public void setEndDate(java.util.Date aValue) {
		this.endDate = aValue;
	}	

	/**
	 * 制定人	 * @return Long
	 * @hibernate.property column="createId" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getCreateId() {
		return this.createId;
	}
	
	/**
	 * Set the createId
	 */	
	public void setCreateId(Long aValue) {
		this.createId = aValue;
	}	

	/**
	 * 制定时间	 * @return java.util.Date
	 * @hibernate.property column="createDate" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getCreateDate() {
		return this.createDate;
	}
	
	/**
	 * Set the createDate
	 */	
	public void setCreateDate(java.util.Date aValue) {
		this.createDate = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof TaskProxy)) {
			return false;
		}
		TaskProxy rhs = (TaskProxy) object;
		return new EqualsBuilder()
				.append(this.id, rhs.id)
				.append(this.principalId, rhs.principalId)
				.append(this.agentId, rhs.agentId)
				.append(this.startDate, rhs.startDate)
				.append(this.endDate, rhs.endDate)
				.append(this.createId, rhs.createId)
				.append(this.createDate, rhs.createDate)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.id) 
				.append(this.principalId) 
				.append(this.agentId) 
				.append(this.startDate) 
				.append(this.endDate) 
				.append(this.createId) 
				.append(this.createDate) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("id", this.id) 
				.append("principalId", this.principalId) 
				.append("agentId", this.agentId) 
				.append("startDate", this.startDate) 
				.append("endDate", this.endDate) 
				.append("createId", this.createId) 
				.append("createDate", this.createDate) 
				.toString();
	}

	public String getPrincipalName() {
		return principalName;
	}

	public void setPrincipalName(String principalName) {
		this.principalName = principalName;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}



}
