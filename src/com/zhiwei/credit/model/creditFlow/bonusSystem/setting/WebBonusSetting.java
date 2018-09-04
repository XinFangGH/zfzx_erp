package com.zhiwei.credit.model.creditFlow.bonusSystem.setting;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/

/**
 * 积分规则配置表
 * @author LIUSL
 *
 */
public class WebBonusSetting extends com.zhiwei.core.model.BaseModel {
	
    protected Long   bonusId;
    
    /*******************积分开启条件******************************/
	protected String userAction;//Service类名
	protected String userActionKey;//Service方法名
	/**
	 *  ["登录", "Login"],
		["注册", "register"],
		["投标", "tender"],
		["充值", "recharge"],
		["推荐", "invite"],
		["推荐第一次投标", "inviteOnce"]
	 */
	protected String flagKey;//积分规则标识Key
	/**
	 * 会员等级id
	 */
	protected String memberLevel = "0";
	/**
	 * 会员等级
	 */
	protected String memberLevelValue;
	/**
	 * 积分规则开关  0开，1关
	 */
	protected String bonusswitch;
	
	
	 /*******************积分计算条件******************************/
	/**
	 * 积分类型
	 *  1普通积分
	 *  2活动积分
	 *  3论坛积分
	 */
	protected String bonusType;
	/**
	 * 奖励方式名称
	 */
	protected String bonusName;
	/**
	 * 奖励分值
	 */
	protected String bonus;
	/**
	 * 奖惩措施   1增加积分，2扣除积分
	 */
	protected String isBonus = "1";
	/**
	 * 奖励周期
	 */
	protected String bomusPeriod;
	/**
	 * 奖励周期类型 ["一次性","once"],["分钟","min"], ["小时","hour"], ["天","date"]
	 */
	protected String bomusPeriodType;
	/**
	 * 奖励周期内奖励次数
	 */
	protected String periodBonusLimit = "1";
	/**
	 * 通知用户方式
	 */
	protected String sendMsgType;
	/**
	 * 积分规则说明
	 */
	protected String description;
	/**
	 * 积分用意   如：登录 、注册、
	 */
	protected String bonusIntention;
	
	
	public Long getBonusId() {
		return bonusId;
	}
	public void setBonusId(Long bonusId) {
		this.bonusId = bonusId;
	}
	public String getUserAction() {
		return userAction;
	}
	public void setUserAction(String userAction) {
		this.userAction = userAction;
	}
	public String getUserActionKey() {
		return userActionKey;
	}
	public void setUserActionKey(String userActionKey) {
		this.userActionKey = userActionKey;
	}
	public String getBonusType() {
		return bonusType;
	}
	public void setBonusType(String bonusType) {
		this.bonusType = bonusType;
	}
	public String getBonusName() {
		return bonusName;
	}
	public void setBonusName(String bonusName) {
		this.bonusName = bonusName;
	}
	public String getMemberLevel() {
		return memberLevel;
	}
	public void setMemberLevel(String memberLevel) {
		this.memberLevel = memberLevel;
	}
	public String getMemberLevelValue() {
		return memberLevelValue;
	}
	public void setMemberLevelValue(String memberLevelValue) {
		this.memberLevelValue = memberLevelValue;
	}
	public String getBonus() {
		return bonus;
	}
	public void setBonus(String bonus) {
		this.bonus = bonus;
	}
	public String getIsBonus() {
		return isBonus;
	}
	public void setIsBonus(String isBonus) {
		this.isBonus = isBonus;
	}
	public String getBomusPeriod() {
		return bomusPeriod;
	}
	public void setBomusPeriod(String bomusPeriod) {
		this.bomusPeriod = bomusPeriod;
	}
	public String getPeriodBonusLimit() {
		return periodBonusLimit;
	}
	public void setPeriodBonusLimit(String periodBonusLimit) {
		this.periodBonusLimit = periodBonusLimit;
	}
	public String getSendMsgType() {
		return sendMsgType;
	}
	public void setSendMsgType(String sendMsgType) {
		this.sendMsgType = sendMsgType;
	}
	public String getBomusPeriodType() {
		return bomusPeriodType;
	}
	public void setBomusPeriodType(String bomusPeriodType) {
		this.bomusPeriodType = bomusPeriodType;
	}
	public String getBonusswitch() {
		return bonusswitch;
	}
	public void setBonusswitch(String bonusswitch) {
		this.bonusswitch = bonusswitch;
	}
	public String getFlagKey() {
		return flagKey;
	}
	public void setFlagKey(String flagKey) {
		this.flagKey = flagKey;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getBonusIntention() {
		return bonusIntention;
	}
	public void setBonusIntention(String bonusIntention) {
		this.bonusIntention = bonusIntention;
	}
	
	
	

}
