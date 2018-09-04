package com.zhiwei.credit.dao.creditFlow.customer.enterprise.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.creditFlow.customer.enterprise.BpCustEntUpanddownstreamDao;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.BpCustEntDownstreamCustom;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.BpCustEntLawsuit;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.BpCustEntUpanddownstream;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.BpCustEntUpstreamCustom;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class BpCustEntUpanddownstreamDaoImpl extends BaseDaoImpl<BpCustEntUpanddownstream> implements BpCustEntUpanddownstreamDao{

	public BpCustEntUpanddownstreamDaoImpl() {
		super(BpCustEntUpanddownstream.class);
	}

	@Override
	public List<BpCustEntUpanddownstream> getByentpriseid(Integer enterpriseId) {
		String hql="from BpCustEntUpanddownstream as e where e.enterprise.id=?";
		return getSession().createQuery(hql).setParameter(0, enterpriseId).list();
	}

	@Override
	public List<BpCustEntUpstreamCustom> getByupAndDownCustomId(
			Integer upAndDownCustomId) {
		String hql="from BpCustEntUpstreamCustom as e where e.bpCustEntUpanddownstream.upAndDownCustomId=?";
		return getSession().createQuery(hql).setParameter(0, upAndDownCustomId).list();
	}

	@Override
	public List<BpCustEntDownstreamCustom> getByupAndDownCustomId1(
			Integer upAndDownCustomId) {
		String hql="from BpCustEntDownstreamCustom as e where e.bpCustEntUpanddownstream.upAndDownCustomId=?";
		return getSession().createQuery(hql).setParameter(0, upAndDownCustomId).list();
	}

	@Override
	public BpCustEntUpanddownstream getByeid(Integer upAndDownCustomId) {
		String hql="from BpCustEntUpanddownstream as e where e.upAndDownCustomId=?";
		return (BpCustEntUpanddownstream) getSession().createQuery(hql).setParameter(0, upAndDownCustomId).uniqueResult();
	}

	@Override
	public BpCustEntDownstreamCustom getByedownid(Integer downCustomId) {
		String hql="from BpCustEntDownstreamCustom as e where e.downCustomId=?";
		return (BpCustEntDownstreamCustom) getSession().createQuery(hql).setParameter(0, downCustomId).uniqueResult();
	}

	@Override
	public BpCustEntUpstreamCustom getByeupid(Integer upCustomId) {
		String hql="from BpCustEntUpstreamCustom as e where e.upCustomId=?";
		return (BpCustEntUpstreamCustom) getSession().createQuery(hql).setParameter(0, upCustomId).uniqueResult();
	}

}