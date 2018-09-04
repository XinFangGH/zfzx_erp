package com.zhiwei.credit.dao.creditFlow.pawn.project.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.creditFlow.pawn.project.PawnContinuedManagmentDao;
import com.zhiwei.credit.model.creditFlow.pawn.project.PawnContinuedManagment;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class PawnContinuedManagmentDaoImpl extends BaseDaoImpl<PawnContinuedManagment> implements PawnContinuedManagmentDao{

	public PawnContinuedManagmentDaoImpl() {
		super(PawnContinuedManagment.class);
	}

	@Override
	public List<PawnContinuedManagment> getListByProjectId(Long projectId,
			String businessType, Long continueId) {
		String hql="from PawnContinuedManagment as p where p.projectId=? and p.businessType=?";
		if(null!=continueId){
			hql=hql+" and p.continueId!="+continueId;
		}
		return getSession().createQuery(hql).setParameter(0, projectId).setParameter(1, businessType).list();
	}

}