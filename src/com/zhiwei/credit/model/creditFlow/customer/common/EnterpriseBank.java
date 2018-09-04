package com.zhiwei.credit.model.creditFlow.customer.common;

import com.zhiwei.core.model.BaseModel;


public class EnterpriseBank extends BaseModel implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	/**
	 * 用户id
	 */
	private Integer enterpriseid;
	/**
	 * 银行id
	 */
	private Long bankid;
	/**
	 * 银行名称
	 */
	private String bankname;
	/**
	 * 银行卡号
	 */
	private String accountnum;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 开户类型，0 个人1公司
	 */
	protected Short openType;
	/**
	 * 账户类型,0个人储蓄户,1基本户,2一般户
	 */
	protected Short accountType;
	/**
	 * 是否主贷款（0是1否）
	 */
	private Short iscredit;
	private String creditnum;
	private String creditpsw;
	private String remarks;
	/**
	 * 0本币1外币
	 */
	private Short openCurrency;
	/**
	 * 是否是企业(0企业，1是个人)
	 */
	private Short isEnterprise;
	/**
	 * 0表示普通客户
	 * 1表示投资客户
	 * 3表示债权系统的投资客户
	 * 4线上投资客户
	 */
	private Short isInvest;//是否是投资客户(0是,1否)
	private String areaId;
	/**
	 * 开户地区
	 */
	private String areaName;
	/**
	 * 网点名称
	 */
	private String  bankOutletsName;
	public EnterpriseBank() {
	}
	
	public EnterpriseBank(Integer enterpriseid, Long bankid,
			String bankname, String accountnum, Short accountType,
			Short openType, Short iscredit, String creditnum,
			String creditpsw, String remarks, Short openCurrency,Short isEnterprise,String name,
			String areaId,String areaName,String bankOutletsName) {
		this.enterpriseid = enterpriseid;
		this.bankid = bankid;
		this.bankname = bankname;
		this.accountnum = accountnum;
		this.accountType = accountType;
		this.openType = openType;
		this.iscredit = iscredit;
		this.creditnum = creditnum;
		this.creditpsw = creditpsw;
		this.remarks = remarks;
		this.openCurrency = openCurrency;
		this.isEnterprise = isEnterprise;
		this.name = name;
		this.areaId=areaId;
		this.areaName=areaName;
		this.bankOutletsName=bankOutletsName;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public String getBankOutletsName() {
		return bankOutletsName;
	}

	public void setBankOutletsName(String bankOutletsName) {
		this.bankOutletsName = bankOutletsName;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getEnterpriseid() {
		return this.enterpriseid;
	}

	public void setEnterpriseid(Integer enterpriseid) {
		this.enterpriseid = enterpriseid;
	}

	

	public String getBankname() {
		return this.bankname;
	}

	public void setBankname(String bankname) {
		this.bankname = bankname;
	}

	public String getAccountnum() {
		return this.accountnum;
	}

	public void setAccountnum(String accountnum) {
		this.accountnum = accountnum;
	}

	

	

	public String getCreditnum() {
		return this.creditnum;
	}

	public void setCreditnum(String creditnum) {
		this.creditnum = creditnum;
	}

	public String getCreditpsw() {
		return this.creditpsw;
	}

	public void setCreditpsw(String creditpsw) {
		this.creditpsw = creditpsw;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Long getBankid() {
		return bankid;
	}

	public void setBankid(Long bankid) {
		this.bankid = bankid;
	}

	public Short getOpenType() {
		return openType;
	}

	public void setOpenType(Short openType) {
		this.openType = openType;
	}

	public Short getAccountType() {
		return accountType;
	}

	public void setAccountType(Short accountType) {
		this.accountType = accountType;
	}

	public Short getOpenCurrency() {
		return openCurrency;
	}

	public void setOpenCurrency(Short openCurrency) {
		this.openCurrency = openCurrency;
	}

	public Short getIscredit() {
		return iscredit;
	}

	public void setIscredit(Short iscredit) {
		this.iscredit = iscredit;
	}

	public Short getIsEnterprise() {
		return isEnterprise;
	}

	public void setIsEnterprise(Short isEnterprise) {
		this.isEnterprise = isEnterprise;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}



	public Short getIsInvest() {
		return isInvest;
	}

	public void setIsInvest(Short isInvest) {
		this.isInvest = isInvest;
	}



	
}