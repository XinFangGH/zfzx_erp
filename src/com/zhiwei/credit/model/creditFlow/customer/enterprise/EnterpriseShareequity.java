package com.zhiwei.credit.model.creditFlow.customer.enterprise;

import java.util.Date;

import com.zhiwei.core.model.BaseModel;

/**
 * CsEnterpriseShareequity entity. @author MyEclipse Persistence Tools
 */

public class EnterpriseShareequity extends BaseModel implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer personid;
	private Integer enterpriseid;
	private String shareholdertype;
	private String shareholdercode;
	private Double capital;
	private String capitaltype;
	private Double share;
	private String shareholder;
	private String remarks;
    private Date createTime;
	// Constructors

	/** default constructor */
	public EnterpriseShareequity() {
	}

	/** full constructor */
	public EnterpriseShareequity(Integer personid, Integer enterpriseid,
			String shareholdertype, String shareholdercode, Double capital,
			String capitaltype, Double share, String shareholder, String remarks,Date createTime) {
		this.personid = personid;
		this.enterpriseid = enterpriseid;
		this.shareholdertype = shareholdertype;
		this.shareholdercode = shareholdercode;
		this.capital = capital;
		this.capitaltype = capitaltype;
		this.share = share;
		this.shareholder = shareholder;
		this.remarks = remarks;
		this.createTime=createTime;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
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

	public Integer getEnterpriseid() {
		return this.enterpriseid;
	}

	public void setEnterpriseid(Integer enterpriseid) {
		this.enterpriseid = enterpriseid;
	}

	public String getShareholdertype() {
		return this.shareholdertype;
	}

	public void setShareholdertype(String shareholdertype) {
		this.shareholdertype = shareholdertype;
	}

	public String getShareholdercode() {
		return this.shareholdercode;
	}

	public void setShareholdercode(String shareholdercode) {
		this.shareholdercode = shareholdercode;
	}

	public Double getCapital() {
		return this.capital;
	}

	public void setCapital(Double capital) {
		this.capital = capital;
	}

	public String getCapitaltype() {
		return this.capitaltype;
	}

	public void setCapitaltype(String capitaltype) {
		this.capitaltype = capitaltype;
	}

	public Double getShare() {
		return this.share;
	}

	public void setShare(Double share) {
		this.share = share;
	}

	public String getShareholder() {
		return this.shareholder;
	}

	public void setShareholder(String shareholder) {
		this.shareholder = shareholder;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}