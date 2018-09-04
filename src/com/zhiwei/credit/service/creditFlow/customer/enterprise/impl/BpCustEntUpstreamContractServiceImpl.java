package com.zhiwei.credit.service.creditFlow.customer.enterprise.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.creditFlow.customer.enterprise.BpCustEntUpstreamContractDao;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.BpCustEntUpstreamContract;
import com.zhiwei.credit.service.creditFlow.customer.enterprise.BpCustEntUpstreamContractService;

/**
 * 
 * @author 
 *
 */
public class BpCustEntUpstreamContractServiceImpl extends BaseServiceImpl<BpCustEntUpstreamContract> implements BpCustEntUpstreamContractService{
	@SuppressWarnings("unused")
	private BpCustEntUpstreamContractDao dao;
	
	public BpCustEntUpstreamContractServiceImpl(BpCustEntUpstreamContractDao dao) {
		super(dao);
		this.dao=dao;
	}

}