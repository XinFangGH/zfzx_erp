package com.zhiwei.credit.model.creditFlow.thirdRalation;
/*
 *  北京互融时代软件有限公司 JOffice协同办公管理系统   -- http://www.hurongtime.com
 *  Copyright (C) 2008-2009 BEIJING JINZHIWANWEI SOFTWARES LIMITED COMPANY
*/
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * SVEnterprisePerson1 Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class SVEnterprisePerson extends com.zhiwei.core.model.BaseModel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Long id;
	protected String name;
	protected String code;
	protected String tel;
	protected String type;

	protected String contractCategoryText;//具体合同类型名称
	protected String contractCategoryTypeText;//合同分类名称
    protected Integer contractId;
	protected Boolean isLegalCheck;
	protected Long thirdRalationId;
	protected Integer categoryId;
	protected Integer temptId;
	
	protected String key;

	/**
	 * Default Empty Constructor for class SVEnterprisePerson1
	 */
	public SVEnterprisePerson () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class SVEnterprisePerson1
	 */
	public SVEnterprisePerson (
			String in_key
        ) {
		this.setKey(in_key);
    }

    

	/**
	 * 	 * @return Long
     * @hibernate.id column="id" type="java.lang.Long" 
	 */
	public Long getId() {
		return this.id;
	}
	
	/**
	 * Set the id
	 */	
	public void setId(Long aValue) {
		this.id = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="name" type="java.lang.String" length="100" not-null="false" unique="false"
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Set the name
	 */	
	public void setName(String aValue) {
		this.name = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="code" type="java.lang.String" length="100" not-null="false" unique="false"
	 */
	public String getCode() {
		return this.code;
	}
	
	/**
	 * Set the code
	 */	
	public void setCode(String aValue) {
		this.code = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="tel" type="java.lang.String" length="50" not-null="false" unique="false"
	 */
	public String getTel() {
		return this.tel;
	}
	
	/**
	 * Set the tel
	 */	
	public void setTel(String aValue) {
		this.tel = aValue;
	}	

	/**
	 * 	 * @return Short
	 * @hibernate.property column="type" type="java.lang.Short" length="5" not-null="false" unique="false"
	 */
     
      
	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof SVEnterprisePerson)) {
			return false;
		}
		SVEnterprisePerson rhs = (SVEnterprisePerson) object;
		return new EqualsBuilder()
				.append(this.id, rhs.id)
				.append(this.name, rhs.name)
				.append(this.code, rhs.code)
				.append(this.tel, rhs.tel)
				.append(this.type, rhs.type)
				.append(this.key, rhs.key)
				.isEquals();
	}

	public Long getThirdRalationId() {
		return thirdRalationId;
	}

	public void setThirdRalationId(Long thirdRalationId) {
		this.thirdRalationId = thirdRalationId;
	}

	public Integer getContractId() {
		return contractId;
	}

	public void setContractId(Integer contractId) {
		this.contractId = contractId;
	}

	public Boolean getIsLegalCheck() {
		return isLegalCheck;
	}

	public void setIsLegalCheck(Boolean isLegalCheck) {
		this.isLegalCheck = isLegalCheck;
	}

	public String getContractCategoryText() {
		return contractCategoryText;
	}

	public void setContractCategoryText(String contractCategoryText) {
		this.contractCategoryText = contractCategoryText;
	}

	public String getContractCategoryTypeText() {
		return contractCategoryTypeText;
	}

	public void setContractCategoryTypeText(String contractCategoryTypeText) {
		this.contractCategoryTypeText = contractCategoryTypeText;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getTemptId() {
		return temptId;
	}

	public void setTemptId(Integer temptId) {
		this.temptId = temptId;
	}

	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.id) 
				.append(this.name) 
				.append(this.code) 
				.append(this.tel) 
				.append(this.type) 
				.append(this.key)
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("id", this.id) 
				.append("name", this.name) 
				.append("code", this.code) 
				.append("tel", this.tel) 
				.append("type", this.type)
				.append("key", this.key)
				.toString();
	}



}
