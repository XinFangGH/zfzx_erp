package com.zhiwei.credit.model.creditFlow.guarantee.guaranteefinance;
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
 * GlAccountBankCautionmoney Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class GlAccountBankCautionmoney extends com.zhiwei.core.model.BaseModel {

    protected Long id;
	protected Long parentId;      //授信银行的id
	protected Long bankBranchId;  //自己的银行id
	protected Long accountId;
	protected String text;    //支行
	protected java.util.Date createDate;
	protected String remark;
	protected Boolean leaf;
	protected String bankBranchName;   //全称 对应remarks
	protected String serviceTypeAccount;
	protected String bankAddress;
	protected String accountname;
	private java.math.BigDecimal accountFrozenMoney;//冻结总额
	private java.math.BigDecimal rawauthorizationMoney;//初始账户总额
	private java.math.BigDecimal rawsurplusMoney;//初始可用额度
	private java.math.BigDecimal authorizationMoney;//账户总额
	protected java.math.BigDecimal surplusMoney;  //可用额度
	protected Short idDelete;
	
	protected Long bankParentId;
	
	
    public Long getBankParentId() {
		return bankParentId;
	}

	public void setBankParentId(Long bankParentId) {
		this.bankParentId = bankParentId;
	}

	public Short getIdDelete() {
		return idDelete;
	}

	public void setIdDelete(Short idDelete) {
		this.idDelete = idDelete;
	}

	protected String bankName; //支行名称
	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	/**
	 * Default Empty Constructor for class GlAccountBankCautionmoney
	 */
	public GlAccountBankCautionmoney () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class GlAccountBankCautionmoney
	 */
	public GlAccountBankCautionmoney (
			Long in_id
        ) {
		this.setId(in_id);
    }

    

	public java.math.BigDecimal getRawauthorizationMoney() {
		return rawauthorizationMoney;
	}

	public void setRawauthorizationMoney(java.math.BigDecimal rawauthorizationMoney) {
		this.rawauthorizationMoney = rawauthorizationMoney;
	}

	/**
	 * 	 * @return Integer
     * @hibernate.id column="id" type="java.lang.Integer" generator-class="native"
	 */
	
	
	public String getBankAddress() {
		return bankAddress;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setBankAddress(String bankAddress) {
		this.bankAddress = bankAddress;
	}

	public String getAccountname() {
		return accountname;
	}

	public void setAccountname(String accountname) {
		this.accountname = accountname;
	}

	/**
	 * 授信银行表主id	 * @return String
	 * @hibernate.property column="parentId" type="java.lang.String" length="40" not-null="false" unique="false"
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

	public java.math.BigDecimal getAccountFrozenMoney() {
		return accountFrozenMoney;
	}

	public void setAccountFrozenMoney(java.math.BigDecimal accountFrozenMoney) {
		this.accountFrozenMoney = accountFrozenMoney;
	}



	public java.math.BigDecimal getAuthorizationMoney() {
		return authorizationMoney;
	}

	public void setAuthorizationMoney(java.math.BigDecimal authorizationMoney) {
		this.authorizationMoney = authorizationMoney;
	}

	public java.math.BigDecimal getSurplusMoney() {
		return surplusMoney;
	}

	public void setSurplusMoney(java.math.BigDecimal surplusMoney) {
		this.surplusMoney = surplusMoney;
	}

	/**
	 * 开户支行id	 * @return Integer
	 * @hibernate.property column="bankBranchId" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Long getBankBranchId() {
		return this.bankBranchId;
	}
	
	/**
	 * Set the bankBranchId
	 */	
	public void setBankBranchId(Long aValue) {
		this.bankBranchId = aValue;
	}	

	/**
	 * 保证金账户id	 * @return String
	 * @hibernate.property column="accountId" type="java.lang.String" length="40" not-null="false" unique="false"
	 */
	public Long getAccountId() {
		return this.accountId;
	}
	
	/**
	 * Set the accountId
	 */	
	public void setAccountId(Long aValue) {
		this.accountId = aValue;
	}	

	/**
	 * 保证金账户名称	 * @return String
	 * @hibernate.property column="text" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getText() {
		return this.text;
	}
	
	/**
	 * Set the text
	 */	
	public void setText(String aValue) {
		this.text = aValue;
	}	

	/**
	 * 账户创建时间	 * @return java.util.Date
	 * @hibernate.property column="createDate" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getCreateDate() {
		return this.createDate;
	}
	
	/**
	 * Set the createDate
	 */	
	public void setCreateDate(java.util.Date aValue) {
		this.createDate = aValue;
	}	

	/**
	 * 备注	 * @return String
	 * @hibernate.property column="remark" type="java.lang.String" length="255" not-null="false" unique="false"
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
	 * @hibernate.property column="bankBranchName" type="java.lang.String" length="50" not-null="false" unique="false"
	 */
	public String getBankBranchName() {
		return this.bankBranchName;
	}
	
	/**
	 * Set the bankBranchName
	 */	
	public void setBankBranchName(String aValue) {
		this.bankBranchName = aValue;
	}	

	public java.math.BigDecimal getRawsurplusMoney() {
		return rawsurplusMoney;
	}

	public void setRawsurplusMoney(java.math.BigDecimal rawsurplusMoney) {
		this.rawsurplusMoney = rawsurplusMoney;
	}

	/**
	 * 	 * @return String
	 * @hibernate.property column="serviceTypeAccount" type="java.lang.String" length="100" not-null="false" unique="false"
	 */
	public String getServiceTypeAccount() {
		return this.serviceTypeAccount;
	}
	
	/**
	 * Set the serviceTypeAccount
	 */	
	public void setServiceTypeAccount(String aValue) {
		this.serviceTypeAccount = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof GlAccountBankCautionmoney)) {
			return false;
		}
		GlAccountBankCautionmoney rhs = (GlAccountBankCautionmoney) object;
		return new EqualsBuilder()
				.append(this.id, rhs.id)
				.append(this.parentId, rhs.parentId)
				.append(this.bankBranchId, rhs.bankBranchId)
				.append(this.accountId, rhs.accountId)
				.append(this.text, rhs.text)
				.append(this.createDate, rhs.createDate)
				.append(this.remark, rhs.remark)
				.append(this.leaf, rhs.leaf)
				.append(this.bankBranchName, rhs.bankBranchName)
				.append(this.serviceTypeAccount, rhs.serviceTypeAccount)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.id) 
				.append(this.parentId) 
				.append(this.bankBranchId) 
				.append(this.accountId) 
				.append(this.text) 
				.append(this.createDate) 
				.append(this.remark) 
				.append(this.leaf) 
				.append(this.bankBranchName) 
				.append(this.serviceTypeAccount) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("id", this.id) 
				.append("parentId", this.parentId) 
				.append("bankBranchId", this.bankBranchId) 
				.append("accountId", this.accountId) 
				.append("text", this.text) 
				.append("createDate", this.createDate) 
				.append("remark", this.remark) 
				.append("leaf", this.leaf) 
				.append("bankBranchName", this.bankBranchName) 
				.append("serviceTypeAccount", this.serviceTypeAccount) 
				.append("bankParentId", this.bankParentId) 
				.toString();
	}

	

}
