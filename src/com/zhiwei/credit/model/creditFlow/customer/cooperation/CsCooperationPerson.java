package com.zhiwei.credit.model.creditFlow.customer.cooperation;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.math.BigDecimal;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 
 * @author 
 *
 */
/**
 * CsCooperationPerson Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class CsCooperationPerson extends com.zhiwei.core.model.BaseModel {

	protected Long id;
	/**
	 * 类型
	 * lenders : 债权客户     financial：  理财顾问
	 */
    protected String type;
	protected String name;
	/**
	 * 性别
	 * 0男
	 * 1女
	 */
	protected String sex;
	/**
	 * 数据字典ID
	 */
	protected String cardType;
	protected String cardNumber;
	protected String phoneNumber;
	protected String email;
	protected String qqNumber;
	protected String weixinNumber;
	protected String remark;
	/**
	 * 是否启用
	 * 0启用
	 * 1禁用
	 */
	protected String isUsing = "0";
	/**
	 * 是否开通P2P
	 * 0开通
	 * 1没开通
	 */
	protected String isOpenP2P = "1";
	
	
	protected String p2ploginname;
	protected String p2ptruename;
	protected String p2pcardcode;
	protected String p2ptelphone;
	protected String p2pemail;
	protected String p2pid;
	protected String p2pisForbidden;
	protected String thirdPayFlagId;
	protected String isCheckCard;//是否实名认证
	
	
	/**
	 * 资金账户
	 */
	protected Long accountId;
	protected String accountName;
	protected String accountNumber;
	protected Long investmentPersonId;
	protected Short investPersionType;
	protected BigDecimal totalMoney; 
	
	public String getThirdPayFlagId() {
		return thirdPayFlagId;
	}

	public void setThirdPayFlagId(String thirdPayFlagId) {
		this.thirdPayFlagId = thirdPayFlagId;
	}
	
	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public Long getInvestmentPersonId() {
		return investmentPersonId;
	}

	public void setInvestmentPersonId(Long investmentPersonId) {
		this.investmentPersonId = investmentPersonId;
	}

	public Short getInvestPersionType() {
		return investPersionType;
	}

	public void setInvestPersionType(Short investPersionType) {
		this.investPersionType = investPersionType;
	}

	public BigDecimal getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(BigDecimal totalMoney) {
		this.totalMoney = totalMoney;
	}


	
	

	public String getP2pisForbidden() {
		return p2pisForbidden;
	}

	public void setP2pisForbidden(String p2pisForbidden) {
		this.p2pisForbidden = p2pisForbidden;
	}

	public String getP2ploginname() {
		return p2ploginname;
	}

	public void setP2ploginname(String p2ploginname) {
		this.p2ploginname = p2ploginname;
	}

	public String getP2ptruename() {
		return p2ptruename;
	}

	public void setP2ptruename(String p2ptruename) {
		this.p2ptruename = p2ptruename;
	}

	public String getP2pcardcode() {
		return p2pcardcode;
	}

	public void setP2pcardcode(String p2pcardcode) {
		this.p2pcardcode = p2pcardcode;
	}

	public String getP2ptelphone() {
		return p2ptelphone;
	}

	public void setP2ptelphone(String p2ptelphone) {
		this.p2ptelphone = p2ptelphone;
	}

	public String getP2pemail() {
		return p2pemail;
	}

	public void setP2pemail(String p2pemail) {
		this.p2pemail = p2pemail;
	}

	public String getP2pid() {
		return p2pid;
	}

	public void setP2pid(String p2pid) {
		this.p2pid = p2pid;
	}

	/**
	 * Default Empty Constructor for class CsCooperationPerson
	 */
	public CsCooperationPerson () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class CsCooperationPerson
	 */
	public CsCooperationPerson (
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
	 * 姓名	 * @return String
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
	 * 性别	 * @return String
	 * @hibernate.property column="sex" type="java.lang.String" length="1" not-null="false" unique="false"
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
	 * 证件类型	 * @return String
	 * @hibernate.property column="cardType" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getCardType() {
		return this.cardType;
	}
	
	/**
	 * Set the cardType
	 */	
	public void setCardType(String aValue) {
		this.cardType = aValue;
	}	

	/**
	 * 证件号码	 * @return String
	 * @hibernate.property column="cardNumber" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getCardNumber() {
		return this.cardNumber;
	}
	
	/**
	 * Set the cardNumber
	 */	
	public void setCardNumber(String aValue) {
		this.cardNumber = aValue;
	}	

	/**
	 * 手机号码	 * @return String
	 * @hibernate.property column="phoneNumber" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getPhoneNumber() {
		return this.phoneNumber;
	}
	
	/**
	 * Set the phoneNumber
	 */	
	public void setPhoneNumber(String aValue) {
		this.phoneNumber = aValue;
	}	

	/**
	 * 邮箱	 * @return String
	 * @hibernate.property column="email" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getEmail() {
		return this.email;
	}
	
	/**
	 * Set the email
	 */	
	public void setEmail(String aValue) {
		this.email = aValue;
	}	

	/**
	 * QQ	 * @return String
	 * @hibernate.property column="qqNumber" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getQqNumber() {
		return this.qqNumber;
	}
	
	/**
	 * Set the qqNumber
	 */	
	public void setQqNumber(String aValue) {
		this.qqNumber = aValue;
	}	

	/**
	 *  微信	 * @return String
	 * @hibernate.property column="weixinNumber" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getWeixinNumber() {
		return this.weixinNumber;
	}
	
	/**
	 * Set the weixinNumber
	 */	
	public void setWeixinNumber(String aValue) {
		this.weixinNumber = aValue;
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
	 * 是否启用	 * @return String
	 * @hibernate.property column="isUsing" type="java.lang.String" length="1" not-null="false" unique="false"
	 */
	public String getIsUsing() {
		return this.isUsing;
	}
	
	/**
	 * Set the isUsing
	 */	
	public void setIsUsing(String aValue) {
		this.isUsing = aValue;
	}	

	/**
	 * 是否开通p2p	 * @return String
	 * @hibernate.property column="isOpenP2P" type="java.lang.String" length="1" not-null="false" unique="false"
	 */
	public String getIsOpenP2P() {
		return this.isOpenP2P;
	}
	
	/**
	 * Set the isOpenP2P
	 */	
	public void setIsOpenP2P(String aValue) {
		this.isOpenP2P = aValue;
	}	
	
	 public String getType() {
			return type;
		}

	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof CsCooperationPerson)) {
			return false;
		}
		CsCooperationPerson rhs = (CsCooperationPerson) object;
		return new EqualsBuilder()
				.append(this.id, rhs.id)
				.append(this.name, rhs.name)
				.append(this.sex, rhs.sex)
				.append(this.cardType, rhs.cardType)
				.append(this.cardNumber, rhs.cardNumber)
				.append(this.phoneNumber, rhs.phoneNumber)
				.append(this.email, rhs.email)
				.append(this.qqNumber, rhs.qqNumber)
				.append(this.weixinNumber, rhs.weixinNumber)
				.append(this.remark, rhs.remark)
				.append(this.isUsing, rhs.isUsing)
				.append(this.isOpenP2P, rhs.isOpenP2P)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.id) 
				.append(this.name) 
				.append(this.sex) 
				.append(this.cardType) 
				.append(this.cardNumber) 
				.append(this.phoneNumber) 
				.append(this.email) 
				.append(this.qqNumber) 
				.append(this.weixinNumber) 
				.append(this.remark) 
				.append(this.isUsing) 
				.append(this.isOpenP2P) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("id", this.id) 
				.append("name", this.name) 
				.append("sex", this.sex) 
				.append("cardType", this.cardType) 
				.append("cardNumber", this.cardNumber) 
				.append("phoneNumber", this.phoneNumber) 
				.append("email", this.email) 
				.append("qqNumber", this.qqNumber) 
				.append("weixinNumber", this.weixinNumber) 
				.append("remark", this.remark) 
				.append("isUsing", this.isUsing) 
				.append("isOpenP2P", this.isOpenP2P) 
				.toString();
	}

	public String getIsCheckCard() {
		return isCheckCard;
	}

	public void setIsCheckCard(String isCheckCard) {
		this.isCheckCard = isCheckCard;
	}



}
