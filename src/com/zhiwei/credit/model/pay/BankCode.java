package com.zhiwei.credit.model.pay;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
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
 * BankCode Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * 乾多多对应的省市表
 */
public class BankCode extends com.hurong.core.model.BaseModel {
	/**
	 * 主键id
	 */
    protected String code;
    /**
	 * 名称
	 */
	protected String name;
	/**
	 * 父节点code
	 */
	protected String parentCode;
	/**
	 * 第三方支付配置字段
	 */
	protected String thirdPayConfig;
	
	public String getThirdPayConfig() {
		return thirdPayConfig;
	}

	public void setThirdPayConfig(String thirdPayConfig) {
		this.thirdPayConfig = thirdPayConfig;
	}

	/**
	 * Default Empty Constructor for class BankCode
	 */
	public BankCode () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class BankCode
	 */
	public BankCode (
		 String in_code
        ) {
		this.setCode(in_code);
    }

    

	/**
	 * 省市代码	 * @return String
     * @hibernate.id column="code" type="java.lang.String" generator-class="native"
	 */
	public String getCode() {
		return this.code;
	}
	
	/**
	 * Set the code
	 */	
	public void setCode(String aValue) {
		this.code = aValue;
	}	

	/**
	 * 名称	 * @return String
	 * @hibernate.property column="name" type="java.lang.String" length="255" not-null="false" unique="false"
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
	 * 上一级	 * @return String
	 * @hibernate.property column="parentCode" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getParentCode() {
		return this.parentCode;
	}
	
	/**
	 * Set the parentCode
	 */	
	public void setParentCode(String aValue) {
		this.parentCode = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof BankCode)) {
			return false;
		}
		BankCode rhs = (BankCode) object;
		return new EqualsBuilder()
				.append(this.code, rhs.code)
				.append(this.name, rhs.name)
				.append(this.parentCode, rhs.parentCode)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.code) 
				.append(this.name) 
				.append(this.parentCode) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("code", this.code) 
				.append("name", this.name) 
				.append("parentCode", this.parentCode) 
				.toString();
	}



}
