package com.zhiwei.credit.model.system;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/

import java.util.Date;

import com.zhiwei.core.model.BaseModel;

public class Company extends BaseModel {
	
	private  Long companyId;           
	private  String companyNo;           
	private  String companyName;         
	private  String companyDesc;         
	private  String legalPerson;          
	private  Date setup;                
	private  String phone;               
	private  String fax;                
	private  String site;                 
	private  String logo;   
	private  String logoId;//logoId
	
	public Company() {
		// TODO Auto-generated constructor stub
	}

	
	public String getLogoId() {
		return logoId;
	}


	public void setLogoId(String logoId) {
		this.logoId = logoId;
	}


	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getCompanyNo() {
		return companyNo;
	}

	public void setCompanyNo(String companyNo) {
		this.companyNo = companyNo;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyDesc() {
		return companyDesc;
	}

	public void setCompanyDesc(String companyDesc) {
		this.companyDesc = companyDesc;
	}

	public String getLegalPerson() {
		return legalPerson;
	}

	public void setLegalPerson(String legalPerson) {
		this.legalPerson = legalPerson;
	}

	public Date getSetup() {
		return setup;
	}

	public void setSetup(Date setup) {
		this.setup = setup;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	
}
