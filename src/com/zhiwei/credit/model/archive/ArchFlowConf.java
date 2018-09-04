package com.zhiwei.credit.model.archive;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
*/
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * ArchFlowConf Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * ������������������
 */
public class ArchFlowConf extends com.zhiwei.core.model.BaseModel {

    public static Short ARCH_SEND_TYPE=0;//发文流程
    public static Short ARCH_REC_TYPE=1;//收文流程
	protected Long configId;
	protected String processName;
	protected Long defId;
	protected Short archType;
	protected Long depId;


	/**
	 * Default Empty Constructor for class ArchFlowConf
	 */
	public ArchFlowConf () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class ArchFlowConf
	 */
	public ArchFlowConf (
		 Long in_configId
        ) {
		this.setConfigId(in_configId);
    }

    

	/**
	 * 	 * @return Long
     * @hibernate.id column="configId" type="java.lang.Long" generator-class="native"
	 */
	public Long getConfigId() {
		return this.configId;
	}
	
	/**
	 * Set the configId
	 */	
	public void setConfigId(Long aValue) {
		this.configId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="processName" type="java.lang.String" length="128" not-null="true" unique="false"
	 */
	public String getProcessName() {
		return this.processName;
	}
	
	/**
	 * Set the processName
	 * @spring.validator type="required"
	 */	
	public void setProcessName(String aValue) {
		this.processName = aValue;
	}	

	/**
	 * 	 * @return Long
	 * @hibernate.property column="processDefId" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getDefId() {
		return this.defId;
	}
	
	/**
	 * Set the processDefId
	 */	
	public void setDefId(Long aValue) {
		this.defId = aValue;
	}	

	public Long getDepId() {
		return depId;
	}

	public void setDepId(Long depId) {
		this.depId = depId;
	}

	/**
	 * 	 * @return Short
	 * @hibernate.property column="archType" type="java.lang.Short" length="5" not-null="false" unique="false"
	 */
	public Short getArchType() {
		return this.archType;
	}
	
	/**
	 * Set the archType
	 */	
	public void setArchType(Short aValue) {
		this.archType = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof ArchFlowConf)) {
			return false;
		}
		ArchFlowConf rhs = (ArchFlowConf) object;
		return new EqualsBuilder()
				.append(this.configId, rhs.configId)
				.append(this.processName, rhs.processName)
				.append(this.defId, rhs.defId)
				.append(this.archType, rhs.archType)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.configId) 
				.append(this.processName) 
				.append(this.defId) 
				.append(this.archType) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("configId", this.configId) 
				.append("processName", this.processName) 
				.append("processDefId", this.defId) 
				.append("archType", this.archType) 
				.toString();
	}



}
