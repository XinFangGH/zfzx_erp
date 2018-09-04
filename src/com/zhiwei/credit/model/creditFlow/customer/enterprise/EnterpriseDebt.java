package com.zhiwei.credit.model.creditFlow.customer.enterprise;

import com.zhiwei.core.model.BaseModel;

/**
 * CsEnterpriseDebt entity. @author MyEclipse Persistence Tools
 */

public class EnterpriseDebt extends BaseModel {

	// Fields

	private Integer id;
	private Integer enterpriseid;
	private Integer zwrpid;
	private String zwrname;
	private Double creditmoney;
	private java.util.Date creditstartdate;
	private java.util.Date creditenddate;
	private Double repayment;
	private Double borrowemoney;
	private java.util.Date lastpaydate;
	private Integer repaymentway;
	private String voucherway;
	private String assurecondition;
	private String remarks;
	private Double pretotalearn;
	private String debtexplain;
	
	//不与数据库映射
	private String voucherwayValue;
	private String repaymentwayValue;

	// Constructors

	/** default constructor */
	public EnterpriseDebt() {
	}

	/** full constructor */
	public EnterpriseDebt(Integer enterpriseid, Integer zwrpid,
			String zwrname, Double creditmoney, java.util.Date creditstartdate,
			java.util.Date creditenddate, Double repayment, Double borrowemoney,
			java.util.Date lastpaydate, Integer repaymentway, String voucherway,
			String assurecondition, String remarks, Double pretotalearn,
			String debtexplain) {
		this.enterpriseid = enterpriseid;
		this.zwrpid = zwrpid;
		this.zwrname = zwrname;
		this.creditmoney = creditmoney;
		this.creditstartdate = creditstartdate;
		this.creditenddate = creditenddate;
		this.repayment = repayment;
		this.borrowemoney = borrowemoney;
		this.lastpaydate = lastpaydate;
		this.repaymentway = repaymentway;
		this.voucherway = voucherway;
		this.assurecondition = assurecondition;
		this.remarks = remarks;
		this.pretotalearn = pretotalearn;
		this.debtexplain = debtexplain ;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public String getVoucherwayValue() {
		return voucherwayValue;
	}

	public void setVoucherwayValue(String voucherwayValue) {
		this.voucherwayValue = voucherwayValue;
	}

	public String getRepaymentwayValue() {
		return repaymentwayValue;
	}

	public void setRepaymentwayValue(String repaymentwayValue) {
		this.repaymentwayValue = repaymentwayValue;
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

	public Integer getZwrpid() {
		return this.zwrpid;
	}

	public void setZwrpid(Integer zwrpid) {
		this.zwrpid = zwrpid;
	}

	public String getZwrname() {
		return this.zwrname;
	}

	public void setZwrname(String zwrname) {
		this.zwrname = zwrname;
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

	public Double getBorrowemoney() {
		return this.borrowemoney;
	}

	public void setBorrowemoney(Double borrowemoney) {
		this.borrowemoney = borrowemoney;
	}

	public java.util.Date getLastpaydate() {
		return this.lastpaydate;
	}

	public void setLastpaydate(java.util.Date lastpaydate) {
		this.lastpaydate = lastpaydate;
	}


	public Integer getRepaymentway() {
		return repaymentway;
	}

	public void setRepaymentway(Integer repaymentway) {
		this.repaymentway = repaymentway;
	}

	public String getVoucherway() {
		return this.voucherway;
	}

	public void setVoucherway(String voucherway) {
		this.voucherway = voucherway;
	}

	public String getAssurecondition() {
		return this.assurecondition;
	}

	public void setAssurecondition(String assurecondition) {
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

	public String getDebtexplain() {
		return debtexplain;
	}

	public void setDebtexplain(String debtexplain) {
		this.debtexplain = debtexplain;
	}

}