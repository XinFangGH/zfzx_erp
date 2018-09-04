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
 * BpMoneyManager Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * 账户金额管理
 */
public class BpMoneyManager extends com.zhiwei.core.model.BaseModel {
	/**
	 * 1充值  2提现  4投标 5还款  6二次分配 7 平台收费项
	 */
    public  static  final String TYPE1="1";
    public  static  final String TYPE2="2";
    public  static  final String TYPE4="4";
    public  static  final String TYPE5="5";
    public  static  final String TYPE6="6";
    public  static  final String TYPE7="7";
    protected Long id;
	protected String type;
	protected Integer CardType;
	protected String banknumber;
	protected String status;
	protected String RechargeMoneymoremore;
	protected String Amount;
	protected String LoanNo;
	protected String OrderNo;
	protected String SignInfo;
	protected String WithdrawMoneymoremore;
	protected String PlatformMoneymoremore;
	protected String FeePercent;
	protected String Fee;
	protected String FreeLimit;
	protected Long memberId;
	protected String BranchBankName;
	protected String Province;
	protected String City;
	protected String BankCode;
	protected java.util.Date dotime;
	protected String description;
	protected String BatchNo; //标号如是00+数字的代表该标 的其他收费

	public String getBatchNo() {
		return BatchNo;
	}

	public void setBatchNo(String batchNo) {
		BatchNo = batchNo;
	}

	/**
	 * Default Empty Constructor for class BpMoneyManager
	 */
	public BpMoneyManager () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class BpMoneyManager
	 */
	public BpMoneyManager (
			Long in_id
        ) {
		this.setId(in_id);
    }

    

	/**
	 * 	 * @return Integer 
     * @hibernate.id column="id" type="java.lang.Integer" generator-class="native"
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
	 * 类型（充值，提现，转账） 类型	 * @return String  1充值  2提现  4投标 5还款 * @return String
	 * @hibernate.property column="type" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getType() {
		return this.type;
	}
	
	/**
	 * Set the type
	 */	
	public void setType(String aValue) {
		this.type = aValue;
	}	

	/**
	 * 银行卡类型（0.借记卡1.信用卡）	 * @return Integer
	 * @hibernate.property column="CardType" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getCardType() {
		return this.CardType;
	}
	
	/**
	 * Set the CardType
	 */	
	public void setCardType(Integer aValue) {
		this.CardType = aValue;
	}	

	/**
	 * 银行卡号	 * @return String
	 * @hibernate.property column="banknumber" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getBanknumber() {
		return this.banknumber;
	}
	
	/**
	 * Set the banknumber
	 */	
	public void setBanknumber(String aValue) {
		this.banknumber = aValue;
	}	

	/**
	 * 操作状态（成功，失败）	 * @return String
	 * @hibernate.property column="status" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getStatus() {
		return this.status;
	}
	
	/**
	 * Set the status
	 */	
	public void setStatus(String aValue) {
		this.status = aValue;
	}	

	/**
	 * 要充值的账号的乾多多标识	 * @return String
	 * @hibernate.property column="RechargeMoneymoremore" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getRechargeMoneymoremore() {
		return this.RechargeMoneymoremore;
	}
	
	/**
	 * Set the RechargeMoneymoremore
	 */	
	public void setRechargeMoneymoremore(String aValue) {
		this.RechargeMoneymoremore = aValue;
	}	

	/**
	 * 金额	 * @return String
	 * @hibernate.property column="Amount" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getAmount() {
		return this.Amount;
	}
	
	/**
	 * Set the Amount
	 */	
	public void setAmount(String aValue) {
		this.Amount = aValue;
	}	

	/**
	 * 乾多多流水号	 * @return String
	 * @hibernate.property column="LoanNo" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getLoanNo() {
		return this.LoanNo;
	}
	
	/**
	 * Set the LoanNo
	 */	
	public void setLoanNo(String aValue) {
		this.LoanNo = aValue;
	}	

	/**
	 * 平台的充值订单号	 * @return String
	 * @hibernate.property column="OrderNo" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getOrderNo() {
		return this.OrderNo;
	}
	
	/**
	 * Set the OrderNo
	 */	
	public void setOrderNo(String aValue) {
		this.OrderNo = aValue;
	}	

	/**
	 * 加密验证信息	 * @return String
	 * @hibernate.property column="SignInfo" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getSignInfo() {
		return this.SignInfo;
	}
	
	/**
	 * Set the SignInfo
	 */	
	public void setSignInfo(String aValue) {
		this.SignInfo = aValue;
	}	

	/**
	 * 要提现的账号的乾多多标识	 * @return String
	 * @hibernate.property column="WithdrawMoneymoremore" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getWithdrawMoneymoremore() {
		return this.WithdrawMoneymoremore;
	}
	
	/**
	 * Set the WithdrawMoneymoremore
	 */	
	public void setWithdrawMoneymoremore(String aValue) {
		this.WithdrawMoneymoremore = aValue;
	}	

	/**
	 * 平台乾多多标识	 * @return String
	 * @hibernate.property column="PlatformMoneymoremore" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getPlatformMoneymoremore() {
		return this.PlatformMoneymoremore;
	}
	
	/**
	 * Set the PlatformMoneymoremore
	 */	
	public void setPlatformMoneymoremore(String aValue) {
		this.PlatformMoneymoremore = aValue;
	}	

	/**
	 * 平台承担的手续费比例	 * @return String
	 * @hibernate.property column="FeePercent" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getFeePercent() {
		return this.FeePercent;
	}
	
	/**
	 * Set the FeePercent
	 */	
	public void setFeePercent(String aValue) {
		this.FeePercent = aValue;
	}	

	/**
	 * 平台承担的手续费金额	 * @return String
	 * @hibernate.property column="Fee" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getFee() {
		return this.Fee;
	}
	
	/**
	 * Set the Fee
	 */	
	public void setFee(String aValue) {
		this.Fee = aValue;
	}	

	/**
	 * 平台扣除的免费提现额	 * @return String
	 * @hibernate.property column="FreeLimit" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getFreeLimit() {
		return this.FreeLimit;
	}
	
	/**
	 * Set the FreeLimit
	 */	
	public void setFreeLimit(String aValue) {
		this.FreeLimit = aValue;
	}	

	/**
	 * 关联平台用户ID	 * @return String
	 * @hibernate.property column="memberId" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public Long getMemberId() {
		return this.memberId;
	}
	
	/**
	 * Set the memberId
	 */	
	public void setMemberId(Long aValue) {
		this.memberId = aValue;
	}	

	/**
	 * 开户行名称	 * @return String
	 * @hibernate.property column="BranchBankName" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getBranchBankName() {
		return this.BranchBankName;
	}
	
	/**
	 * Set the BranchBankName
	 */	
	public void setBranchBankName(String aValue) {
		this.BranchBankName = aValue;
	}	

	/**
	 * 开户行省份	 * @return String
	 * @hibernate.property column="Province" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getProvince() {
		return this.Province;
	}
	
	/**
	 * Set the Province
	 */	
	public void setProvince(String aValue) {
		this.Province = aValue;
	}	

	/**
	 * 开户行城市	 * @return String
	 * @hibernate.property column="City" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getCity() {
		return this.City;
	}
	
	/**
	 * Set the City
	 */	
	public void setCity(String aValue) {
		this.City = aValue;
	}	

	/**
	 * 银行代码	 * @return String
	 * @hibernate.property column="BankCode" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getBankCode() {
		return this.BankCode;
	}
	
	/**
	 * Set the BankCode
	 */	
	public void setBankCode(String aValue) {
		this.BankCode = aValue;
	}	

	/**
	 * 操作时间	 * @return java.util.Date
	 * @hibernate.property column="dotime" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getDotime() {
		return this.dotime;
	}
	
	/**
	 * Set the dotime
	 */	
	public void setDotime(java.util.Date aValue) {
		this.dotime = aValue;
	}	

	/**
	 * 描述	 * @return String
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
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof BpMoneyManager)) {
			return false;
		}
		BpMoneyManager rhs = (BpMoneyManager) object;
		return new EqualsBuilder()
				.append(this.id, rhs.id)
				.append(this.type, rhs.type)
				.append(this.CardType, rhs.CardType)
				.append(this.banknumber, rhs.banknumber)
				.append(this.status, rhs.status)
				.append(this.RechargeMoneymoremore, rhs.RechargeMoneymoremore)
				.append(this.Amount, rhs.Amount)
				.append(this.LoanNo, rhs.LoanNo)
				.append(this.OrderNo, rhs.OrderNo)
				.append(this.SignInfo, rhs.SignInfo)
				.append(this.WithdrawMoneymoremore, rhs.WithdrawMoneymoremore)
				.append(this.PlatformMoneymoremore, rhs.PlatformMoneymoremore)
				.append(this.FeePercent, rhs.FeePercent)
				.append(this.Fee, rhs.Fee)
				.append(this.FreeLimit, rhs.FreeLimit)
				.append(this.memberId, rhs.memberId)
				.append(this.BranchBankName, rhs.BranchBankName)
				.append(this.Province, rhs.Province)
				.append(this.City, rhs.City)
				.append(this.BankCode, rhs.BankCode)
				.append(this.dotime, rhs.dotime)
				.append(this.description, rhs.description)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.id) 
				.append(this.type) 
				.append(this.CardType) 
				.append(this.banknumber) 
				.append(this.status) 
				.append(this.RechargeMoneymoremore) 
				.append(this.Amount) 
				.append(this.LoanNo) 
				.append(this.OrderNo) 
				.append(this.SignInfo) 
				.append(this.WithdrawMoneymoremore) 
				.append(this.PlatformMoneymoremore) 
				.append(this.FeePercent) 
				.append(this.Fee) 
				.append(this.FreeLimit) 
				.append(this.memberId) 
				.append(this.BranchBankName) 
				.append(this.Province) 
				.append(this.City) 
				.append(this.BankCode) 
				.append(this.dotime) 
				.append(this.description) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("id", this.id) 
				.append("type", this.type) 
				.append("CardType", this.CardType) 
				.append("banknumber", this.banknumber) 
				.append("status", this.status) 
				.append("RechargeMoneymoremore", this.RechargeMoneymoremore) 
				.append("Amount", this.Amount) 
				.append("LoanNo", this.LoanNo) 
				.append("OrderNo", this.OrderNo) 
				.append("SignInfo", this.SignInfo) 
				.append("WithdrawMoneymoremore", this.WithdrawMoneymoremore) 
				.append("PlatformMoneymoremore", this.PlatformMoneymoremore) 
				.append("FeePercent", this.FeePercent) 
				.append("Fee", this.Fee) 
				.append("FreeLimit", this.FreeLimit) 
				.append("memberId", this.memberId) 
				.append("BranchBankName", this.BranchBankName) 
				.append("Province", this.Province) 
				.append("City", this.City) 
				.append("BankCode", this.BankCode) 
				.append("dotime", this.dotime) 
				.append("description", this.description) 
				.toString();
	}



}
