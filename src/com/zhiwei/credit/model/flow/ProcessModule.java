package com.zhiwei.credit.model.flow;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
*/
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * ProcessModule Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * 
 */
public class ProcessModule extends com.zhiwei.core.model.BaseModel {

    protected Long moduleid;
	protected String modulename;
	protected String modulekey;
	protected String descp;
	protected String processkey;
	protected String creator;
	protected java.util.Date createtime;
	protected com.zhiwei.credit.model.flow.ProDefinition proDefinition;


	/**
	 * Default Empty Constructor for class ProcessModule
	 */
	public ProcessModule () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class ProcessModule
	 */
	public ProcessModule (
		 Long in_moduleid
        ) {
		this.setModuleid(in_moduleid);
    }

	
	public com.zhiwei.credit.model.flow.ProDefinition getProDefinition () {
		return proDefinition;
	}	
	
	public void setProDefinition (com.zhiwei.credit.model.flow.ProDefinition in_proDefinition) {
		this.proDefinition = in_proDefinition;
	}
    

	/**
	 * 	 * @return Long
     * @hibernate.id column="MODULEID" type="java.lang.Long" generator-class="native"
	 */
	public Long getModuleid() {
		return this.moduleid;
	}
	
	/**
	 * Set the moduleid
	 */	
	public void setModuleid(Long aValue) {
		this.moduleid = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="MODULENAME" type="java.lang.String" length="256" not-null="true" unique="false"
	 */
	public String getModulename() {
		return this.modulename;
	}
	
	/**
	 * Set the modulename
	 * @spring.validator type="required"
	 */	
	public void setModulename(String aValue) {
		this.modulename = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="MODULEKEY" type="java.lang.String" length="128" not-null="true" unique="false"
	 */
	public String getModulekey() {
		return this.modulekey;
	}
	
	/**
	 * Set the modulekey
	 * @spring.validator type="required"
	 */	
	public void setModulekey(String aValue) {
		this.modulekey = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="DESCP" type="java.lang.String" length="4000" not-null="false" unique="false"
	 */
	public String getDescp() {
		return this.descp;
	}
	
	/**
	 * Set the descp
	 */	
	public void setDescp(String aValue) {
		this.descp = aValue;
	}	

	/**
	 * 	 * @return Long
	 */
	public Long getDefId() {
		return this.getProDefinition()==null?null:this.getProDefinition().getDefId();
	}
	
	/**
	 * Set the defid
	 */	
	public void setDefId(Long aValue) {
	    if (aValue==null) {
	    	proDefinition = null;
	    } else if (proDefinition == null) {
	        proDefinition = new com.zhiwei.credit.model.flow.ProDefinition(aValue);
	        proDefinition.setVersion(new Integer(0));//set a version to cheat hibernate only
	    } else {
	    	//
			proDefinition.setDefId(aValue);
	    }
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="PROCESSKEY" type="java.lang.String" length="256" not-null="false" unique="false"
	 */
	public String getProcesskey() {
		return this.processkey;
	}
	
	/**
	 * Set the processkey
	 */	
	public void setProcesskey(String aValue) {
		this.processkey = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="CREATOR" type="java.lang.String" length="64" not-null="false" unique="false"
	 */
	public String getCreator() {
		return this.creator;
	}
	
	/**
	 * Set the creator
	 */	
	public void setCreator(String aValue) {
		this.creator = aValue;
	}	

	/**
	 * 	 * @return java.util.Date
	 * @hibernate.property column="CREATETIME" type="java.util.Date" length="7" not-null="false" unique="false"
	 */
	public java.util.Date getCreatetime() {
		return this.createtime;
	}
	
	/**
	 * Set the createtime
	 */	
	public void setCreatetime(java.util.Date aValue) {
		this.createtime = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof ProcessModule)) {
			return false;
		}
		ProcessModule rhs = (ProcessModule) object;
		return new EqualsBuilder()
				.append(this.moduleid, rhs.moduleid)
				.append(this.modulename, rhs.modulename)
				.append(this.modulekey, rhs.modulekey)
				.append(this.descp, rhs.descp)
						.append(this.processkey, rhs.processkey)
				.append(this.creator, rhs.creator)
				.append(this.createtime, rhs.createtime)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.moduleid) 
				.append(this.modulename) 
				.append(this.modulekey) 
				.append(this.descp) 
						.append(this.processkey) 
				.append(this.creator) 
				.append(this.createtime) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("moduleid", this.moduleid) 
				.append("modulename", this.modulename) 
				.append("modulekey", this.modulekey) 
				.append("descp", this.descp) 
						.append("processkey", this.processkey) 
				.append("creator", this.creator) 
				.append("createtime", this.createtime) 
				.toString();
	}



}
