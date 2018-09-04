package com.credit.proj.entity;

/**
 * CsProcreditMortgagePerson entity. @author MyEclipse Persistence Tools
 */

public class ProcreditMortgagePerson implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer mortgageid;
	//private String name;
	//private String idcard;
	//private String phone;
	private Integer societynexusid;
	private Double assetvalue;
	private Double modelrangeprice;
    private Boolean isCivilServant;//是否为公务员
    private String business;
    private String assets;//主要资产
    private String monthlyIncome;//月收入
	// Constructors

	/** default constructor */
	public ProcreditMortgagePerson() {
	}

	/** full constructor */
	public ProcreditMortgagePerson(Integer mortgageid,Integer societynexusid,
			Double assetvalue, Double modelrangeprice,Boolean isCivilServant,
			String business,String assets,String monthlyIncome) {
		this.mortgageid = mortgageid;
		this.societynexusid = societynexusid;
		this.assetvalue = assetvalue;
		this.modelrangeprice = modelrangeprice;
		this.isCivilServant=isCivilServant;
		this.business=business;
		this.assetvalue=assetvalue;
		this.assets=assets;
		this.monthlyIncome=monthlyIncome;
		
	}

	// Property accessors

	public Boolean getIsCivilServant() {
		return isCivilServant;
	}

	public void setIsCivilServant(Boolean isCivilServant) {
		this.isCivilServant = isCivilServant;
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

	public Double getAssetvalue() {
		return this.assetvalue;
	}

	public void setAssetvalue(Double assetvalue) {
		this.assetvalue = assetvalue;
	}

	public Double getModelrangeprice() {
		return this.modelrangeprice;
	}

	public void setModelrangeprice(Double modelrangeprice) {
		this.modelrangeprice = modelrangeprice;
	}

}