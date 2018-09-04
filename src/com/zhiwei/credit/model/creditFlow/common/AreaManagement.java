package com.zhiwei.credit.model.creditFlow.common;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
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
 * AreaManagement Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class AreaManagement extends com.zhiwei.core.model.BaseModel {

    protected Long id;
	protected Long parentId;
	protected String text;
	protected Boolean leaf;
	protected String remarks;
	protected String remarks1;

	/**
	 * Default Empty Constructor for class AreaManagement
	 */
	public AreaManagement () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class AreaManagement
	 */
	public AreaManagement (
		 Long in_id
        ) {
		this.setId(in_id);
    }

    

	public String getRemarks1() {
		return remarks1;
	}

	public void setRemarks1(String remarks1) {
		this.remarks1 = remarks1;
	}

	/**
	 * 	 * @return Long
     * @hibernate.id column="areaId" type="java.lang.Long" generator-class="native"
	 */
	public Long getId() {
		return this.id;
	}
	
	/**
	 * Set the areaId
	 */	
	public void setId(Long aValue) {
		this.id = aValue;
	}	

	/**
	 * 	 * @return Long
	 * @hibernate.property column="parentId" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getParentId() {
		return this.parentId;
	}
	
	/**
	 * Set the parentId
	 */	
	public void setParentId(Long aValue) {
		this.parentId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="title" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getText() {
		return this.text;
	}
	
	/**
	 * Set the title
	 */	
	public void setText(String aValue) {
		this.text = aValue;
	}	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="leaf" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Boolean getLeaf() {
		return this.leaf;
	}
	
	/**
	 * Set the leaf
	 */	
	public void setLeaf(Boolean aValue) {
		this.leaf = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="remarks" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getRemarks() {
		return this.remarks;
	}
	
	/**
	 * Set the remarks
	 */	
	public void setRemarks(String aValue) {
		this.remarks = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof AreaManagement)) {
			return false;
		}
		AreaManagement rhs = (AreaManagement) object;
		return new EqualsBuilder()
				.append(this.id, rhs.id)
				.append(this.parentId, rhs.parentId)
				.append(this.text, rhs.text)
				.append(this.leaf, rhs.leaf)
				.append(this.remarks, rhs.remarks)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.id) 
				.append(this.parentId) 
				.append(this.text) 
				.append(this.leaf) 
				.append(this.remarks) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("id", this.id) 
				.append("parentId", this.parentId) 
				.append("text", this.text) 
				.append("leaf", this.leaf) 
				.append("remarks", this.remarks) 
				.toString();
	}



}
