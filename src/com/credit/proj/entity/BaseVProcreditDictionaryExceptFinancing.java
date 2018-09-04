package com.credit.proj.entity;

import java.math.BigDecimal;

import com.google.gson.annotations.Expose;

/*
 * add by gao 
 * 除了VProcreditMortgageFinance以外的所有VProcreditMortgage的父类，包含他们的公共属性
 * 方便向上转型，就行赋值操作，避免不同busenessType，要选择不同对象。
 * */
public class BaseVProcreditDictionaryExceptFinancing extends BaseVProcreditDictionary{
	protected Integer assuretypeid;//担保类型数据字典值
	protected Integer typeid;//类型id
	//可能有问题，因为 financing的id  很特别，damn it
	protected Integer id;//编号
	
	protected String mortgagepersontypeforvalue;//反担保类型值
	protected BigDecimal finalCertificationPrice;//最终认证价格
	protected Integer assureofname;//反担保人/企业名称=所有权人
	protected Integer personTypeId;//所有人类型id
	protected String assureofnameEnterOrPerson;//企业名称或个人名字(所有权人)
	public Integer getAssuretypeid() {
		return assuretypeid;
	}
	public void setAssuretypeid(Integer assuretypeid) {
		this.assuretypeid = assuretypeid;
	}
	public String getMortgagepersontypeforvalue() {
		return mortgagepersontypeforvalue;
	}
	public void setMortgagepersontypeforvalue(String mortgagepersontypeforvalue) {
		this.mortgagepersontypeforvalue = mortgagepersontypeforvalue;
	}
	public Integer getTypeid() {
		return typeid;
	}
	public void setTypeid(Integer typeid) {
		this.typeid = typeid;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public BigDecimal getFinalCertificationPrice() {
		return finalCertificationPrice;
	}
	public void setFinalCertificationPrice(BigDecimal finalCertificationPrice) {
		this.finalCertificationPrice = finalCertificationPrice;
	}
	public Integer getAssureofname() {
		return assureofname;
	}
	public void setAssureofname(Integer assureofname) {
		this.assureofname = assureofname;
	}
	public Integer getPersonTypeId() {
		return personTypeId;
	}
	public void setPersonTypeId(Integer personTypeId) {
		this.personTypeId = personTypeId;
	}
	public String getAssureofnameEnterOrPerson() {
		return assureofnameEnterOrPerson;
	}
	public void setAssureofnameEnterOrPerson(String assureofnameEnterOrPerson) {
		this.assureofnameEnterOrPerson = assureofnameEnterOrPerson;
	}
	
	
}
