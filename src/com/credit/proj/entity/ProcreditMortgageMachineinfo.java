package com.credit.proj.entity;


/**
 * CsProcreditMortgageMachineinfo entity. @author MyEclipse Persistence Tools
 */

public class ProcreditMortgageMachineinfo implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer mortgageid;
	private String machinename;
	private String controller;
	private Integer controllertypeid;
	private String machinetype;
	private Integer commongradeid;
	private Integer capabilitystatusid;
	private Integer cashabilityid;
	private Double newcarprice;
	private java.util.Date leavefactorydate;
	private Double havedusedtime;
	private Integer registerinfoid;
	private Double secondaryvalueone;
	private Double secondaryvaluetow;
	private Double modelrangeprice;
	private String objectType;//类型  典当物品:pawn,抵质押物:mortgage
	// Constructors

	/** default constructor */
	public ProcreditMortgageMachineinfo() {
	}

	/** full constructor */
	public ProcreditMortgageMachineinfo(Integer mortgageid,
			String machinename, String controller, Integer controllertypeid,
			String machinetype, Integer commongradeid,
			Integer capabilitystatusid, Integer cashabilityid,
			Double newcarprice, java.util.Date leavefactorydate,
			Double havedusedtime, Integer registerinfoid,
			Double secondaryvalueone, Double secondaryvaluetow,
			Double modelrangeprice) {
		this.mortgageid = mortgageid;
		this.machinename = machinename;
		this.controller = controller;
		this.controllertypeid = controllertypeid;
		this.machinetype = machinetype;
		this.commongradeid = commongradeid;
		this.capabilitystatusid = capabilitystatusid;
		this.cashabilityid = cashabilityid;
		this.newcarprice = newcarprice;
		this.leavefactorydate = leavefactorydate;
		this.havedusedtime = havedusedtime;
		this.registerinfoid = registerinfoid;
		this.secondaryvalueone = secondaryvalueone;
		this.secondaryvaluetow = secondaryvaluetow;
		this.modelrangeprice = modelrangeprice;
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

	public String getMachinename() {
		return this.machinename;
	}

	public void setMachinename(String machinename) {
		this.machinename = machinename;
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

	public String getMachinetype() {
		return this.machinetype;
	}

	public void setMachinetype(String machinetype) {
		this.machinetype = machinetype;
	}

	public Integer getCommongradeid() {
		return this.commongradeid;
	}

	public void setCommongradeid(Integer commongradeid) {
		this.commongradeid = commongradeid;
	}

	public Integer getCapabilitystatusid() {
		return this.capabilitystatusid;
	}

	public void setCapabilitystatusid(Integer capabilitystatusid) {
		this.capabilitystatusid = capabilitystatusid;
	}

	public Integer getCashabilityid() {
		return this.cashabilityid;
	}

	public void setCashabilityid(Integer cashabilityid) {
		this.cashabilityid = cashabilityid;
	}

	public Double getNewcarprice() {
		return this.newcarprice;
	}

	public void setNewcarprice(Double newcarprice) {
		this.newcarprice = newcarprice;
	}


	public java.util.Date getLeavefactorydate() {
		return leavefactorydate;
	}

	public void setLeavefactorydate(java.util.Date leavefactorydate) {
		this.leavefactorydate = leavefactorydate;
	}

	public Double getHavedusedtime() {
		return this.havedusedtime;
	}

	public void setHavedusedtime(Double havedusedtime) {
		this.havedusedtime = havedusedtime;
	}

	public Integer getRegisterinfoid() {
		return this.registerinfoid;
	}

	public void setRegisterinfoid(Integer registerinfoid) {
		this.registerinfoid = registerinfoid;
	}

	public Double getSecondaryvalueone() {
		return this.secondaryvalueone;
	}

	public void setSecondaryvalueone(Double secondaryvalueone) {
		this.secondaryvalueone = secondaryvalueone;
	}

	public Double getSecondaryvaluetow() {
		return this.secondaryvaluetow;
	}

	public void setSecondaryvaluetow(Double secondaryvaluetow) {
		this.secondaryvaluetow = secondaryvaluetow;
	}

	public Double getModelrangeprice() {
		return this.modelrangeprice;
	}

	public void setModelrangeprice(Double modelrangeprice) {
		this.modelrangeprice = modelrangeprice;
	}

}