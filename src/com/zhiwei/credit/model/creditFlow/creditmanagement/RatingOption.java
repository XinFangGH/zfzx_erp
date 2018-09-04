package com.zhiwei.credit.model.creditFlow.creditmanagement;

import com.zhiwei.core.model.BaseModel;

public class RatingOption extends BaseModel{
	/**
	 * 主键
	 */
	private int id;
	/**
	 * 所属指标Id
	 */
	private int indicatorId;
	/**
	 * 序号
	 */
	private int sortNo;
	/**
	 * 指标选项
	 */
	private String optionName;
	/**
	 * 分值
	 */
	private int score;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getIndicatorId() {
		return indicatorId;
	}
	
	public void setIndicatorId(int indicatorId) {
		this.indicatorId = indicatorId;
	}
	
	public int getSortNo() {
		return sortNo;
	}
	
	public void setSortNo(int sortNo) {
		this.sortNo = sortNo;
	}
	
	public String getOptionName() {
		return optionName;
	}
	
	public void setOptionName(String optionName) {
		this.optionName = optionName;
	}
	
	public int getScore() {
		return score;
	}
	
	public void setScore(int score) {
		this.score = score;
	}
	
	
}
