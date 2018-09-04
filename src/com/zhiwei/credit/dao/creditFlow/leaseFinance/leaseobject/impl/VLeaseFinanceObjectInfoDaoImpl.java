package com.zhiwei.credit.dao.creditFlow.leaseFinance.leaseobject.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.creditFlow.leaseFinance.leaseobject.VLeaseFinanceObjectInfoDao;
import com.zhiwei.credit.model.creditFlow.leaseFinance.leaseobject.VLeaseFinanceObjectInfo;
import com.zhiwei.credit.model.creditFlow.pawn.pawnItems.PawnItemsList;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class VLeaseFinanceObjectInfoDaoImpl extends BaseDaoImpl<VLeaseFinanceObjectInfo> implements VLeaseFinanceObjectInfoDao{

	public VLeaseFinanceObjectInfoDaoImpl() {
		super(VLeaseFinanceObjectInfo.class);
	}
	@Override
	public List<VLeaseFinanceObjectInfo> getListByProjectId(Long projectId) {
		String hql="from VLeaseFinanceObjectInfo as p where p.projectId=?";
		return getSession().createQuery(hql).setParameter(0, projectId).list();
	}
}