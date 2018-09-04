package com.zhiwei.credit.model.creditFlow.creditmanagement;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.zhiwei.core.model.BaseModel;

public class Options extends BaseModel{
	/**
	 * 主键
	 */
	private int id;
	/**
	 * 指标库Id
	 */
	private int indicatorId;
	/**
	 * 序号
	 */
	private int sortNo;
	/**
	 * 选项名称
	 */
	private String optionName;
	/**
	 * 分值
	 */
	private int score;
	/**
	 * 分类
	 * dl：定量，dx：定性
	 */
	private String ptype;//用来区分 各种指标的文件
	
	/**
	 * 开始数值
	 */
	private String optionStart;
	/**
	 * 结束数值
	 */
	private String optionEnd;
	
	

	public String getPtype() {
		return ptype;
	}

	public void setPtype(String ptype) {
		this.ptype = ptype;
	}

	public Options() {
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
	 public String  getComparableValue(){
		String regEx="[^0-9]";   
		Pattern p = Pattern.compile(regEx);   
		String optionName=getOptionName();
		if(null!=optionName){
			Matcher m = p.matcher(optionName); 
			return m.replaceAll("").trim();
		}else{
			return "";
		}
	 }

	public String getOptionStart() {
		return optionStart;
	}

	public void setOptionStart(String optionStart) {
		this.optionStart = optionStart;
	}

	public String getOptionEnd() {
		return optionEnd;
	}

	public void setOptionEnd(String optionEnd) {
		this.optionEnd = optionEnd;
	}
	
}
