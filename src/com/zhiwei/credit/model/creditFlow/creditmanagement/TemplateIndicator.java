package com.zhiwei.credit.model.creditFlow.creditmanagement;

import java.util.Date;
import java.util.List;

import com.zhiwei.core.model.BaseModel;

public class TemplateIndicator extends BaseModel{
	/**
	 * 主键
	 */
	private int id;
	/**
	 * 父指标ID
	 */
	private int parentIndicatorId;
	/**
	 * 指标 类型ID
	 */
	private int indicatorTypeId;
	/**
	 * 指标类型
	 */
	private String indicatorType;
	/**
	 * 指标名称
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
	 * 
	 */
	private String lastModifier;
	/**
	 * 
	 */
	private Date lastModifTime;
	/**
	 * 模板Id
	 */
	private int templateId;
	
	private List OptionsList;
	
	private String optionResult;
	
	private float scoreResult;
	
	private String indicatorAssess;
	private int quanzhong=1;
	public TemplateIndicator() {
		super();
	}
	
	public TemplateIndicator(int parentIndicatorId,int indicatorTypeId, String indicatorType,
			String indicatorName, String indicatorDesc, String creater,
			Date createTime, String lastModifier, Date lastModifTime,
			int templateId) {
		super();
		this.parentIndicatorId = parentIndicatorId;
		this.indicatorTypeId = indicatorTypeId;
		this.indicatorType = indicatorType;
		this.indicatorName = indicatorName;
		this.indicatorDesc = indicatorDesc;
		this.creater = creater;
		this.createTime = createTime;
		this.lastModifier = lastModifier;
		this.lastModifTime = lastModifTime;
		this.templateId = templateId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getParentIndicatorId() {
		return parentIndicatorId;
	}

	public void setParentIndicatorId(int parentIndicatorId) {
		this.parentIndicatorId = parentIndicatorId;
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

	public int getTemplateId() {
		return templateId;
	}

	public void setTemplateId(int templateId) {
		this.templateId = templateId;
	}

	public List getOptionsList() {
		return OptionsList;
	}

	public void setOptionsList(List optionsList) {
		OptionsList = optionsList;
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

	public int getQuanzhong() {
		return quanzhong;
	}

	public void setQuanzhong(int quanzhong) {
		this.quanzhong = quanzhong;
	}
	
	
}
