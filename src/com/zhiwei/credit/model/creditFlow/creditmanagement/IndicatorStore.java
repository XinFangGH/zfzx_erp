package com.zhiwei.credit.model.creditFlow.creditmanagement;

import com.zhiwei.core.model.BaseModel;

public class IndicatorStore extends BaseModel{
	/**
	 * 主键
	 */
	private int id;
	/**
	 * 父级Id
	 */
	private int parentId;
	/**
	 * 指标名称
	 */
	private String indicatorType;
	/**
	 * 是否为最低级
	 * 0：否，1：是
	 */
	private boolean isleaf;
	/**
	 * 备注
	 */
	private String remarks;
	/**
	 * 类型
	 * dl：定量，dx：定性
	 */
	private String ptype;
	
	
	public String getPtype() {
		return ptype;
	}

	public void setPtype(String ptype) {
		this.ptype = ptype;
	}

	public IndicatorStore() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public String getIndicatorType() {
		return indicatorType;
	}

	public void setIndicatorType(String indicatorType) {
		this.indicatorType = indicatorType;
	}

	public boolean isIsleaf() {
		return isleaf;
	}

	public void setIsleaf(boolean isleaf) {
		this.isleaf = isleaf;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	
}
