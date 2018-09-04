package com.zhiwei.credit.model.system;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
*/
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * SystemLog Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class SystemLog extends com.zhiwei.core.model.BaseModel {

    protected Long logId;
	protected String username;
	protected Long userId;
	protected java.util.Date createtime;
	protected String exeOperation;
	
	protected String className;// text;  类名称
	protected String methodName;// VARCHAR(100);  方法名
	protected String params;// text;  参数
	protected String modelName;// VARCHAR(100);  模块名称
	protected String ip;// VARCHAR(100);  
	protected String err;// text;   错误信息
	protected String flag;// VARCHAR(10); 0 表示 错误 1 表示正确
	



	/**
	 * Default Empty Constructor for class SystemLog
	 */
	public SystemLog () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class SystemLog
	 */
	public SystemLog (
		 Long in_logId
        ) {
		this.setLogId(in_logId);
    }

    

	/**
	 * 	 * @return Long
     * @hibernate.id column="logId" type="java.lang.Long" generator-class="native"
	 */
	public Long getLogId() {
		return this.logId;
	}
	
	/**
	 * Set the logId
	 */	
	public void setLogId(Long aValue) {
		this.logId = aValue;
	}	

	/**
	 * 用户名	 * @return String
	 * @hibernate.property column="username" type="java.lang.String" length="128" not-null="true" unique="false"
	 */
	public String getUsername() {
		return this.username;
	}
	
	/**
	 * Set the username
	 * @spring.validator type="required"
	 */	
	public void setUsername(String aValue) {
		this.username = aValue;
	}	

	/**
	 * 用户ID	 * @return Long
	 * @hibernate.property column="userId" type="java.lang.Long" length="19" not-null="true" unique="false"
	 */
	public Long getUserId() {
		return this.userId;
	}
	
	/**
	 * Set the userId
	 * @spring.validator type="required"
	 */	
	public void setUserId(Long aValue) {
		this.userId = aValue;
	}	

	/**
	 * 执行时间	 * @return java.util.Date
	 * @hibernate.property column="createtime" type="java.util.Date" length="19" not-null="true" unique="false"
	 */
	public java.util.Date getCreatetime() {
		return this.createtime;
	}
	
	/**
	 * Set the createtime
	 * @spring.validator type="required"
	 */	
	public void setCreatetime(java.util.Date aValue) {
		this.createtime = aValue;
	}	

	/**
	 * 执行操作	 * @return String
	 * @hibernate.property column="exeOperation" type="java.lang.String" length="512" not-null="true" unique="false"
	 */
	public String getExeOperation() {
		return this.exeOperation;
	}
	
	/**
	 * Set the exeOperation
	 * @spring.validator type="required"
	 */	
	public void setExeOperation(String aValue) {
		this.exeOperation = aValue;
	}	
	
	

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getErr() {
		return err;
	}

	public void setErr(String err) {
		this.err = err;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public Long getOrgid() {
		return orgId;
	}

	public void setOrgid(Long orgid) {
		this.orgId = orgid;
	}
	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof SystemLog)) {
			return false;
		}
		SystemLog rhs = (SystemLog) object;
		return new EqualsBuilder()
				.append(this.logId, rhs.logId)
				.append(this.username, rhs.username)
				.append(this.userId, rhs.userId)
				.append(this.createtime, rhs.createtime)
				.append(this.exeOperation, rhs.exeOperation)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.logId) 
				.append(this.username) 
				.append(this.userId) 
				.append(this.createtime) 
				.append(this.exeOperation) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("logId", this.logId) 
				.append("username", this.username) 
				.append("userId", this.userId) 
				.append("createtime", this.createtime) 
				.append("exeOperation", this.exeOperation) 
				.toString();
	}



}
