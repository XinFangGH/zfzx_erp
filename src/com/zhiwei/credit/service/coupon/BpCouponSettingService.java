package com.zhiwei.credit.service.coupon;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import javax.servlet.http.HttpServletRequest;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.credit.model.coupon.BpCouponSetting;

/**
 * 
 * @author 
 *
 */
public interface BpCouponSettingService extends BaseService<BpCouponSetting>{

	public String  check(String checkType,Long categoryId);
	
}


