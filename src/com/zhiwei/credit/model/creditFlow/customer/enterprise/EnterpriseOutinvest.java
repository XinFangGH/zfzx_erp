package com.zhiwei.credit.model.creditFlow.customer.enterprise;

import com.zhiwei.core.model.BaseModel;

/**
 * CsEnterpriseOutinvest entity. @author MyEclipse Persistence Tools
 */

public class EnterpriseOutinvest extends BaseModel {

	// Fields

	private Integer id;
	private Integer enterpriseid;
	private String investobject;
	private Double money;
	private java.util.Date startdate;
	private java.util.Date enddate;
	private Double expectincome;
	private Double factincome;
	private String remarks;
	private Integer investway;
	private Double pretotalearn;
	private Double investincome;

	private Double bpredictincome;          //上期预计投资收益.万元
	private Double bpracticeincome;         //上期实际投资收益.万元
	
	//不与数据库映射
	private String investwayValue;
	// Constructors

	/** default constructor */
	public EnterpriseOutinvest() {
	}

	/** full constructor */
	public EnterpriseOutinvest(Integer enterpriseid, String investobject,
			Double money, java.util.Date startdate, java.util.Date enddate,
			Double expectincome, Double factincome, String remarks,
			Integer investway, Double pretotalearn, Double investincome,
			Double bpredictincome,Double bpracticeincome) {
		this.enterpriseid = enterpriseid;
		this.investobject = investobject;
		this.money = money;
		this.startdate = startdate;
		this.enddate = enddate;
		this.expectincome = expectincome;
		this.factincome = factincome;
		this.remarks = remarks;
		this.investway = investway;
		this.pretotalearn = pretotalearn;
		this.investincome = investincome;
		this.bpredictincome = bpredictincome ;
		this.bpracticeincome = bpracticeincome ;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public String getInvestwayValue() {
		return investwayValue;
	}

	public void setInvestwayValue(String investwayValue) {
		this.investwayValue = investwayValue;
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

	public String getInvestobject() {
		return this.investobject;
	}

	public void setInvestobject(String investobject) {
		this.investobject = investobject;
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

	public Double getExpectincome() {
		return this.expectincome;
	}

	public void setExpectincome(Double expectincome) {
		this.expectincome = expectincome;
	}

	public Double getFactincome() {
		return this.factincome;
	}

	public void setFactincome(Double factincome) {
		this.factincome = factincome;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}


	public Integer getInvestway() {
		return investway;
	}

	public void setInvestway(Integer investway) {
		this.investway = investway;
	}

	public Double getPretotalearn() {
		return this.pretotalearn;
	}

	public void setPretotalearn(Double pretotalearn) {
		this.pretotalearn = pretotalearn;
	}

	public Double getInvestincome() {
		return this.investincome;
	}

	public void setInvestincome(Double investincome) {
		this.investincome = investincome;
	}

	public Double getBpredictincome() {
		return bpredictincome;
	}

	public void setBpredictincome(Double bpredictincome) {
		this.bpredictincome = bpredictincome;
	}

	public Double getBpracticeincome() {
		return bpracticeincome;
	}

	public void setBpracticeincome(Double bpracticeincome) {
		this.bpracticeincome = bpracticeincome;
	}

}