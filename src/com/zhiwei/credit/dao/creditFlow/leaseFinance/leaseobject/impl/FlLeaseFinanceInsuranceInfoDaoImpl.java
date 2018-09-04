package com.zhiwei.credit.dao.creditFlow.leaseFinance.leaseobject.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import org.hibernate.Query;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.creditFlow.leaseFinance.leaseobject.FlLeaseFinanceInsuranceInfoDao;
import com.zhiwei.credit.model.creditFlow.leaseFinance.leaseobject.FlLeaseFinanceInsuranceInfo;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class FlLeaseFinanceInsuranceInfoDaoImpl extends BaseDaoImpl<FlLeaseFinanceInsuranceInfo> implements FlLeaseFinanceInsuranceInfoDao{

	public FlLeaseFinanceInsuranceInfoDaoImpl() {
		super(FlLeaseFinanceInsuranceInfo.class);
	}
	@Override
	public List<FlLeaseFinanceInsuranceInfo> getListByLeaseObjectId(Long leaseObjectId) {
		String hql="from FlLeaseFinanceInsuranceInfo as p where p.leaseObjectId=?"; 
		return getSession().createQuery(hql).setParameter(0, leaseObjectId).list();
	}
	
	public void deleteByLeaseObjectId(Long leaseObjectId){
		String hql="delete from FlLeaseFinanceInsuranceInfo r where r.leaseObjectId=?"; 
		Query query = getSession().createQuery(hql).setLong(0, leaseObjectId);
		query.executeUpdate();
	}

}
