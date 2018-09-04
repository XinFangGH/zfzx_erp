package com.zhiwei.credit.model.customer;
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
 * Customerbanklinkman Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class Customerbanklinkman extends com.zhiwei.core.model.BaseModel {

    protected Long id;
	protected String name;
	protected String pos;
	protected String sex;
	protected String phone;
	protected String main;
	protected String address;
	protected Long enterpriseid;


	/**
	 * Default Empty Constructor for class Customerbanklinkman
	 */
	public Customerbanklinkman () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class Customerbanklinkman
	 */
	public Customerbanklinkman (
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
	 * 	 * @return String
	 * @hibernate.property column="pos" type="java.lang.String" length="20" not-null="false" unique="false"
	 */
	public String getPos() {
		return this.pos;
	}
	
	/**
	 * Set the pos
	 */	
	public void setPos(String aValue) {
		this.pos = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="sex" type="java.lang.String" length="10" not-null="false" unique="false"
	 */
	public String getSex() {
		return this.sex;
	}
	
	/**
	 * Set the sex
	 */	
	public void setSex(String aValue) {
		this.sex = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="phone" type="java.lang.String" length="50" not-null="false" unique="false"
	 */
	public String getPhone() {
		return this.phone;
	}
	
	/**
	 * Set the phone
	 */	
	public void setPhone(String aValue) {
		this.phone = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="main" type="java.lang.String" length="10" not-null="false" unique="false"
	 */
	public String getMain() {
		return this.main;
	}
	
	/**
	 * Set the main
	 */	
	public void setMain(String aValue) {
		this.main = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="address" type="java.lang.String" length="200" not-null="false" unique="false"
	 */
	public String getAddress() {
		return this.address;
	}
	
	/**
	 * Set the address
	 */	
	public void setAddress(String aValue) {
		this.address = aValue;
	}	

	/**
	 * 	 * @return Long
	 * @hibernate.property column="enterpriseid" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getEnterpriseid() {
		return this.enterpriseid;
	}
	
	/**
	 * Set the enterpriseid
	 */	
	public void setEnterpriseid(Long aValue) {
		this.enterpriseid = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof Customerbanklinkman)) {
			return false;
		}
		Customerbanklinkman rhs = (Customerbanklinkman) object;
		return new EqualsBuilder()
				.append(this.id, rhs.id)
				.append(this.name, rhs.name)
				.append(this.pos, rhs.pos)
				.append(this.sex, rhs.sex)
				.append(this.phone, rhs.phone)
				.append(this.main, rhs.main)
				.append(this.address, rhs.address)
				.append(this.enterpriseid, rhs.enterpriseid)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.id) 
				.append(this.name) 
				.append(this.pos) 
				.append(this.sex) 
				.append(this.phone) 
				.append(this.main) 
				.append(this.address) 
				.append(this.enterpriseid) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("id", this.id) 
				.append("name", this.name) 
				.append("pos", this.pos) 
				.append("sex", this.sex) 
				.append("phone", this.phone) 
				.append("main", this.main) 
				.append("address", this.address) 
				.append("enterpriseid", this.enterpriseid) 
				.toString();
	}



}
