package com.credit.proj.entity;


/**
 * CsProcreditMortgageIndustry entity. @author MyEclipse Persistence Tools
 */

public class ProcreditMortgageIndustry implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer mortgageid;
	private String address;
	private String certificatenumber;
	private String propertyperson;
	private Integer groundcharacterid;
	private Double occupyacreage;
	private Integer descriptionid;
	private java.util.Date buytime;
	private Double residualyears;
	private Integer registerinfoid;
	private Double mortgagesbalance;
	private Double rentpriceformonthone;
	private Double rentpriceformonthtow;
	private Double exchangepriceone;
	private Double exchangepricetow;
	private Double exchangeprice;
	private Double tenancyrangeprice;
	private Double venditionrangeprice;
	private String objectType;//类型  典当物品:pawn,抵质押物:mortgage
	// Constructors

	/** default constructor */
	public ProcreditMortgageIndustry() {
	}

	/** full constructor */
	public ProcreditMortgageIndustry(Integer mortgageid, String address,
			String certificatenumber, String propertyperson,
			Integer groundcharacterid, Double occupyacreage,
			Integer descriptionid, java.util.Date buytime, Double residualyears,
			Integer registerinfoid, Double mortgagesbalance,
			Double rentpriceformonthone, Double rentpriceformonthtow,
			Double exchangepriceone, Double exchangepricetow,
			Double exchangeprice, Double tenancyrangeprice,
			Double venditionrangeprice) {
		this.mortgageid = mortgageid;
		this.address = address;
		this.certificatenumber = certificatenumber;
		this.propertyperson = propertyperson;
		this.groundcharacterid = groundcharacterid;
		this.occupyacreage = occupyacreage;
		this.descriptionid = descriptionid;
		this.buytime = buytime;
		this.residualyears = residualyears;
		this.registerinfoid = registerinfoid;
		this.mortgagesbalance = mortgagesbalance;
		this.rentpriceformonthone = rentpriceformonthone;
		this.rentpriceformonthtow = rentpriceformonthtow;
		this.exchangepriceone = exchangepriceone;
		this.exchangepricetow = exchangepricetow;
		this.exchangeprice = exchangeprice;
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

	public Integer getGroundcharacterid() {
		return this.groundcharacterid;
	}

	public void setGroundcharacterid(Integer groundcharacterid) {
		this.groundcharacterid = groundcharacterid;
	}

	public Double getOccupyacreage() {
		return this.occupyacreage;
	}

	public void setOccupyacreage(Double occupyacreage) {
		this.occupyacreage = occupyacreage;
	}

	public Integer getDescriptionid() {
		return this.descriptionid;
	}

	public void setDescriptionid(Integer descriptionid) {
		this.descriptionid = descriptionid;
	}

	

	public java.util.Date getBuytime() {
		return buytime;
	}

	public void setBuytime(java.util.Date buytime) {
		this.buytime = buytime;
	}

	public Double getResidualyears() {
		return this.residualyears;
	}

	public void setResidualyears(Double residualyears) {
		this.residualyears = residualyears;
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

	public Double getRentpriceformonthone() {
		return this.rentpriceformonthone;
	}

	public void setRentpriceformonthone(Double rentpriceformonthone) {
		this.rentpriceformonthone = rentpriceformonthone;
	}

	public Double getRentpriceformonthtow() {
		return this.rentpriceformonthtow;
	}

	public void setRentpriceformonthtow(Double rentpriceformonthtow) {
		this.rentpriceformonthtow = rentpriceformonthtow;
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

	public Double getExchangeprice() {
		return this.exchangeprice;
	}

	public void setExchangeprice(Double exchangeprice) {
		this.exchangeprice = exchangeprice;
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