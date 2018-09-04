package com.credit.proj.entity;

/**
 * CsProcreditMortgageHouseground entity. @author MyEclipse Persistence Tools
 */

public class ProcreditMortgageHouseground implements java.io.Serializable {

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
	private Double exchangeprice;
	private Double exchangepriceone;
	private Double exchangepricetow;
	private Double modelrangepriceone;
	private Double modelrangepricetow;
	private String objectType;//类型  典当物品:pawn,抵质押物:mortgage
	// Constructors

	/** default constructor */
	public ProcreditMortgageHouseground() {
	}

	/** full constructor */
	public ProcreditMortgageHouseground(Integer mortgageid, String address,
			String certificatenumber, String propertyperson, Double acreage,
			Integer descriptionid, Double anticipateacreage,
			Integer registerinfoid, Double mortgagesbalance,
			Double exchangeprice, Double exchangepriceone,
			Double exchangepricetow, Double modelrangepriceone,
			Double modelrangepricetow) {
		this.mortgageid = mortgageid;
		this.address = address;
		this.certificatenumber = certificatenumber;
		this.propertyperson = propertyperson;
		this.acreage = acreage;
		this.descriptionid = descriptionid;
		this.anticipateacreage = anticipateacreage;
		this.registerinfoid = registerinfoid;
		this.mortgagesbalance = mortgagesbalance;
		this.exchangeprice = exchangeprice;
		this.exchangepriceone = exchangepriceone;
		this.exchangepricetow = exchangepricetow;
		this.modelrangepriceone = modelrangepriceone;
		this.modelrangepricetow = modelrangepricetow;
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
		return acreage;
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

	public Double getExchangeprice() {
		return this.exchangeprice;
	}

	public void setExchangeprice(Double exchangeprice) {
		this.exchangeprice = exchangeprice;
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

	public Double getModelrangepriceone() {
		return this.modelrangepriceone;
	}

	public void setModelrangepriceone(Double modelrangepriceone) {
		this.modelrangepriceone = modelrangepriceone;
	}

	public Double getModelrangepricetow() {
		return this.modelrangepricetow;
	}

	public void setModelrangepricetow(Double modelrangepricetow) {
		this.modelrangepricetow = modelrangepricetow;
	}

}