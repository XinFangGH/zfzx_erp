package com.zhiwei.credit.model.creditFlow.bonusSystem;

public class WebBonusConstant {
	
	/**
	 * WebBonusRecord.recordDirector
	 * 1表示奖励
	 */
	public static final String DIRECTION_ADD = "1"; 
	/**
	 * WebBonusRecord.recordDirector
	 * 2：表示扣除
	 */
	public static final String DIRECTION_REDUCE = "2"; 
	
	
	
	/**
	 * WebBonusSetting.isBonus
	 * 奖惩措施
	 *    增加积分
	 */
	public static final String IS_BONUS_1 = "1";
	/**
	 * WebBonusSetting.isBonus
	 * 奖惩措施
	 *    扣除积分
	 */
	public static final String IS_BONUS_2 = "2";
	
	
	/**
 	 * WebBonusSetting.bomusPeriod
	 * 奖励周期类型
	 * 		一次性
	 */
	public static final String BOMUSPERIOD_TYPE_ONCE = "once";
	
	
	/**
 	 * WebBonusSetting.bomusPeriodType
	 * 奖励周期类型
	 * 		分钟
	 */
	public static final String BOMUSPERIOD_TYPE_MIN = "min";
	
	/**
 	 * WebBonusSetting.bomusPeriodType
	 * 奖励周期类型
	 * 		小时
	 */
	public static final String BOMUSPERIOD_TYPE_HOUR = "hour";
	
	/**
 	 * WebBonusSetting.bomusPeriodType
	 * 奖励周期类型
	 * 		天
	 */
	public static final String BOMUSPERIOD_TYPE_DATE = "date";
	
	
}
