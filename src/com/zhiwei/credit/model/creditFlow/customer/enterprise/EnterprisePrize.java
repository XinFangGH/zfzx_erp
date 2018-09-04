package com.zhiwei.credit.model.creditFlow.customer.enterprise;

import com.zhiwei.core.model.BaseModel;

/**
 * CsEnterprisePrize entity. @author MyEclipse Persistence Tools
 */

public class EnterprisePrize extends BaseModel {

	// Fields

	private Integer id;
	private Integer enterpriseid;
	private String certificatename;  //证书名称
	private String certificatecode;  //证书编号
	private String organname;        //颁发机构名称
	private String prizerpname;      //获奖人
	private Integer prizerpid;
	private java.util.Date licencedate;   //颁发日期
	private String remarks;          //备注
	private String bfevent;           //颁发事件

	// Constructors

	/** default constructor */
	public EnterprisePrize() {
	}

	/** full constructor */
	public EnterprisePrize(Integer enterpriseid, String certificatename,
			String certificatecode, String organname, String prizerpname,
			Integer prizerpid, java.util.Date licencedate, String remarks,
			String bfevent) {
		this.enterpriseid = enterpriseid;
		this.certificatename = certificatename;
		this.certificatecode = certificatecode;
		this.organname = organname;
		this.prizerpname = prizerpname;
		this.prizerpid = prizerpid;
		this.licencedate = licencedate;
		this.remarks = remarks;
		this.bfevent = bfevent ;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getEnterpriseid() {
		return this.enterpriseid;
	}

	public void setEnterpriseid(Integer enterpriseid) {
		this.enterpriseid = enterpriseid;
	}

	public String getCertificatename() {
		return this.certificatename;
	}

	public void setCertificatename(String certificatename) {
		this.certificatename = certificatename;
	}

	public String getCertificatecode() {
		return this.certificatecode;
	}

	public void setCertificatecode(String certificatecode) {
		this.certificatecode = certificatecode;
	}

	public String getOrganname() {
		return this.organname;
	}

	public void setOrganname(String organname) {
		this.organname = organname;
	}

	public String getPrizerpname() {
		return this.prizerpname;
	}

	public void setPrizerpname(String prizerpname) {
		this.prizerpname = prizerpname;
	}

	public Integer getPrizerpid() {
		return this.prizerpid;
	}

	public void setPrizerpid(Integer prizerpid) {
		this.prizerpid = prizerpid;
	}

	public java.util.Date getLicencedate() {
		return this.licencedate;
	}

	public void setLicencedate(java.util.Date licencedate) {
		this.licencedate = licencedate;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getBfevent() {
		return bfevent;
	}

	public void setBfevent(String bfevent) {
		this.bfevent = bfevent;
	}

}