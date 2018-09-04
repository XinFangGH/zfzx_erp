package com.zhiwei.credit.service.thirdPay.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.thirdPay.BpThirdpayCityDao;
import com.zhiwei.credit.model.thirdPay.BpThirdpayCity;
import com.zhiwei.credit.service.thirdPay.BpThirdpayCityService;

/**
 * 
 * @author 
 *
 */
public class BpThirdpayCityServiceImpl extends BaseServiceImpl<BpThirdpayCity> implements BpThirdpayCityService{
	@SuppressWarnings("unused")
	private BpThirdpayCityDao dao;
	
	public BpThirdpayCityServiceImpl(BpThirdpayCityDao dao) {
		super(dao);
		this.dao=dao;
	}

}