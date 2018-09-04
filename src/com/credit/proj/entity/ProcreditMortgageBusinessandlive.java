package com.credit.proj.entity;

/**
 * CsProcreditMortgageBusinessandlive entity. @author MyEclipse Persistence
 * Tools
 */

public class ProcreditMortgageBusinessandlive implements java.io.Serializable {

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
	private Double exchangepriceground;
	private Double rentpriceformonth;
	private Double exchangepricebusiness;
	private Double exchangepricehouse;
	private Double modelrangeprice;
	private Double tenancyrangeprice;
	private Double venditionrangeprice;
	private String objectType;//类型  典当物品:pawn,抵质押物:mortgage
	// Constructors

	/** default constructor */
	public ProcreditMortgageBusinessandlive() {
	}

	/** full constructor */
	public ProcreditMortgageBusinessandlive(Integer mortgageid,
			String address, String certificatenumber, String propertyperson,
			Double acreage, Integer descriptionid, Double anticipateacreage,
			Integer registerinfoid, Double mortgagesbalance,
			Double exchangepriceground, Double rentpriceformonth,
			Double exchangepricebusiness, Double exchangepricehouse,
			Double modelrangeprice, Double tenancyrangeprice,
			Double venditionrangeprice) {
		this.mortgageid = mortgageid;
		this.address = address;
		this.certificatenumber = certificatenumber;
		this.propertyperson = propertyperson;
		this.acreage = acreage;
		this.descriptionid = descriptionid;
		this.anticipateacreage = anticipateacreage;
		this.registerinfoid = registerinfoid;
		this.mortgagesbalance = mortgagesbalance;
		this.exchangepriceground = exchangepriceground;
		this.rentpriceformonth = rentpriceformonth;
		this.exchangepricebusiness = exchangepricebusiness;
		this.exchangepricehouse = exchangepricehouse;
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

	public Double getExchangepriceground() {
		return this.exchangepriceground;
	}

	public void setExchangepriceground(Double exchangepriceground) {
		this.exchangepriceground = exchangepriceground;
	}

	public Double getRentpriceformonth() {
		return this.rentpriceformonth;
	}

	public void setRentpriceformonth(Double rentpriceformonth) {
		this.rentpriceformonth = rentpriceformonth;
	}

	public Double getExchangepricebusiness() {
		return this.exchangepricebusiness;
	}

	public void setExchangepricebusiness(Double exchangepricebusiness) {
		this.exchangepricebusiness = exchangepricebusiness;
	}

	public Double getExchangepricehouse() {
		return this.exchangepricehouse;
	}

	public void setExchangepricehouse(Double exchangepricehouse) {
		this.exchangepricehouse = exchangepricehouse;
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