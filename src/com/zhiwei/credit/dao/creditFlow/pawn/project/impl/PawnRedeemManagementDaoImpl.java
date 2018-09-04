package com.zhiwei.credit.dao.creditFlow.pawn.project.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.creditFlow.pawn.project.PawnRedeemManagementDao;
import com.zhiwei.credit.model.creditFlow.pawn.project.PawnRedeemManagement;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class PawnRedeemManagementDaoImpl extends BaseDaoImpl<PawnRedeemManagement> implements PawnRedeemManagementDao{

	public PawnRedeemManagementDaoImpl() {
		super(PawnRedeemManagement.class);
	}

	@Override
	public List<PawnRedeemManagement> getListByProjectId(Long projectId,
			String businessType) {
		String hql="from PawnRedeemManagement as p where p.projectId=? and p.businessType=?";
		return getSession().createQuery(hql).setParameter(0, projectId).setParameter(1, businessType).list();
	}

}