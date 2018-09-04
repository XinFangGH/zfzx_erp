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
 * PlKeepProtype Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * 借款项目类型
 */
public class PlKeepProtype extends com.zhiwei.core.model.BaseModel {
	/**
	 * 主键id
	 */
    protected Long typeId;
	/**
	 * 名称
	 */
    @Expose
	protected String name;
	/**
	 * 关键字
	 */
	protected String keyStr;
	/**
	 * 备注
	 */
	protected String remark;
	/**
	 * 是否删除0  或者 null  为空表示未删除，1 表示已删除
	 */
	protected Short isDelete;//0  或者 null  为空表示未删除，1 表示已删除

	protected java.util.Set plBusinessDirProKeeps = new java.util.HashSet();
	protected java.util.Set plPersionDirProKeeps = new java.util.HashSet();

	/**
	 * Default Empty Constructor for class PlKeepProtype
	 */
	public PlKeepProtype () {
		super();
	}
	
	public Short getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Short isDelete) {
		this.isDelete = isDelete;
	}

	/**
	 * Default Key Fields Constructor for class PlKeepProtype
	 */
	public PlKeepProtype (
		 Long in_typeId
        ) {
		this.setTypeId(in_typeId);
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
	 * 	 * @return Long
     * @hibernate.id column="typeId" type="java.lang.Long" generator-class="native"
	 */
	public Long getTypeId() {
		return this.typeId;
	}
	
	/**
	 * Set the typeId
	 */	
	public void setTypeId(Long aValue) {
		this.typeId = aValue;
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
	 * key	 * @return String
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
		if (!(object instanceof PlKeepProtype)) {
			return false;
		}
		PlKeepProtype rhs = (PlKeepProtype) object;
		return new EqualsBuilder()
				.append(this.typeId, rhs.typeId)
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
				.append(this.typeId) 
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
				.append("typeId", this.typeId) 
				.append("name", this.name) 
				.append("keyStr", this.keyStr) 
				.append("remark", this.remark) 
				.toString();
	}



}
