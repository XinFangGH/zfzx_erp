package com.zhiwei.credit.model.creditFlow.customer.enterprise;

/**
 * CsEnterpriseFinance entity. @author MyEclipse Persistence Tools
 */

public class EnterpriseFinance extends com.zhiwei.core.model.BaseModel {

	// Fields

	private Integer id;
	private String textFeildId;
	private String textFeildText;
	private Integer enterpriseId;

	// Constructors

	/** default constructor */
	public EnterpriseFinance() {
	}

	/** full constructor */
	public EnterpriseFinance(String textFeildId, String textFeildText,
			Integer enterpriseId) {
		this.textFeildId = textFeildId;
		this.textFeildText = textFeildText;
		this.enterpriseId = enterpriseId;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTextFeildId() {
		return this.textFeildId;
	}

	public void setTextFeildId(String textFeildId) {
		this.textFeildId = textFeildId;
	}

	public String getTextFeildText() {
		return this.textFeildText;
	}

	public void setTextFeildText(String textFeildText) {
		this.textFeildText = textFeildText;
	}

	public Integer getEnterpriseId() {
		return this.enterpriseId;
	}

	public void setEnterpriseId(Integer enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

}