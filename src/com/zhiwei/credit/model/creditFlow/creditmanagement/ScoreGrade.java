package com.zhiwei.credit.model.creditFlow.creditmanagement;

import com.zhiwei.core.model.BaseModel;

public class ScoreGrade extends BaseModel{
	/**
	 * 主键
	 */
	private int id;
	/**
	 * 开始百分制分数
	 */
	private float minScore;
	/**
	 * 结束百分制分数
	 */
	private float maxScore;
	/**
	 * 信用级别名称
	 */
	private String grandName;
	/**
	 * 
	 */
	private float assureRate;
	/**
	 * 
	 */
	private float marginsRate;
	/**
	 * 
	 */
	private float counterGuaranteeArea;
	/**
	 * 级别说明
	 */
	private String advise;
	
	public ScoreGrade() {
		super();
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public float getMinScore() {
		return minScore;
	}

	public void setMinScore(float minScore) {
		this.minScore = minScore;
	}

	public float getMaxScore() {
		return maxScore;
	}

	public void setMaxScore(float maxScore) {
		this.maxScore = maxScore;
	}

	public String getGrandName() {
		return grandName;
	}

	public void setGrandName(String grandName) {
		this.grandName = grandName;
	}

	public float getAssureRate() {
		return assureRate;
	}

	public void setAssureRate(float assureRate) {
		this.assureRate = assureRate;
	}

	public float getMarginsRate() {
		return marginsRate;
	}

	public void setMarginsRate(float marginsRate) {
		this.marginsRate = marginsRate;
	}

	public float getCounterGuaranteeArea() {
		return counterGuaranteeArea;
	}

	public void setCounterGuaranteeArea(float counterGuaranteeArea) {
		this.counterGuaranteeArea = counterGuaranteeArea;
	}

	public String getAdvise() {
		return advise;
	}

	public void setAdvise(String advise) {
		this.advise = advise;
	}
	
	
}
