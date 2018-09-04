package com.zhiwei.credit.model.creditFlow.creditmanagement;

import com.zhiwei.core.model.BaseModel;

public class ScoreGradeOfClass extends BaseModel{
	
	/**
	 * 主键
	 */
	private Long grandId;
	/**
	 * 信用级别名称
	 */
	private String grandname;
	/**
	 * 级别等级
	 */
	private String grandType;
	/**
	 * 含义
	 */
	private String hanyi;
	/**
	 * 级别说明
	 */
	private String grandExplain;
	/**
	 * 开始百分制分数
	 */
	private Float startScore;
	/**
	 * 结束百分制分数
	 */
	private Float endScore;
	/**
	 * 信用评级标准Id
	 */
	private Long classId;
	/**
	 * 信用评级标准名称
	 */
	private String className;
	
    public ScoreGradeOfClass(){
    	super();
    }

	public Long getGrandId() {
		return grandId;
	}

	public void setGrandId(Long id) {
		this.grandId = id;
	}

	public String getGrandname() {
		return grandname;
	}

	public void setGrandname(String grandname) {
		this.grandname = grandname;
	}

	public String getGrandType() {
		return grandType;
	}

	public void setGrandType(String grandType) {
		this.grandType = grandType;
	}

	public String getHanyi() {
		return hanyi;
	}

	public void setHanyi(String hanyi) {
		this.hanyi = hanyi;
	}

	public String getGrandExplain() {
		return grandExplain;
	}

	public void setGrandExplain(String grandExplain) {
		this.grandExplain = grandExplain;
	}

	public Float getStartScore() {
		return startScore;
	}

	public void setStartScore(Float startScore) {
		this.startScore = startScore;
	}

	public Float getEndScore() {
		return endScore;
	}

	public void setEndScore(Float endScore) {
		this.endScore = endScore;
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
    
}
