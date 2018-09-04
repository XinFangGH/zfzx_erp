package com.zhiwei.credit.model.creditFlow.creditmanagement;

import com.zhiwei.core.model.BaseModel;

public class TemplateOptions extends BaseModel{
	/**
	 * 主键
	 */
	private int id;
	/**
	 * 评估模板要素Id
	 */
	private int indicatorId;
	/**
	 * 序号
	 */
	private int sortNo;
	/**
	 * 选项值
	 */
	private String optionName;
	/**
	 * 分值
	 */
	private int score;
	/**
	 * 权重后分值
	 */
	private int qzScore;
	public TemplateOptions() {
		super();
	}

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

	public int getQzScore() {
		return qzScore;
	}

	public void setQzScore(int qzScore) {
		this.qzScore = qzScore;
	}
	
	
}
