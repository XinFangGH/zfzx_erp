package com.zhiwei.credit.service.creditFlow.customer.enterprise.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.creditFlow.customer.enterprise.BpCustEntDownstreamContractDao;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.BpCustEntDownstreamContract;
import com.zhiwei.credit.service.creditFlow.customer.enterprise.BpCustEntDownstreamContractService;

/**
 * 
 * @author 
 *
 */
public class BpCustEntDownstreamContractServiceImpl extends BaseServiceImpl<BpCustEntDownstreamContract> implements BpCustEntDownstreamContractService{
	@SuppressWarnings("unused")
	private BpCustEntDownstreamContractDao dao;
	
	public BpCustEntDownstreamContractServiceImpl(BpCustEntDownstreamContractDao dao) {
		super(dao);
		this.dao=dao;
	}

}