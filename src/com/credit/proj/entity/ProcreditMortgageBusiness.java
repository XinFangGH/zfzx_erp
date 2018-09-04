package com.credit.proj.entity;

/**
 * CsProcreditMortgageBusiness entity. @author MyEclipse Persistence Tools
 */

public class ProcreditMortgageBusiness implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer mortgageid;
	private String address;
	private String certificatenumber;
	private String propertyperson;
	private Double acreage;
	private Integer descriptionid;
	private Double anticipateacreage;
	private Integer registerinfoid;
	private Double mortgagesbalance;
	private Double groundexchangeprice;
	private Double lendpriceformonthone;
	private Double lendpriceformonthtow;
	private Double exchangepriceone;
	private Double exchangepricetow;
	private Double modelrangeprice;
	private Double tenancyrangeprice;
	private Double venditionrangeprice;
	private String objectType;//类型  典当物品:pawn,抵质押物:mortgage
	// Constructors

	/** default constructor */
	public ProcreditMortgageBusiness() {
	}

	/** full constructor */
	public ProcreditMortgageBusiness(Integer mortgageid, String address,
			String certificatenumber, String propertyperson, Double acreage,
			Integer descriptionid, Double anticipateacreage,
			Integer registerinfoid, Double mortgagesbalance,
			Double groundexchangeprice, Double lendpriceformonthone,
			Double lendpriceformonthtow, Double exchangepriceone,
			Double exchangepricetow, Double modelrangeprice,
			Double tenancyrangeprice, Double venditionrangeprice) {
		this.mortgageid = mortgageid;
		this.address = address;
		this.certificatenumber = certificatenumber;
		this.propertyperson = propertyperson;
		this.acreage = acreage;
		this.descriptionid = descriptionid;
		this.anticipateacreage = anticipateacreage;
		this.registerinfoid = registerinfoid;
		this.mortgagesbalance = mortgagesbalance;
		this.groundexchangeprice = groundexchangeprice;
		this.lendpriceformonthone = lendpriceformonthone;
		this.lendpriceformonthtow = lendpriceformonthtow;
		this.exchangepriceone = exchangepriceone;
		this.exchangepricetow = exchangepricetow;
		this.modelrangeprice = modelrangeprice;
		this.tenancyrangeprice = tenancyrangeprice;
		this.venditionrangeprice = venditionrangeprice;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public String getObjectType() {
		return objectType;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
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

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCertificatenumber() {
		return this.certificatenumber;
	}

	public void setCertificatenumber(String certificatenumber) {
		this.certificatenumber = certificatenumber;
	}

	public String getPropertyperson() {
		return this.propertyperson;
	}

	public void setPropertyperson(String propertyperson) {
		this.propertyperson = propertyperson;
	}

	public Double getAcreage() {
		return this.acreage;
	}

	public void setAcreage(Double acreage) {
		this.acreage = acreage;
	}

	public Integer getDescriptionid() {
		return this.descriptionid;
	}

	public void setDescriptionid(Integer descriptionid) {
		this.descriptionid = descriptionid;
	}

	public Double getAnticipateacreage() {
		return this.anticipateacreage;
	}

	public void setAnticipateacreage(Double anticipateacreage) {
		this.anticipateacreage = anticipateacreage;
	}

	public Integer getRegisterinfoid() {
		return this.registerinfoid;
	}

	public void setRegisterinfoid(Integer registerinfoid) {
		this.registerinfoid = registerinfoid;
	}

	public Double getMortgagesbalance() {
		return this.mortgagesbalance;
	}

	public void setMortgagesbalance(Double mortgagesbalance) {
		this.mortgagesbalance = mortgagesbalance;
	}

	public Double getGroundexchangeprice() {
		return this.groundexchangeprice;
	}

	public void setGroundexchangeprice(Double groundexchangeprice) {
		this.groundexchangeprice = groundexchangeprice;
	}

	public Double getLendpriceformonthone() {
		return this.lendpriceformonthone;
	}

	public void setLendpriceformonthone(Double lendpriceformonthone) {
		this.lendpriceformonthone = lendpriceformonthone;
	}

	public Double getLendpriceformonthtow() {
		return this.lendpriceformonthtow;
	}

	public void setLendpriceformonthtow(Double lendpriceformonthtow) {
		this.lendpriceformonthtow = lendpriceformonthtow;
	}

	public Double getExchangepriceone() {
		return this.exchangepriceone;
	}

	public void setExchangepriceone(Double exchangepriceone) {
		this.exchangepriceone = exchangepriceone;
	}

	public Double getExchangepricetow() {
		return this.exchangepricetow;
	}

	public void setExchangepricetow(Double exchangepricetow) {
		this.exchangepricetow = exchangepricetow;
	}

	public Double getModelrangeprice() {
		return this.modelrangeprice;
	}

	public void setModelrangeprice(Double modelrangeprice) {
		this.modelrangeprice = modelrangeprice;
	}

	public Double getTenancyrangeprice() {
		return this.tenancyrangeprice;
	}

	public void setTenancyrangeprice(Double tenancyrangeprice) {
		this.tenancyrangeprice = tenancyrangeprice;
	}

	public Double getVenditionrangeprice() {
		return this.venditionrangeprice;
	}

	public void setVenditionrangeprice(Double venditionrangeprice) {
		this.venditionrangeprice = venditionrangeprice;
	}

}