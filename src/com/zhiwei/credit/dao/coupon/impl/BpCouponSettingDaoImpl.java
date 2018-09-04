package com.zhiwei.credit.dao.coupon.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.coupon.BpCouponSettingDao;
import com.zhiwei.credit.model.coupon.BpCouponSetting;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class BpCouponSettingDaoImpl extends BaseDaoImpl<BpCouponSetting> implements BpCouponSettingDao{

	public BpCouponSettingDaoImpl() {
		super(BpCouponSetting.class);
	}

}