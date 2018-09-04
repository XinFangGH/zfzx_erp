package com.credit.proj.entity;

/**
 * CsProcreditMortgageProduct entity. @author MyEclipse Persistence Tools
 */

public class ProcreditMortgageProduct implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer mortgageid;
	private String name;
	private String controller;
	private Integer controllertypeid;
	private String brand;
	private String type;
	private String depositary;
	private Integer amount;
	private Integer commongradeid;
	private Integer cashabilityid;
	private Double priceone;
	private Double pricetow;
	private Double modelrangeprice;
	private String objectType;//类型  典当物品:pawn,抵质押物:mortgage
	// Constructors

	public String getObjectType() {
		return objectType;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}

	/** default constructor */
	public ProcreditMortgageProduct() {
	}

	/** full constructor */
	public ProcreditMortgageProduct(Integer mortgageid, String name,
			String controller, Integer controllertypeid, String brand,
			String type, String depositary, Integer amount,
			Integer commongradeid, Integer cashabilityid, Double priceone,
			Double pricetow, Double modelrangeprice) {
		this.mortgageid = mortgageid;
		this.name = name;
		this.controller = controller;
		this.controllertypeid = controllertypeid;
		this.brand = brand;
		this.type = type;
		this.depositary = depositary;
		this.amount = amount;
		this.commongradeid = commongradeid;
		this.cashabilityid = cashabilityid;
		this.priceone = priceone;
		this.pricetow = pricetow;
		this.modelrangeprice = modelrangeprice;
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

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getController() {
		return this.controller;
	}

	public void setController(String controller) {
		this.controller = controller;
	}

	public Integer getControllertypeid() {
		return this.controllertypeid;
	}

	public void setControllertypeid(Integer controllertypeid) {
		this.controllertypeid = controllertypeid;
	}

	public String getBrand() {
		return this.brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDepositary() {
		return this.depositary;
	}

	public void setDepositary(String depositary) {
		this.depositary = depositary;
	}

	public Integer getAmount() {
		return this.amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Integer getCommongradeid() {
		return this.commongradeid;
	}

	public void setCommongradeid(Integer commongradeid) {
		this.commongradeid = commongradeid;
	}

	public Integer getCashabilityid() {
		return this.cashabilityid;
	}

	public void setCashabilityid(Integer cashabilityid) {
		this.cashabilityid = cashabilityid;
	}

	public Double getPriceone() {
		return this.priceone;
	}

	public void setPriceone(Double priceone) {
		this.priceone = priceone;
	}

	public Double getPricetow() {
		return this.pricetow;
	}

	public void setPricetow(Double pricetow) {
		this.pricetow = pricetow;
	}

	public Double getModelrangeprice() {
		return this.modelrangeprice;
	}

	public void setModelrangeprice(Double modelrangeprice) {
		this.modelrangeprice = modelrangeprice;
	}

}