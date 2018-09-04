package com.zhiwei.credit.model.creditFlow.financingAgency.typeManger;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

import com.google.gson.annotations.Expose;

/**
 * 
 * @author 
 *
 */
/**
 * PlKeepCreditlevel Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * 项目维护信用等级
 */
public class PlKeepCreditlevel extends com.zhiwei.core.model.BaseModel {
	/**
	 * 主键
	 */
    protected Long creditLevelId;
	/**
	 * 名称
	 */
    @Expose
	protected String name;
	/**
	 * 等级
	 */
	protected String keyStr;
	/**
	 * 备注
	 */
	protected String remark;

	protected java.util.Set plBusinessDirProKeeps = new java.util.HashSet();
	protected java.util.Set plPersionDirProKeeps = new java.util.HashSet();

	/**
	 * Default Empty Constructor for class PlKeepCreditlevel
	 */
	public PlKeepCreditlevel () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class PlKeepCreditlevel
	 */
	public PlKeepCreditlevel (
		 Long in_creditLevelId
        ) {
		this.setCreditLevelId(in_creditLevelId);
    }


	public java.util.Set getPlBusinessDirProKeeps () {
		return plBusinessDirProKeeps;
	}	
	
	public void setPlBusinessDirProKeeps (java.util.Set in_plBusinessDirProKeeps) {
		this.plBusinessDirProKeeps = in_plBusinessDirProKeeps;
	}

	public java.util.Set getPlPersionDirProKeeps () {
		return plPersionDirProKeeps;
	}	
	
	public void setPlPersionDirProKeeps (java.util.Set in_plPersionDirProKeeps) {
		this.plPersionDirProKeeps = in_plPersionDirProKeeps;
	}
    

	/**
	 * creditLevelId	 * @return Long
     * @hibernate.id column="creditLevelId" type="java.lang.Long" generator-class="native"
	 */
	public Long getCreditLevelId() {
		return this.creditLevelId;
	}
	
	/**
	 * Set the creditLevelId
	 */	
	public void setCreditLevelId(Long aValue) {
		this.creditLevelId = aValue;
	}	

	/**
	 * 名称	 * @return String
	 * @hibernate.property column="name" type="java.lang.String" length="50" not-null="false" unique="false"
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Set the name
	 */	
	public void setName(String aValue) {
		this.name = aValue;
	}	

	/**
	 * Key	 * @return String
	 * @hibernate.property column="keyStr" type="java.lang.String" length="50" not-null="false" unique="false"
	 */
	public String getKeyStr() {
		return this.keyStr;
	}
	
	/**
	 * Set the keyStr
	 */	
	public void setKeyStr(String aValue) {
		this.keyStr = aValue;
	}	

	/**
	 * 描述	 * @return String
	 * @hibernate.property column="remark" type="java.lang.String" length="65535" not-null="false" unique="false"
	 */
	public String getRemark() {
		return this.remark;
	}
	
	/**
	 * Set the remark
	 */	
	public void setRemark(String aValue) {
		this.remark = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof PlKeepCreditlevel)) {
			return false;
		}
		PlKeepCreditlevel rhs = (PlKeepCreditlevel) object;
		return new EqualsBuilder()
				.append(this.creditLevelId, rhs.creditLevelId)
				.append(this.name, rhs.name)
				.append(this.keyStr, rhs.keyStr)
				.append(this.remark, rhs.remark)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.creditLevelId) 
				.append(this.name) 
				.append(this.keyStr) 
				.append(this.remark) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("creditLevelId", this.creditLevelId) 
				.append("name", this.name) 
				.append("keyStr", this.keyStr) 
				.append("remark", this.remark) 
				.toString();
	}



}
