package com.zhiwei.credit.service.coupon;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.credit.model.activity.BpActivityManage;
import com.zhiwei.credit.model.coupon.BpCouponSetting;
import com.zhiwei.credit.model.coupon.BpCoupons;

/**
 * 
 * @author 
 *
 */
public interface BpCouponsService extends BaseService<BpCoupons>{

	public Boolean createBpCoupons(BpCouponSetting bps, String couponresourceNormal);

	public List<BpCoupons> bouponBelongList(HttpServletRequest request,Integer start, Integer limit);

	public Boolean couponDistribute(Long id, String truename,Long opraterId,String oprateName,BpCoupons bps);
	public Boolean couponAllDistribute(Long id, String truename,Long opraterId,String oprateName,Long categoryId,String setPattern, String couponNumber);

	public List<BpCoupons> listForNotDistributeCoupon(HttpServletRequest request, Integer start, Integer limit);
	
	public String createCouponNumber(String couponType) ;
	public List<BpCoupons>  getActivityType(String Type,Long activityId,String bpCustMemberId);
	public BpCoupons saveBpCoupons(String bpCustMemberId,BpActivityManage bpActivityManage);
	
	/**
	 * 定时器 ，优惠券过期提醒
	 * @return
	 */
	public void sendExpirationRemind();
}


