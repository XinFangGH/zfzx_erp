package com.zhiwei.credit.model.creditFlow.contract;
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
 * CsElementCategory Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class CsElementCategory extends com.zhiwei.core.model.BaseModel {

    protected Long id;
	protected String elementCode;
	protected String description;
	protected String method;
	protected String businessType;


	/**
	 * Default Empty Constructor for class CsElementCategory
	 */
	public CsElementCategory () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class CsElementCategory
	 */
	public CsElementCategory (
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
	 * 要素编码	 * @return String
	 * @hibernate.property column="elementCode" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getElementCode() {
		return this.elementCode;
	}
	
	/**
	 * Set the elementCode
	 */	
	public void setElementCode(String aValue) {
		this.elementCode = aValue;
	}	

	/**
	 * 要素描述	 * @return String
	 * @hibernate.property column="description" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getDescription() {
		return this.description;
	}
	
	/**
	 * Set the description
	 */	
	public void setDescription(String aValue) {
		this.description = aValue;
	}	

	/**
	 * 预留  拓展	 * @return String
	 * @hibernate.property column="method" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getMethod() {
		return this.method;
	}
	
	/**
	 * Set the method
	 */	
	public void setMethod(String aValue) {
		this.method = aValue;
	}	

	/**
	 * 业务类别	 * @return String
	 * @hibernate.property column="businessType" type="java.lang.String" length="100" not-null="false" unique="false"
	 */
	public String getBusinessType() {
		return this.businessType;
	}
	
	/**
	 * Set the businessType
	 */	
	public void setBusinessType(String aValue) {
		this.businessType = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof CsElementCategory)) {
			return false;
		}
		CsElementCategory rhs = (CsElementCategory) object;
		return new EqualsBuilder()
				.append(this.id, rhs.id)
				.append(this.elementCode, rhs.elementCode)
				.append(this.description, rhs.description)
				.append(this.method, rhs.method)
				.append(this.businessType, rhs.businessType)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.id) 
				.append(this.elementCode) 
				.append(this.description) 
				.append(this.method) 
				.append(this.businessType) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("id", this.id) 
				.append("elementCode", this.elementCode) 
				.append("description", this.description) 
				.append("method", this.method) 
				.append("businessType", this.businessType) 
				.toString();
	}



}
