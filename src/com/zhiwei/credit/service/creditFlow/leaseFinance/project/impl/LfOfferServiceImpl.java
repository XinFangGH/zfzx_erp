package com.zhiwei.credit.service.creditFlow.leaseFinance.project.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.creditFlow.leaseFinance.project.LfOfferDao;
import com.zhiwei.credit.model.creditFlow.leaseFinance.project.LfOffer;
import com.zhiwei.credit.service.creditFlow.leaseFinance.project.LfOfferService;

/**
 * 
 * @author 
 *
 */
public class LfOfferServiceImpl extends BaseServiceImpl<LfOffer> implements LfOfferService{
	@SuppressWarnings("unused")
	private LfOfferDao dao;
	
	public LfOfferServiceImpl(LfOfferDao dao) {
		super(dao);
		this.dao=dao;
	}

}