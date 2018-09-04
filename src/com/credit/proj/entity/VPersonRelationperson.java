package com.credit.proj.entity;

/**
 * VPersonRelationperson entity. @author MyEclipse Persistence Tools
 */

public class VPersonRelationperson implements java.io.Serializable {

	private Integer id;
	private String relationName;
	private Integer relationShip;
	private String relationPhone;
	private String relationCellPhone;
	private Integer personId;
	private String relationShipValue;
	
	private String relationProfession;//职业
	private String relationAddress;//住址
	private String relationCompanyPhone;//单位电话
	private String relationJobCompany;//工作单位
	private String relationJobAddress;//单位地址
	
	private String flag;//0 家庭联系人  1工作证明人 2紧急联系人
	private String content;//电核内容
	
	public VPersonRelationperson() {
		super();
	}
	public VPersonRelationperson(Integer id, String relationName,
			Integer relationShip, String relationPhone,
			String relationCellPhone, Integer personId, String relationShipValue) {
		super();
		this.id = id;
		this.relationName = relationName;
		this.relationShip = relationShip;
		this.relationPhone = relationPhone;
		this.relationCellPhone = relationCellPhone;
		this.personId = personId;
		this.relationShipValue = relationShipValue;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getRelationName() {
		return relationName;
	}
	public void setRelationName(String relationName) {
		this.relationName = relationName;
	}
	public Integer getRelationShip() {
		return relationShip;
	}
	public void setRelationShip(Integer relationShip) {
		this.relationShip = relationShip;
	}
	public String getRelationPhone() {
		return relationPhone;
	}
	public void setRelationPhone(String relationPhone) {
		this.relationPhone = relationPhone;
	}
	public String getRelationCellPhone() {
		return relationCellPhone;
	}
	public void setRelationCellPhone(String relationCellPhone) {
		this.relationCellPhone = relationCellPhone;
	}
	public Integer getPersonId() {
		return personId;
	}
	public void setPersonId(Integer personId) {
		this.personId = personId;
	}
	public String getRelationShipValue() {
		return relationShipValue;
	}
	public void setRelationShipValue(String relationShipValue) {
		this.relationShipValue = relationShipValue;
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