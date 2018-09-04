package com.zhiwei.credit.model.creditFlow.customer.enterprise;

/**
 * CsEnterpriseEmployeestructure entity. @author MyEclipse Persistence Tools
 */

public class EnterpriseEmployeeStructure extends com.zhiwei.core.model.BaseModel {

	// Fields

	private Integer id;
	private String textfieldId;
	private String textfieldText;
	private Integer enterpriseId;

	// Constructors

	/** default constructor */
	public EnterpriseEmployeeStructure() {
	}

	/** full constructor */
	public EnterpriseEmployeeStructure(String textfieldId,
			String textfieldText, Integer enterpriseId) {
		this.textfieldId = textfieldId;
		this.textfieldText = textfieldText;
		this.enterpriseId = enterpriseId;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTextfieldId() {
		return this.textfieldId;
	}

	public void setTextfieldId(String textfieldId) {
		this.textfieldId = textfieldId;
	}

	public String getTextfieldText() {
		return this.textfieldText;
	}

	public void setTextfieldText(String textfieldText) {
		this.textfieldText = textfieldText;
	}

	public Integer getEnterpriseId() {
		return this.enterpriseId;
	}

	public void setEnterpriseId(Integer enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

}