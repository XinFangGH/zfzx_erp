package com.zhiwei.credit.service.creditFlow.customer.enterprise.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.creditFlow.customer.enterprise.BpCustEntLawsuitDao;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.BpCustEntLawsuit;
import com.zhiwei.credit.service.creditFlow.customer.enterprise.BpCustEntLawsuitService;

/**
 * 
 * @author 
 *
 */
public class BpCustEntLawsuitServiceImpl extends BaseServiceImpl<BpCustEntLawsuit> implements BpCustEntLawsuitService{
	@SuppressWarnings("unused")
	private BpCustEntLawsuitDao dao;
	
	public BpCustEntLawsuitServiceImpl(BpCustEntLawsuitDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public BpCustEntLawsuit getbyId(Integer lawsuitId) {
		// TODO Auto-generated method stub
		return dao.getbyId(lawsuitId);
	}



}