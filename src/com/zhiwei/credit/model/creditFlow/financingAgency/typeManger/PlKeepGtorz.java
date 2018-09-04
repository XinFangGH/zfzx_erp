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
 * PlKeepGtorz Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * 担保机构
 */
public class PlKeepGtorz extends com.zhiwei.core.model.BaseModel {
	/**
	 * 主键id
	 */
    @Expose
    protected Long projGtOrzId;
	/**
	 * 名称
	 */
    @Expose
	protected String name;
	/**
	 * 备注
	 */
    @Expose
	protected String remark;
	/**
	 * 关键字
	 */
    @Expose
    protected String keyStr;

	public String getKeyStr() {
		return keyStr;
	}

	public void setKeyStr(String keyStr) {
		this.keyStr = keyStr;
	}

	protected java.util.Set plBusinessDirProKeeps = new java.util.HashSet();
	protected java.util.Set plPersionDirProKeeps = new java.util.HashSet();

	/**
	 * Default Empty Constructor for class PlKeepGtorz
	 */
	public PlKeepGtorz () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class PlKeepGtorz
	 */
	public PlKeepGtorz (
		 Long in_projGtOrzId
        ) {
		this.setProjGtOrzId(in_projGtOrzId);
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
	 * projGtOrzId	 * @return Long
     * @hibernate.id column="projGtOrzId" type="java.lang.Long" generator-class="native"
	 */
	public Long getProjGtOrzId() {
		return this.projGtOrzId;
	}
	
	/**
	 * Set the projGtOrzId
	 */	
	public void setProjGtOrzId(Long aValue) {
		this.projGtOrzId = aValue;
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
	 * 简介	 * @return String
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
		if (!(object instanceof PlKeepGtorz)) {
			return false;
		}
		PlKeepGtorz rhs = (PlKeepGtorz) object;
		return new EqualsBuilder()
				.append(this.projGtOrzId, rhs.projGtOrzId)
				.append(this.name, rhs.name)
				.append(this.remark, rhs.remark)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.projGtOrzId) 
				.append(this.name) 
				.append(this.remark) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("projGtOrzId", this.projGtOrzId) 
				.append("name", this.name) 
				.append("remark", this.remark) 
				.toString();
	}



}
