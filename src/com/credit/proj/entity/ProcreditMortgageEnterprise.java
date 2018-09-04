package com.credit.proj.entity;

/**
 * CsProcreditMortgageEnterprise entity. @author MyEclipse Persistence Tools
 */

public class ProcreditMortgageEnterprise implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer mortgageid;
	/*private String enterprisename;
	private String licensenumber;
	private String registeraddress;
	private String corporate;
	private String corporatetel;*/
	private Integer societynexusid;
	private Double netassets;
	private Double modelrangeprice;
	private String business;
	private String assets;//主要资产
    private String monthlyIncome;//月营业额
	// Constructors

	/** default constructor */
	public ProcreditMortgageEnterprise() {
	}

	/** full constructor */
	public ProcreditMortgageEnterprise(Integer mortgageid,
			Integer societynexusid, Double netassets, Double modelrangeprice,String business,String assets,String monthlyIncome) {
		this.mortgageid = mortgageid;
		this.societynexusid = societynexusid;
		this.netassets = netassets;
		this.modelrangeprice = modelrangeprice;
		this.business=business;
		this.assets=assets;
		this.monthlyIncome=monthlyIncome;
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

	public Integer getSocietynexusid() {
		return this.societynexusid;
	}

	public void setSocietynexusid(Integer societynexusid) {
		this.societynexusid = societynexusid;
	}

	public Double getNetassets() {
		return this.netassets;
	}

	public void setNetassets(Double netassets) {
		this.netassets = netassets;
	}

	public Double getModelrangeprice() {
		return this.modelrangeprice;
	}

	public void setModelrangeprice(Double modelrangeprice) {
		this.modelrangeprice = modelrangeprice;
	}

	public String getBusiness() {
		return business;
	}

	public void setBusiness(String business) {
		this.business = business;
	}

	public String getAssets() {
		return assets;
	}

	public void setAssets(String assets) {
		this.assets = assets;
	}

	public String getMonthlyIncome() {
		return monthlyIncome;
	}

	public void setMonthlyIncome(String monthlyIncome) {
		this.monthlyIncome = monthlyIncome;
	}

}