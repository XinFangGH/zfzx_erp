package com.zhiwei.credit.dao.creditFlow.customer.enterprise.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.creditFlow.customer.enterprise.BpCustEntUpanddowncontractDao;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.BpCustEntDownstreamContract;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.BpCustEntDownstreamCustom;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.BpCustEntUpanddowncontract;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.BpCustEntUpanddownstream;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.BpCustEntUpstreamContract;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.BpCustEntUpstreamCustom;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class BpCustEntUpanddowncontractDaoImpl extends BaseDaoImpl<BpCustEntUpanddowncontract> implements BpCustEntUpanddowncontractDao{

	public BpCustEntUpanddowncontractDaoImpl() {
		super(BpCustEntUpanddowncontract.class);
	}

	@Override
	public BpCustEntDownstreamContract getByedownid(Integer downContractId) {
		String hql="from BpCustEntDownstreamContract as e where e.downContractId=?";
		return (BpCustEntDownstreamContract) getSession().createQuery(hql).setParameter(0, downContractId).uniqueResult();
	}
	@Override
	public BpCustEntUpanddowncontract getByeid(Integer upAndDownContractId) {
		String hql="from BpCustEntUpanddowncontract as e where e.upAndDownContractId=?";
		return (BpCustEntUpanddowncontract) getSession().createQuery(hql).setParameter(0, upAndDownContractId).uniqueResult();
	}

	@Override
	public List<BpCustEntUpanddowncontract> getByentpriseid(Integer entpriseid) {
		String hql="from BpCustEntUpanddowncontract as e where e.enterprise.id=?";
		return getSession().createQuery(hql).setParameter(0, entpriseid).list();
	}


	@Override
	public BpCustEntUpstreamContract getByeupid(Integer upContractId) {
		String hql="from BpCustEntUpstreamContract as e where e.upContractId=?";
		return (BpCustEntUpstreamContract) getSession().createQuery(hql).setParameter(0, upContractId).uniqueResult();
	}

	@Override
	public List<BpCustEntUpstreamContract> getByupAndDownContractId(
			Integer upAndDownContractId) {
		String hql="from BpCustEntUpstreamContract as e where e.bpCustEntUpanddowncontract.upAndDownContractId=?";
		return getSession().createQuery(hql).setParameter(0, upAndDownContractId).list();
	}

	@Override
	public List<BpCustEntDownstreamContract> getByupAndDownContractId1(
			Integer upAndDownContractId) {
		String hql="from BpCustEntDownstreamContract as e where e.bpCustEntUpanddowncontract.upAndDownContractId=?";
		return getSession().createQuery(hql).setParameter(0, upAndDownContractId).list();
	}

}