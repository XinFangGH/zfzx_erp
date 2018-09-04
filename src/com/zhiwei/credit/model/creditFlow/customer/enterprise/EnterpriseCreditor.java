package com.zhiwei.credit.model.creditFlow.customer.enterprise;

import com.zhiwei.core.model.BaseModel;

/**
 * CsEnterpriseCreditor entity. @author MyEclipse Persistence Tools
 */

public class EnterpriseCreditor extends BaseModel {

	// Fields

	private Integer id;
	private Integer enterpriseid;
	private Integer zqrpid;
	private String zqrpname;
	private Double creditmoney;
	private java.util.Date creditstartdate;
	private java.util.Date creditenddate;
	private Double repayment;
	private Double bowmoney;
	private java.util.Date lastdate;
	private String repayway;
	private String voucherway;
	private Integer assurecondition;
	private String remarks;
	private Double pretotalearn;
	private String creditexplain ;//债权说明

	//不与数据库映射
	private String repaywayValue;
	private String voucherwayValue;
	// Constructors

	/** default constructor */
	public EnterpriseCreditor() {
	}

	/** full constructor */
	public EnterpriseCreditor(Integer enterpriseid, Integer zqrpid,
			String zqrpname, Double creditmoney, java.util.Date creditstartdate,
			java.util.Date creditenddate, Double repayment, Double bowmoney,
			java.util.Date lastdate, String repayway, String voucherway,
			Integer assurecondition, String remarks, Double pretotalearn,
			String creditexplain) {
		this.enterpriseid = enterpriseid;
		this.zqrpid = zqrpid;
		this.zqrpname = zqrpname;
		this.creditmoney = creditmoney;
		this.creditstartdate = creditstartdate;
		this.creditenddate = creditenddate;
		this.repayment = repayment;
		this.bowmoney = bowmoney;
		this.lastdate = lastdate;
		this.repayway = repayway;
		this.voucherway = voucherway;
		this.assurecondition = assurecondition;
		this.remarks = remarks;
		this.pretotalearn = pretotalearn;
		this.creditexplain =creditexplain ;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public String getRepaywayValue() {
		return repaywayValue;
	}

	public void setRepaywayValue(String repaywayValue) {
		this.repaywayValue = repaywayValue;
	}

	public String getVoucherwayValue() {
		return voucherwayValue;
	}

	public void setVoucherwayValue(String voucherwayValue) {
		this.voucherwayValue = voucherwayValue;
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

	public Integer getZqrpid() {
		return this.zqrpid;
	}

	public void setZqrpid(Integer zqrpid) {
		this.zqrpid = zqrpid;
	}

	public String getZqrpname() {
		return this.zqrpname;
	}

	public void setZqrpname(String zqrpname) {
		this.zqrpname = zqrpname;
	}

	public Double getCreditmoney() {
		return this.creditmoney;
	}

	public void setCreditmoney(Double creditmoney) {
		this.creditmoney = creditmoney;
	}

	public java.util.Date getCreditstartdate() {
		return this.creditstartdate;
	}

	public void setCreditstartdate(java.util.Date creditstartdate) {
		this.creditstartdate = creditstartdate;
	}

	public java.util.Date getCreditenddate() {
		return this.creditenddate;
	}

	public void setCreditenddate(java.util.Date creditenddate) {
		this.creditenddate = creditenddate;
	}

	public Double getRepayment() {
		return this.repayment;
	}

	public void setRepayment(Double repayment) {
		this.repayment = repayment;
	}

	public Double getBowmoney() {
		return this.bowmoney;
	}

	public void setBowmoney(Double bowmoney) {
		this.bowmoney = bowmoney;
	}

	public java.util.Date getLastdate() {
		return this.lastdate;
	}

	public void setLastdate(java.util.Date lastdate) {
		this.lastdate = lastdate;
	}

	public String getRepayway() {
		return this.repayway;
	}

	public void setRepayway(String repayway) {
		this.repayway = repayway;
	}

	public String getVoucherway() {
		return this.voucherway;
	}

	public void setVoucherway(String voucherway) {
		this.voucherway = voucherway;
	}


	public Integer getAssurecondition() {
		return assurecondition;
	}

	public void setAssurecondition(Integer assurecondition) {
		this.assurecondition = assurecondition;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Double getPretotalearn() {
		return this.pretotalearn;
	}

	public void setPretotalearn(Double pretotalearn) {
		this.pretotalearn = pretotalearn;
	}

	public String getCreditexplain() {
		return creditexplain;
	}

	public void setCreditexplain(String creditexplain) {
		this.creditexplain = creditexplain;
	}

}