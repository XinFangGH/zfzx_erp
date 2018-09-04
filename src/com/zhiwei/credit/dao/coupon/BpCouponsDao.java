package com.zhiwei.credit.dao.coupon;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.credit.model.coupon.BpCoupons;

/**
 * 
 * @author 
 *
 */
public interface BpCouponsDao extends BaseDao<BpCoupons>{

	public void saveList(List<BpCoupons> list);

	public List<BpCoupons> listForNotDistributeCoupon(
			HttpServletRequest request, Integer start, Integer limit);

	public List<BpCoupons> bouponBelongList(HttpServletRequest request,
			Integer start, Integer limit);
	public List<BpCoupons>  getActivityType(String Type,Long activityId,String bpCustMemberId);
	
	public List<BpCoupons>  getCouponEndDate(String endDate);
}