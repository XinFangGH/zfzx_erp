package com.zhiwei.credit.model.creditFlow.customer.enterprise;

import com.zhiwei.core.model.BaseModel;

/**
 * CsEnterpriseOutassure entity. @author MyEclipse Persistence Tools
 */

public class EnterpriseOutassure extends BaseModel {

	// Fields

	private Integer id;
	private Integer enterpriseid;
	private String assureobject;
	private Double money;
	private java.util.Date startdate;
	private java.util.Date enddate;
	private Integer assureterm;
	private String objectstatus;
	private Double dutyrate;
	private Double dutymoney;
	private String remarks;
	private String assureway;
	private String assurecontent;
	private String assurecondition;
	private Double pretotalearn;

	//不与数据库映射
	private String assurewayValue;
	// Constructors

	/** default constructor */
	public EnterpriseOutassure() {
	}

	/** full constructor */
	public EnterpriseOutassure(Integer enterpriseid, String assureobject,
			Double money, java.util.Date startdate, java.util.Date enddate,
			Integer assureterm, String objectstatus, Double dutyrate,
			Double dutymoney, String remarks, String assureway,
			String assurecontent, String assurecondition, Double pretotalearn) {
		this.enterpriseid = enterpriseid;
		this.assureobject = assureobject;
		this.money = money;
		this.startdate = startdate;
		this.enddate = enddate;
		this.assureterm = assureterm;
		this.objectstatus = objectstatus;
		this.dutyrate = dutyrate;
		this.dutymoney = dutymoney;
		this.remarks = remarks;
		this.assureway = assureway;
		this.assurecontent = assurecontent;
		this.assurecondition = assurecondition;
		this.pretotalearn = pretotalearn;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public String getAssurewayValue() {
		return assurewayValue;
	}

	public void setAssurewayValue(String assurewayValue) {
		this.assurewayValue = assurewayValue;
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

	public String getAssureobject() {
		return this.assureobject;
	}

	public void setAssureobject(String assureobject) {
		this.assureobject = assureobject;
	}

	public Double getMoney() {
		return this.money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public java.util.Date getStartdate() {
		return this.startdate;
	}

	public void setStartdate(java.util.Date startdate) {
		this.startdate = startdate;
	}

	public java.util.Date getEnddate() {
		return this.enddate;
	}

	public void setEnddate(java.util.Date enddate) {
		this.enddate = enddate;
	}

	public Integer getAssureterm() {
		return this.assureterm;
	}

	public void setAssureterm(Integer assureterm) {
		this.assureterm = assureterm;
	}

	public String getObjectstatus() {
		return this.objectstatus;
	}

	public void setObjectstatus(String objectstatus) {
		this.objectstatus = objectstatus;
	}

	public Double getDutyrate() {
		return this.dutyrate;
	}

	public void setDutyrate(Double dutyrate) {
		this.dutyrate = dutyrate;
	}

	public Double getDutymoney() {
		return this.dutymoney;
	}

	public void setDutymoney(Double dutymoney) {
		this.dutymoney = dutymoney;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getAssureway() {
		return this.assureway;
	}

	public void setAssureway(String assureway) {
		this.assureway = assureway;
	}

	public String getAssurecontent() {
		return this.assurecontent;
	}

	public void setAssurecontent(String assurecontent) {
		this.assurecontent = assurecontent;
	}

	public String getAssurecondition() {
		return this.assurecondition;
	}

	public void setAssurecondition(String assurecondition) {
		this.assurecondition = assurecondition;
	}

	public Double getPretotalearn() {
		return this.pretotalearn;
	}

	public void setPretotalearn(Double pretotalearn) {
		this.pretotalearn = pretotalearn;
	}

}