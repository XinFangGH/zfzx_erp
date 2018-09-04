package com.zhiwei.credit.model.p2p;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
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
 * BusPayAccount Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * 云购支付账户表
 */
public class BusPayAccount extends com.zhiwei.core.model.BaseModel {

    protected Long id;
	protected String accountType;
	protected String trueName;
	protected String account;
	protected String accountName;


	/**
	 * Default Empty Constructor for class BusPayAccount
	 */
	public BusPayAccount () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class BusPayAccount
	 */
	public BusPayAccount (
		 Long in_id
        ) {
		this.setId(in_id);
    }

    

	/**
	 * 主键id	 * @return Long
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
	 * 支付账户类型	 * @return String
	 * @hibernate.property column="accountType" type="java.lang.String" length="20" not-null="false" unique="false"
	 */
	public String getAccountType() {
		return this.accountType;
	}
	
	/**
	 * Set the accountType
	 */	
	public void setAccountType(String aValue) {
		this.accountType = aValue;
	}	

	/**
	 * 真实姓名	 * @return String
	 * @hibernate.property column="trueName" type="java.lang.String" length="10" not-null="false" unique="false"
	 */
	public String getTrueName() {
		return this.trueName;
	}
	
	/**
	 * Set the trueName
	 */	
	public void setTrueName(String aValue) {
		this.trueName = aValue;
	}	

	/**
	 * 账号	 * @return String
	 * @hibernate.property column="account" type="java.lang.String" length="20" not-null="false" unique="false"
	 */
	public String getAccount() {
		return this.account;
	}
	
	/**
	 * Set the account
	 */	
	public void setAccount(String aValue) {
		this.account = aValue;
	}	

	/**
	 * 账户名称	 * @return String
	 * @hibernate.property column="accountName" type="java.lang.String" length="100" not-null="false" unique="false"
	 */
	public String getAccountName() {
		return this.accountName;
	}
	
	/**
	 * Set the accountName
	 */	
	public void setAccountName(String aValue) {
		this.accountName = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof BusPayAccount)) {
			return false;
		}
		BusPayAccount rhs = (BusPayAccount) object;
		return new EqualsBuilder()
				.append(this.id, rhs.id)
				.append(this.accountType, rhs.accountType)
				.append(this.trueName, rhs.trueName)
				.append(this.account, rhs.account)
				.append(this.accountName, rhs.accountName)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.id) 
				.append(this.accountType) 
				.append(this.trueName) 
				.append(this.account) 
				.append(this.accountName) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("id", this.id) 
				.append("accounttype", this.accountType) 
				.append("trueName", this.trueName) 
				.append("account", this.account) 
				.append("accountName", this.accountName) 
				.toString();
	}
}