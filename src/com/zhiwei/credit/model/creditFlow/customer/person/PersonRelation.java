package com.zhiwei.credit.model.creditFlow.customer.person;

import com.zhiwei.core.model.BaseModel;

/**
 * CsPersonRelation entity. @author MyEclipse Persistence Tools
 */

public class PersonRelation extends BaseModel {

	// Fields

	private Integer id;
	private String relationName;
	private Integer relationShip;
	private String relationPhone;
	private String relationCellPhone;
	private String relationShipValue;
	private Integer personId;
	
	// add by zcb
	private String relationProfession;//职业
	private String relationAddress;//住址
	private String relationCompanyPhone;//单位电话
	private String relationJobCompany;//工作单位
	private String relationJobAddress;//单位地址
	
	private String flag;//0 家庭联系人  1工作证明人 2紧急联系人
	private String content;//电核内容

	// Constructors

	/** default constructor */
	public PersonRelation() {
	}

	/** full constructor */
	public PersonRelation(String relationName, Integer relationShip,
			String relationPhone, String relationCellPhone,
			String relationShipValue, Integer personId) {
		this.relationName = relationName;
		this.relationShip = relationShip;
		this.relationPhone = relationPhone;
		this.relationCellPhone = relationCellPhone;
		this.relationShipValue = relationShipValue;
		this.personId = personId;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRelationName() {
		return this.relationName;
	}

	public void setRelationName(String relationName) {
		this.relationName = relationName;
	}

	public Integer getRelationShip() {
		return this.relationShip;
	}

	public void setRelationShip(Integer relationShip) {
		this.relationShip = relationShip;
	}

	public String getRelationPhone() {
		return this.relationPhone;
	}

	public void setRelationPhone(String relationPhone) {
		this.relationPhone = relationPhone;
	}

	public String getRelationCellPhone() {
		return this.relationCellPhone;
	}

	public void setRelationCellPhone(String relationCellPhone) {
		this.relationCellPhone = relationCellPhone;
	}

	public String getRelationShipValue() {
		return this.relationShipValue;
	}

	public void setRelationShipValue(String relationShipValue) {
		this.relationShipValue = relationShipValue;
	}

	public Integer getPersonId() {
		return this.personId;
	}

	public void setPersonId(Integer personId) {
		this.personId = personId;
	}

	public String getRelationProfession() {
		return relationProfession;
	}

	public void setRelationProfession(String relationProfession) {
		this.relationProfession = relationProfession;
	}

	public String getRelationAddress() {
		return relationAddress;
	}

	public void setRelationAddress(String relationAddress) {
		this.relationAddress = relationAddress;
	}

	public String getRelationCompanyPhone() {
		return relationCompanyPhone;
	}

	public void setRelationCompanyPhone(String relationCompanyPhone) {
		this.relationCompanyPhone = relationCompanyPhone;
	}

	public String getRelationJobCompany() {
		return relationJobCompany;
	}

	public void setRelationJobCompany(String relationJobCompany) {
		this.relationJobCompany = relationJobCompany;
	}

	public String getRelationJobAddress() {
		return relationJobAddress;
	}

	public void setRelationJobAddress(String relationJobAddress) {
		this.relationJobAddress = relationJobAddress;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}