package com.zhiwei.credit.model.creditFlow.creditmanagement;

import java.util.Date;
import java.util.List;

import com.zhiwei.core.model.BaseModel;

public class RatingIndicator extends BaseModel{
	/**
	 * 主键
	 */
	private int id;
	/**
	 * 
	 */
	private int ratingId;
	/**
	 * 父指标ID
	 */
	private int parentIndicatorId;
	/**
	 * 指标类型ID
	 */
	private int indicatorTypeId;
	/**
	 * 指标类型
	 */
	private String indicatorType;
	/**
	 * 指标
	 */
	private String indicatorName;
	/**
	 * 选项
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
	
	//不与数据库映射
	private List optionsList;
	private String optionResult;
	private float scoreResult;
	private String indicatorAssess;
	
	public RatingIndicator() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public RatingIndicator(int ratingId, int parentIndicatorId,
			int indicatorTypeId, String indicatorType, String indicatorName,
			String indicatorDesc, String creater, Date createTime,
			String lastModifier, Date lastModifTime) {
		super();
		this.ratingId = ratingId;
		this.parentIndicatorId = parentIndicatorId;
		this.indicatorTypeId = indicatorTypeId;
		this.indicatorType = indicatorType;
		this.indicatorName = indicatorName;
		this.indicatorDesc = indicatorDesc;
		this.creater = creater;
		this.createTime = createTime;
		this.lastModifier = lastModifier;
		this.lastModifTime = lastModifTime;
	}



	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getRatingId() {
		return ratingId;
	}
	
	public void setRatingId(int ratingId) {
		this.ratingId = ratingId;
	}
	
	public int getParentIndicatorId() {
		return parentIndicatorId;
	}
	
	public void setParentIndicatorId(int parentIndicatorId) {
		this.parentIndicatorId = parentIndicatorId;
	}
	
	public int getIndicatorTypeId() {
		return indicatorTypeId;
	}
	
	public void setIndicatorTypeId(int indicatorTypeId) {
		this.indicatorTypeId = indicatorTypeId;
	}
	
	public String getIndicatorType() {
		return indicatorType;
	}
	
	public void setIndicatorType(String indicatorType) {
		this.indicatorType = indicatorType;
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
	
	public List getOptionsList() {
		return optionsList;
	}
	
	public void setOptionsList(List optionsList) {
		this.optionsList = optionsList;
	}
	
	public String getOptionResult() {
		return optionResult;
	}
	
	public void setOptionResult(String optionResult) {
		this.optionResult = optionResult;
	}
	
	public float getScoreResult() {
		return scoreResult;
	}
	
	public void setScoreResult(float scoreResult) {
		this.scoreResult = scoreResult;
	}
	
	public String getIndicatorAssess() {
		return indicatorAssess;
	}
	
	public void setIndicatorAssess(String indicatorAssess) {
		this.indicatorAssess = indicatorAssess;
	}


	
	
	
}
