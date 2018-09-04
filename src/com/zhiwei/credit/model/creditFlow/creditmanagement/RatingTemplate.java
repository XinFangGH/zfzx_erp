package com.zhiwei.credit.model.creditFlow.creditmanagement;

import java.util.Date;

import com.zhiwei.core.model.BaseModel;

public class RatingTemplate extends BaseModel{
	/**
	 * 主键
	 */
	private int id;
	/**
	 * 模板名称
	 */
	private String templateName;
	/**
	 * 客户类型
	 */
	private String customerType;
	/**
	 * 适用特征
	 */
	private String applyPoint;
	/**
	 * 创建者
	 */
	private String creater;
	/**
	 * 创建时间
	 */
	private Date careteTime;
	/**
	 * 
	 */
	private String lastModifier;
	/**
	 * 
	 */
	private Date lastModifTime;
	/**
	 * 是否提交模板指标
	 */
	private int subTemplateIndicator;
	/**
	 * 信用评级标准的Id
	 */
	private Long classId;
	/**
	 * 信用评级标准的名称
	 */
	private String className;
	/**
	 * 所属类别
	 * dl：定量，dx：定性
	 */
	private String ptype;//
	public RatingTemplate() {
		super();
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public String getApplyPoint() {
		return applyPoint;
	}

	public void setApplyPoint(String applyPoint) {
		this.applyPoint = applyPoint;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public Date getCareteTime() {
		return careteTime;
	}

	public void setCareteTime(Date careteTime) {
		this.careteTime = careteTime;
	}

	public String getLastModifier() {
		return lastModifier;
	}

	public void setLastModifier(String lastModifier) {
		this.lastModifier = lastModifier;
	}

	public Date getLastModifTime() {
		return lastModifTime;
	}

	public void setLastModifTime(Date lastModifTime) {
		this.lastModifTime = lastModifTime;
	}

	public int getSubTemplateIndicator() {
		return subTemplateIndicator;
	}

	public void setSubTemplateIndicator(int subTemplateIndicator) {
		this.subTemplateIndicator = subTemplateIndicator;
	}

	public Long getClassId() {
		return classId;
	}

	public void setClassId(Long classId) {
		this.classId = classId;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getPtype() {
		return ptype;
	}

	public void setPtype(String ptype) {
		this.ptype = ptype;
	}
	
	
}
