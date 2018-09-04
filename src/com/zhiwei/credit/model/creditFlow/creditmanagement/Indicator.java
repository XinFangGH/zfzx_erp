package com.zhiwei.credit.model.creditFlow.creditmanagement;

import java.util.Date;

import com.zhiwei.core.model.BaseModel;

public class Indicator extends BaseModel{
	/**
	 * 主键
	 */
	private int id;
	/**
	 * 指标类型ID
	 */
	private int indicatorTypeId;
	/**
	 * 指标类型名称
	 */
	private String indicatorType;
	/**
	 * 指标名称
	 */
	private String indicatorName;
	/**
	 * 指标说明
	 */
	private String indicatorDesc;
	/**
	 * 创建者
	 */
	private String creater;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 得分
	 */
	private String lastModifier;
	/**
	 * 
	 */
	private Date lastModifTime;
	/**
	 * 
	 */
	private String ptype;
	/**
	 * 指标分类
	 * Person：表示个人，Enterprise：表示企业
	 */
	private String operationType;
	/**
	 * 定量要素编码
	 */
	private String elementCode;
	
	

	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	public String getPtype() {
		return ptype;
	}

	public void setPtype(String ptype) {
		this.ptype = ptype;
	}

	public Indicator() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIndicatorType() {
		return indicatorType;
	}

	public void setIndicatorType(String indicatorType) {
		this.indicatorType = indicatorType;
	}
	
	public int getIndicatorTypeId() {
		return indicatorTypeId;
	}

	public void setIndicatorTypeId(int indicatorTypeId) {
		this.indicatorTypeId = indicatorTypeId;
	}

	public String getIndicatorName() {
		return indicatorName;
	}

	public void setIndicatorName(String indicatorName) {
		this.indicatorName = indicatorName;
	}

	public String getIndicatorDesc() {
		return indicatorDesc;
	}

	public void setIndicatorDesc(String indicatorDesc) {
		this.indicatorDesc = indicatorDesc;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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

	public void setElementCode(String elementCode) {
		this.elementCode = elementCode;
	}

	public String getElementCode() {
		return elementCode;
	}

	
	
	
}
