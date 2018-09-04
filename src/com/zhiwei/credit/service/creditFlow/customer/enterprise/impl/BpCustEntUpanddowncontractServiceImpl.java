package com.zhiwei.credit.service.creditFlow.customer.enterprise.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.creditFlow.customer.enterprise.BpCustEntUpanddowncontractDao;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.BpCustEntDownstreamContract;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.BpCustEntUpanddowncontract;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.BpCustEntUpstreamContract;
import com.zhiwei.credit.service.creditFlow.customer.enterprise.BpCustEntUpanddowncontractService;

/**
 * 
 * @author 
 *
 */
public class BpCustEntUpanddowncontractServiceImpl extends BaseServiceImpl<BpCustEntUpanddowncontract> implements BpCustEntUpanddowncontractService{
	@SuppressWarnings("unused")
	private BpCustEntUpanddowncontractDao dao;
	
	public BpCustEntUpanddowncontractServiceImpl(BpCustEntUpanddowncontractDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public BpCustEntDownstreamContract getByedownid(Integer downContractId) {
		// TODO Auto-generated method stub
		return dao.getByedownid(downContractId);
	}

	@Override
	public BpCustEntUpanddowncontract getByeid(Integer upAndDownContractId) {
		// TODO Auto-generated method stub
		return dao.getByeid(upAndDownContractId);
	}

	@Override
	public List<BpCustEntUpanddowncontract> getByentpriseid(Integer entpriseid) {
		// TODO Auto-generated method stub
		return dao.getByentpriseid(entpriseid);
	}

	@Override
	public BpCustEntUpstreamContract getByeupid(Integer upContractId) {
		// TODO Auto-generated method stub
		return dao.getByeupid(upContractId);
	}

	@Override
	public List<BpCustEntUpstreamContract> getByupAndDownContractId(
			Integer upAndDownContractId) {
		// TODO Auto-generated method stub
		return dao.getByupAndDownContractId(upAndDownContractId);
	}

	@Override
	public List<BpCustEntDownstreamContract> getByupAndDownContractId1(
			Integer upAndDownContractId) {
		// TODO Auto-generated method stub
		return dao.getByupAndDownContractId1(upAndDownContractId);
	}

}