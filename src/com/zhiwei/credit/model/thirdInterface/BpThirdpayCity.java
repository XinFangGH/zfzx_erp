package com.zhiwei.credit.model.thirdInterface;
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
 * BpThirdpayCity Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class BpThirdpayCity extends com.hurong.core.model.BaseModel {

    protected Long id;
	protected String name;
	protected String number;
	protected String typeKey;


	/**
	 * Default Empty Constructor for class BpThirdpayCity
	 */
	public BpThirdpayCity () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class BpThirdpayCity
	 */
	public BpThirdpayCity (
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
	 * 	 * @return String
	 * @hibernate.property column="name" type="java.lang.String" length="100" not-null="false" unique="false"
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
	 * 	 * @return String
	 * @hibernate.property column="number" type="java.lang.String" length="50" not-null="false" unique="false"
	 */
	public String getNumber() {
		return this.number;
	}
	
	/**
	 * Set the number
	 */	
	public void setNumber(String aValue) {
		this.number = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="typeKey" type="java.lang.String" length="50" not-null="false" unique="false"
	 */
	public String getTypeKey() {
		return this.typeKey;
	}
	
	/**
	 * Set the typeKey
	 */	
	public void setTypeKey(String aValue) {
		this.typeKey = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof BpThirdpayCity)) {
			return false;
		}
		BpThirdpayCity rhs = (BpThirdpayCity) object;
		return new EqualsBuilder()
				.append(this.id, rhs.id)
				.append(this.name, rhs.name)
				.append(this.number, rhs.number)
				.append(this.typeKey, rhs.typeKey)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.id) 
				.append(this.name) 
				.append(this.number) 
				.append(this.typeKey) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("id", this.id) 
				.append("name", this.name) 
				.append("number", this.number) 
				.append("typeKey", this.typeKey) 
				.toString();
	}



}
