package com.zhiwei.credit.dao.creditFlow.pawn.pawnItems.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.creditFlow.pawn.pawnItems.PawnInspectionRecordDao;
import com.zhiwei.credit.model.creditFlow.pawn.pawnItems.PawnCrkRecord;
import com.zhiwei.credit.model.creditFlow.pawn.pawnItems.PawnInspectionRecord;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class PawnInspectionRecordDaoImpl extends BaseDaoImpl<PawnInspectionRecord> implements PawnInspectionRecordDao{

	public PawnInspectionRecordDaoImpl() {
		super(PawnInspectionRecord.class);
	}

	@Override
	public List<PawnInspectionRecord> getListByPawnItemId(Long projectId,
			String businessType, Long pawnItemId) {
		String hql="from PawnInspectionRecord as p where p.projectId=? and p.businessType=? and p.pawnItemId=?";
		return getSession().createQuery(hql).setParameter(0, projectId).setParameter(1, businessType).setParameter(2, pawnItemId).list();
	}

}