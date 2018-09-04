package com.zhiwei.credit.model.creditFlow.customer.person;

import java.math.BigDecimal;

import com.zhiwei.core.model.BaseModel;

public class Spouse extends BaseModel  implements  java.io.Serializable{
	private Long spouseId;
	private String name;//姓名
	private Short cardtype;//证件类型
	private String cardnumber;//证件号码
	private Short dgree;//学历
	private String currentcompany;//工作单位
	private Short job;//职务
	private Short politicalStatus;//政治面貌
	private String linkTel;//联系电话
	private Integer personId;
	
	//add by gao 大连天储
	private String unitAddress;//单位地址
	private String unitProperty;//单位性质
	private BigDecimal incomeAfterTax;//税后收入
	private String unitPhoneNO;//单位电话
	
	
	public Spouse(){
		
	}
	
	public Spouse(String name,Short cardtype,String cardnumber,Short dgree,String currentcompany,Short job,Short politicalStatus,String linkTel,Integer personId){
		this.name=name;
		this.cardtype=cardtype;
		this.cardnumber=cardnumber;
		this.dgree=dgree;
		this.currentcompany=currentcompany;
		this.job=job;
		this.politicalStatus=politicalStatus;
		this.linkTel=linkTel;
		this.personId=personId;
	}
	
	public Long getSpouseId() {
		return spouseId;
	}
	public void setSpouseId(Long spouseId) {
		this.spouseId = spouseId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Short getCardtype() {
		return cardtype;
	}
	public void setCardtype(Short cardtype) {
		this.cardtype = cardtype;
	}
	public String getCardnumber() {
		return cardnumber;
	}
	public void setCardnumber(String cardnumber) {
		this.cardnumber = cardnumber;
	}
	public Short getDgree() {
		return dgree;
	}
	public void setDgree(Short dgree) {
		this.dgree = dgree;
	}
	public String getCurrentcompany() {
		return currentcompany;
	}
	public void setCurrentcompany(String currentcompany) {
		this.currentcompany = currentcompany;
	}
	public Short getJob() {
		return job;
	}
	public void setJob(Short job) {
		this.job = job;
	}
	public Short getPoliticalStatus() {
		return politicalStatus;
	}
	public void setPoliticalStatus(Short politicalStatus) {
		this.politicalStatus = politicalStatus;
	}
	public String getLinkTel() {
		return linkTel;
	}
	public void setLinkTel(String linkTel) {
		this.linkTel = linkTel;
	}
	public Integer getPersonId() {
		return personId;
	}
	public void setPersonId(Integer personId) {
		this.personId = personId;
	}

	public String getUnitAddress() {
		return unitAddress;
	}

	public void setUnitAddress(String unitAddress) {
		this.unitAddress = unitAddress;
	}

	public String getUnitProperty() {
		return unitProperty;
	}

	public void setUnitProperty(String unitProperty) {
		this.unitProperty = unitProperty;
	}

	public BigDecimal getIncomeAfterTax() {
		return incomeAfterTax;
	}

	public void setIncomeAfterTax(BigDecimal incomeAfterTax) {
		this.incomeAfterTax = incomeAfterTax;
	}

	public String getUnitPhoneNO() {
		return unitPhoneNO;
	}

	public void setUnitPhoneNO(String unitPhoneNO) {
		this.unitPhoneNO = unitPhoneNO;
	}

	public Object toElementStr() {
		StringBuffer sb = new StringBuffer();
		sb.append("甲方（借款人）:"+this.getName()+"</br>");
		sb.append("证件号码："+this.getCardnumber()+"</br>");
		sb.append("通讯地址："+this.getUnitAddress()+"</br>");
		sb.append("联系电话："+this.getLinkTel()+"</br>");
		return sb.toString();
	}
	
	
}
