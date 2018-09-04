package com.zhiwei.credit.model.creditFlow.bonusSystem.gradeSet;

import com.zhiwei.core.model.BaseModel;

/**
 * 会员等级设置
 * @author LIUSL
 *
 */
@SuppressWarnings("serial")
public class MemberGradeSet extends BaseModel {

	public MemberGradeSet() {
		super();
	}
	/**
	 * 主键
	 */
	public Long levelId;
	/**
	 * 等级名称
	 */
	public String levelName;
	/**
	 * 等级描述
	 */
	public String levelMark;
	/**
	 * 等级图标
	 */
	public String levelImageUrl;
	/**
	 * 最低积分
	 */
	public Long levelMinBonus;
	public Long getLevelId() {
		return levelId;
	}
	public void setLevelId(Long levelId) {
		this.levelId = levelId;
	}
	public String getLevelName() {
		return levelName;
	}
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
	public String getLevelMark() {
		return levelMark;
	}
	public void setLevelMark(String levelMark) {
		this.levelMark = levelMark;
	}
	public String getLevelImageUrl() {
		return levelImageUrl;
	}
	public void setLevelImageUrl(String levelImageUrl) {
		this.levelImageUrl = levelImageUrl;
	}
	public Long getLevelMinBonus() {
		return levelMinBonus;
	}
	public void setLevelMinBonus(Long levelMinBonus) {
		this.levelMinBonus = levelMinBonus;
	}
	
	
	

}
