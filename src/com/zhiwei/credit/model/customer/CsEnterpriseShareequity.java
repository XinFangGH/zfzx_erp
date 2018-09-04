package com.zhiwei.credit.model.customer;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * CsEnterpriseShareequity Base Java Bean, base class for the.oa.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * 也称股东表，如果是法人股东就是企业，自然人股东就是个人
 */
public class CsEnterpriseShareequity extends com.zhiwei.core.model.BaseModel {

    protected Long id;
	protected Long personid;
	protected Long enterpriseid;
	protected String shareholdertype;
	protected String shareholdercode;
	protected Double capital;
	protected String capitaltype;
	protected Double share;
	protected String shareholder;
	protected String remarks;


	/**
	 * Default Empty Constructor for class CsEnterpriseShareequity
	 */
	public CsEnterpriseShareequity () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class CsEnterpriseShareequity
	 */
	public CsEnterpriseShareequity (
		 Long in_id
        ) {
		this.setId(in_id);
    }

    

	/**
	 * 表主键，increment	 * @return Long
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
	 * 人员表的主键id	 * @return Long
	 * @hibernate.property column="personid" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getPersonid() {
		return this.personid;
	}
	
	/**
	 * Set the personid
	 */	
	public void setPersonid(Long aValue) {
		this.personid = aValue;
	}	

	/**
	 * 企业表的主键id	 * @return Long
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
	 * 股东类别：请输入代码（01：法人股东；02：自然人股东）	 * @return String
	 * @hibernate.property column="shareholdertype" type="java.lang.String" length="45" not-null="false" unique="false"
	 */
	public String getShareholdertype() {
		return this.shareholdertype;
	}
	
	/**
	 * Set the shareholdertype
	 */	
	public void setShareholdertype(String aValue) {
		this.shareholdertype = aValue;
	}	

	/**
	 * 股东代码：法人股东请输入组织机构代码；自然人股东请输入身份证号码。	 * @return String
	 * @hibernate.property column="shareholdercode" type="java.lang.String" length="45" not-null="false" unique="false"
	 */
	public String getShareholdercode() {
		return this.shareholdercode;
	}
	
	/**
	 * Set the shareholdercode
	 */	
	public void setShareholdercode(String aValue) {
		this.shareholdercode = aValue;
	}	

	/**
	 * 	 * @return Double
	 * @hibernate.property column="capital" type="java.lang.Double" length="20" not-null="false" unique="false"
	 */
	public Double getCapital() {
		return this.capital;
	}
	
	/**
	 * Set the capital
	 */	
	public void setCapital(Double aValue) {
		this.capital = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="capitaltype" type="java.lang.String" length="45" not-null="false" unique="false"
	 */
	public String getCapitaltype() {
		return this.capitaltype;
	}
	
	/**
	 * Set the capitaltype
	 */	
	public void setCapitaltype(String aValue) {
		this.capitaltype = aValue;
	}	

	/**
	 * 	 * @return Double
	 * @hibernate.property column="share" type="java.lang.Double" length="20" not-null="false" unique="false"
	 */
	public Double getShare() {
		return this.share;
	}
	
	/**
	 * Set the share
	 */	
	public void setShare(Double aValue) {
		this.share = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="shareholder" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getShareholder() {
		return this.shareholder;
	}
	
	/**
	 * Set the shareholder
	 */	
	public void setShareholder(String aValue) {
		this.shareholder = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="remarks" type="java.lang.String" length="65535" not-null="false" unique="false"
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
		if (!(object instanceof CsEnterpriseShareequity)) {
			return false;
		}
		CsEnterpriseShareequity rhs = (CsEnterpriseShareequity) object;
		return new EqualsBuilder()
				.append(this.id, rhs.id)
				.append(this.personid, rhs.personid)
				.append(this.enterpriseid, rhs.enterpriseid)
				.append(this.shareholdertype, rhs.shareholdertype)
				.append(this.shareholdercode, rhs.shareholdercode)
				.append(this.capital, rhs.capital)
				.append(this.capitaltype, rhs.capitaltype)
				.append(this.share, rhs.share)
				.append(this.shareholder, rhs.shareholder)
				.append(this.remarks, rhs.remarks)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.id) 
				.append(this.personid) 
				.append(this.enterpriseid) 
				.append(this.shareholdertype) 
				.append(this.shareholdercode) 
				.append(this.capital) 
				.append(this.capitaltype) 
				.append(this.share) 
				.append(this.shareholder) 
				.append(this.remarks) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("id", this.id) 
				.append("personid", this.personid) 
				.append("enterpriseid", this.enterpriseid) 
				.append("shareholdertype", this.shareholdertype) 
				.append("shareholdercode", this.shareholdercode) 
				.append("capital", this.capital) 
				.append("capitaltype", this.capitaltype) 
				.append("share", this.share) 
				.append("shareholder", this.shareholder) 
				.append("remarks", this.remarks) 
				.toString();
	}



}
