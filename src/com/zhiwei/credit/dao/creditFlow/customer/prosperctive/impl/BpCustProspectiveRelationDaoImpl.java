package com.zhiwei.credit.dao.creditFlow.customer.prosperctive.impl;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.creditFlow.customer.prosperctive.BpCustProspectiveRelationDao;
import com.zhiwei.credit.model.creditFlow.customer.prosperctive.BpCustProspectiveRelation;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class BpCustProspectiveRelationDaoImpl extends BaseDaoImpl<BpCustProspectiveRelation> implements BpCustProspectiveRelationDao{

	public BpCustProspectiveRelationDaoImpl() {
		super(BpCustProspectiveRelation.class);
	}

	@Override
	public List<BpCustProspectiveRelation> listByPerId(String perId) {
		// TODO Auto-generated method stub
		if(perId!=null && !"".equals(perId)&& !"null".equals(perId) && !"undefined".equals(perId)){
			String hql ="from BpCustProspectiveRelation as p where p.bpCustProsperctive.perId =?  order by p.relateId  asc";
			return this.getSession().createQuery(hql).setParameter(0, Long.valueOf(perId)).list();
		}
		return null;
	}

}