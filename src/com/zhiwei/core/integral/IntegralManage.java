package com.zhiwei.core.integral;

import java.math.BigDecimal;

import com.zhiwei.credit.model.activity.BpActivityManage;
import com.zhiwei.credit.model.creditFlow.bonusSystem.setting.WebBonusSetting;


/**
 * 积分计算接口
 * @author LIUSL
 *
 */
public interface IntegralManage {
	
	
	/**
	 * 分值计算时增加一个共享同步锁
	 */
	public static final Object obj = new Object();
	
	/**
	 * 增加积分或减少积分
	 * 	    --增加传正数
	 * 		--减少传负数
	 * 
	 * @param userId  --BpCustMember.id(p2p账户ID) 
	 * @param score   --分值(正分还是负分)
	 * @param settingFlag
	 * 		     	如果使用的是积分规则,则传积分规则ID
	 * 				没有传自定义flag(不同操作点，区别开flag，不可相同):例充值传A,起息传B
 	 * @return true 成功
	 * 		   false 失败  
	 */
	public boolean addScore(Long userId,Long score,String settingFlag);
	
	/**
	 * 通过活动积分规则: 增加积分或减少积分
	 * 		
	 * 	    --增加传正数
	 * 		--减少传负数
	 * 
	 * @param userId  --BpCustMember.id(p2p账户ID) 
	 * @param score   --分值(正分还是负分)
	 * @param BpActivityManage bpActivityManage --活动积分规则的  规则对象
	 * 		     	
 	 * @return true 成功
	 * 		   false 失败  
	 */
	public boolean addSocreBpActivityManage(Long userId,Long score,BpActivityManage bpActivityManage);
	
	
	/**
	 * 用于以金额为条件给的积分计算
	 * 
	 * @param userId  --BpCustMember.id(p2p账户ID) 
	 * @param money	  --金额
	 * @param rate	  --金额 积分比       (如1000元钱1积分侧)1/1000=1000 ,传积分金额比为1000
 	 * @return true 成功
	 * 		   false 失败
	 */
	public boolean addScordByMoney(Long userId,BigDecimal money,BigDecimal rate);
	
	
	/**
	 * 通过积分规则增加积分   【手动代码的形式调用】
	 * 代码指定的使用具体的积分规则ID
	 * 
	 * @param userId  			--BpCustMember.id(p2p账户ID) 
	 * @param webBonusSetting   --积分规则对象
 	 * @return true 成功
	 * 		   false 失败
	 */
	public boolean addScordBySetting(Long userId,WebBonusSetting webBonusSetting);
	
	/**
	 * 通过指定积分规则Key值增加积分   【手动代码的形式调用】
	 * 区别于addScordBySetting()方法
	 * 自动区分相同Key值，会员等级要求不同的积分规则【如同一积分Key值，一级会员涨一分，二级会员涨2分】
	 * 自动匹配会员等级查找出用户最接近的的积分规则
	 * 
	 * @param userId	--BpCustMember.id(p2p账户ID)
	 * @param flagKey   --积分规则Key值
	 * @return true 成功
	 * 		   false 失败
	 */
	public boolean addScordByFlagKey(Long userId,String flagKey);
	
	
	/**
	 * 自动化配置积分计算引擎【AOP调用】【手动代码不调用】
	 * @param userAction	 --类名
	 * @param userActionKey  --方法名
	 */
	public void integralEngine(String userAction,String userActionKey);
	
	
}
