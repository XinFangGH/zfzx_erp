package com.credit.proj.entity;

/**
 * CsProcreditMortgageDroit entity. @author MyEclipse Persistence Tools
 */

public class ProcreditMortgageDroit implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */

	private Integer id;
	private Integer mortgageid;
	private String droitname;
	private Double droitpercent;
	private Double dealdroittime;
	private Double residualdroittime;
	private Double droitlucre;
	private Integer negotiabilityid;
	private Integer dealstatusid;
	private Integer droitmassid;
	private Integer registerinfoid;
	private Double modelrangeprice;
	private String objectType;//类型  典当物品:pawn,抵质押物:mortgage
	// Constructors

	/** default constructor */
	public ProcreditMortgageDroit() {
	}

	/** full constructor */
	public ProcreditMortgageDroit(Integer mortgageid, String droitname,
			Double droitpercent, Double dealdroittime,
			Double residualdroittime, Double droitlucre,
			Integer negotiabilityid, Integer dealstatusid, Integer droitmassid,
			Integer registerinfoid, Double modelrangeprice) {
		this.mortgageid = mortgageid;
		this.droitname = droitname;
		this.droitpercent = droitpercent;
		this.dealdroittime = dealdroittime;
		this.residualdroittime = residualdroittime;
		this.droitlucre = droitlucre;
		this.negotiabilityid = negotiabilityid;
		this.dealstatusid = dealstatusid;
		this.droitmassid = droitmassid;
		this.registerinfoid = registerinfoid;
		this.modelrangeprice = modelrangeprice;
	}

	// Property accessors

	public String getObjectType() {
		return objectType;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
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

	public String getDroitname() {
		return this.droitname;
	}

	public void setDroitname(String droitname) {
		this.droitname = droitname;
	}

	public Double getDroitpercent() {
		return this.droitpercent;
	}

	public void setDroitpercent(Double droitpercent) {
		this.droitpercent = droitpercent;
	}

	public Double getDealdroittime() {
		return this.dealdroittime;
	}

	public void setDealdroittime(Double dealdroittime) {
		this.dealdroittime = dealdroittime;
	}

	public Double getResidualdroittime() {
		return this.residualdroittime;
	}

	public void setResidualdroittime(Double residualdroittime) {
		this.residualdroittime = residualdroittime;
	}

	public Double getDroitlucre() {
		return this.droitlucre;
	}

	public void setDroitlucre(Double droitlucre) {
		this.droitlucre = droitlucre;
	}

	public Integer getNegotiabilityid() {
		return this.negotiabilityid;
	}

	public void setNegotiabilityid(Integer negotiabilityid) {
		this.negotiabilityid = negotiabilityid;
	}

	public Integer getDealstatusid() {
		return this.dealstatusid;
	}

	public void setDealstatusid(Integer dealstatusid) {
		this.dealstatusid = dealstatusid;
	}

	public Integer getDroitmassid() {
		return this.droitmassid;
	}

	public void setDroitmassid(Integer droitmassid) {
		this.droitmassid = droitmassid;
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

}