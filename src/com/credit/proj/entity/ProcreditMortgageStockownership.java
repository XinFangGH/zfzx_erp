package com.credit.proj.entity;

/**
 * CsProcreditMortgageStockownership entity. @author MyEclipse Persistence Tools
 */

public class ProcreditMortgageStockownership implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer mortgageid;
	private String stockownership;
	private Integer corporationname;//存放企业表id
	//private String licencenumber;
	//private Double enrolcapital;
	//private Double enroltiame;
	private Double managementtime;
	private Integer managementstatusid;
	//private Integer producttraitid;
	private Double stockownershippercent;
	private Double netassets;
	private Integer registerinfoid;
	private Double modelrangeprice;
	private String businessType;
	private String objectType;//类型  典当物品:pawn,抵质押物:mortgage

	// Constructors

	

	/** default constructor */
	public ProcreditMortgageStockownership() {
	}

	/** full constructor */
	public ProcreditMortgageStockownership(Integer mortgageid,
			String stockownership, Integer corporationname,
			Double managementtime, Integer managementstatusid,
			Double stockownershippercent,
			Double netassets, Integer registerinfoid, Double modelrangeprice,
			String businessType) {
		this.mortgageid = mortgageid;
		this.stockownership = stockownership;
		this.corporationname = corporationname;
		this.managementtime = managementtime;
		this.managementstatusid = managementstatusid;
		this.stockownershippercent = stockownershippercent;
		this.netassets = netassets;
		this.registerinfoid = registerinfoid;
		this.modelrangeprice = modelrangeprice;
		this.businessType = businessType;
	}
	public String getObjectType() {
		return objectType;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}
	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getMortgageid() {
		return this.mortgageid;
	}

	public void setMortgageid(Integer mortgageid) {
		this.mortgageid = mortgageid;
	}

	public String getStockownership() {
		return this.stockownership;
	}

	public void setStockownership(String stockownership) {
		this.stockownership = stockownership;
	}


	public Integer getCorporationname() {
		return corporationname;
	}

	public void setCorporationname(Integer corporationname) {
		this.corporationname = corporationname;
	}

	public Double getManagementtime() {
		return managementtime;
	}

	public void setManagementtime(Double managementtime) {
		this.managementtime = managementtime;
	}

	public Integer getManagementstatusid() {
		return this.managementstatusid;
	}

	public void setManagementstatusid(Integer managementstatusid) {
		this.managementstatusid = managementstatusid;
	}

	public Double getStockownershippercent() {
		return this.stockownershippercent;
	}

	public void setStockownershippercent(Double stockownershippercent) {
		this.stockownershippercent = stockownershippercent;
	}

	public Double getNetassets() {
		return this.netassets;
	}

	public void setNetassets(Double netassets) {
		this.netassets = netassets;
	}

	public Integer getRegisterinfoid() {
		return this.registerinfoid;
	}

	public void setRegisterinfoid(Integer registerinfoid) {
		this.registerinfoid = registerinfoid;
	}

	public Double getModelrangeprice() {
		return this.modelrangeprice;
	}

	public void setModelrangeprice(Double modelrangeprice) {
		this.modelrangeprice = modelrangeprice;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

}