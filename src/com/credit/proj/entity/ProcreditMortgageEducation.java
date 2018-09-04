package com.credit.proj.entity;


/**
 * CsProcreditMortgageEducation entity. @author MyEclipse Persistence Tools
 */

public class ProcreditMortgageEducation implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer mortgageid;
	private String address;
	private String certificatenumber;
	private String propertyperson;
	private Integer groundcharacterid;
	private Double acreage;
	private Integer descriptionid;
	private java.util.Date buytime;
	private Double residualyears;
	private Integer registerinfoid;
	private Double builacreage;
	private Double groundrentpriceformonth;
	private Double buildrentpriceformonth;
	private Double groundtenancyrangeprice;
	private Double buildtenancyrangeprice;
	private String objectType;//类型  典当物品:pawn,抵质押物:mortgage
	// Constructors

	/** default constructor */
	public ProcreditMortgageEducation() {
	}

	/** full constructor */
	public ProcreditMortgageEducation(Integer mortgageid, String address,
			String certificatenumber, String propertyperson,
			Integer groundcharacterid, Double acreage, Integer descriptionid,
			java.util.Date buytime, Double residualyears, Integer registerinfoid,
			Double builacreage, Double groundrentpriceformonth,
			Double buildrentpriceformonth, Double groundtenancyrangeprice,
			Double buildtenancyrangeprice) {
		this.mortgageid = mortgageid;
		this.address = address;
		this.certificatenumber = certificatenumber;
		this.propertyperson = propertyperson;
		this.groundcharacterid = groundcharacterid;
		this.acreage = acreage;
		this.descriptionid = descriptionid;
		this.buytime = buytime;
		this.residualyears = residualyears;
		this.registerinfoid = registerinfoid;
		this.builacreage = builacreage;
		this.groundrentpriceformonth = groundrentpriceformonth;
		this.buildrentpriceformonth = buildrentpriceformonth;
		this.groundtenancyrangeprice = groundtenancyrangeprice;
		this.buildtenancyrangeprice = buildtenancyrangeprice;
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

	public Double getBuilacreage() {
		return this.builacreage;
	}

	public void setBuilacreage(Double builacreage) {
		this.builacreage = builacreage;
	}

	public Double getGroundrentpriceformonth() {
		return this.groundrentpriceformonth;
	}

	public void setGroundrentpriceformonth(Double groundrentpriceformonth) {
		this.groundrentpriceformonth = groundrentpriceformonth;
	}

	public Double getBuildrentpriceformonth() {
		return this.buildrentpriceformonth;
	}

	public void setBuildrentpriceformonth(Double buildrentpriceformonth) {
		this.buildrentpriceformonth = buildrentpriceformonth;
	}

	public Double getGroundtenancyrangeprice() {
		return this.groundtenancyrangeprice;
	}

	public void setGroundtenancyrangeprice(Double groundtenancyrangeprice) {
		this.groundtenancyrangeprice = groundtenancyrangeprice;
	}

	public Double getBuildtenancyrangeprice() {
		return this.buildtenancyrangeprice;
	}

	public void setBuildtenancyrangeprice(Double buildtenancyrangeprice) {
		this.buildtenancyrangeprice = buildtenancyrangeprice;
	}

}