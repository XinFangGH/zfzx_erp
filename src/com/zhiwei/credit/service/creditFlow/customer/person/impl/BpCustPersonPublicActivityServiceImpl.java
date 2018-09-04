package com.zhiwei.credit.service.creditFlow.customer.person.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.creditFlow.customer.person.BpCustPersonPublicActivityDao;
import com.zhiwei.credit.model.creditFlow.customer.person.BpCustPersonPublicActivity;
import com.zhiwei.credit.service.creditFlow.customer.person.BpCustPersonPublicActivityService;

/**
 * 
 * @author 
 *
 */
public class BpCustPersonPublicActivityServiceImpl extends BaseServiceImpl<BpCustPersonPublicActivity> implements BpCustPersonPublicActivityService{
	@SuppressWarnings("unused")
	private BpCustPersonPublicActivityDao dao;
	
	public BpCustPersonPublicActivityServiceImpl(BpCustPersonPublicActivityDao dao) {
		super(dao);
		this.dao=dao;
	}

}