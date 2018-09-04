package com.zhiwei.credit.service.activity;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.core.web.paging.PageBean;
import com.zhiwei.credit.model.activity.BpActivityManage;
import com.zhiwei.credit.model.creditFlow.finance.BpFundIntent;
import com.zhiwei.credit.model.creditFlow.financingAgency.PlBidInfo;
import com.zhiwei.credit.model.creditFlow.financingAgency.PlBidPlan;

/**
 * 
 * @author 
 *
 */
public interface BpActivityManageService extends BaseService<BpActivityManage>{

	/**
	 * 根据活动类型及当前日期生成活动编号
	 * @param flag
	 */
	public void findActivityNumber(String flag);
	
	/**
	 * 根据活动类型及当前日期生成活动编号
	 * @param bpActivityManage 活动实体类
	 * @return true存在 false不存在
	 */
	public boolean findExistCrossDate(BpActivityManage bpActivityManage);
	
	/**
	 * 根基ids集合删除相应活动
	 * @param ids
	 * @return true成功, false失败
	 */
	public boolean closeActivity(String[] ids);

	/**
	 * 根据pageBean查询集合
	 * @param pageBean
	 */
	public void findList(PageBean<BpActivityManage> pageBean);
	
	
	/**
	 * 查找全部当前日期存活的活动(含积分丶红包丶优惠券)
	 * 说明--当前活动没有被关闭，并且活动结束日期包含当前日期
	 * @return
	 * @author LIUSL
	 */
	public List<BpActivityManage> listActivity(String activityType);
	
	
	/**
	 * 自动分发活动奖励引擎
	 * 活动中心，所有活动查询
	 * --自动派发积分，
	 *   自动派发红包
	 *   自动派发优惠券
     * @param bpCustMemberId   --p2p账户ID
     * @param money   --金额--用于投标，充值时进行条件判断
     * @param activityType   --活动操作类型
     * 		  			["注册", "1"],
					["邀请", "2"],
					["投标", "3"],
					["充值", "4"],
					["邀请好友第一次投标", "5"]
					["累计投资", "6"],
					["累计充值", "7"],
					["首投", "8"],
					["累计推荐投资", "9"]		
     * 
     * 
     * 
     * @author LIUSL
	 */
	public void autoDistributeEngine(String activityType, String bpCustMemberId,BigDecimal money,Date buyDate);
	
	/**
	 * 查询当前同一种活动，且同一个操作类型开启的活动
	 * @param sendType  --活动操作类型
	 * @param flag      --活动类型
	 * @param valueOf   --状态  0开启  1关闭
	 * @return
	 */
	public List<BpActivityManage> findBySendTypeAndState(Long sendType, Integer flag,
			Integer valueOf);
	/**
	 * 检查是否有投标活动
	 * @param bidInfo1
	 */
	public void addbpActivityManage(Long investPersonId,BigDecimal buyMoney,Date buyDate);
	/**
	 * 判断此标是否可用优惠券
	 * @param bidplan
	 * @param bidInfo1
	 * @param orderNo
	 */
	public void checkCoupons(PlBidPlan bidplan,PlBidInfo bidInfo1,String orderNo);
	/**
	 * 优惠券派发奖励
	 * @param bp
	 * @param bidInfo1
	 * @param bidplan
	 * @param transferType
	 */
	public void couponIntent(List<BpFundIntent> bp,PlBidInfo bidInfo1,PlBidPlan bidplan,String transferType);
}


