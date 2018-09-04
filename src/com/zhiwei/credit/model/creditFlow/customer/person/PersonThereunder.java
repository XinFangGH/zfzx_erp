package com.zhiwei.credit.model.creditFlow.customer.person;

import com.zhiwei.core.model.BaseModel;

/**
 * CsPersonThereunder entity. @author MyEclipse Persistence Tools
 */

public class PersonThereunder extends BaseModel {

	// Fields

	private Integer id;
	private Integer personid;
	private Integer companyname;
	private Integer lnpid;
	private String licensenum;
	private Integer relate;
	private java.util.Date registerdate;
	private Double registercapital;
	private String address;
	private String lnpname;
	private String phone;
	private String remarks;
	
	//不与数据库映射
	private String shortname;
	private String relateValue;
	private String name;
	// Constructors

	/** default constructor */
	public PersonThereunder() {
	}

	/** full constructor */
	public PersonThereunder(Integer personid, Integer companyname,
			String licensenum, Integer relate, java.util.Date registerdate,
			Double registercapital, String address, String lnpname,
			Integer lnpid, String phone, String remarks) {
		this.personid = personid;
		this.companyname = companyname;
		this.licensenum = licensenum;
		this.relate = relate;
		this.registerdate = registerdate;
		this.registercapital = registercapital;
		this.address = address;
		this.lnpname = lnpname;
		this.lnpid = lnpid;
		this.phone = phone;
		this.remarks = remarks;
	}

	// Property accessors

	
	public Integer getId() {
		return this.id;
	}

	public String getShortname() {
		return shortname;
	}

	public void setShortname(String shortname) {
		this.shortname = shortname;
	}

	public String getRelateValue() {
		return relateValue;
	}

	public void setRelateValue(String relateValue) {
		this.relateValue = relateValue;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPersonid() {
		return this.personid;
	}

	public void setPersonid(Integer personid) {
		this.personid = personid;
	}
	public Integer getCompanyname() {
		return companyname;
	}

	public void setCompanyname(Integer companyname) {
		this.companyname = companyname;
	}

	public String getLicensenum() {
		return this.licensenum;
	}

	public void setLicensenum(String licensenum) {
		this.licensenum = licensenum;
	}

	public Integer getRelate() {
		return this.relate;
	}

	public void setRelate(Integer relate) {
		this.relate = relate;
	}

	public java.util.Date getRegisterdate() {
		return this.registerdate;
	}

	public void setRegisterdate(java.util.Date registerdate) {
		this.registerdate = registerdate;
	}

	public Double getRegistercapital() {
		return this.registercapital;
	}

	public void setRegistercapital(Double registercapital) {
		this.registercapital = registercapital;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLnpname() {
		return this.lnpname;
	}

	public void setLnpname(String lnpname) {
		this.lnpname = lnpname;
	}

	public Integer getLnpid() {
		return this.lnpid;
	}

	public void setLnpid(Integer lnpid) {
		this.lnpid = lnpid;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

}