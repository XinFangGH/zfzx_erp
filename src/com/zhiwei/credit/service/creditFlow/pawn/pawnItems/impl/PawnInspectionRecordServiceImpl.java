package com.zhiwei.credit.service.creditFlow.pawn.pawnItems.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.creditFlow.pawn.pawnItems.PawnInspectionRecordDao;
import com.zhiwei.credit.model.creditFlow.pawn.pawnItems.PawnInspectionRecord;
import com.zhiwei.credit.service.creditFlow.pawn.pawnItems.PawnInspectionRecordService;

/**
 * 
 * @author 
 *
 */
public class PawnInspectionRecordServiceImpl extends BaseServiceImpl<PawnInspectionRecord> implements PawnInspectionRecordService{
	@SuppressWarnings("unused")
	private PawnInspectionRecordDao dao;
	
	public PawnInspectionRecordServiceImpl(PawnInspectionRecordDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public List<PawnInspectionRecord> getListByPawnItemId(Long projectId,
			String businessType, Long pawnItemId) {
		
		return dao.getListByPawnItemId(projectId, businessType, pawnItemId);
	}

}