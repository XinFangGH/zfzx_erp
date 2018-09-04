package com.zhiwei.credit.dao.creditFlow.leaseFinance.leaseobject.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import org.hibernate.Query;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.creditFlow.leaseFinance.leaseobject.FlLeaseObjectManagePlaceDao;
import com.zhiwei.credit.model.creditFlow.leaseFinance.leaseobject.FlLeaseFinanceInsuranceInfo;
import com.zhiwei.credit.model.creditFlow.leaseFinance.leaseobject.FlLeaseObjectManagePlace;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class FlLeaseObjectManagePlaceDaoImpl extends BaseDaoImpl<FlLeaseObjectManagePlace> implements FlLeaseObjectManagePlaceDao{

	public FlLeaseObjectManagePlaceDaoImpl() {
		super(FlLeaseObjectManagePlace.class);
	}

	@Override
	public List<FlLeaseObjectManagePlace> getListByLeaseObjectId(Long leaseObjectId) {
		String hql="from FlLeaseObjectManagePlace as p where p.leaseObjectId=?"; 
		return getSession().createQuery(hql).setParameter(0, leaseObjectId).list();
	}
	
	public void deleteByLeaseObjectId(Long leaseObjectId){
		String hql="delete from FlLeaseObjectManagePlace r where r.leaseObjectId=?"; 
		Query query = getSession().createQuery(hql).setLong(0, leaseObjectId);
		query.executeUpdate();
	}
}