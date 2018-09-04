package com.zhiwei.credit.model.customer;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

import com.google.gson.annotations.Expose;

/**
 * ContractConfig Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * ���������������
 */
public class ContractConfig extends com.zhiwei.core.model.BaseModel {

	@Expose
    protected Long configId;
	@Expose
	protected String itemName;
	@Expose
	protected String itemSpec;
	@Expose
	protected java.math.BigDecimal amount;
	@Expose
	protected String notes;
	
	protected com.zhiwei.credit.model.customer.Contract contract;


	/**
	 * Default Empty Constructor for class ContractConfig
	 */
	public ContractConfig () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class ContractConfig
	 */
	public ContractConfig (
		 Long in_configId
        ) {
		this.setConfigId(in_configId);
    }

	
	public com.zhiwei.credit.model.customer.Contract getContract () {
		return contract;
	}	
	
	public void setContract (com.zhiwei.credit.model.customer.Contract in_contract) {
		this.contract = in_contract;
	}
    

	/**
	 * 	 * @return Long
     * @hibernate.id column="configId" type="java.lang.Long" generator-class="native"
	 */
	public Long getConfigId() {
		return this.configId;
	}
	
	/**
	 * Set the configId
	 */	
	public void setConfigId(Long aValue) {
		this.configId = aValue;
	}	

	/**
	 * 设备名称	 * @return String
	 * @hibernate.property column="itemName" type="java.lang.String" length="128" not-null="true" unique="false"
	 */
	public String getItemName() {
		return this.itemName;
	}
	
	/**
	 * Set the itemName
	 * @spring.validator type="required"
	 */	
	public void setItemName(String aValue) {
		this.itemName = aValue;
	}	

	/**
	 * 	 * @return Long
	 */
	public Long getContractId() {
		return this.getContract()==null?null:this.getContract().getContractId();
	}
	
	/**
	 * Set the contactId
	 */	
	public void setContractId(Long aValue) {
	    if (aValue==null) {
	    	contract = null;
	    } else if (contract == null) {
	        contract = new com.zhiwei.credit.model.customer.Contract(aValue);
	        contract.setVersion(new Integer(0));//set a version to cheat hibernate only
	    } else {
			contract.setContractId(aValue);
	    }
	}	

	/**
	 * 设置规格	 * @return String
	 * @hibernate.property column="itemSpec" type="java.lang.String" length="128" not-null="true" unique="false"
	 */
	public String getItemSpec() {
		return this.itemSpec;
	}
	
	/**
	 * Set the itemSpec
	 * @spring.validator type="required"
	 */	
	public void setItemSpec(String aValue) {
		this.itemSpec = aValue;
	}	

	/**
	 * 数量	 * @return java.math.BigDecimal
	 * @hibernate.property column="amount" type="java.math.BigDecimal" length="18" not-null="true" unique="false"
	 */
	public java.math.BigDecimal getAmount() {
		return this.amount;
	}
	
	/**
	 * Set the amount
	 * @spring.validator type="required"
	 */	
	public void setAmount(java.math.BigDecimal aValue) {
		this.amount = aValue;
	}	

	/**
	 * 备注	 * @return String
	 * @hibernate.property column="notes" type="java.lang.String" length="200" not-null="false" unique="false"
	 */
	public String getNotes() {
		return this.notes;
	}
	
	/**
	 * Set the notes
	 */	
	public void setNotes(String aValue) {
		this.notes = aValue;
	}	


	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof ContractConfig)) {
			return false;
		}
		ContractConfig rhs = (ContractConfig) object;
		return new EqualsBuilder()
				.append(this.configId, rhs.configId)
				.append(this.itemName, rhs.itemName)
						.append(this.itemSpec, rhs.itemSpec)
				.append(this.amount, rhs.amount)
				.append(this.notes, rhs.notes)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.configId) 
				.append(this.itemName) 
						.append(this.itemSpec) 
				.append(this.amount) 
				.append(this.notes) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("configId", this.configId) 
				.append("itemName", this.itemName) 
						.append("itemSpec", this.itemSpec) 
				.append("amount", this.amount) 
				.append("notes", this.notes) 
				.toString();
	}



}
