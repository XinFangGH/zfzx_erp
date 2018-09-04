package com.zhiwei.credit.model.creditFlow.customer.enterprise;

import com.zhiwei.core.model.BaseModel;

public class EnterpriseRelationPerson extends BaseModel{

	private Integer id ;
	private Integer enterpriseid; 
	private String relationName;
	private String relationJob ;
	private String relationFixedPhone;    //联系人固定电话
	private String relationMovePhone;     //联系人移动电话
	private String relationFamilyAddress ;//联系人家庭住址
	private String bossName ;
	private String bossPhone ;
	private String remarks ;
	private Boolean mark;                  // 是否是主要联系人
	
	/** default constructor */
	public EnterpriseRelationPerson() {
	}

	/** full constructor */
	public EnterpriseRelationPerson(Integer enterpriseid,
			String relationName, String relationJob, String relationFixedPhone,
			String relationMovePhone, String relationFamilyAddress,
			String bossName, String bossPhone, Boolean mark, String remarks) {
		this.enterpriseid = enterpriseid;
		this.relationName = relationName;
		this.relationJob = relationJob;
		this.relationFixedPhone = relationFixedPhone;
		this.relationMovePhone = relationMovePhone;
		this.relationFamilyAddress = relationFamilyAddress;
		this.bossName = bossName;
		this.bossPhone = bossPhone;
		this.mark = mark;
		this.remarks = remarks;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getEnterpriseid() {
		return enterpriseid;
	}
	public void setEnterpriseid(Integer enterpriseid) {
		this.enterpriseid = enterpriseid;
	}
	public String getRelationName() {
		return relationName;
	}
	public void setRelationName(String relationName) {
		this.relationName = relationName;
	}
	public String getRelationJob() {
		return relationJob;
	}
	public void setRelationJob(String relationJob) {
		this.relationJob = relationJob;
	}
	public String getRelationFixedPhone() {
		return relationFixedPhone;
	}
	public void setRelationFixedPhone(String relationFixedPhone) {
		this.relationFixedPhone = relationFixedPhone;
	}
	public String getRelationMovePhone() {
		return relationMovePhone;
	}
	public void setRelationMovePhone(String relationMovePhone) {
		this.relationMovePhone = relationMovePhone;
	}
	public String getRelationFamilyAddress() {
		return relationFamilyAddress;
	}
	public void setRelationFamilyAddress(String relationFamilyAddress) {
		this.relationFamilyAddress = relationFamilyAddress;
	}
	public Boolean getMark() {
		return mark;
	}
	public void setMark(Boolean mark) {
		this.mark = mark;
	}
	public String getBossName() {
		return bossName;
	}
	public void setBossName(String bossName) {
		this.bossName = bossName;
	}
	public String getBossPhone() {
		return bossPhone;
	}
	public void setBossPhone(String bossPhone) {
		this.bossPhone = bossPhone;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	
}
