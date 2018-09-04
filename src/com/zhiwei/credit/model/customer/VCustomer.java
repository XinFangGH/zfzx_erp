package com.zhiwei.credit.model.customer;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/

import com.google.gson.annotations.Expose;

/**
 * Customer Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * ������������
 */
public class VCustomer extends com.zhiwei.core.model.BaseModel {

	@Expose
	protected String key;
    protected Integer id;
	@Expose
	protected String customerType;
	@Expose
	protected String customerTypeValue;
	@Expose
	protected String customerName;
	@Expose
	protected String code;
	@Expose
	protected String creator;
	@Expose
	protected String belongedId;
	@Expose
	protected String belonger;//共享人
	@Expose
	protected String depName;//共享人所在部门
	@Expose
	protected String idStr;
	/*add by gao 2013-10-10*/
	@Expose
	protected String address;//注册地址
	@Expose
	protected String postcoding;
	@Expose
	protected String fax;
	@Expose
	protected String organizecode;
	@Expose
	protected String area;// 企业通信地址
	@Expose
	private String telephone ;               //联系电话
	@Expose
	private String cciaa ;               //营业执照号码
	@Expose
	private String email ;               //营业执照号码
	@Expose
	private String mortgagepersontypeforvalue;//保证人证件种类  非数据库字段
	@Expose
	private Integer cardtype;  //证件类型   数字
	@Expose
	private String cardtypeValue;  //证件类型  文字
	@Expose
	private String cardnumber;  //证件号
	/*add by gao 2013-10-10  end*/
	
	/**
	 * Default Empty Constructor for class Customer
	 */
	public VCustomer () {
	}
	
	
	
	
	public String getCardnumber() {
		return cardnumber;
	}




	public void setCardnumber(String cardnumber) {
		this.cardnumber = cardnumber;
	}




	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public Integer getCardtype() {
		return cardtype;
	}



	public void setCardtype(Integer cardtype) {
		this.cardtype = cardtype;
	}






	public String getCardtypeValue() {
		return cardtypeValue;
	}



	public void setCardtypeValue(String cardtypeValue) {
		this.cardtypeValue = cardtypeValue;
	}



	public String getCciaa() {
		return cciaa;
	}


	public void setCciaa(String cciaa) {
		this.cciaa = cciaa;
	}


	public String getMortgagepersontypeforvalue() {
		return mortgagepersontypeforvalue;
	}


	public void setMortgagepersontypeforvalue(String mortgagepersontypeforvalue) {
		this.mortgagepersontypeforvalue = mortgagepersontypeforvalue;
	}


	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public String getCustomerTypeValue() {
		return customerTypeValue;
	}

	public void setCustomerTypeValue(String customerTypeValue) {
		this.customerTypeValue = customerTypeValue;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getBelongedId() {
		return belongedId;
	}

	public void setBelongedId(String belongedId) {
		this.belongedId = belongedId;
	}

	public String getBelonger() {
		return belonger;
	}

	public void setBelonger(String belonger) {
		this.belonger = belonger;
	}

	public String getDepName() {
		return depName;
	}

	public void setDepName(String depName) {
		this.depName = depName;
	}

	public String getIdStr() {
		return idStr;
	}

	public void setIdStr(String idStr) {
		this.idStr = idStr;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPostcoding() {
		return postcoding;
	}

	public void setPostcoding(String postcoding) {
		this.postcoding = postcoding;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getOrganizecode() {
		return organizecode;
	}

	public void setOrganizecode(String organizecode) {
		this.organizecode = organizecode;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}
	
	

}
