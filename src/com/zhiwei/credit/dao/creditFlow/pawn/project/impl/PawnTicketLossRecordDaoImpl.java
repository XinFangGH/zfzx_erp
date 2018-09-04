package com.zhiwei.credit.dao.creditFlow.pawn.project.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.creditFlow.pawn.project.PawnTicketLossRecordDao;
import com.zhiwei.credit.model.creditFlow.pawn.project.PawnTicketLossRecord;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class PawnTicketLossRecordDaoImpl extends BaseDaoImpl<PawnTicketLossRecord> implements PawnTicketLossRecordDao{

	public PawnTicketLossRecordDaoImpl() {
		super(PawnTicketLossRecord.class);
	}

	@Override
	public List<PawnTicketLossRecord> getListByProjectId(Long projectId,
			String businessType) {
		String hql="from PawnTicketLossRecord as p where p.projectId=? and p.businessType=?";
		return getSession().createQuery(hql).setParameter(0, projectId).setParameter(1, businessType).list();
	}

}